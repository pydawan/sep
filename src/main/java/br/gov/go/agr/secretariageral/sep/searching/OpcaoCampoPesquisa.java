package br.gov.go.agr.secretariageral.sep.searching;

import java.io.Serializable;

import javax.faces.model.SelectItem;

/**
 * Define uma opção de campo de pesquisa a ser apresentada em tela para o
 * usuário.
 * 
 * @author thiago
 * @version v1.0.0 03/02/2017
 * @since v1.0.0
 */
public class OpcaoCampoPesquisa implements Serializable {
   
   private static final long serialVersionUID = 1L;
   private String label;
   private String campo;
   // lista de critério do campo.
   
   public OpcaoCampoPesquisa() {
      
   }
   
   public OpcaoCampoPesquisa(String label, String campo) {
      this.label = label;
      this.campo = campo;
   }
   
   public String getLabel() {
      return label;
   }
   
   public void setLabel(String label) {
      this.label = label;
   }
   
   public String getCampo() {
      return campo;
   }
   
   public void setCampo(String campo) {
      this.campo = campo;
   }
   
   public String label() {
      return label;
   }
   
   public OpcaoCampoPesquisa label(String label) {
      this.label = label;
      return this;
   }
   
   public String campo() {
      return campo;
   }
   
   public OpcaoCampoPesquisa campo(String campo) {
      this.campo = campo;
      return this;
   }
   
   @Override
   public String toString() {
      return String.format("%s/%s", label, campo);
   }
   
   public SelectItem toSelectItem() {
      SelectItem selectItem = new SelectItem();
      selectItem.setLabel(this.label);
      selectItem.setValue(this.campo);
      return selectItem;
   }
   
}
