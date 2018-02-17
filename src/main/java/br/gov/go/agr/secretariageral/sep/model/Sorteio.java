package br.gov.go.agr.secretariageral.sep.model;

import jedi.db.models.CharField;
import jedi.db.models.ForeignKeyField;
import jedi.db.models.Manager;
import jedi.db.models.Model;
import jedi.db.models.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Representa um sorteio com o proposito de
 * distribuir um ou mais processos na
 * Câmara de Julgamento ou no Conselho Regulador da AGR.
 * 
 * @author thiago
 * @version v1.0.0 19/01/2017
 * @since v1.0.0
 */
@Getter
@Setter
@Accessors(fluent = false, chain = true)
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@ToString(includeFieldNames = true)
@Table(name = "sorteios")
public class Sorteio extends Model {
   
   private static final long serialVersionUID = 1L;
   
   @NonNull
   @ForeignKeyField
   private DistribuicaoProcesso distribuicao;
   
   @NonNull
   @ForeignKeyField
   private Processo processo;
   
   @NonNull
   @ForeignKeyField
   private Relator relator;
   
   @CharField(max_length = 255, unique = false, required = false)
   private String codigoValidacao;
   
   public static Manager objects = new Manager(Sorteio.class);
   
   public void setProcesso(Processo processo) {
      this.processo = processo;
      if (processo != null && relator != null) {
         processo.setRelator(relator);
      }
   }
   
   public void setRelator(Relator relator) {
      this.relator = relator;
      if (relator != null && distribuicao != null) {
         relator.setInstancia(distribuicao.getInstancia());
      }
   }
   
   public void ativar() {
      this.update("situacao='" + SituacaoCadastro.ATIVO + "'");
   }
   
   public void inativar() {
      this.update("situacao='" + SituacaoCadastro.INATIVO + "'");
   }
   
}
