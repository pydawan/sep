package br.gov.go.agr.secretariageral.sep.model;

import static br.org.verify.Verify.isNotEmptyOrNull;
import static br.org.verify.Verify.isNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.gov.go.agr.secretariageral.sep.logging.Logger;

/**
 * @author thiago
 * @version v1.0.1 14/03/2017
 * @since v1.0.0 14/02/2017
 */
public class GestorSorteio {
   
   private static final Random random = new Random();
   private Integer numeroDaRodada = 0;
   private Integer numeroDoSorteio = 0;
   private Integer processoSorteado = null;
   private Integer relatorSorteado = null;
   private List<Processo> processos;
   private List<Relator> relatores;
   private List<Relator> participantes;
   private List<Sorteio> resultado;
   
   public GestorSorteio(List<Processo> processos, List<Relator> relatores) {
      this.processos = processos;
      this.relatores = relatores;
      participantes = new ArrayList<>();
      resultado = new ArrayList<>();
      Logger.Sorteio.CABECALHO();
      Logger.Sorteio.DADOS_INICIAIS();
      System.out.println();
      if (isNotEmptyOrNull(processos)) {
         Logger.Sorteio.DADOS_INICIAIS_PROCESSOS(processos);
      }
      if (isNotEmptyOrNull(relatores)) {
         participantes = new ArrayList<>();
         participantes.addAll(relatores);
         Logger.Sorteio.DADOS_INICIAIS_RELATORES(relatores);
      }
      System.out.println();
   }
   
   public GestorSorteio() {
      this(new ArrayList<>(), new ArrayList<>());
   }
   
   public List<Sorteio> getResultadoSorteio() {
      return resultado;
   }
   
   public void sortear() {
      if (isNotNull(relatores) && isNotEmptyOrNull(processos)) {
         if (relatores.isEmpty()) {
            relatores.addAll(participantes);
            numeroDaRodada++;
            Logger.Sorteio.NOVA_RODADA(processos, relatores);
         }
         Logger.print(Logger.Sorteio.LINHA_SORTEIO);
         processoSorteado = random.nextInt(processos.size());
         relatorSorteado = random.nextInt(relatores.size());
         numeroDoSorteio++;
         Processo processo = processos.get(processoSorteado);
         Relator relator = relatores.get(relatorSorteado);
         Logger.Sorteio.RESULTADO_DO_SORTEIO(numeroDoSorteio);
         Logger.Sorteio.RODADA_DO_SORTEIO(numeroDaRodada);
         Logger.Sorteio.PROCESSO_E_RELATOR_SORTEADOS(processo, relator);
         Sorteio sorteio = new Sorteio();
         sorteio.setRelator(relator);
         sorteio.setProcesso(processo);
         processo.setRelator(relator);
         resultado.add(sorteio);
         processos.remove(processo);
         relatores.remove(relator);
         Logger.Sorteio.PROCESSOS_E_RELATORES_RESTANTES(processos, relatores);
         Logger.print(Logger.Sorteio.LINHA_SORTEIO);
         sortear();
      }
   }
   
   public Processo sortearProcesso() {
      processoSorteado = random.nextInt(processos.size());
      return processos.get(processoSorteado);
   }
   
   public Processo sortearProcesso(List<Processo> processos) {
      Processo processo = null;
      if (isNotEmptyOrNull(processos)) {
         processoSorteado = random.nextInt(processos.size());
         processo = processos.get(processoSorteado);
      }
      return processo;
   }
   
   public Relator sortearRelator() {
      relatorSorteado = random.nextInt(relatores.size());
      return relatores.get(relatorSorteado);
   }
   
   public Relator sortearRelator(List<Relator> relatores) {
      Relator relator = null;
      if (isNotEmptyOrNull(relatores)) {
         relatorSorteado = random.nextInt(relatores.size());
         relator = relatores.get(relatorSorteado);
      }
      return relator;
   }
   
}
