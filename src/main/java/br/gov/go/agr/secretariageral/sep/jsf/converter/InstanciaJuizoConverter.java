package br.gov.go.agr.secretariageral.sep.jsf.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.gov.go.agr.secretariageral.sep.model.InstanciaJuizo;

/**
 * @author thiago
 * @version v1.0.0 30/01/2017
 * @since v1.0.0
 */
@FacesConverter(value = "instanciaConverter")
public class InstanciaJuizoConverter implements Converter {
   
   @Override
   public Object getAsObject(FacesContext context, UIComponent component, String value) {
      return InstanciaJuizo.valueOf(value);
   }
   
   @Override
   public String getAsString(FacesContext context, UIComponent component, Object value) {
      return value == null ? "" : InstanciaJuizo.valueOf(value.toString()).getNome();
   }
   
}
