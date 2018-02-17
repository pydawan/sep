package br.gov.go.agr.secretariageral.sep.logging;

import static br.org.verify.Verify.isNotEmptyOrNull;
import static br.org.verify.Verify.isNotNull;
import static br.org.verify.Verify.notContainsNull;

import java.util.List;

import br.gov.go.agr.secretariageral.sep.model.Processo;
import br.gov.go.agr.secretariageral.sep.model.Relator;
import br.gov.go.agr.secretariageral.sep.model.Usuario;
import br.gov.go.agr.secretariageral.sep.util.AnsiEscape;

/**
 * Registra os eventos do sistema, geralmente na saída padrão.
 * Alguns registro já são pré-definidos por categorias.
 * 
 * @author thiago
 * @version v1.0.0 26/01/2017
 * @since v1.0.0
 */
public class Logger {
   
   private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Logger.class.getName());
   
   public static void print(Object msg) {
      System.out.println(msg);
   }
   
   public static void print(String formatador, Object... argumentos) {
      System.out.printf(formatador, argumentos);
   }
   
   public static void print(String text) {
      print(AnsiEscape.render(text).reset());
   }
   
   public static void render(String formatador, Object... argumentos) {
      print(AnsiEscape.render(formatador, argumentos).reset());
   }
   
   public static void config(String msg) {
      logger.config(msg);
   }
   
   public static void config(String formatador, Object... argumentos) {
      logger.config(String.format(formatador, argumentos));
   }
   
   public static void info(String msg) {
      logger.info(msg);
   }
   
   public static void info(String formatador, Object... argumentos) {
      logger.info(String.format(formatador, argumentos));
   }
   
   public static void warning(String msg) {
      logger.warning(msg);
   }
   
   public static void warning(String formatador, Object... argumentos) {
      logger.warning(String.format(formatador, argumentos));
   }
   
   public static void error(String msg) {
      logger.severe(msg);
   }
   
   public static void error(String formatador, Object... argumentos) {
      logger.severe(String.format(formatador, argumentos));
   }
   
   public static final String MENSAGEM(String formatador, Object... argumentos) {
      if (isNotEmptyOrNull(formatador, argumentos)) {
         return String.format(formatador, argumentos);
      }
      return "";
   }
   
   public static final class Login {
      
      public static final String MSG_SUCESSO_LOGIN = "[LOGIN] => SUCESSO NO PROCESSO DE LOGIN DO USUÁRIO: %s";
      public static final String MSG_FALHA_LOGIN = "[LOGIN] => FALHA NO PROCESSO DE LOGIN DO USUÁRIO: %s";
      
      public static final void SUCESSO(Object o) {
         Logger.info(MENSAGEM(MSG_SUCESSO_LOGIN, o));
      }
      
      public static final void FALHA(Object o) {
         Logger.warning(MENSAGEM(MSG_FALHA_LOGIN, o));
      }
   }
   
   public static final class Logout {
      
      public static final String MSG_SUCESSO_LOGOUT = "[LOGOUT] => SUCESSO NO PROCESSO DE LOGOUT DO USUÁRIO: %s";
      public static final String MSG_FALHA_LOGOUT = "[LOGOUT] => FALHA NO PROCESSO DE LOGOUT DO USUÁRIO: %s";
      
      public static final void SUCESSO(Object o) {
         Logger.info(MENSAGEM(MSG_SUCESSO_LOGOUT, o));
      }
      
      public static final void FALHA(Object o) {
         Logger.warning(MENSAGEM(MSG_FALHA_LOGOUT, o));
      }
   }
   
   public static final class ActiveDirectory {
      
      public static final String MSG_USUARIO_ENCONTRADO = "[ACTIVE DIRECTORY] => ENCONTRADO REGISTRO DO USUÁRIO: %s";
      public static final String MSG_USUARIO_NAO_CADASTRADO = "[ACTIVE DIRECTORY] => NÃO FOI ENCONTRADO REGISTRO DO USUÁRIO: %s";
      public static final String MSG_USUARIOS_NULOS = "[ACTIVE DIRECTORY] => OS USUÁRIOS REMOTO (Active Directory) E " +
            "LOCAL (Banco de Dados) NÃO PODEM SER NULOS!";
      
      public static final void USUARIO_ENCONTRADO(Object o) {
         Logger.info(MENSAGEM(MSG_USUARIO_ENCONTRADO, o));
      }
      
      public static final void USUARIO_NAO_CADASTRADO(Object o) {
         Logger.warning(MENSAGEM(MSG_USUARIO_NAO_CADASTRADO, o));
      }
      
   }
   
   public static final class Criptografia {
      
      public static final String MSG_SENHA_GERADA = "[CRIPTOGRAFIA] => SENHA GERADA PARA O USUÁRIO: %s";
      
      public static final void SENHA_GERADA(Object o) {
         if (o instanceof String) {
            Logger.info(MENSAGEM(MSG_SENHA_GERADA, o));
         }
         if (o instanceof Usuario) {
            if (isNotNull(o)) {
               Logger.info(MENSAGEM(MSG_SENHA_GERADA, ((Usuario) o).getUsername()));
            }
         }
      }
      
   }
   
   public static final class Autenticacao {
      
      public static final String MSG_AUTENTICACAO_SUCESSO = "[AUTENTICAÇÃO] => SUCESSO NO PROCESSO DE AUTENTICAÇÃO DO USUÁRIO: %s";
      public static final String MSG_AUTENTICACAO_FALHA = "[AUTENTICAÇÃO] => FALHA NO PROCESSO DE AUTENTICAÇÃO DO USUÁRIO: %s";
      public static final String MSG_ACESSO_NEGADO = "[AUTENTICAÇÃO] => FALHA AO PROVER ACESSO AO SISTEMA PARA O USUÁRIO: %s";
      public static final String MSG = "[AUTENTICAÇÃO] => %s";
      
      public static final void SUCESSO(Object o) {
         Logger.info(MENSAGEM(MSG_AUTENTICACAO_SUCESSO, o));
      }
      
      public static final void FALHA(Object o) {
         Logger.warning(MENSAGEM(MSG_AUTENTICACAO_FALHA, o));
      }
      
      public static final void ACESSO_NEGADO(Object o) {
         Logger.warning(MENSAGEM(MSG_ACESSO_NEGADO, o));
      }
      
      public static final void config(Object o) {
         Logger.config(MENSAGEM(MSG, o));
      }
      
      public static final void info(Object o) {
         Logger.info(MENSAGEM(MSG, o));
      }
      
      public static final void warning(Object o) {
         Logger.warning(MENSAGEM(MSG, o));
      }
      
      public static final void render(Object o) {
         Logger.render(MENSAGEM(MSG, o));
      }
      
   }
   
   public static final class Autorizacao {
      
   }
   
   public static final class Sorteio {
      
      public static final String CABECALHO = "" +
            "#########################################################################################################################\n" +
            "#                                           @|red SEP - SORTEIO ELETRÔNICO DE PROCESSOS|@                                       #\n" +
            "#########################################################################################################################\n";
      public static final String LINHA_SORTEIO = "===================================================================================";
      public static final String LINHA_NOVA_RODADA = "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++";
      public static final String MSG_RESULTADO_SORTEIO = "@|red [SEP]|@ => @|blue |RESULTADO DO SORTEIO||@ nº %s";
      public static final String MSG_RODADA_DO_SORTEIO = "\n@|red [SEP]|@ => @|blue |RODADA||@ nº %s";
      public static final String MSG_PROCESSO_E_RELATOR_SORTEADOS = "\n@|blue [SEP]|@ => @|blue |SORTEADOS||@: [processo: %s, relator: %s]\n";
      public static final String MSG_TOTAL_DE_PROCESSOS = "@|red [SEP]|@ => @|blue |TOTAL DE PROCESSOS|@| A SEREM SORTEADOS: %s\n\n";
      public static final String MSG_PROCESSOS_A_SEREM_SORTEADOS = "@|red [SEP]|@ => @|blue |PROCESSOS||@ A SEREM SORTEADOS: %s\n\n";
      public static final String MSG_TOTAL_DE_RELATORES = "@|red [SEP]|@ => @|blue |TOTAL DE RELATORES||@ PARTICIPANTES DO SORTEIO: %s\n\n";
      public static final String MSG_RELATORES_PARTICIPANTES_DO_SORTEIO = "@|red [SEP]|@ => @|blue |RELATORES||@ PARTICIPANTES DO SORTEIO: %s\n";
      public static final String MSG_NOVA_RODADA = "\n@|red [SEP]|@ => INICIANDO @|blue |NOVA RODADA||@ DE SORTEIOS\n\n";
      public static final String MSG_PROCESSOS_APOS_SORTEIO = "@|red [SEP]|@ => @|blue |PROCESSOS RESTANTES||@: %s\n";
      public static final String MSG_RELATORES_APOS_SORTEIO = "@|red [SEP]|@ => @|blue |RELATORES RESTANTES||@: %s\n";
      public static final String MSG_DADOS_ANTERIORES_AOS_SORTEIOS = "@|red [SEP]|@ => @|blue |DADOS ANTERIORES AOS SORTEIOS||@";
      public static final String MSG_TOTAL_PROCESSOS_APOS_SORTEIO = "@|red [SEP]|@ => @|blue |TOTAL DE PROCESSOS RESTANTES||@ %s\n";
      public static final String MSG_TOTAL_RELATORES_APOS_SORTEIO = "@|red [SEP]|@ => @|blue |TOTAL DE RELATORES RESTANTES||@ %s\n";
      
      public static void CABECALHO() {
         Logger.render(CABECALHO);
      }
      
      public static void DADOS_INICIAIS() {
         Logger.render(MSG_DADOS_ANTERIORES_AOS_SORTEIOS);
      }
      
      public static void DADOS_INICIAIS_PROCESSOS(List<Processo> processos) {
         if (isNotNull(processos)) {
            Logger.render(MSG_TOTAL_DE_PROCESSOS, processos.size());
            Logger.render(MSG_PROCESSOS_A_SEREM_SORTEADOS, processos);
         }
      }
      
      public static void DADOS_INICIAIS_RELATORES(List<Relator> relatores) {
         if (isNotNull(relatores)) {
            Logger.render(MSG_TOTAL_DE_RELATORES, relatores.size());
            Logger.render(MSG_RELATORES_PARTICIPANTES_DO_SORTEIO, relatores);
         }
      }
      
      public static void NOVA_RODADA(List<Processo> processos, List<Relator> relatores) {
         if (notContainsNull(processos, relatores)) {
            Logger.print(LINHA_NOVA_RODADA);
            Logger.render(MSG_NOVA_RODADA);
            Logger.Sorteio.DADOS_INICIAIS_PROCESSOS(processos);
            Logger.Sorteio.DADOS_INICIAIS_RELATORES(relatores);
            Logger.print(LINHA_NOVA_RODADA);
         }
      }
      
      public static void RESULTADO_DO_SORTEIO(Integer numeroDoSorteio) {
         if (isNotNull(numeroDoSorteio)) {
            Logger.render(MSG_RESULTADO_SORTEIO, numeroDoSorteio);
         }
      }
      
      public static void RODADA_DO_SORTEIO(Integer numeroDaRodada) {
         if (isNotNull(numeroDaRodada)) {
            Logger.render(MSG_RODADA_DO_SORTEIO, numeroDaRodada + 1);
         }
      }
      
      public static void PROCESSO_E_RELATOR_SORTEADOS(Processo processo, Relator relator) {
         if (notContainsNull(processo, relator)) {
            Logger.render(MSG_PROCESSO_E_RELATOR_SORTEADOS, processo.getNumero(), relator.getNome());
         }
      }
      
      public static void PROCESSOS_E_RELATORES_RESTANTES(List<Processo> processos, List<Relator> juizes) {
         if (isNotNull(processos)) {
            Logger.render(MSG_TOTAL_PROCESSOS_APOS_SORTEIO, processos.size());
            Logger.render(MSG_PROCESSOS_APOS_SORTEIO, processos);
         }
         if (isNotNull(juizes)) {
            Logger.render(MSG_TOTAL_RELATORES_APOS_SORTEIO, juizes.size());
            Logger.render(MSG_RELATORES_APOS_SORTEIO, juizes);
         }
      }
   }
   
}
