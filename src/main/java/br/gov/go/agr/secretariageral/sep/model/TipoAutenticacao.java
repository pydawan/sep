package br.gov.go.agr.secretariageral.sep.model;

/**
 * @author thiago
 * @version v1.0.0
 * @since v1.0.0
 */
public enum TipoAutenticacao {
   
   BANCO_DE_DADOS("Banco de Dados"),
   ACTIVE_DIRECTORY("Active Directory");
   private final String nome;
   
   private TipoAutenticacao(final String nome) {
      this.nome = nome;
   }
   
   public String getNome() {
      return nome;
   }
   
}
