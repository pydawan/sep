package br.gov.go.agr.secretariageral.sep.searching;

import static br.org.verify.Verify.isNotEmptyOrNull;
import static jedi.db.models.QueryPage.orderBy;
import static jedi.db.models.QueryPage.pageFilters;
import static jedi.db.models.QueryPage.pageSize;
import static jedi.db.models.QueryPage.pageStart;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import jedi.db.models.Manager;
import jedi.db.models.Model;

/**
 * Define funcionalidades relativas a pesquisa.
 * 
 * @author thiago
 * @version v1.0.0 03/02/2017
 * @since v1.0.0
 */
public class Pesquisa<T> implements Serializable {
   
   private static final long serialVersionUID = 1L;
   private ParametrosPesquisa parametros;
   private OpcoesCampoPesquisa opcoesCampoPesquisa;
   private OpcoesCriterioPesquisa opcoesCriterioPesquisa;
   private Class<? extends Model> classe;
   private static final List<? extends Model> listaVazia = new ArrayList<>(0);
   
   @SuppressWarnings("unchecked")
   public Pesquisa() {
      parametros = new ParametrosPesquisa();
      opcoesCampoPesquisa = new OpcoesCampoPesquisa();
      opcoesCriterioPesquisa = new OpcoesCriterioPesquisa();
      Type gsc = getClass().getGenericSuperclass();
      ParameterizedType param = (ParameterizedType) gsc;
      Type arg = param.getActualTypeArguments()[0];
      classe = (Class<? extends Model>) arg;
   }
   
   public ParametrosPesquisa getParametros() {
      return parametros;
   }
   
   public ParametrosPesquisa parametros() {
      return parametros;
   }
   
   public OpcoesCampoPesquisa getOpcoesCampoPesquisa() {
      return opcoesCampoPesquisa;
   }
   
   public OpcoesCampoPesquisa opcoesCampoPesquisa() {
      return opcoesCampoPesquisa;
   }
   
   public OpcoesCampoPesquisa opcoesCampo() {
      return opcoesCampoPesquisa;
   }
   
   public OpcoesCriterioPesquisa getOpcoesCriterioPesquisa() {
      return opcoesCriterioPesquisa;
   }
   
   public OpcoesCriterioPesquisa opcoesCriterioPesquisa() {
      return opcoesCriterioPesquisa;
   }
   
   public OpcoesCriterioPesquisa opcoesCriterio() {
      return opcoesCriterioPesquisa;
   }
   
   public String selecioneOCampo() {
      return "-------------------- SELECIONE O CAMPO --------------------";
   }
   
   public String selecioneOCriterio() {
      return "-------------------- SELECIONE O CRITÉRIO -----------------";
   }
   
   public List<? extends Model> pesquisar() {
      if (isNotEmptyOrNull(parametros)) {
         Manager objects = new Manager(classe);
         objects.page(pageStart(0), pageSize(10), orderBy("-id"), (String[]) null);
         return objects.page(pageStart(0), pageSize(5), pageFilters(parametros.toString()));
      }
      return listaVazia;
   }
   
}
