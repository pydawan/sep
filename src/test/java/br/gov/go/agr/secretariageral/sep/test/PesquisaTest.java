package br.gov.go.agr.secretariageral.sep.test;

import static jedi.db.models.QueryPage.pageFilters;
import static jedi.db.models.QueryPage.pageNumber;
import static jedi.db.models.QueryPage.pageSize;
import static jedi.db.models.QueryPage.pageStart;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.gov.go.agr.secretariageral.sep.model.Relator;
import br.gov.go.agr.secretariageral.sep.model.Sorteio;

/**
 * Testa os comportamentos de Pesquisa.
 * 
 * @author thiago
 * @version v1.0.0 03/02/2017
 * @since v1.0.0
 */
public class PesquisaTest {
   
   @Ignore
   @Test
   public void testPesquisaConselhoRegulador() {
      List<Relator> conselhoRegulador = Relator.objects.page(pageStart(0), pageSize(5), pageFilters("instancia__exact='CONSELHO_REGULADOR'"));
      System.out.println("===== CONSELHOR REGULADOR =====");
      conselhoRegulador.forEach(membro -> System.out.println(membro));
      System.out.println(Relator.objects.count("instancia__exact='CONSELHO_REGULADOR'"));
      System.out.println(Relator.objects.count("instancia__!exact='CAMARA_DE_JULGAMENTO'"));
      System.out.println("===============================");
   }
   
   @Ignore
   @Test
   public void testPesquisaCamaraJulgamento() {
      List<Relator> camaraJulgamento = Relator.objects.page(pageStart(0), pageSize(5), pageFilters("instancia__exact='CAMARA_DE_JULGAMENTO'"));
      System.out.println("===== CÂMARA DE JULGAMENTO =====");
      camaraJulgamento.forEach(membro -> System.out.println(membro));
      System.out.println(Relator.objects.count("instancia__exact='CAMARA_DE_JULGAMENTO'"));
      System.out.println(Relator.objects.count("instancia__!exact='CONSELHO_REGULADOR'"));
      System.out.println("===============================");
   }
   
   @Test
   public void testPesquisaAvancada() {
      System.out.println(Sorteio.objects.filter("juiz.nome__!iendswith='tista'"));
      System.out.println(Sorteio.objects.count("juiz.nome__!iendswith='tista'"));
      System.out.println(Sorteio.objects.exclude("juiz.nome__!iendswith='tista'"));
      System.out.println(Sorteio.objects.page(pageNumber(0), pageSize(10), pageFilters("juiz.nome__!iendswith='tista'")));
      System.out.println(Sorteio.objects.reversePage(pageNumber(0), pageSize(10), pageFilters("juiz.nome__!iendswith='tista'")));
   }
   
}
