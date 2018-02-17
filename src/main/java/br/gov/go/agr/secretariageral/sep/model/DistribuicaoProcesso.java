package br.gov.go.agr.secretariageral.sep.model;

import java.util.Date;

import jedi.db.models.CharField;
import jedi.db.models.DateTimeField;
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
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Representa a distribuição de processos
 * para relatores na Câmara de Julgamento ou
 * no Conselho Regulador.
 * 
 * @author thiago
 * @version v1.0.0 05/04/2017
 * @since v1.0.0
 */
@Getter
@Setter
@Accessors(fluent = true, chain = true)
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@ToString(includeFieldNames = true)
@Table(name = "distribuicoes_processo")
public class DistribuicaoProcesso extends Model {
   
   private static final long serialVersionUID = 1L;
   
   @DateTimeField(auto_now_add = true, auto_now = true)
   private Date data;
   
   @CharField(max_length = 50)
   private String cidade = "Goiânia";
   
   @CharField(max_length = 50)
   private String local = "Auditório Augusto Brandão Cunha";
   
   @NonNull
   @CharField(max_length = 50)
   private String responsavel;
   
   @CharField(max_length = 20)
   private String instancia = InstanciaJuizo.CAMARA_DE_JULGAMENTO.getValor();
   
   @CharField(max_length = 7)
   private String situacao = SituacaoCadastro.ATIVO.getValor();
   
   @TextField(required = false)
   private String observacoes;
   
   @CharField(max_length = 255, unique = false, required = false)
   private String codigoValidacao;
   
   public static Manager objects = new Manager(DistribuicaoProcesso.class);
   
   public Date getData() {
      return data;
   }
   
   public void setData(Date data) {
      this.data = data;
   }
   
   public String getCidade() {
      return cidade;
   }
   
   public void setCidade(String cidade) {
      this.cidade = cidade;
   }
   
   public String getLocal() {
      return local;
   }
   
   public void setLocal(String local) {
      this.local = local;
   }
   
   public String getResponsavel() {
      return responsavel;
   }
   
   public void setResponsavel(String responsavel) {
      this.responsavel = responsavel;
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
   
   public DistribuicaoProcesso instancia(InstanciaJuizo instancia) {
      setInstancia(instancia);
      return this;
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
   
   public DistribuicaoProcesso situacao(SituacaoCadastro situacao) {
      setSituacao(situacao);
      return this;
   }
   
   public String getObservacoes() {
      return observacoes;
   }
   
   public void setObservacoes(String observacoes) {
      this.observacoes = observacoes;
   }
   
   public String getCodigoValidacao() {
      return codigoValidacao;
   }
   
   public void setCodigoValidacao(String codigoValidacao) {
      this.codigoValidacao = codigoValidacao;
   }
   
   public void ativar() {
      this.update("situacao='" + SituacaoCadastro.ATIVO + "'");
   }
   
   public void inativar() {
      this.update("situacao='" + SituacaoCadastro.INATIVO + "'");
   }
   
   public QuerySet<Sorteio> getSorteioSet() {
      return Sorteio.objects.getSet(DistribuicaoProcesso.class, this.id);
   }
   
   public QuerySet<Sorteio> sorteioSet() {
      return getSorteioSet();
   }
   
}
