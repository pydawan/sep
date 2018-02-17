package br.gov.go.agr.secretariageral.sep.model;

/**
 * Define as ações padrão dos formulários do sistema.
 * 
 * @author thiago
 * @version v1.0.0 01/02/2017
 * @since v1.0.0
 */
public enum AcaoFormulario {
   
   ADICIONAR("Adicionar"),
   EDITAR("Editar"),
   REMOVER("Remover"),
   LISTAR("Listar"),
   BUSCAR("Buscar"),
   CONSULTAR("Consultar"),
   PESQUISAR("Pesquisar"),
   LIMPAR("Limpar"),
   SALVAR("Salvar"),
   FECHAR("Fechar"),
   CANCELAR("Cancelar"),
   ENTRAR("Entrar"),
   INDETERMINADA("Indeterminada"),
   NOVO("Novo"),
   ATIVAR("Ativar"),
   INATIVAR("Inativar"),
   EXPORTAR("Exportar"),
   IMPRIMIR("Imprimir");
   
   private final String valor;
   
   private AcaoFormulario(final String valor) {
      this.valor = valor;
   }
   
   public String getValor() {
      return valor;
   }
   
   @Override
   public String toString() {
      return valor;
   }
   
}
