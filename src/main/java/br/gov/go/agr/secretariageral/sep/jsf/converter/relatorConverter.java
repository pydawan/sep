package br.gov.go.agr.secretariageral.sep.jsf.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.gov.go.agr.secretariageral.sep.model.Relator;

@FacesConverter("relatorConverter")
public class relatorConverter implements Converter {
   
   @Override
   public Object getAsObject(FacesContext context, UIComponent component, String value) {
      return Relator.objects.get("id", value);
   }
   
   @Override
   public String getAsString(FacesContext context, UIComponent component, Object value) {
      return "" + value;
   }
   
}
