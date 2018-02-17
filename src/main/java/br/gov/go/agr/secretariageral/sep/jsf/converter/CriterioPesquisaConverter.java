package br.gov.go.agr.secretariageral.sep.jsf.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.gov.go.agr.secretariageral.sep.searching.CriterioPesquisa;

/**
 * @author thiago
 * @version v1.0.0 10/02/2017
 * @since v1.0.0
 */
@FacesConverter(value = "criterioPesquisaConverter")
public class CriterioPesquisaConverter implements Converter {
   
   @Override
   public Object getAsObject(FacesContext context, UIComponent component, String value) {
      CriterioPesquisa criterioPesquisa = CriterioPesquisa.valueOf(value);
      return criterioPesquisa;
   }
   
   @Override
   public String getAsString(FacesContext context, UIComponent component, Object value) {
      String criterioPesquisa = value == null ? "" : value.toString();
      return criterioPesquisa;
   }
   
}
