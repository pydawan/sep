package br.gov.go.agr.secretariageral.sep.model;

import jedi.db.models.CharField;
import jedi.db.models.Manager;
import jedi.db.models.Model;
import jedi.db.models.QuerySet;
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
 * Modela o conceito de permissão de usuário no sistema.
 * 
 * @author thiago
 * @version v1.0.0 23/01/2017
 * @since v1.0.0
 */
@Getter
@Setter
@Accessors(fluent = true, chain = true)
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString(includeFieldNames = true)
@Table(name = "permissoes")
public class Permissao extends Model {
   
   private static final long serialVersionUID = 1L;
   
   @NonNull
   @CharField(max_length = 50, unique = true)
   private String nome;
   
   @NonNull
   @CharField(max_length = 100, unique = true)
   private String valor;
   
   @CharField(max_length = 7)
   private String situacao = SituacaoCadastro.ATIVO.getValor();
   
   public static Manager objects = new Manager(Permissao.class);
   
   public String getNome() {
      return nome;
   }
   
   public void setNome(String nome) {
      this.nome = nome;
   }
   
   public String getValor() {
      return valor;
   }
   
   public void setValor(String valor) {
      this.valor = valor;
   }
   
   public String getSituacao() {
      return situacao;
   }
   
   public void setSituacao(String situacao) {
      this.situacao = situacao;
   }
   
   public void ativar() {
      this.update("situacao='" + SituacaoCadastro.ATIVO + "'");
   }
   
   public void inativar() {
      this.update("situacao='" + SituacaoCadastro.INATIVO + "'");
   }
   
   public QuerySet<Grupo> getGrupoSet() {
      return Grupo.objects.getSet(Permissao.class, this.id);
   }
   
   public QuerySet<Grupo> grupoSet() {
      return getGrupoSet();
   }
   
}
