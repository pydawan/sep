package br.gov.go.agr.secretariageral.sep.jsf.converter;

import static br.org.verify.Verify.isEmptyOrNull;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.gov.go.agr.secretariageral.sep.searching.OpcaoCampoPesquisa;

@FacesConverter(value = "campoPesquisaConverter")
public class CampoPesquisaConverter implements Converter {
   
   @Override
   public Object getAsObject(FacesContext context, UIComponent component, String value) {
      OpcaoCampoPesquisa campoPesquisa = null;
      if (isEmptyOrNull(campoPesquisa)) {
         return new OpcaoCampoPesquisa("qual campo?", "");
      }
      String[] array = value.split("/");
      String label = array[0];
      String campo = array[1];
      return new OpcaoCampoPesquisa(label, campo);
   }
   
   @Override
   public String getAsString(FacesContext context, UIComponent component, Object value) {
      OpcaoCampoPesquisa campoPesquisa = (OpcaoCampoPesquisa) value;
      return campoPesquisa.toString();
   }
   
}
