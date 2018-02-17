package br.gov.go.agr.secretariageral.sep.searching;

import java.io.Serializable;

/**
 * Define um parâmetro de pesquisa.
 * Na verdade o parâmetro é um elemento que compõe um filtro da pesquisa.
 * Um filtro de pesquisa pode conter nenhum ou vários parâmetros de pesquisa.
 * 
 * @author thiago
 * @version v1.0.0 02/02/2017
 * @since v1.0.0
 */
public class ParametroPesquisa implements Serializable {
   
   private static final long serialVersionUID = 1L;
   
   private Object operandoDaEsquerda;
   private CriterioPesquisa criterio;
   private Object operandoDaDireita;
   
   public ParametroPesquisa(Object operandoDaEsquerda, CriterioPesquisa criterio, Object operandoDaDireita) {
      this.operandoDaEsquerda = operandoDaEsquerda;
      this.criterio = criterio;
      this.operandoDaDireita = operandoDaDireita;
   }
   
   public ParametroPesquisa(CriterioPesquisa criterio) {
      this.operandoDaEsquerda = "";
      this.criterio = criterio;
      this.operandoDaDireita = "";
   }
   
   public ParametroPesquisa() {
      
   }
   
   public Object getOperandoDaEsquerda() {
      return operandoDaEsquerda;
   }
   
   public void setOperandoDaEsquerda(Object operandoDaEsquerda) {
      this.operandoDaEsquerda = operandoDaEsquerda;
   }
   
   public Object getOperandoDaDireita() {
      return operandoDaDireita;
   }
   
   public void setOperandoDaDireita(Object operandoDaDireita) {
      this.operandoDaDireita = operandoDaDireita;
   }
   
   public CriterioPesquisa getCriterio() {
      return criterio;
   }
   
   public void setCriterio(CriterioPesquisa criterio) {
      this.criterio = criterio;
   }
   
   public static ParametroPesquisa parametroPesquisa(Object operandoDaEsquerda, CriterioPesquisa criterio, Object operandoDaDireita) {
      return new ParametroPesquisa(operandoDaEsquerda, criterio, operandoDaDireita);
   }
   
   public static ParametroPesquisa parametroPesquisa(CriterioPesquisa criterio) {
      return new ParametroPesquisa(criterio);
   }
   
   public static ParametroPesquisa paramPesquisa(Object operandoDaEsquerda, CriterioPesquisa criterio, Object operandoDaDireita) {
      return parametroPesquisa(operandoDaEsquerda, criterio, operandoDaDireita);
   }
   
   public static ParametroPesquisa paramPesquisa(CriterioPesquisa criterio) {
      return new ParametroPesquisa(criterio);
   }
   
   @Override
   public String toString() {
      return String.format(criterio.getValor(), operandoDaEsquerda, operandoDaDireita);
   }
   
}
