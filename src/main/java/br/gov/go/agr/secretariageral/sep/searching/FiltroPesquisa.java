package br.gov.go.agr.secretariageral.sep.searching;

import static br.org.verify.Verify.isNotEmptyOrNull;

import java.util.ArrayList;

/**
 * Define o filtro de pesquisa.
 * 
 * @author thiago
 * @version v1.0.0 03/02/2017
 * @since v1.0.0
 */
public class FiltroPesquisa extends ArrayList<Object> {
   
   private static final long serialVersionUID = 1L;
   
   public void adicionarParametro(Object parametro) {
      if (isNotEmptyOrNull(parametro)) {
         // se o parametro não foi encontrado adicione-o!
         if (this.indexOf(parametro) == -1) {
            this.add(parametro);
         }
      }
   }
   
   public void adicionarParametros(Object... parametros) {
      if (isNotEmptyOrNull(parametros)) {
         for (Object parametro : parametros) {
            adicionarParametro(parametro);
         }
      }
   }
   
   public void parametros(Object... parametros) {
      adicionarParametros(parametros);
   }
   
   @Override
   public String toString() {
      StringBuffer sql = new StringBuffer();
      this.forEach(e -> sql.append(e + " "));
      return sql.toString().trim();
   }
   
}
