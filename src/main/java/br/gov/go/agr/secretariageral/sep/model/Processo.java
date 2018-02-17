package br.gov.go.agr.secretariageral.sep.model;

import java.util.Date;

import br.org.jext.Dates;
import jedi.db.models.CharField;
import jedi.db.models.DateTimeField;
import jedi.db.models.ForeignKeyField;
import jedi.db.models.Manager;
import jedi.db.models.Model;
import jedi.db.models.QuerySet;
import jedi.db.models.Table;
import jedi.db.models.TextField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Representa um processo a ser julgado por um
 * conselheiro da Câmara de Julgamento de Processos
 * na AGR.
 * 
 * @author thiago
 * @version v1.0.0 19/01/2017
 * @since v1.0.0
 */
@Getter
@Setter
@Accessors(fluent = true, chain = true)
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "processos")
public class Processo extends Model {
   
   private static final long serialVersionUID = 1L;
   
   @NonNull
   @CharField(max_length = 50, unique = false)
   private String numero;
   
   @CharField(max_length = 20)
   private String instancia = InstanciaJuizo.CAMARA_DE_JULGAMENTO.getValor();
   
   @NonNull
   @CharField(max_length = 100)
   private String interessado;
   
   @ForeignKeyField(required = false)
   private Relator relator;
   
   @TextField(required = false)
   private String observacoes;
   
   @DateTimeField(auto_now_add = true)
   private Date dataAdicao;
   
   @DateTimeField(auto_now = true, required = false)
   private Date dataEdicao;
   
   @CharField(max_length = 7)
   private String situacao = SituacaoCadastro.ATIVO.getValor();
   
   public static Manager objects = new Manager(Processo.class);
   
   public static Processo of(String numero, String interessado) {
      return new Processo(numero, interessado);
   }
   
   public static Processo of() {
      return new Processo();
   }
   
   public String getNumero() {
      return numero;
   }
   
   public void setNumero(String numero) {
      this.numero = numero;
   }
   
   public String getInstancia() {
      return instancia;
   }
   
   public void setInstancia(String instancia) {
      this.instancia = instancia;
   }
   
   public void setInstancia(InstanciaJuizo instancia) {
      this.instancia = instancia == null ? "" : instancia.toString();
   }
   
   public Processo instancia(InstanciaJuizo instancia) {
      setInstancia(instancia);
      return this;
   }
   
   public String getInteressado() {
      return interessado;
   }
   
   public Relator getRelator() {
      return relator;
   }
   
   public void setRelator(Relator relator) {
      this.relator = relator;
      if (relator != null) {
         instancia = relator.getInstancia();
      }
   }
   
   public Processo relator(Relator relator) {
      setRelator(relator);
      return this;
   }
   
   public void setInteressado(String interessado) {
      this.interessado = interessado;
   }
   
   public String getObservacoes() {
      return observacoes;
   }
   
   public void setObservacoes(String observacoes) {
      this.observacoes = observacoes;
   }
   
   public Date getDataAdicao() {
      return dataAdicao;
   }
   
   public void setDataAdicao(Date dataAdicao) {
      this.dataAdicao = dataAdicao;
   }
   
   public Date getDataEdicao() {
      return dataEdicao;
   }
   
   public void setDataEdicao(Date dataEdicao) {
      this.dataEdicao = dataEdicao;
   }
   
   public String getSituacao() {
      return situacao;
   }
   
   public void setSituacao(String situacao) {
      this.situacao = situacao;
   }
   
   public void setSituacao(SituacaoCadastro situacao) {
      this.situacao = situacao == null ? "" : situacao.toString();
   }
   
   public Processo situacao(SituacaoCadastro situacao) {
      setSituacao(situacao);
      return this;
   }
   
   public void ativar() {
      getSorteioSet().forEach(Sorteio::ativar);
      this.update("situacao='" + SituacaoCadastro.ATIVO + "'");
   }
   
   public void inativar() {
      getSorteioSet().forEach(Sorteio::inativar);
      this.update("situacao='" + SituacaoCadastro.INATIVO + "'");
   }
   
   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("Processo(");
      sb.append("id=" + id);
      sb.append(", numero=" + numero);
      sb.append(", instancia=" + instancia);
      sb.append(", interessado=" + interessado);
      sb.append(", relator=" + relator);
      sb.append(", observacoes=" + observacoes);
      if (dataAdicao != null) {
         sb.append(", dataAdicao=" + Dates.fullDateTime(dataAdicao));
      } else {
         sb.append(", dataAdicao=null");
      }
      if (dataEdicao != null) {
         sb.append(", dataEdicao=" + Dates.fullDateTime(dataEdicao));
      } else {
         sb.append(", dataEdicao=null");
      }
      sb.append(", situacao=" + situacao);
      sb.append(")");
      return sb.toString();
   }
   
   public QuerySet<Sorteio> getSorteioSet() {
      return Sorteio.objects.getSet(Processo.class, this.id);
   }
   
   public QuerySet<Sorteio> sorteioSet() {
      return getSorteioSet();
   }
   
}
