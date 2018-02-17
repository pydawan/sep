package br.gov.go.agr.secretariageral.sep.model;

import static br.org.verify.Verify.isNotEmptyOrNull;

import java.util.List;

import jedi.db.models.CharField;
import jedi.db.models.Manager;
import jedi.db.models.ManyToManyField;
import jedi.db.models.Model;
import jedi.db.models.QuerySet;
import jedi.db.models.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Modela o conceito de grupo de usuário do sistema.
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
@ToString(includeFieldNames = true)
@Table(name = "grupos")
public class Grupo extends Model {
   
   private static final long serialVersionUID = 1L;
   
   @CharField(max_length = 50, unique = true)
   private String nome;
   
   @CharField(max_length = 7)
   private String situacao = SituacaoCadastro.ATIVO.getValor();
   
   @ManyToManyField
   private QuerySet<Permissao> permissoes = new QuerySet<>();
   
   public static Manager objects = new Manager(Grupo.class);
   
   public Grupo(String nome, List<Permissao> permissoes) {
      this.nome = nome;
      if (isNotEmptyOrNull(permissoes)) {
         this.permissoes.addAll(permissoes);
      }
   }
   
   public String getNome() {
      return nome;
   }
   
   public void setNome(String nome) {
      this.nome = nome;
   }
   
   public String getSituacao() {
      return situacao;
   }
   
   public void setSituacao(String situacao) {
      this.situacao = situacao;
   }
   
   public QuerySet<Permissao> getPermissoes() {
      return permissoes;
   }
   
   public void setPermissoes(QuerySet<Permissao> permissoes) {
      this.permissoes = permissoes;
   }
   
   public void ativar() {
      this.update("situacao='" + SituacaoCadastro.ATIVO + "'");
   }
   
   public void inativar() {
      this.update("situacao='" + SituacaoCadastro.INATIVO + "'");
   }
   
   public QuerySet<Usuario> getUsuarioSet() {
      return Usuario.objects.getSet(Grupo.class, this.id);
   }
   
   public QuerySet<Usuario> usuarioSet() {
      return getUsuarioSet();
   }
   
}
