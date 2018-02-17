package br.gov.go.agr.secretariageral.sep.searching;

import static br.org.verify.Verify.isNotEmptyOrNull;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Define as opções de criterios de pesquisa a serem apresentadas em tela
 * para o usuário.
 * 
 * @author thiago
 * @version v1.0.0 03/02/2017
 * @since v1.0.0
 */
public class OpcoesCriterioPesquisa extends ArrayList<OpcaoCriterioPesquisa> {
   
   private static final long serialVersionUID = 1L;
   
   public void adicionarOpcao(OpcaoCriterioPesquisa filtro) {
      this.add(filtro);
   }
   
   public void adicionarOpcoes(OpcaoCriterioPesquisa... filtros) {
      if (isNotEmptyOrNull((Object[]) filtros)) {
         this.addAll(Arrays.asList(filtros));
      }
   }
   
   public void adicionar(OpcaoCriterioPesquisa... filtros) {
      adicionarOpcoes(filtros);
   }
   
   public static OpcaoCriterioPesquisa opcaoCriterioPesquisa(String label, CriterioPesquisa valor) {
      return new OpcaoCriterioPesquisa(label, valor);
   }
   
   public static OpcaoCriterioPesquisa criterioPesquisa(String label, CriterioPesquisa valor) {
      return opcaoCriterioPesquisa(label, valor);
   }
   
   public static OpcaoCriterioPesquisa opcaoCriterioPesquisa(CriterioPesquisa criterio) {
      return new OpcaoCriterioPesquisa(criterio);
   }
   
   public static OpcaoCriterioPesquisa opcaoCriterioPesquisa(String label) {
      return new OpcaoCriterioPesquisa(label);
   }
   
   public static OpcaoCriterioPesquisa criterioPesquisa(CriterioPesquisa criterio) {
      return new OpcaoCriterioPesquisa(criterio);
   }
   
   public static OpcaoCriterioPesquisa criterioPesquisa(String label) {
      return new OpcaoCriterioPesquisa(label);
   }
   
}
