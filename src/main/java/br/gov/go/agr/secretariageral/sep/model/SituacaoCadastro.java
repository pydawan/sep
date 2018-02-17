package br.gov.go.agr.secretariageral.sep.model;

/**
 * Representa as possíveis situações cadastrais
 * de um registro no sistema.
 * 
 * @author thiago
 * @version v1.0.0 18/01/2017
 * @since v1.0.0
 */
public enum SituacaoCadastro {
   
   ATIVO("Ativo", "ATIVO"), 
   INATIVO("Inativo", "INATIVO");
   
   private final String nome;
   private final String valor;
   
   private SituacaoCadastro(final String nome, final String valor) {
      this.nome = nome;
      this.valor = valor;
   }
   
   public String getNome() {
      return nome;
   }
   
   public String getValor() {
      return valor;
   }
   
   public String nome() {
      return getNome();
   }
   
   public String valor() {
      return getValor();
   }
   
   @Override
   public String toString() {
      return valor;
   }
   
}
