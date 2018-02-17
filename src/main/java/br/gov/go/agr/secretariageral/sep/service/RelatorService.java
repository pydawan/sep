package br.gov.go.agr.secretariageral.sep.service;

import static br.org.verify.Verify.isEmptyOrNull;

import java.util.ArrayList;
import java.util.List;

import br.gov.go.agr.secretariageral.sep.model.Relator;

/**
 * @author thiago
 * @version v1.0.0 08/03/2017
 * @since v1.0.0
 */
public class RelatorService {
   
   public static final List<Relator> LISTA_RELATORES_VAZIA = new ArrayList<>(0);
   
   public static List<Relator> buscarRelatores() {
      List<Relator> relatores = Relator.objects.all();
      if (isEmptyOrNull(relatores)) {
         relatores = LISTA_RELATORES_VAZIA;
      }
      return relatores;
   }
   
   public static List<Relator> buscarMembrosCamaraJulgamento() {
      List<Relator> membrosCJ = Relator.objects.filter("instancia__exact='CAMARA_DE_JULGAMENTO'", "and", "situacao='ATIVO'");
      if (isEmptyOrNull(membrosCJ)) {
         membrosCJ = LISTA_RELATORES_VAZIA;
      }
      return membrosCJ;
   }
   
   public static List<Relator> buscarMembrosConselhoRegulador() {
      List<Relator> membrosCR = Relator.objects.filter("instancia__exact='CONSELHO_REGULADOR'", "and", "situacao='ATIVO'");
      if (isEmptyOrNull(membrosCR)) {
         membrosCR = LISTA_RELATORES_VAZIA;
      }
      return membrosCR;
   }
   
}
