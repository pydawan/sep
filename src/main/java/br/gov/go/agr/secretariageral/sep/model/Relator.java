package br.gov.go.agr.secretariageral.sep.model;

import static br.org.verify.Verify.isNotEmptyOrNull;

import java.util.Date;

import br.org.jext.Dates;
import jedi.db.models.CharField;
import jedi.db.models.DateTimeField;
import jedi.db.models.EmailField;
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
 * Representa um relator de processo na AGR.
 * Pode pertencer a Câmara de Julgamento ou ao Conselho Regulador.
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
@Table(name = "relatores")
public class Relator extends Model {
   
   private static final long serialVersionUID = 1L;
   
   @NonNull
   @CharField(max_length = 50, unique = true)
   private String nome;
   
   @EmailField(required = false, unique = false)
   private String email;
   
   @CharField(max_length = 20)
   private String instancia = InstanciaJuizo.CAMARA_DE_JULGAMENTO.getValor();
   
   @DateTimeField(auto_now_add = true)
   private Date dataAdicao;
   
   @DateTimeField(auto_now = true, required = false)
   private Date dataEdicao;
   
   @CharField(max_length = 7)
   private String situacao = SituacaoCadastro.ATIVO.getValor();
   
   @TextField(required = false)
   private String observacoes;
   
   public static Manager objects = new Manager(Relator.class);
   
   public Relator(String nome, InstanciaJuizo instancia) {
      this.nome = nome;
      this.instancia = instancia == null ? "" : instancia.getValor();
   }
   
   public static Relator of(String nome, InstanciaJuizo instancia) {
      return new Relator(nome, instancia);
   }
   
   public static Relator of() {
      return new Relator();
   }
   
   public String getNome() {
      return nome;
   }
   
   public void setNome(String nome) {
      this.nome = nome;
   }
   
   public String getEmail() {
      return email;
   }
   
   public void setEmail(String email) {
      this.email = email;
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
   
   public Relator instancia(InstanciaJuizo instancia) {
      setInstancia(instancia);
      return this;
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
   
   public Relator situacao(SituacaoCadastro situacao) {
      setSituacao(situacao);
      return this;
   }
   
   public String getObservacoes() {
      return observacoes;
   }
   
   public void setObservacoes(String observacoes) {
      this.observacoes = observacoes;
   }
   
   public void ativar() {
      getProcessoSet().forEach(Processo::ativar);
      this.update("situacao='" + SituacaoCadastro.ATIVO + "'");
   }
   
   public void inativar() {
      getProcessoSet().forEach(Processo::inativar);
      this.update("situacao='" + SituacaoCadastro.INATIVO + "'");
   }
   
   public boolean getParticipouDeSorteio() {
      boolean participouDeSorteio = false;
      QuerySet<Sorteio> sorteios = getSorteioSet();
      participouDeSorteio = isNotEmptyOrNull(sorteios) ? true : false;
      return participouDeSorteio;
   }
   
   public boolean getNaoParticipouDeSorteio() {
      return !getParticipouDeSorteio();
   }
   
   public boolean participouDeSorteio() {
      return getParticipouDeSorteio();
   }
   
   public boolean naoParticipouDeSorteio() {
      return getNaoParticipouDeSorteio();
   }
   
   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("Relator(");
      sb.append("id=" + id);
      sb.append(", nome=" + nome);
      sb.append(", email=" + email);
      sb.append(", instancia=" + instancia);
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
      sb.append(", observacoes=" + observacoes);
      sb.append(")");
      return sb.toString();
   }
   
   public QuerySet<Sorteio> getSorteioSet() {
      return Sorteio.objects.getSet(Relator.class, this.id);
   }
   
   public QuerySet<Sorteio> sorteioSet() {
      return getSorteioSet();
   }
   
   public QuerySet<Processo> getProcessoSet() {
      return Processo.objects.getSet(Relator.class, this.id);
   }
   
   public QuerySet<Processo> processoSet() {
      return getProcessoSet();
   }
   
}
