package br.gov.go.agr.secretariageral.sep.service;

import static br.org.verify.Verify.isEmptyOrNull;
import static br.org.verify.Verify.isNull;

import java.util.ArrayList;
import java.util.List;

import br.gov.go.agr.secretariageral.sep.model.InstanciaJuizo;
import br.gov.go.agr.secretariageral.sep.model.Processo;

/**
 * @author thiago
 * @version v1.0.0 23/02/2017
 * @since v1.0.0
 */
public class ProcessoService {
   
   public static final List<Processo> LISTA_PROCESSOS_VAZIA = new ArrayList<>(0);
   
   public static List<Processo> buscarProcessos() {
      return Processo.objects.all();
   }
   
   public static List<Processo> buscarProcessosSemRelator() {
      List<Processo> processos = Processo.objects.filter("relator__isnull=true", "and", "situacao='ATIVO'");
      if (isEmptyOrNull(processos)) {
         processos = LISTA_PROCESSOS_VAZIA;
      }
      return processos;
   }
   
   public static List<Processo> buscarProcessosSemRelator(InstanciaJuizo instancia) {
      if (isNull(instancia)) {
         return LISTA_PROCESSOS_VAZIA;
      }
      return Processo.objects.filter(
         "relator__isnull=true", 
         "and", 
         String.format("instancia__exact='%s'", instancia), 
         "and", 
         "situacao='ATIVO'"
      );
   }
   
   public static final class CamaraJulgamento {
      
      public static List<Processo> buscarProcessosAtivosSorteados() {
         List<Processo> processos = Processo.objects.filter(
            "instancia__exact='CAMARA_DE_JULGAMENTO'",
            "situacao__exact='ATIVO'",
            "relator__isnull=false"
         );
         return processos;
      }
      
      public static List<Processo> buscarProcessosAtivosNaoSorteados() {
         List<Processo> processos = Processo.objects.filter(
            "instancia__exact='CAMARA_DE_JULGAMENTO'",
            "situacao__exact='ATIVO'",
            "relator__isnull=true"
         );
         return processos; 
      }
      
      public static List<Processo> buscarProcessosInativosSorteados() {
         List<Processo> processos = Processo.objects.filter(
            "instancia__exact='CAMARA_DE_JULGAMENTO'",
            "situacao__exact='INATIVO'",
            "relator__isnull=false"
         );
         return processos;
      }
      
      public static List<Processo> buscarProcessosInativosNaoSorteados() {
         List<Processo> processos = Processo.objects.filter(
            "instancia__exact='CAMARA_DE_JULGAMENTO'",
            "situacao__exact='INATIVO'",
            "relator__isnull=true"
         );
         return processos;
      }
      
      public static List<Processo> buscarProcessosSorteio() {
         return buscarProcessosAtivosNaoSorteados();
      }
      
   }
   
   public static final class ConselhoRegulador {
      
      public static List<Processo> buscarProcessosAtivosSorteados() {
         List<Processo> processos = Processo.objects.filter(
            "instancia__exact='CONSELHO_REGULADOR'",
            "situacao__exact='ATIVO'",
            "relator__isnull=false"
         );
         return processos;
      }
      
      public static List<Processo> buscarProcessosAtivosNaoSorteados() {
         List<Processo> processos = Processo.objects.filter(
            "instancia__exact='CONSELHO_REGULADOR'",
            "situacao__exact='ATIVO'",
            "relator__isnull=true"
         );
         return processos; 
      }
      
      public static List<Processo> buscarProcessosInativosSorteados() {
         List<Processo> processos = Processo.objects.filter(
            "instancia__exact='CONSELHO_REGULADOR'",
            "situacao__exact='INATIVO'",
            "relator__isnull=false"
         );
         return processos;
      }
      
      public static List<Processo> buscarProcessosInativosNaoSorteados() {
         List<Processo> processos = Processo.objects.filter(
            "instancia__exact='CONSELHO_REGULADOR'",
            "situacao__exact='INATIVO'",
            "relator__isnull=true"
         );
         return processos;
      }
      
      public static List<Processo> buscarProcessosSorteio() {
         List<Processo> processos = ConselhoRegulador.buscarProcessosAtivosNaoSorteados();
         return processos;
      }
      
   }
   
}
