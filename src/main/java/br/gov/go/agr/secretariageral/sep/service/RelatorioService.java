package br.gov.go.agr.secretariageral.sep.service;

import static br.gov.go.agr.secretariageral.sep.model.InstanciaJuizo.CAMARA_DE_JULGAMENTO;
import static br.gov.go.agr.secretariageral.sep.model.InstanciaJuizo.CONSELHO_REGULADOR;
import static br.gov.go.agr.secretariageral.sep.service.SegurancaService.Validador.gerarCodigoValidacao;
import static br.org.jext.DateUtil.dataFormatada;
import static br.org.jext.DateUtil.dataPorExtenso;
import static br.org.jext.DateUtil.diaMesAno;
import static br.org.jext.DateUtil.format;
import static br.org.jext.StringUtil.format;
import static br.org.jext.StringUtil.lower;
import static br.org.verify.Verify.isEmptyOrNull;
import static br.org.verify.Verify.isNotEmpty;
import static br.org.verify.Verify.isNotEmptyOrNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import br.gov.go.agr.secretariageral.sep.model.DistribuicaoProcesso;
import br.gov.go.agr.secretariageral.sep.model.InstanciaJuizo;
import br.gov.go.agr.secretariageral.sep.model.Processo;
import br.gov.go.agr.secretariageral.sep.model.Sorteio;
import br.gov.go.agr.secretariageral.sep.model.Usuario;
import br.gov.go.agr.secretariageral.sep.report.ReportLoader;
import net.glxn.qrgen.javase.QRCode;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

public class RelatorioService {
   
   private Map<String, Object> parametros;
   private List<?> colecao;
   private String jasper = "";
   private String filename = "";
   
   public Map<String, Object> getParametros() {
      if (parametros == null) {
         parametros = new HashMap<String, Object>();
      }
      return parametros;
   }
   
   public void setParametros(Map<String, Object> parametros) {
      this.parametros = parametros;
   }
   
   public List<?> getColecao() {
      if (colecao == null) {
         colecao = new ArrayList<>();
      }
      return colecao;
   }
   
   public void setColecao(List<?> colecao) {
      this.colecao = colecao;
   }
   
   public List<?> colecao() {
      return getColecao();
   }
   
   public void colecao(List<?> colecao) {
      setColecao(colecao);
   }
   
   public String getJasper() {
      return jasper;
   }
   
   public void setJasper(String jasper) {
      this.jasper = jasper;
   }
   
   public String jasper() {
      return getJasper();
   }
   
   public void jasper(String jasper) {
      setJasper(jasper);
   }
   
   public String getFilename() {
      return filename;
   }
   
   public void setFilename(String filename) {
      this.filename = filename;
   }
   
   public String filename() {
      return getFilename();
   }
   
