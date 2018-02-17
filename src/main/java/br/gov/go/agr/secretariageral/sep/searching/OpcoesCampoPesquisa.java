package br.gov.go.agr.secretariageral.sep.searching;

import static br.org.verify.Verify.isNotEmptyOrNull;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Define as opções de campo disponíveis na pesquisa.
 * 
 * @author thiago
 * @version v1.0.0 03/02/2017
 * @since v1.0.0
 */
public class OpcoesCampoPesquisa extends ArrayList<OpcaoCampoPesquisa> {
   
   private static final long serialVersionUID = 1L;
   
   public void adicionarOpcao(OpcaoCampoPesquisa campo) {
      this.add(campo);
   }
   
   public void adicionarOpcoes(OpcaoCampoPesquisa... campos) {
      if (isNotEmptyOrNull((Object[]) campos)) {
         this.addAll(Arrays.asList(campos));
      }
   }
   
   public void adicionar(OpcaoCampoPesquisa... campos) {
      adicionarOpcoes(campos);
   }
   
   public static OpcaoCampoPesquisa opcaoCampoPesquisa(String label, String campo) {
      return new OpcaoCampoPesquisa(label, campo);
   }
   
   public static OpcaoCampoPesquisa campoPesquisa(String label, String campo) {
      return new OpcaoCampoPesquisa(label, campo);
   }
   
}
