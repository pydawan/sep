package br.gov.go.agr.secretariageral.sep.jsf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.gov.go.agr.secretariageral.sep.model.Usuario;

/**
 * @author thiago-amm
 * @version v1.0.0 08/11/2017
 * @since v1.0.0
 */
@SessionScoped
@ManagedBean(name = "usuarioMB", eager = true)
public class UsuarioBean implements Serializable {
   
   private static final long serialVersionUID = 1L;
   private Usuario usuario;
   
   public Usuario getUsuario() {
      return usuario;
   }
   
   public void setUsuario(Usuario usuario) {
      this.usuario = usuario;
   }
   
   public boolean isAdmin() {
      return usuario.isAdmin();
   }
   
}
