package br.gov.go.agr.secretariageral.sep.jsf.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.gov.go.agr.secretariageral.sep.model.Processo;

/**
 * @author thiago
 * @version v1.0.0 13/02/2017
 * @since v1.0.0
 */
@FacesConverter("processoConverter")
public class ProcessoConverter implements Converter {
   
   @Override
   public Object getAsObject(FacesContext context, UIComponent component, String value) {
      return Processo.objects.get("id", value);
   }
   
   @Override
   public String getAsString(FacesContext context, UIComponent component, Object value) {
      return "" + value;
   }
   
}
