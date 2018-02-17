package br.gov.go.agr.secretariageral.sep.test;

import static br.gov.go.agr.secretariageral.sep.model.InstanciaJuizo.CAMARA_DE_JULGAMENTO;
import static br.gov.go.agr.secretariageral.sep.model.SituacaoCadastro.ATIVO;
import static jedi.db.models.QueryPage.orderBy;
import static jedi.db.models.QueryPage.pageFilters;
import static jedi.db.models.QueryPage.pageSize;
import static jedi.db.models.QueryPage.pageStart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.javafaker.Faker;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.gov.go.agr.secretariageral.sep.model.DistribuicaoProcesso;
import br.gov.go.agr.secretariageral.sep.model.Grupo;
import br.gov.go.agr.secretariageral.sep.model.Permissao;
import br.gov.go.agr.secretariageral.sep.model.Processo;
import br.gov.go.agr.secretariageral.sep.model.Relator;
import br.gov.go.agr.secretariageral.sep.model.Sorteio;
import br.gov.go.agr.secretariageral.sep.model.Usuario;
import br.gov.go.agr.secretariageral.sep.service.ProcessoService;
import br.gov.go.agr.secretariageral.sep.service.SegurancaService;
import jedi.db.engine.JediEngine;

/**
 * Classe de testes das operações manipulação de banco de dados.
 * 
 * @author thiago
 * @version v1.0.0 18/01/2017
 * @version v1.0.1 08/11/2017
 * @since v1.0.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BancoDeDadosTest {
   
   private static final Logger logger = Logger.getLogger(BancoDeDadosTest.class.getName());
   private static final Lorem lorem = LoremIpsum.getInstance();
   
   @BeforeClass
   public static void setup() throws Exception {
      
      logger.info("Criando o banco de dados...\n");
      
      JediEngine.dropTables();
      JediEngine.createTables();
      
      Faker faker = new Faker(new Locale("pt-BR"));
      
      // Distribuição de Processo na Câmara de Julgamento.
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
                  "Rio Verde"
               )
            );
            add("local", faker.address().streetAddress());
            add("responsavel", firstName());
            add("instancia", "CAMARA_DE_JULGAMENTO");
            add("observacoes", lorem.getParagraphs(0, 4));
         }
      });
      
      // Distribuição de Processo no Conselho Regulador.
      Fixture.of(DistribuicaoProcesso.class).addTemplate("distribuicao.cr", new Rule() {
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
                  "Rio Verde"
               )
            );
            add("local", faker.address().streetAddress());
            add("responsavel", firstName());
            add("instancia", "CONSELHO_REGULADOR");
            add("observacoes", lorem.getParagraphs(0, 4));
         }
      });
      
      // Relator.
      Fixture.of(Relator.class).addTemplate("relator", new Rule() {
         {
            add("nome", name());
            add("email", regex("\\w{5,15}@agr.go.gov.br"));
            add("instancia", random("CAMARA_DE_JULGAMENTO", "CONSELHO_REGULADOR"));
         }
      });
      
      // Relator membro da Câmara de Julgamento.
      Fixture.of(Relator.class).addTemplate("relator.cj", new Rule() {
         {
            add("nome", name());
            add("email", regex("\\w{5,15}@agr.go.gov.br"));
            add("instancia", "CAMARA_DE_JULGAMENTO");
         }
      });
      
      // Relator membro do Conselho Regulador.
      Fixture.of(Relator.class).addTemplate("relator.cr", new Rule() {
         {
            add("nome", name());
            add("email", regex("\\w{5,15}@agr.go.gov.br"));
            add("instancia", "CONSELHO_REGULADOR");
         }
      });
      
      // Processo
      Fixture.of(Processo.class).addTemplate("processo", new Rule() {
         {
            add("numero", regex("201700029\\d{6}"));
            add("instancia", random("CAMARA_DE_JULGAMENTO", "CONSELHO_REGULADOR"));
            add("interessado", name());
            add("relator", null);
         }
      });
      
      // Processo da Câmara de Julgamento.
      Fixture.of(Processo.class).addTemplate("processo.cj", new Rule() {
         {
            add("numero", regex("201700029\\d{6}"));
            add("instancia", "CAMARA_DE_JULGAMENTO");
            add("interessado", name());
            add("relator", null);
         }
      });
      
      // Processo do Conselho Regulador.
      Fixture.of(Processo.class).addTemplate("processo.cr", new Rule() {
         {
            add("numero", regex("201700029\\d{6}"));
            add("instancia", "CONSELHO_REGULADOR");
            add("interessado", name());
            add("relator", null);
         }
      });
      
      // Sorteio na Câmara de Julgamento.
      Fixture.of(Sorteio.class).addTemplate("sorteio.cj", new Rule() {
         {
            add("distribuicao", one(DistribuicaoProcesso.class, "distribuicao.cj"));
            add("processo", one(Processo.class, "processo.cj"));
            add("relator", one(Relator.class, "relator.cj"));
         }
      });
      
      // Sorteio no Conselho Regulador.
      Fixture.of(Sorteio.class).addTemplate("sorteio.cr", new Rule() {
         {
            add("distribuicao", one(DistribuicaoProcesso.class, "distribuicao.cr"));
            add("processo", one(Processo.class, "processo.cr"));
            add("relator", one(Relator.class, "relator.cr"));
         }
      });
      
   }
   
   /**
    * Testa a distribuição de processos.
    */
   @Test
   public void testA() {
      
      // =========================
      // Distribuição de Processo
      // =========================
      DistribuicaoProcesso distribuicao = new DistribuicaoProcesso();
      distribuicao.setCidade("Goiânia");
      distribuicao.setLocal("Auditório Augusto Brandão Cunha");
      distribuicao.setResponsavel("Cristiane");
      distribuicao.setInstancia(CAMARA_DE_JULGAMENTO);
      distribuicao.setSituacao(ATIVO);
      
      // ==========
      // Relatores
      // ==========
      
      // Relator(nome, instancia)
      Relator relator1 = new Relator("Gilvan do Espírito Santo Batista", CAMARA_DE_JULGAMENTO);
      Relator relator2 = new Relator("Bruno Batista", CAMARA_DE_JULGAMENTO);
      Relator relator3 = new Relator("Luciana Dutra", CAMARA_DE_JULGAMENTO);
      
      // ===========
      // Processos
      // ===========
      
      // Processo(numero, interessado)
      Processo processo1 = new Processo("201700029000001", "Thiago Alexandre Martins Monteiro");
      Processo processo2 = new Processo("201700029000002", "José Taveira de Barros");
      Processo processo3 = new Processo("201700029000003", "Catarina Cardozo Azevedo");
      
      // ==========
      // Sorteios
      // ==========
      Sorteio.objects.save(
         new Sorteio(distribuicao, processo1, relator1),
         new Sorteio(distribuicao, processo2, relator2),
         new Sorteio(distribuicao, processo3, relator3)
      );
   }
   
   @Test
   public void testB() {
      List<Relator> relatores = Fixture.from(Relator.class).gimme(25, "relator");
      relatores.forEach(relator -> relator.save());
   }
   
   @Test
   public void testC() {
      List<Processo> processos = Fixture.from(Processo.class).gimme(250, "processo");
      processos.forEach(processo -> processo.save());
   }
   
   @Test
   public void testD() {
      List<Sorteio> sorteios = Fixture.from(Sorteio.class).gimme(50, "sorteio.cj");
      sorteios.forEach(sorteio -> {
         sorteio.getProcesso().setRelator(sorteio.getRelator());
         sorteio.save();
      });
   }
   
   @Test
   public void testE() {
      List<Sorteio> sorteios = Fixture.from(Sorteio.class).gimme(50, "sorteio.cr");
      sorteios.forEach(sorteio -> sorteio.save());
   }
   
   /**
    * Testa a criação de permissões no sistema.
    */
   @Test
   public void testF() {
      Permissao.objects.save(
         new Permissao("ADICIONAR_USUARIO", "usuario:adicionar"),
         new Permissao("EDITAR_USUARIO", "usuario:editar"),
         new Permissao("REMOVER_USUARIO", "usuario:remover"),
         new Permissao("INATIVAR_USUARIO", "usuario:inativar"),
         new Permissao("LISTAR_USUARIO", "usuario:listar"),
         new Permissao("VER_USUARIO", "usuario:ver"),
         new Permissao("PESQUISAR_USUARIO", "usuario:pesquisar"),
         
         new Permissao("ADICIONAR_GRUPO", "grupo:adicionar"),
         new Permissao("EDITAR_GRUPO", "grupo:editar"),
         new Permissao("REMOVER_GRUPO", "grupo:remover"),
         new Permissao("INATIVAR_GRUPO", "grupo:inativar"),
         new Permissao("LISTAR_GRUPO", "grupo:listar"),
         new Permissao("VER_GRUPO", "grupo:ver"),
         new Permissao("PESQUISAR_GRUPO", "grupo:pesquisar"),
         
         new Permissao("ADICIONAR_PERMISSAO", "permissao:adicionar"),
         new Permissao("EDITAR_PERMISSAO", "permissao:editar"),
         new Permissao("REMOVER_PERMISSAO", "permissao:remover"),
         new Permissao("INATIVAR_PERMISSAO", "permissao:inativar"),
         new Permissao("LISTAR_PERMISSAO", "permissao:listar"),
         new Permissao("VER_PERMISSAO", "permissao:ver"),
         new Permissao("PESQUISAR_PERMISSAO", "permissao:pesquisar"),
         
         new Permissao("ADICIONAR_PROCESSO", "processo:adicionar"),
         new Permissao("EDITAR_PROCESSO", "processo:editar"),
         new Permissao("REMOVER_PROCESSO", "processo:remover"),
         new Permissao("INATIVAR_PROCESSO", "processo:inativar"),
         new Permissao("LISTAR_PROCESSO", "processo:listar"),
         new Permissao("VER_PROCESSO", "processo:ver"),
         new Permissao("PESQUISAR_PROCESSO", "processo:pesquisar"),
         new Permissao("DISTRIBUIR_PROCESSO", "processo:distribuir"),
         
         new Permissao("ADICIONAR_RELATOR", "relator:adicionar"),
         new Permissao("EDITAR_RELATOR", "relator:editar"),
         new Permissao("REMOVER_RELATOR", "relator:remover"),
         new Permissao("INATIVAR_RELATOR", "relator:inativar"),
         new Permissao("LISTAR_RELATOR", "relator:listar"),
         new Permissao("VER_RELATOR", "relator:ver"),
         new Permissao("PESQUISAR_RELATOR", "relator:pesquisar"),
         
         new Permissao("ADICIONAR_SORTEIO", "sorteio:adicionar"),
         new Permissao("EDITAR_SORTEIO", "sorteio:editar"),
         new Permissao("REMOVER_SORTEIO", "sorteio:remover"),
         new Permissao("INATIVAR_SORTEIO", "sorteio:inativar"),
         new Permissao("LISTAR_SORTEIO", "sorteio:listar"),
         new Permissao("VER_SORTEIO", "sorteio:ver"),
         new Permissao("PESQUISAR_SORTEIO", "sorteio:pesquisar"),
         new Permissao("IMPRIMIR_SORTEIO", "sorteio:imprimir"),
         
         new Permissao("ENVIAR_EMAIL", "email:enviar")
      );
   }
   
   /**
    * Testa a criação de grupos e a atribuição de permissões aos mesmos.
    */
   @Test
   public void testG() {
      // Recupera as permissões de usuário.
      List<Permissao> nenhumaPermissao = new ArrayList<>();
      List<Permissao> todasPermissoes = Permissao.objects.all();
      List<Permissao> permissoesProcesso = Permissao.objects.filter("valor__startswith='processo'");
      List<Permissao> permissoesRelator = Permissao.objects.filter("valor__startswith='relator'");
      List<Permissao> permissoesSorteio = Permissao.objects.filter("valor__startswith='sorteio'");
      
      List<Permissao> permissoesAdmin = new ArrayList<>();
      permissoesAdmin.addAll(todasPermissoes);
      
      List<Permissao> permissoesSecretariaGeral = new ArrayList<>();
      permissoesSecretariaGeral.addAll(permissoesProcesso);
      permissoesSecretariaGeral.addAll(permissoesRelator);
      permissoesSecretariaGeral.addAll(permissoesSorteio);
      // Crianda os grupos de usuário e lhes atribui as permissões.
      Grupo.objects.save(
         new Grupo("COORDENACAO_INFORMATICA", permissoesAdmin),
         new Grupo("GERENCIA_SECRETARIA_GERAL", permissoesSecretariaGeral),
         new Grupo("AGR", nenhumaPermissao)
      );
   }
   
   /**
    * Testa a criação de usuários do sistema.
    */
   @Ignore
   @Test
   public void testH() {
      Usuario usuario = new Usuario();
      usuario.setNome("Thiago Alexandre Martins Monteiro");
      usuario.setUsername("thiago-amm");
      usuario.setPassword("thiago");
      usuario.setEmail("thiago.amm.agr@gmail.com");
      usuario.setAdmin(true);
      usuario.setGrupo(Grupo.objects.get("nome", "COORDENACAO_INFORMATICA"));
      SegurancaService.Criptografia.gerarSenha(usuario);
      usuario.save();
   }
   
   @Test
   public void testI() {
      Processo.objects.filter("dataAdicao__startswith='22/03/2017 15:45'");
      Processo.objects.page(pageStart(0), pageSize(10), orderBy("-id"), pageFilters((String[]) null));
   }
   
   @Test
   public void testJ() {
      ProcessoService.ConselhoRegulador.buscarProcessosSorteio().forEach(System.out::println);
   }
   
   @Test
   public void testK() {
      DistribuicaoProcesso.objects.filter("data__year=2016").forEach(System.out::println);
      DistribuicaoProcesso.objects.filter("data__year__gte=2014").forEach(System.out::println);
      System.out.println(DistribuicaoProcesso.objects.filter("data__year__gte=2014").count());
      DistribuicaoProcesso.objects.filter("data__hour=12").forEach(System.out::println);
   }
   
   @Test
   public void testL() {
      System.out.println(Relator.objects.filter("nome__regex=^Gil"));
      System.out.println(Relator.objects.filter("nome__iregex=sta$"));
   }
   
}
