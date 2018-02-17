package br.gov.go.agr.secretariageral.sep.model;

import br.gov.go.agr.secretariageral.sep.jsf.JSF;

/**
 * Define as rotas ou caminhos das páginas
 * da aplicação.
 * 
 * @author thiago
 * @version v1.0.0 20/03/2017
 * @since v1.0.0
 */
public enum Url {
   
   HOST("http://localhost:8080"),
   PAGINA_SEP("/sep/"),
   PAGINA_ADMIN("/sep/admin/"),
   PAGINA_LOGIN("/sep/login.xhtml"),
   PAGINA_LOGOUT("/sep/logout.xhtml"),
   PAGINA_NAO_ENCONTRADA("/sep/erro/404.xhtml"),
   PAGINA_PROIBIDA("/sep/erro/403.xhtml"),
   PAGINA_ERRO_INTERNO_SERVIDOR("sep/erro/500.xhtml"),
   PAGINA_RELATOR("/sep/admin/relator/"),
   PAGINA_PROCESSO("/sep/admin/processo/"),
   PAGINA_SORTEIO("/sep/admin/sorteio/"),
   PAGINA_SORTEIO_REALIZAR("/sep/admin/sorteio/"),
   PAGINA_SORTEIO_RESULTADO("/sep/admin/sorteio/resultado/"),
   PAGINA_SORTEIO_PESQUISAR("/sep/admin/sorteio/pesquisar/"),
   PAGINA_SORTEIO_VALIDAR("/sep/admin/sorteio/validar/"),
   PAGINA_SORTEIO_RESULTADO_RELATORIO(HOST + "/sep/spring/relatorio/sorteio/");
      
   private final String url;
   
   private Url(final String url) {
      this.url = url;
   }
   
   public String getURL() {
      return url;
   }
   
   public String url() {
      return url;
   }
   
   @Override
   public String toString() {
      return url;
   }
   
   public boolean isRequestedURI() {
      String uri = JSF.request().getRequestURI().trim();
      return uri != null && this.url.equals(uri);
   }
   
   public boolean requestURI() {
      return isRequestedURI();
   }
   
}