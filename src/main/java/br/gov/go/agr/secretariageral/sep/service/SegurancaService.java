package br.gov.go.agr.secretariageral.sep.service;

import static br.org.verify.Verify.exists;
import static br.org.verify.Verify.isNotEmptyOrNull;
import static br.org.verify.Verify.isNotNull;

import java.io.IOException;

import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.message.BindRequest;
import org.apache.directory.api.ldap.model.message.BindRequestImpl;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;

import br.gov.go.agr.secretariageral.sep.logging.Logger;
import br.gov.go.agr.secretariageral.sep.message.Mensagens;
import br.gov.go.agr.secretariageral.sep.model.DistribuicaoProcesso;
import br.gov.go.agr.secretariageral.sep.model.Grupo;
import br.gov.go.agr.secretariageral.sep.model.SituacaoCadastro;
import br.gov.go.agr.secretariageral.sep.model.Sorteio;
import br.gov.go.agr.secretariageral.sep.model.Usuario;
import jedi.db.exceptions.DoesNotExistException;

/**
 * <p>
 * Provê serviços de segurança da aplicação, a saber:
 * Autenticação: verifica se o usuário é quem ele diz ser.
 * Autorização: verifica as permissões do usuário com relação a partes do
 * sistema.
 * Criptografia: geração de senhas.
 * ActiveDirectory: busca usuário no banco de dados remoto do AD
 * e sincroniza no banco de dados local.
 * </p>
 * 
 * @author thiago
 * @version v1.0.0 24/01/2017
 * @since v1.0.0
 */
public abstract class SegurancaService {
   
   /**
    * Expõe os serviços de segurança relacionado a criptografia.
    */
   public static final class Criptografia {
      
      private static final RandomNumberGenerator rng = new SecureRandomNumberGenerator();
      
      /**
       * Método responsável pela geração de salt usado na geração de senha
       * segura.
       * 
       * @return um salt que irá ajudar na composição de um hash
       */
      public static Object gerarSalt() {
         return rng.nextBytes();
      }
      
      /**
       * Método responsável pela geração de hash em base 64 a partir de um
       * texto.
       * 
       * @return um hash de um texto.
       */
      public static String gerarHash(String texto, String salt) {
         texto = texto == null ? "" : texto;
         salt = salt == null ? "" : salt;
         return new Sha256Hash(texto, salt, 1024).toBase64();
      }
      
      public static String gerarHash(String texto) {
         texto = texto == null ? "" : texto;
         return new Sha256Hash(texto, null, 1024).toBase64();
      }
      
      public static void gerarSenha(Usuario usuario, String texto) {
         if (isNotNull(usuario) && isNotEmptyOrNull(texto)) {
            // Definindo o novo estado dos dados do usuário.
            usuario.setSalt(gerarSalt().toString());
            usuario.setPassword(gerarHash(texto, usuario.getSalt()));
            Logger.Criptografia.SENHA_GERADA(usuario);
         }
      }
      
      public static void gerarSenha(Usuario usuario) {
         gerarSenha(usuario, usuario.getPassword());
      }
      
      public static String obterSalt(String username) {
         String salt = "";
         if (isNotEmptyOrNull(username)) {
            salt = Usuario.objects.<Usuario> get("username", username).getSalt();
         }
         return salt;
      }
      
   }
   
   /**
    * Expõe os serviços de segurança relacionados a autenticação.
    */
   public static final class Autenticador {
      
