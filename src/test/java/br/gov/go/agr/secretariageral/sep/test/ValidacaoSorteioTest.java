package br.gov.go.agr.secretariageral.sep.test;

import static br.gov.go.agr.secretariageral.sep.model.SituacaoCadastro.ATIVO;
import static br.gov.go.agr.secretariageral.sep.service.SegurancaService.Validador.gerarCodigoValidacao;
import static br.org.jext.DateUtil.datetime;
import static br.org.jext.PrintUtil.printf;
import static br.org.jext.PrintUtil.println;
import static org.junit.Assert.fail;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.gov.go.agr.secretariageral.sep.model.DistribuicaoProcesso;
import br.gov.go.agr.secretariageral.sep.model.InstanciaJuizo;
import br.gov.go.agr.secretariageral.sep.model.Relator;
import net.glxn.qrgen.javase.QRCode;

public class ValidacaoSorteioTest {
   
   @Ignore
   @Test
   public void testConstanciaCodigoValidacao() {
      DistribuicaoProcesso distribuicao = new DistribuicaoProcesso();
      distribuicao.setResponsavel("Cristiane");
      distribuicao.setCidade("Goiânia");
      distribuicao.setLocal("Auditório Augusto Brandão Cunha");
      distribuicao.setData(datetime(2017, 4, 11, 11, 9, 10));
      distribuicao.setInstancia(InstanciaJuizo.CAMARA_DE_JULGAMENTO);
      distribuicao.setSituacao(ATIVO);
      for (int i = 0; i < 10; i++) {
         gerarCodigoValidacao(distribuicao);
         System.out.println(distribuicao.getCodigoValidacao());
      }
   }
   
   @Ignore
   @Test
   public void testColisaoCodigoValidacao() {
      Fixture.of(DistribuicaoProcesso.class).addTemplate("distribuicao.constante", new Rule() {
         {
            add("responsavel", "Cristiane");
            add("cidade", "Goiânia");
            add("local", "Auditório Augusto Brandão Cunha");
            add("data", datetime());
            add("instancia", "CAMARA_DE_JULGAMENTO");
         }
      });
      Fixture.of(DistribuicaoProcesso.class).addTemplate("distribuicao.variavel", new Rule() {
         {
            add("data", randomDate("2016-01-01", "2017-03-21", new SimpleDateFormat("yyyy-MM-dd")));
            add("cidade", random("Goiânia", "Aparecida de Goiânia", "Anápolis", "Goianésia", "Jaraguá", "Rio Verde"));
            add("responsavel", firstName());
            add("instancia", random("CAMARA_DE_JULGAMENTO", "CONSELHO_REGULADOR"));
         }
      });
      List<String> codigosValidacao = new ArrayList<>();
      List<DistribuicaoProcesso> distribuicoes = Fixture.from(DistribuicaoProcesso.class).gimme(100, "distribuicao.variavel");
      distribuicoes.forEach(distribuicao -> {
         gerarCodigoValidacao(distribuicao);
         System.out.println(distribuicao.getCodigoValidacao());
         if (codigosValidacao.contains(distribuicao.getCodigoValidacao())) {
            fail("O CÓDIGO DE VALIDAÇÃO GERADO JÁ ESTÁ SENDO USADO!");
         } else {
            codigosValidacao.add(distribuicao.getCodigoValidacao());
         }
      });
   }

   @Ignore
   @Test
   public void testQRCode() {
      File file = QRCode.from("Hello World").file();
      System.out.println(file);
   }
   
   @Test
   public void test() {
      Relator relator = Relator.objects.get("id", 5);
      println(relator.getSorteioSet());
      println(relator.getParticipouDeSorteio());
      printf("Nome: %s, Idade: %d", "Thiago Monteiro", 34);
   }
   
}
