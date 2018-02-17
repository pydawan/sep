package br.gov.go.agr.secretariageral.sep.searching;

/**
 * Define os operadores a serem utilizados pelo mecanismo de pesquisa.
 * 
 * @author thiago
 * @version v1.0.0 03/02/2017
 * @since v1.0.0
 */
public enum CriterioPesquisa {
   
   NENHUM("com qual critério?", ""),
   IGUAL_A("igual a", "%s__exact='%s'", "igual a"),
   DIFERENTE_DE("diferente de", "%s__!exact='%s'", "diferente de"),
   MAIOR_QUE("maior que", "%s__gt='%s'", "maior que"),
   MENOR_QUE("menor que", "%s__lt='%s'", "menor que"),
   MAIOR_QUE_OU_IGUAL_A("maior que ou igual a", "%s__gte='%s'", "maior que ou igual a"),
   MENOR_QUE_OU_IGUAL_A("menor que ou igual a", "%s__lte='%s'", "menor que ou igual a"),
   CONTEM("contém", "%s__contains='%s'", "contendo"),
   NAO_CONTEM("não contém", "%s__!contains='%s'", "não contendo"),
   COMECA_COM("começa com", "%s__startswith='%s'", "começando com"),
   NAO_COMECA_COM("não começa com", "%s__!startswith='%s'", "não começando com"),
   TERMINA_COM("termina com", "%s__endswith='%s'", "terminando com"),
   NAO_TERMINA_COM("não termina com", "%s__!endswith='%s'", "não terminando com"),
   NO_INTERVALO("no intervalo", "%s__range=['%s', '%s']", "no intervalo"),
   FORA_DO_INTERVALO("fora do intervalo", "%s__!range=['%s', '%s']", "fora do intervalo"),
   CONTIDO("está contido", "%s__in=[%s]", "está contido"),
   NAO_CONTIDO("não está contido", "%s__!in=[%s]", "não está contido"),
   E("e", "AND", "e"),
   OU("ou", "OR", "ou");
 
   private final String label;
   private final String valor;
   private final String descricao;
   // tipo de dado ao qual o critério se aplica.
   
   private CriterioPesquisa(final String label, final String valor, final String descricao) {
      this.label = label;
      this.valor = valor;
      this.descricao = descricao;
   }
   
   private CriterioPesquisa(final String label, final String valor) {
      this.label = label;
      this.valor = valor;
      this.descricao = "";
   }
   
   public String getLabel() {
      return label;
   }
 
   public String getValor() {
      return valor;
   }
   
   public String getDescricao() {
      return descricao;
   }
   
}
