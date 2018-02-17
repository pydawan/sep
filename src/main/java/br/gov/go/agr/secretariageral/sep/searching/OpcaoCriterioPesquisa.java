package br.gov.go.agr.secretariageral.sep.searching;

import java.io.Serializable;

import javax.faces.model.SelectItem;

/**
 * Define uma opção de critério de pesquisa a ser apresentada em tela para o
 * usuário.
 * 
 * @author thiago
 * @version v1.0.0 03/02/2017
 * @since v1.0.0
 */
public class OpcaoCriterioPesquisa implements Serializable {
   
   private static final long serialVersionUID = 1L;
   private String label;
   private CriterioPesquisa criterio;
   
   public OpcaoCriterioPesquisa() {
      
   }
   
   public OpcaoCriterioPesquisa(String label) {
      this.label = label;
      this.criterio = null;
   }
   
   public OpcaoCriterioPesquisa(String label, CriterioPesquisa criterio) {
      this.label = label;
      this.criterio = criterio;
   }
   
   public OpcaoCriterioPesquisa(CriterioPesquisa criterio) {
      this.criterio = criterio;
      if (criterio != null) {
         this.label = criterio.getLabel();
      }
      // TODO
      // 1 - Definir o critério conforme o tipo de dados do campo.
   }
   
   public String getLabel() {
      return label;
   }
   
   public void setLabel(String label) {
      this.label = label;
   }
   
   public CriterioPesquisa getCriterio() {
      return criterio;
   }
   
   public void setValor(CriterioPesquisa criterio) {
      this.criterio = criterio;
   }
   
   public String label() {
      return label;
   }
   
   public OpcaoCriterioPesquisa label(String label) {
      this.label = label;
      return this;
   }
   
   public CriterioPesquisa criterio() {
      return criterio;
   }
   
   public OpcaoCriterioPesquisa criterio(CriterioPesquisa criterio) {
      this.criterio = criterio;
      return this;
   }
   
   @Override
   public String toString() {
      return String.format("OpcaoCriterioPesquisa(label = %s, criterio = %s)", label, criterio);
   }
   
   public SelectItem toSelectItem() {
      SelectItem selectItem = new SelectItem();
      selectItem.setLabel(this.label);
      selectItem.setValue(this.criterio.getValor());
      return selectItem;
   }
   
}
