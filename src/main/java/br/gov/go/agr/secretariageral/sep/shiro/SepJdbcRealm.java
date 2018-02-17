package br.gov.go.agr.secretariageral.sep.shiro;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;

import br.gov.go.agr.secretariageral.sep.logging.Logger;
import br.gov.go.agr.secretariageral.sep.model.Usuario;

/**
 * Representa os metadados de autorização e autenticação
 * de um usuário do sistema SEP.
 * Esses dados são provenientes de uma fonte de dados JDBC
 * e são disponibilizadas no contexto de segurança do Apache Shiro.
 * 
 * @author thiago
 * @version v1.0.0 23/01/2017
 * @since v1.0.0
 */
public class SepJdbcRealm extends JdbcRealm {
   
   /**
    * Realiza o processo de autenticação do usuário através de uma consulta no banco de dados e
    * retorna um objeto contendo os dados do processo de autenticação.
    */
   @Override
   protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
      UsernamePasswordToken userPassToken = (UsernamePasswordToken) token;
      final String username = userPassToken.getUsername();
      if (username == null) {
         Logger.Autenticacao.warning("FALHA NA AUTENTICAÇÃO DO USUÁRIO: Username é nulo!");
         return null;
      }
      final Usuario usuario = Usuario.objects.get("username", username);
      if (usuario == null) {
         Logger.Autenticacao.warning("FALHA NÃO FOI ENCONTRADO USUÁRIO CADASTRADO COM username IGUAL A [" + username + "]!");
         return null;
      }
      SaltedAuthenticationInfo info = new SepSaltedAuthentificationInfo(username, usuario.getPassword(), usuario.getSalt());
      return info;
   }
   
   /**
    * Realiza o processo de autorização do usuário buscando no banco de dados os grupos aos quais o usuário pertence e as permissões
    * associadas a cada grupo.
    * Retorna um objeto contendo os dados do processo de autorização.
    */
   @Override
   protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
      if (principals == null) {
         throw new AuthorizationException("FALHA: O ARGUMENTO PrincipalCollection NÃO PODE SER NULO!.");
      }
      String username = (String) getAvailablePrincipal(principals);
      final Usuario usuario = Usuario.objects.get("username", username);
      Set<String> roleNames = new HashSet<>();
      Set<String> permissions = new HashSet<>();
      roleNames.add(usuario.getGrupo().getNome());
      if (permissionsLookupEnabled) {
         usuario.getGrupo().getPermissoes().forEach(permissao -> {
            permissions.add(permissao.getValor());
         });
      }
      SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
      info.setStringPermissions(permissions);
      return info;
   }
   
}
