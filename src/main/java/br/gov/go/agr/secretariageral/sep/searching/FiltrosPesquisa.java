package br.gov.go.agr.secretariageral.sep.searching;

import static br.org.verify.Verify.isNotEmptyOrNull;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author thiago
 * @version v1.0.0 03/02/2017
 * @since v1.0.0
 */
public class FiltrosPesquisa extends HashMap<String, FiltroPesquisa> {
   
   private static final long serialVersionUID = 1L;
   
   public void adicionarFiltro(String nome, Object... parametros) {
      if (isNotEmptyOrNull(nome, parametros)) {
         if (!this.containsKey(nome)) {
            this.put(nome, new FiltroPesquisa());
         }
         this.get(nome).addAll(Arrays.asList(parametros));
      }
   }
   
   public void filtro(String nome, Object... parametros) {
      adicionarFiltro(nome, parametros);
   }
   
   @Override
   public String toString() {
      StringBuffer sql = new StringBuffer();
      this.forEach((k, v) -> {
         sql.append(v + "\n");
      });
      return sql.toString().trim();
   }
   
}
