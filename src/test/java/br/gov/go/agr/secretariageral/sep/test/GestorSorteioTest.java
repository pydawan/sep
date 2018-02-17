package br.gov.go.agr.secretariageral.sep.test;

import java.util.List;

import org.junit.Test;

import br.gov.go.agr.secretariageral.sep.model.GestorSorteio;
import br.gov.go.agr.secretariageral.sep.model.Relator;
import br.gov.go.agr.secretariageral.sep.model.Processo;

public class GestorSorteioTest {
   
   @Test
   public void test() {
      List<Processo> processos = Processo.objects._filter("juiz__isnull=true");
      List<Relator> juizes = Relator.objects.all();
      GestorSorteio gestorSorteio = new GestorSorteio(processos, juizes);
      gestorSorteio.sortear();
   }
}
