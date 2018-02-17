package br.gov.go.agr.secretariageral.sep.spring.controller;

import static br.org.verify.Verify.isNotEmptyOrNull;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.gov.go.agr.secretariageral.sep.service.RelatorioService;

/**
 * @author thiago
 * @version v1.0.0 31/03/2017
 * @since v1.0.0
 */
@Controller
@RequestMapping("/relatorio")
public class RelatorioController {
   
   private RelatorioService relatorioService = new RelatorioService();
   
   @RequestMapping(value = "/sorteio/{distribuicao}", method = RequestMethod.GET)
   public void relatorioComprovanteSorteio(
      HttpServletRequest request, 
      HttpServletResponse response,
      @PathVariable("distribuicao") String distribuicao) 
      throws IOException {
      if (isNotEmptyOrNull(distribuicao)) {
         byte[] bytes = relatorioService.relatorioProcessosSorteados(distribuicao);
         response.setContentType("application/pdf");
         response.setHeader("Content-Disposition", "inline;filename=\"" + relatorioService.filename() + "\"");
         response.getOutputStream().write(bytes);
      }
   }
   
   @RequestMapping(value = "/sorteio/{dataInicial}/{dataFinal}/{instancia}", method = RequestMethod.GET)
   public void relatorioProcessosSorteados(
      HttpServletRequest request, 
      HttpServletResponse response, 
      @PathVariable("dataInicial") 
      String dataInicial,
      @PathVariable("dataFinal") 
      String dataFinal,
      @PathVariable("instancia")
      String instancia)
      throws IOException {
      if (isNotEmptyOrNull(dataInicial, dataFinal, instancia)) {
         byte[] bytes = relatorioService.relatorioProcessosSorteados(dataInicial, dataFinal, instancia);
         response.setContentType("application/pdf");
         response.setHeader("Content-Disposition", "inline;filename=\"" + relatorioService.filename() + "\"");
         response.getOutputStream().write(bytes);
      }
   }
   
}
