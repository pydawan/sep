package br.gov.go.agr.secretariageral.sep.test;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.javafaker.Faker;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.gov.go.agr.secretariageral.sep.model.DistribuicaoProcesso;
import br.gov.go.agr.secretariageral.sep.model.Processo;
import br.gov.go.agr.secretariageral.sep.model.Relator;
import br.gov.go.agr.secretariageral.sep.model.Sorteio;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FixtureTest {
   
   @Test
   public void testA() {
      
      Faker faker = new Faker(new Locale("pt-BR"));
      Lorem lorem = LoremIpsum.getInstance();
      
      Fixture.of(DistribuicaoProcesso.class).addTemplate("distribuicao.cj", new Rule() {
         {
            add("data", randomDate("2016-01-01", "2017-03-21", new SimpleDateFormat("yyyy-MM-dd")));
            add(
               "cidade",
               random(
                  "Goiânia",
                  "Aparecida de Goiânia",
                  "Anápolis",
                  "Goianésia",
                  "Jaraguá",
                  "Rio Verde"));
            add("local", faker.address().streetAddress());
            add("responsavel", firstName());
            add("instancia", "CAMARA_DE_JULGAMENTO");
            add("observacoes", lorem.getParagraphs(0, 4));
         }
      });
      
      Fixture.of(Relator.class).addTemplate("relator.cj", new Rule() {
         {
            add("nome", name());
            add("email", regex("\\w{5,15}@agr.go.gov.br"));
            add("instancia", "CAMARA_DE_JULGAMENTO");
         }
      });
      
      Fixture.of(Processo.class).addTemplate("processo.cj", new Rule() {
         {
            add("numero", regex("201700029\\d{6}"));
            add("instancia", "CAMARA_DE_JULGAMENTO");
            add("interessado", name());
            add("relator", null);
         }
      });
      
      Fixture.of(Sorteio.class).addTemplate("sorteio.cj", new Rule() {
         {
            add("distribuicao", one(DistribuicaoProcesso.class, "distribuicao.cj"));
            add("processo", one(Processo.class, "processo.cj"));
            add("relator", one(Relator.class, "relator.cj"));
         }
      });
      
      List<Sorteio> sorteiosCJ = Fixture.from(Sorteio.class).gimme(10, "sorteio.cj");
      sorteiosCJ.forEach(System.out::println);
      sorteiosCJ.forEach(sorteio -> System.out.println(sorteio.getCodigoValidacao()));
   }
   
   @Test
   public void testB() {
      DistribuicaoProcesso.objects.filter("data__year=2016").forEach(System.out::println);
      DistribuicaoProcesso.objects.filter("data__year__gte=2014").forEach(System.out::println);
      System.out.println(DistribuicaoProcesso.objects.filter("data__year__gte=2014").count());
      DistribuicaoProcesso.objects.filter("data__startswith='2017-04-07'").forEach(System.out::println);
      System.out.println(DistribuicaoProcesso.objects.filter("data__startswith='2017-04-07'").count());
   }
   
}
