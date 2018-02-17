package br.gov.go.agr.secretariageral.sep.jsf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;

import br.gov.go.agr.secretariageral.sep.jsf.JSF;
import br.gov.go.agr.secretariageral.sep.logging.Logger;
import br.gov.go.agr.secretariageral.sep.model.Url;
import br.gov.go.agr.secretariageral.sep.model.Usuario;
import br.gov.go.agr.secretariageral.sep.service.SegurancaService;

/**
 * Responsável pelo gerenciamento da autenticação no sistema.
 * 
 * @author thiago
 * @version v1.0.0 18/01/2017
 * @since v1.0.0
 */
@RequestScoped
@ManagedBean(name = "autenticacao")
public class AutenticacaoBean implements Serializable {
   
   private static final long serialVersionUID = 1L;
   private String username;
   private String password;
   
   @ManagedProperty(value = "#{usuarioMB}")
   private UsuarioBean usuarioBean;
   
   public String getUsername() {
      return username;
   }
   
   public void setUsername(String username) {
      this.username = username;
   }
   
   public String getPassword() {
      return password;
   }
   
   public void setPassword(String password) {
      this.password = password;
   }
   
   public UsuarioBean getUsuarioBean() {
      return usuarioBean;
   }
   
   public void setUsuarioBean(UsuarioBean usuarioBean) {
      this.usuarioBean = usuarioBean;
   }
   
   public void login(ActionEvent event) {
      try {
         Usuario usuario = SegurancaService.Autenticador.autenticar(username, password);
         usuarioBean.setUsuario(usuario);
         JSF.redirect(Url.PAGINA_ADMIN);
      } catch (AuthenticationException e) {
         Logger.error(e.getMessage());
         JSF.error("FALHA: ", "Usuário ou senha inválidos!");
      }
   }
   
   public void cancelar(ActionEvent event) {
      username = "";
      password = "";
   }
   
   public void logout(ActionEvent event) {
      Subject subject = SecurityUtils.getSubject();
      Logger.Logout.SUCESSO(subject.getPrincipal());
      subject.logout();
      JSF.redirect(Url.PAGINA_SEP);
   }
   
}
