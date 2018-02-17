package br.gov.go.agr.secretariageral.sep.model;

import java.util.Date;

import jedi.db.models.BooleanField;
import jedi.db.models.CharField;
import jedi.db.models.DateTimeField;
import jedi.db.models.EmailField;
import jedi.db.models.ForeignKeyField;
import jedi.db.models.Manager;
import jedi.db.models.Model;
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
 * Representa um usuário do sistema.
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
@ToString(includeFieldNames = true)
@Table(name = "usuarios")
public class Usuario extends Model {
   
   private static final long serialVersionUID = 1L;
   
   @NonNull
   @CharField(max_length = 100)
   private String nome;
   
   @NonNull
   @CharField(max_length = 50, unique = true)
   private String username;
   
   @NonNull
   @CharField(max_length = 128)
   private String password;
   
   @NonNull
   @CharField(max_length = 128)
   private String salt;
   
   @NonNull
   @EmailField(unique = true)
   private String email;
   
   @BooleanField(default_value = false)
   private boolean admin;
   
   @NonNull
   @ForeignKeyField
   private Grupo grupo;
   
   @DateTimeField(auto_now_add = true)
   private Date dataCriacao;
   
   @DateTimeField(auto_now = true, required = false)
   private Date dataEdicao;
   
   @CharField(max_length = 7)
   private String situacao = SituacaoCadastro.ATIVO.getValor();
   
   @TextField(required = false)
   private String observacoes;
   
   private boolean isLoggedIn = false;
   
   public static Manager objects = new Manager(Usuario.class);
   
   public static Usuario of(String username) {
      Usuario usuario = new Usuario();
      usuario.setUsername(username);
      return usuario;
   }
   
   public String getNome() {
      return nome;
   }
   
   public void setNome(String nome) {
      this.nome = nome;
   }
   
   public String getUsername() {
      return username;
   }
   
   public void setUsername(String username) {
      this.username = username;
   }
   
   public String getPassword() {
      return password;
   }
   
   public void setPassword(String senha) {
      this.password = senha;
   }
   
   public String getSalt() {
      return salt;
   }
   
   public void setSalt(String salt) {
      this.salt = salt;
   }
   
   public String getEmail() {
      return email;
   }
   
   public void setEmail(String email) {
      this.email = email;
   }
   
   public boolean isAdmin() {
      return admin;
   }
   
   public void setAdmin(boolean admin) {
      this.admin = admin;
   }
   
   public Grupo getGrupo() {
      return grupo;
   }
   
   public void setGrupo(Grupo grupo) {
      this.grupo = grupo;
   }
   
   public Date getDataCriacao() {
      return dataCriacao;
   }
   
   public void setDataCriacao(Date dataCriacao) {
      this.dataCriacao = dataCriacao;
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
   
   public String getObservacoes() {
      return observacoes;
   }
   
   public void setObservacoes(String observacoes) {
      this.observacoes = observacoes;
   }
   
   public boolean isLoggedIn() {
      return isLoggedIn;
   }
   
   public void setLoggedIn(boolean isLoggedIn) {
      this.isLoggedIn = isLoggedIn;
   }
   
   public void ativar() {
      this.update("situacao='" + SituacaoCadastro.ATIVO + "'");
   }
   
   public void inativar() {
      this.update("situacao='" + SituacaoCadastro.INATIVO + "'");
   }
   
}
