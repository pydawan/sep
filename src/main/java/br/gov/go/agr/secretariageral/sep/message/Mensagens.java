package br.gov.go.agr.secretariageral.sep.message;

import static br.org.verify.Verify.isNotEmptyOrNull;
import static br.org.verify.Verify.isNotNull;

import java.util.List;

import br.gov.go.agr.secretariageral.sep.model.Usuario;

/**
 * Define por assunto as mensagens de sistema que serão
 * exibidas para o usuário.
 * 
 * @author thiago
 * @version v1.0.0 25/01/2017
 * @since v1.0.0
 */
public final class Mensagens {
   
   public static final String MENSAGEM(String formatador, Object... argumentos) {
      if (isNotEmptyOrNull(formatador, argumentos)) {
         return String.format(formatador, argumentos);
      }
      return "";
   }
   
   public static final class Login {
      
      public static final String MSG_SUCESSO_LOGIN = "Sucesso no processo de login do usuário: %s";
      public static final String MSG_FALHA_LOGIN = "Falha no processo de login do usuário: %s";
      
      public static final String SUCESSO(String username) {
         return MENSAGEM(MSG_SUCESSO_LOGIN, username);
      }
      
      public static final String FALHA(String username) {
         return MENSAGEM(MSG_FALHA_LOGIN, username);
      }
   }
   
   public static final class Logout {
      
      public static final String MSG_SUCESSO_LOGOUT = "Sucesso no processo de logout do usuário: %s";
      public static final String MSG_FALHA_LOGOUT = "Falha no processo de logout do usuário: %s";
      
      public static final String SUCESSO(String username) {
         return MENSAGEM(MSG_SUCESSO_LOGOUT, username);
      }
      
      public static final String FALHA(String username) {
         return MENSAGEM(MSG_FALHA_LOGOUT, username);
      }
   }
   
   public static final class ActiveDirectory {
      
      public static final String MSG_USUARIO_ENCONTRADO = "Encontrado registro do usuário: %s";
      public static final String MSG_USUARIO_NAO_CADASTRADO = "Não foi encontrado registro do usuário: %s";
      public static final String MSG_USUARIOS_NULOS = "Os usuários remoto (Active Directory) e local (Banco de Dados) não podem ser nulos!";
      
      public static final String USUARIO_ENCONTRADO(String username) {
         return MENSAGEM(MSG_USUARIO_ENCONTRADO, username);
      }
      
      public static final String USUARIO_NAO_CADASTRADO(String username) {
         return MENSAGEM(MSG_USUARIO_NAO_CADASTRADO, username);
      }
      
   }
   
   public static final class Criptografia {
      
      public static final String MSG_SENHA_GERADA = "Senha gerada para o usuário: %s";
      
      public static final String SENHA_GERADA(String username) {
         return MENSAGEM(MSG_SENHA_GERADA, username);
      }
      
      public static final String SENHA_GERADA(Usuario usuario) {
         if (isNotNull(usuario)) {
            return MENSAGEM(MSG_SENHA_GERADA, usuario.getUsername());
         }
         return "";
      }
   }
   
   public static final class Autenticacao {
      
      public static final String MSG_AUTENTICACAO_SUCESSO = "Sucesso no processo de autenticação do usuário: %s";
      public static final String MSG_AUTENTICACAO_FALHA = "Falha no processo de autenticação do usuário: %s";
      public static final String MSG_ACESSO_NEGADO = "Acesso ao sistema negado para o usuário: %s!";
      
      public static final String SUCESSO(String username) {
         return MENSAGEM(MSG_AUTENTICACAO_SUCESSO, username);
      }
      
      public static final String FALHA(String username) {
         return MENSAGEM(MSG_AUTENTICACAO_FALHA, username);
      }
      
      public static final String ACESSO_NEGADO(String username) {
         return MENSAGEM(MSG_ACESSO_NEGADO, username);
      }
      
   }
   
   public static final class Autorizacao {
      
   }
   
   public static final class Sorteador {
      
      public static final String MSG_RESULTADO_DO_SORTEIO = "Resultado do sorteio %s - rodada nº %s = [processo: %s, juiz: %s]";
      public static final String MSG_TOTAL_DE_PROCESSOS = "Total de processos a serem sorteados: %s";
      public static final String MSG_PROCESSOS_A_SEREM_SORTEADOS = "Processos a serem sorteados: %s";
      public static final String MSG_TOTAL_DE_JUIZES = "Total de juízes participantes do sorteio: %s";
      public static final String MSG_JUIZES_PARTICIPANTES_DO_SORTEIO = "Juízes participantes do sorteio: %s";
      
      public static final String TOTAL_DE_PROCESSOS(List<String> processos) {
         if (isNotNull(processos)) {
            return MENSAGEM(MSG_TOTAL_DE_PROCESSOS, processos.size());
         }
         return "";
      }
      
      public static final String PROCESSOS_A_SORTEAR(List<String> processos) {
         if (isNotNull(processos)) {
            return MENSAGEM(MSG_PROCESSOS_A_SEREM_SORTEADOS, processos);
         }
         return "";
      }
      
      public static final String TOTAL_DE_JUIZES(List<String> juizes) {
         if (isNotNull(juizes)) {
            return MENSAGEM(MSG_TOTAL_DE_JUIZES, juizes.size());
         }
         return "";
      }
      
      public static final String JUIZES_PARTICIPANTES(List<String> juizes) {
         if (isNotNull(juizes)) {
            return MENSAGEM(MSG_JUIZES_PARTICIPANTES_DO_SORTEIO, juizes);
         }
         return "";
      }
      
   }
   
}
