package br.gov.go.agr.secretariageral.sep.service;

import static br.gov.go.agr.secretariageral.sep.model.InstanciaJuizo.CAMARA_DE_JULGAMENTO;
import static br.gov.go.agr.secretariageral.sep.model.InstanciaJuizo.CONSELHO_REGULADOR;
import static br.org.verify.Verify.isEmptyOrNull;

import java.util.List;

import br.gov.go.agr.secretariageral.sep.model.GestorSorteio;
import br.gov.go.agr.secretariageral.sep.model.InstanciaJuizo;
import br.gov.go.agr.secretariageral.sep.model.Processo;
import br.gov.go.agr.secretariageral.sep.model.Relator;
import br.gov.go.agr.secretariageral.sep.model.Sorteio;

/**
 * Define os serviços disponívies relacionados 
 * ao sorteio de processos.
 * 
 * @author thiago
 * @version v1.0.0 23/02/2017
 * @since v1.0.0
 */
public class SorteioService {
   
   public static final List<Relator> LISTA_RELATORES_VAZIA = RelatorService.LISTA_RELATORES_VAZIA;
   public static final List<Processo> LISTA_PROCESSOS_VAZIA = ProcessoService.LISTA_PROCESSOS_VAZIA;
   
   public static List<Processo> buscarProcessosSemRelator() {
      return ProcessoService.buscarProcessosSemRelator();
   }
   
   public static List<Processo> buscarProcessosSemRelator(InstanciaJuizo instancia) {
      return ProcessoService.buscarProcessosSemRelator(instancia);
   }
   
   public static List<Processo> buscarProcessosSorteioConselhoRegulador() {
      return ProcessoService.ConselhoRegulador.buscarProcessosSorteio();
   }
   
   public static List<Relator> buscarRelatores() {
      return RelatorService.buscarRelatores();
   }
   
   public static List<Relator> buscarMembrosCamaraJulgamento() {
      return RelatorService.buscarMembrosCamaraJulgamento();
   }
   
   public static List<Relator> buscarMembrosConselhoRegulador() {
      return RelatorService.buscarMembrosConselhoRegulador();
   }
   
   public static List<Sorteio> sortear() throws ServiceException {
      List<Processo> processos = buscarProcessosSemRelator();
      List<Relator> relatores = buscarRelatores();
      if (processos.isEmpty()) {
         throw new ServiceException("ATENÇÃO: Não é possível realizar um sorteio sem antes cadastrar processos!");
      }
      GestorSorteio gestorSorteio = new GestorSorteio(processos, relatores);
      gestorSorteio.sortear();
      return gestorSorteio.getResultadoSorteio();
   }
   
   public static List<Sorteio> sortear(List<Processo> processos, List<Relator> relatores) throws ServiceException {
      if (isEmptyOrNull(processos)) {
         throw new ServiceException("ATENÇÃO: Não é possível realizar um sorteio sem antes cadastrar processos!");
      }
      if (isEmptyOrNull(relatores)) {
         throw new ServiceException("ATENÇÃO: Não é possível realizar um sorteio sem antes cadastrar os participantes!");
      }
      GestorSorteio gestorSorteio = new GestorSorteio(processos, relatores);
      gestorSorteio.sortear();
      return gestorSorteio.getResultadoSorteio();
   }
   
   public static Processo sortearProcesso(List<Processo> processos) {
      GestorSorteio gestorSorteio = new GestorSorteio();
      return gestorSorteio.sortearProcesso(processos);
   }
   
   public static Relator sortearRelator(List<Relator> relatores) {
      GestorSorteio gestorSorteio = new GestorSorteio();
      return gestorSorteio.sortearRelator(relatores);
   }
   
   public static Processo sortearProcesso() {
      return sortearProcesso(buscarProcessosSemRelator());
   }
   
   public static Processo sortearProcessoCamaraJulgamento() {
      return sortearProcesso(buscarProcessosSemRelator(CAMARA_DE_JULGAMENTO));
   }
   
   public static Processo sortearProcessoConselhoRegulador() {
      return sortearProcesso(buscarProcessosSemRelator(CONSELHO_REGULADOR));
   }
   
   public static Relator sortearRelator() {
      return sortearRelator(buscarRelatores());
   }
   
   public static Relator sortearMembroCamaraJulgamento() {
      return sortearRelator(buscarMembrosCamaraJulgamento());
   }
   
   public static Relator sortearMembroConselhoRegulador() {
      return sortearRelator(buscarMembrosConselhoRegulador());
   }
   
}
