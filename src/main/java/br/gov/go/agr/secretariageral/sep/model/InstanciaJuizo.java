package br.gov.go.agr.secretariageral.sep.model;

import static br.org.verify.Verify.isNotEmptyOrNull;

import java.util.StringJoiner;

import br.gov.go.agr.secretariageral.sep.jsf.JSF.SelectOption;


/**
 * Representa a instância ou grau de juízo
 * no quão são julgados os processos na AGR.
 * 
 * @author thiago
 * @version v1.0.0 20/01/2017
 * @since v1.0.0
 */
public enum InstanciaJuizo implements SelectOption {
   
   CAMARA_DE_JULGAMENTO("Câmara de Julgamento", "CAMARA_DE_JULGAMENTO", 1), 
   CONSELHO_REGULADOR("Conselho Regulador", "CONSELHO_REGULADOR", 2);
      
   private final String nome;
   private final String valor;
   private final Integer grau;
   
   private InstanciaJuizo(final String nome, final String valor, final Integer grau) {
      this.nome = nome;
      this.valor = valor;
      this.grau = grau;
   }
   
   public String getNome() {
      return nome;
   }
   
   public String nome() {
      return getNome();
   }
   
   public String getValor() {
      return valor;
   }
   
   public String valor() {
      return getValor();
   }
   
   public Integer getGrau() {
      return grau;
   }
   
   public Integer grau() {
      return getGrau();
   }
   
   @Override
   public String toString() {
      return valor;
   }
   
   public static String[] getNomes() {
      String[] nomes = new String[values().length];
      for (int i = 0; i < values().length; i++) {
         nomes[i] = values()[i].getNome();
      }
      return nomes;
   }
   
   public static InstanciaJuizo getValorPeloNome(String nome) {
      if (isNotEmptyOrNull(nome)) {
         for (InstanciaJuizo instancia : values()) {
            if (instancia.getValor().equals(nome)) {
               return instancia;
            }
         }
      }
      return null;
   }
   
   public static InstanciaJuizo get(String nome) {
      return getValorPeloNome(nome);
   }
   
   public static String[][] getNomesEValores() {
      String[][] array = new String[values().length][2];
      for (int i = 0; i < array.length; i++) {
         array[i] = new String[2];
         array[i][0] = values()[i].getNome();
         array[i][1] = values()[i].getValor();
      }
      return array;
   }

   @Override
   public String getLabel() {
      return nome;
   }

   @Override
   public Object getValue() {
      return valor;
   }
   
   public static String todas() {
      StringJoiner joiner = new StringJoiner(", ");
      for (InstanciaJuizo instancia : InstanciaJuizo.values()) {
         joiner.add(String.format("'%s'", instancia.getValor()));
      }
      return joiner.toString();
   }
   
}