      public static Usuario autenticar(String username, String password) throws AuthenticationException {
         Usuario usuario = null;
         if (isNotEmptyOrNull(username, password)) {
            try {
               usuario = ActiveDirectory.sincronizar(username, password);
               if (exists(usuario) && 
                  (usuario.getGrupo().getNome().equals("GERENCIA_SECRETARIA_GERAL") ||
                   usuario.getGrupo().getNome().equals("COORDENACAO_INFORMATICA"))) {
                  Subject subject = SecurityUtils.getSubject();
                  UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                  subject.login(token);
                  usuario.setLoggedIn(true);
                  subject.getSession().setAttribute("usuario", Usuario.objects.get("username", username));
                  Logger.Autenticacao.SUCESSO(username);
               } else {
                  Logger.Autenticacao.FALHA(username);
                  throw new AuthenticationException(Mensagens.Autenticacao.FALHA(username));
               }
            } catch (LdapException e) {
               throw new AuthenticationException(e.getMessage());
            }
         }
         return usuario;
      }
      
      public static void autenticarNoBancoDeDados(String username, String password) throws AuthenticationException {
         if (isNotEmptyOrNull(username, password)) {
            try {
               Usuario usuario = Usuario.objects.get("username", username);
               Subject subject = SecurityUtils.getSubject();
               UsernamePasswordToken token = new UsernamePasswordToken(username, password);
               subject.login(token);
               subject.getSession().setAttribute("usuario", usuario);
               Logger.Autenticacao.SUCESSO(username);
            } catch (Exception e) {
               Logger.Autenticacao.SUCESSO(username);
               throw new AuthenticationException(Mensagens.Autenticacao.FALHA(username));
            }
         }
      }
      
   }
   
   /**
    * Expõe os serviços de segurança relacionados a autorização.
    */
   public static final class Autorizador {
      
   }
   
   public static final class ActiveDirectory {
      
      public static final String SERVIDOR = "10.243.1.5";
      public static final int PORTA = 389;
      