   /**
    * @param jasper
    * @param colecao
    * @param parametros
    * @return
    * @throws NegocioException
    * @author thiago
    */
   public byte[] gerarPDF(String jasper, List<?> colecao, Map<String, Object> parametros) throws ServiceException {
      byte[] bytes = null;
      String mensagem = "";
      if (isNotEmpty(jasper)) {
         File jasperFile = new File(ReportLoader.getFilePath(jasper));
         if (jasperFile.exists()) {
            try {
               colecao = isEmptyOrNull(colecao) ? colecao() : colecao;
               parametros = isEmptyOrNull(parametros) ? parametros() : parametros;
               JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperFile);
               JRDataSource dataSource = colecao == null ? new JREmptyDataSource() : new JRBeanCollectionDataSource(colecao);
               JasperPrint printer = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
               bytes = JasperExportManager.exportReportToPdf(printer);
            } catch (JRException e) {
               mensagem = String.format("Falha ao gerar o relatório %s!", jasper);
               throw new ServiceException(mensagem);
            }
         } else {
            mensagem = String.format("O arquivo %s não foi encontrado!", jasper);
            throw new ServiceException(mensagem);
         }
      }
      return bytes;
   }
   
   public byte[] gerarPDF(String jasper, List<?> colecao) throws ServiceException {
      return gerarPDF(jasper, colecao, null);
   }
   
   public byte[] gerarPDF(String jasper) throws ServiceException {
      return gerarPDF(jasper, null);
   }
   
   public byte[] pdf(String jasper, List<?> colecao, Map<String, Object> parametros) throws ServiceException {
      return gerarPDF(jasper, colecao, parametros);
   }
   
   public byte[] pdf(String jasper, List<?> colecao) throws ServiceException {
      return pdf(jasper, colecao, null);
   }
   
   public byte[] pdf(String jasper) throws ServiceException {
      return pdf(jasper, null);
   }
   
   public byte[] pdf() throws ServiceException {
      return pdf(jasper());
   }
   
   public Map<String, Object> parametros() {
      return getParametros();
   }
   
   public void parametro(String chave, Object valor) {
      getParametros().put(chave, valor);
   }
   
   public Object parametro(String chave) {
      return getParametros().get(chave);
   }
   
   public JRBeanCollectionDataSource convert(Collection<?> collection) {
      JRBeanCollectionDataSource dataSource = null;
      if (collection != null) {
         dataSource = new JRBeanCollectionDataSource(collection);
      }
      return dataSource;
   }
   
   public String filename(InstanciaJuizo instancia, Date data) {
      String filename = "";
      if (instancia != null) {
         if (data == null) {
            data = new Date();
         }
         if (instancia.equals(CAMARA_DE_JULGAMENTO)) {
            filename = "sorteio_camara-julgamento";
         } else if (instancia.equals(CONSELHO_REGULADOR)) {
            filename = "sorteio_conselho-regulador";
         } else {
            
         }
         Subject subject = SecurityUtils.getSubject();
         Usuario usuario = (Usuario) subject.getSession().getAttribute("usuario");
         filename = String.join("_", filename, dataFormatada(data, "ddMMyyyyHHmmss"), usuario.getUsername());
         filename = filename + ".pdf";
      }
      return filename;
   }
   
   public String filename(InstanciaJuizo instancia) {
      return filename(instancia, null);
   }
   
   public String filename(String nome, Date data) {
      String filename = "";
      if (isNotEmptyOrNull(nome)) {
         data = data == null ? new Date() : data;
         Subject subject = SecurityUtils.getSubject();
         Usuario usuario = (Usuario) subject.getSession().getAttribute("usuario");
         filename = String.join("_", nome, dataFormatada(data, "ddMMyyyyHHmmss"), usuario.getUsername());
         filename = filename + ".pdf";
      }
      return filename;
   }
   
   public String filename(String nome) {
      return filename(nome, null);
   }
   
   public byte[] gerarRelatorioProcessosSorteados(String distribuicaoId) {
      byte[] relatorio = null;
      DistribuicaoProcesso distribuicao = DistribuicaoProcesso.objects.get("id", distribuicaoId);
      InstanciaJuizo instancia = InstanciaJuizo.valueOf(distribuicao.getInstancia());
      List<?> colecao = distribuicao.getSorteioSet();
      Date agora = distribuicao.getData();
      String data = dataFormatada(agora, "dd/MM/yyyy");
      String horario = dataFormatada(agora, "HH:mm:ss");
      String nomeRelatorio = "";
      if (instancia.equals(CAMARA_DE_JULGAMENTO)) {
         this.jasper("relatorioSorteioCamaraJulgamento.jasper");
         nomeRelatorio = String.format("comprovante_sorteio_cj_%s.pdf", dataFormatada(agora, "ddMMyyyy-HHmmss"));
      } else {
         this.jasper("relatorioSorteioConselhoRegulador.jasper");
         nomeRelatorio = String.format("comprovante_sorteio_cr_%s.pdf", dataFormatada(agora, "ddMMyyyy-HHmmss"));
      }
      this.parametro("instancia", instancia.getNome().toUpperCase());
      this.parametro("departamento", "Gerência da Secretaria Geral");
      this.parametro("data", data);
      this.parametro("horario", horario);
      this.parametro("local", distribuicao.getLocal());
      this.parametro("logo", ReportLoader.getFilePath("logo.png"));
      this.parametro("codigoValidacao", distribuicao.getCodigoValidacao());
      this.parametro("qrCode", QRCode.from(distribuicao.getCodigoValidacao()).file().getAbsolutePath());
      this.filename(filename(instancia, agora));
      this.colecao(colecao);
      relatorio = this.pdf();
      EmailService email = new EmailService();
      email.autenticar("desenvolvimento.agr.go@gmail.com", "desagr2015");
      email.remetente("SEP");
      email.para("tecnologia.agr@gmail.com");
      email.destinatarios("thiago.amm.agr@gmail.com");
      String assunto = String.format(
         "AGR | SORTEIO ELETRÔNICO DE PROCESSOS | COMPROVANTE DE SORTEIO | DIA %s", 
         diaMesAno(agora)
      );
      email.assunto(assunto);
      StringBuilder mensagem = new StringBuilder();
      mensagem.append("<h3>SORTEIO ELETRÔNICO DE PROCESSOS</h3>");
      mensagem.append("<hr/>");
      mensagem.append("<p>");
      mensagem.append(
         format(
            "Segue em anexo o comprovante do sorteio realizado eletronicamente nesta %s ", 
            lower(dataPorExtenso(agora))
         )
      );
      mensagem.append("<br/>");
      mensagem.append("pelo SEP (Sistema de Sorteio Eletrônico de Processos) da Agência Goiana de Regulação.");
      mensagem.append("</p>");
      mensagem.append("<hr/>");
      mensagem.append("<p>");
      mensagem.append("Atenciosamente,<br/>");
      mensagem.append("Coordenação de Tecnologia da Informação.");
      mensagem.append("</p>");
      email.html(mensagem.toString());
      email.anexarPdf(relatorio, nomeRelatorio);
      email.enviar();
      return relatorio;
   }
   
   public byte[] relatorioProcessosSorteados(String distribuicaoId) {
      return gerarRelatorioProcessosSorteados(distribuicaoId);
   }
   
   public byte[] gerarRelatorioProcessosSorteados(String dataInicial, String dataFinal, String instancia) {
      byte[] relatorio = null;
      if (isNotEmptyOrNull(dataInicial, dataFinal, instancia)) {
         String texto = "";
         List<Processo> processos = new ArrayList<>();
         instancia = instancia.equals("TODAS") ? InstanciaJuizo.todas() : String.format("'%s'", instancia);
         String criterioData = String.format("distribuicao.data__range=['%s 00:00:00', '%s 23:59:59']", dataInicial, dataFinal);
         String criterioInstancia = String.format("distribuicao.instancia__in=[%s]", instancia);
         List<Sorteio> sorteios = Sorteio.objects.<Sorteio> filter(criterioData, criterioInstancia);
         if (isNotEmptyOrNull(sorteios)) {
            Processo processo = null;
            for (Sorteio sorteio : sorteios) {
               processo = sorteio.getProcesso();
               if (processo.getInstancia().equals("CAMARA_DE_JULGAMENTO")) {
                  processo.setInstancia("Câmara de Julgamento");
               } else if (processo.getInstancia().equals("CONSELHO_REGULADOR")) {
                  processo.setInstancia("Conselho Regulador");
               } else {
                  
               }
               processo.setObservacoes(format(sorteio.getDistribuicao().getData(), "dd/MM/yyyy"));
               processos.add(processo);
               texto += sorteio.getId();
            }
            String codigoValidacao = gerarCodigoValidacao(texto);
            this.parametro("codigoValidacao", codigoValidacao);
            this.parametro("qrCode", QRCode.from(codigoValidacao).file().getAbsolutePath());
         }
         this.jasper("relatorioProcessosSorteados.jasper");
         this.parametro("departamento", "Gerência da Secretaria Geral");
         this.parametro("logo", ReportLoader.getFilePath("logo.png"));
         String[] data = dataInicial.split("-");
         dataInicial = format("%s/%s/%s", data[2], data[1], data[0]);
         this.parametro("dataInicial", dataInicial);
         data = dataFinal.split("-");
         dataFinal = format("%s/%s/%s", data[2], data[1], data[0]);
         this.parametro("dataFinal", dataFinal);
         this.filename(filename("relatorio"));
         this.colecao(processos);
         relatorio = this.pdf();
      }
      return relatorio;
   }
   
   public byte[] relatorioProcessosSorteados(String dataInicial, String dataFinal, String instancia) {
      return gerarRelatorioProcessosSorteados(dataInicial, dataFinal, instancia);
   }
   
}
