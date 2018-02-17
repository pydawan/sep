package br.gov.go.agr.secretariageral.sep.service;

import static br.org.verify.Verify.isNotEmptyOrNull;
import static br.org.verify.Verify.isNotNull;
import static br.org.verify.Verify.notContainsNull;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

@SuppressWarnings("unused")
public class EmailService {
   
   private HtmlEmail email;
   private String remetente;
   private List<InternetAddress> destinatarios;
   private String usuario;
   private String senha;
   private DefaultAuthenticator autenticador;
   
   public EmailService() {
      remetente = "AGR | SORTEIO ELETRÔNICO DE PROCESSOS";
      destinatarios = new ArrayList<>();
      email = new HtmlEmail();
      email.setHostName("smtp.gmail.com");
      email.setSmtpPort(587);
      email.setStartTLSEnabled(true);
   }
   
   public void autenticar(String usuario, String senha) {
      this.usuario = usuario;
      this.senha = senha;
      autenticador = new DefaultAuthenticator(usuario, senha);
      email.setAuthenticator(autenticador);
   }
   
   public void setDestinatario(String email) {
      if (isNotEmptyOrNull(email)) {
         try {
            InternetAddress destinatario = new InternetAddress(email);
            destinatarios.add(destinatario);
            this.email.setCc(destinatarios);
         } catch (AddressException e) {
            e.printStackTrace();
         } catch (EmailException e) {
            e.printStackTrace();
         }
      }
   }
   
   public void setDestinatarios(String... emails) {
      if (isNotEmptyOrNull((Object[]) emails)) {
         for (String email : emails) {
            setDestinatario(email);
         }
      }
   }
   
   public void destinatario(String email) {
      setDestinatario(email);
   }
   
   public void destinatarios(String... emails) {
      setDestinatarios(emails);
   }
   
   public void anexarPdf(byte[] bytes, String nome, String descricao) {
      if (notContainsNull(bytes, nome, descricao)) {
         try {
            email.attach(new ByteArrayDataSource(bytes, "application/pdf"), nome, descricao, EmailAttachment.ATTACHMENT);
         } catch (EmailException e) {
            e.printStackTrace();
         }
      }
   }
   
   public void anexarPdf(byte[] bytes, String nome) {
      anexarPdf(bytes, nome, "");
   }
   
   public void anexarPdf(byte[] bytes) {
      anexarPdf(bytes, "", "");
   }
   
   public void remetente(String remetente) {
      if (isNotEmptyOrNull(remetente)) {
         try {
            email.setFrom(usuario, remetente);
         } catch (EmailException e) {
            e.printStackTrace();
         }
      }
   }
   
   public void assunto(String assunto) {
      if (isNotEmptyOrNull(assunto)) {
         email.setSubject(assunto);
      }
   }
   
   public void mensagem(String mensagem) {
      if (isNotNull(mensagem)) {
         try {
            email.setMsg(mensagem);
         } catch (EmailException e) {
            e.printStackTrace();
         }
      }
   }
   
   public void html(String html) {
      if (isNotNull(html)) {
         try {
            email.setHtmlMsg(html);
         } catch (EmailException e) {
            e.printStackTrace();
         }
      }
   }
   
   public void html(String... _html) {
      if (isNotEmptyOrNull( (Object[]) _html)) {
         for (String html : _html) {
            html(html);
         }
      }
   }
   
   public void para(String email) {
      if (isNotEmptyOrNull(email)) {
         try {
            this.email.addTo(email);
         } catch (EmailException e) {
            e.printStackTrace();
         }
      }
   }
   
   public void para(String... emails) {
      if (isNotEmptyOrNull((Object[]) emails)) {
         for (String email : emails) {
            para(email);
         }
      }
   }
   
   public void enviar() {
      new Thread(() -> {
         try {
            email.send();
         } catch (Exception e) {
            e.printStackTrace();
         }
      }).start();
   }
   
}