      public static Usuario buscarUsuario(String username, String password) {
         Usuario usuario = null;
         if (isNotEmptyOrNull(username) && isNotEmptyOrNull(password)) {
            try {
               LdapConnection connection = new LdapNetworkConnection(SERVIDOR, PORTA);
               connection.setTimeOut(10000);
               BindRequest request = new BindRequestImpl();
               request.setSimple(true);
               request.setName(String.format("%s@agr.go", username));
               request.setCredentials(password);
               connection.bind(request);
               String filtro = String.format("(&(objectClass=user)(sAMAccountName=%s))", username);
               EntryCursor cursor = connection.search("OU=agr,DC=agr,DC=go", filtro, SearchScope.SUBTREE, "*");
               while (cursor.next()) {
                  Entry entry = cursor.get();
                  usuario = new Usuario();
                  usuario.setUsername(entry.get("sAMAccountName").get().toString());
                  usuario.setPassword(password);
                  usuario.setNome(entry.get("cn").get().toString());
                  usuario.setEmail(String.format("%s.gov.br", entry.get("userPrincipalName").get().toString()));
                  usuario.setAdmin(false);
                  Logger.ActiveDirectory.USUARIO_ENCONTRADO(entry);
                  if (entry.get("memberOf").toString().contains("memberOf: CN=SUPI,OU=SUPI,OU=GEGPLAN,OU=AGR,DC=agr,DC=go")) {
                     // Usuário da Coordenação de Informática.
                     usuario.setGrupo(Grupo.objects.get("nome", "COORDENACAO_INFORMATICA"));
                     usuario.setAdmin(true);
                  } else if (entry.get("memberOf").toString().contains("memberOf: CN=GESG,OU=GESG,OU=AGR,DC=agr,DC=go")) {
                     // Usuário da Secretaria Geral.
                     usuario.setGrupo(Grupo.objects.get("nome", "GERENCIA_SECRETARIA_GERAL"));
                  } else {
                     // Define grupo de usuário genérico.
                     usuario.setGrupo(Grupo.objects.get("nome", "AGR"));
                  }
               }
               connection.unBind();
               connection.close();
            } catch (LdapException e) {
               e.printStackTrace();
            } catch (CursorException e) {
               e.printStackTrace();
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
         return usuario;
      }
      
      public static void sincronizar(Usuario usuario) throws IllegalArgumentException {
         if (isNotNull(usuario)) {
            Usuario usuarioAD = ActiveDirectory.buscarUsuario(usuario.getUsername(), usuario.getPassword());
            if (isNotNull(usuarioAD)) {
               usuario.setNome(usuarioAD.getNome());
               usuario.setEmail(usuarioAD.getEmail());
               usuario.setUsername(usuarioAD.getUsername());
               usuario.setPassword(usuarioAD.getPassword());
               SegurancaService.Criptografia.gerarSenha(usuarioAD);
               usuarioAD.setSituacao(SituacaoCadastro.ATIVO.getValor());
               Grupo grupo = Grupo.objects.get("nome", "AGR");
               usuarioAD.setGrupo(grupo);
               usuarioAD.save();
            }
         } else {
            throw new IllegalArgumentException(Mensagens.ActiveDirectory.MSG_USUARIOS_NULOS);
         }
      }
      
      public static Usuario sincronizar(String username, String password) throws LdapException {
         Usuario usuario = null;
         if (isNotEmptyOrNull(username) && isNotEmptyOrNull(password)) {
            Usuario usuarioBancoDeDados;
            Usuario usuarioActiveDirectory = ActiveDirectory.buscarUsuario(username, password);
            if (exists(usuarioActiveDirectory)) {
               try {
                  usuarioBancoDeDados = Usuario.objects.get("username", username);
               } catch (DoesNotExistException e) {
                  usuarioBancoDeDados = new Usuario();
               }
               usuarioBancoDeDados.setNome(usuarioActiveDirectory.getNome());
               usuarioBancoDeDados.setEmail(usuarioActiveDirectory.getEmail());
               usuarioBancoDeDados.setUsername(usuarioActiveDirectory.getUsername());
               usuarioBancoDeDados.setPassword(usuarioActiveDirectory.getPassword());
               usuarioBancoDeDados.setGrupo(usuarioActiveDirectory.getGrupo());
               usuarioBancoDeDados.setAdmin(usuarioActiveDirectory.isAdmin());
               SegurancaService.Criptografia.gerarSenha(usuarioBancoDeDados);
               usuarioBancoDeDados.setSituacao(SituacaoCadastro.ATIVO.getValor());
               usuarioBancoDeDados.save();
               usuario = usuarioBancoDeDados;
            } else {
               throw new LdapException(Mensagens.ActiveDirectory.USUARIO_NAO_CADASTRADO(username));
            }
         }
         return usuario;
      }
      
   }
   
   public static final class Validador {
      
      public static void gerarCodigoValidacao(DistribuicaoProcesso distribuicao) {
         if (distribuicao != null && distribuicao.persisted()) {
            distribuicao.setCodigoValidacao(
               distribuicao.getResponsavel() + distribuicao.getCidade() + distribuicao.getLocal() + 
               distribuicao.getData() + distribuicao.getInstancia()
            );
            distribuicao.setCodigoValidacao(Criptografia.gerarHash(distribuicao.getCodigoValidacao()));
            distribuicao.save();
         }
      }
      
      public static void gerarCodigoValidacao(Sorteio sorteio) {
         if (sorteio != null && sorteio.getDistribuicao() != null && sorteio.persisted()) {
            sorteio.setCodigoValidacao(
               sorteio.getDistribuicao().getCodigoValidacao() + sorteio.getProcesso().getNumero() + 
               sorteio.getProcesso().getInstancia() + sorteio.getProcesso().getInteressado() + sorteio.getProcesso().getRelator().getNome()
            );
            sorteio.setCodigoValidacao(Criptografia.gerarHash(sorteio.getCodigoValidacao()));
            sorteio.save();
         }
      }
      
      public static String gerarCodigoValidacao(String texto) {
         String codigoValidacao = "";
         if (isNotEmptyOrNull(texto)) {
            codigoValidacao = Criptografia.gerarHash(texto);
         }
         return codigoValidacao;
      }
      
   }
   
}
