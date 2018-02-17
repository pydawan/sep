package br.gov.go.agr.secretariageral.sep.jsf.bean;

import static br.gov.go.agr.secretariageral.sep.model.InstanciaJuizo.CAMARA_DE_JULGAMENTO;
import static br.gov.go.agr.secretariageral.sep.model.InstanciaJuizo.CONSELHO_REGULADOR;
import static br.gov.go.agr.secretariageral.sep.model.SituacaoCadastro.ATIVO;
import static br.gov.go.agr.secretariageral.sep.model.Url.PAGINA_SORTEIO_PESQUISAR;
import static br.gov.go.agr.secretariageral.sep.model.Url.PAGINA_SORTEIO_REALIZAR;
import static br.gov.go.agr.secretariageral.sep.model.Url.PAGINA_SORTEIO_RESULTADO;
import static br.gov.go.agr.secretariageral.sep.model.Url.PAGINA_SORTEIO_RESULTADO_RELATORIO;
import static br.gov.go.agr.secretariageral.sep.model.Url.PAGINA_SORTEIO_VALIDAR;
import static br.gov.go.agr.secretariageral.sep.searching.OpcoesCampoPesquisa.campoPesquisa;
import static br.gov.go.agr.secretariageral.sep.service.SegurancaService.Validador.gerarCodigoValidacao;
import static br.org.jext.DateUtil.formatarData;
import static br.org.verify.Verify.isEmptyOrNull;
import static br.org.verify.Verify.isNotEmptyOrNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIOutput;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.component.tabview.Tab;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import br.gov.go.agr.secretariageral.sep.jsf.JSF;
import br.gov.go.agr.secretariageral.sep.model.DistribuicaoProcesso;
import br.gov.go.agr.secretariageral.sep.model.InstanciaJuizo;
import br.gov.go.agr.secretariageral.sep.model.Processo;
import br.gov.go.agr.secretariageral.sep.model.Relator;
import br.gov.go.agr.secretariageral.sep.model.Sorteio;
import br.gov.go.agr.secretariageral.sep.model.Usuario;
import br.gov.go.agr.secretariageral.sep.service.ServiceException;
import br.gov.go.agr.secretariageral.sep.service.SorteioService;
import jedi.app.auditoria.model.Auditoria;
import jedi.app.auditoria.model.OperacaoAuditoria;
import jedi.types.DateTime;

/**
 * @author thiago
 * @version v1.0.0 13/02/2017
 * @since v1.0.0
 */
@ViewScoped
@ManagedBean(name = "sorteio")
public class SorteioBean extends CadastroBean<Sorteio> implements Serializable {
   
   private static final long serialVersionUID = 1L;
   private static String mensagem = "";
   private DualListModel<Relator> listaSelecaoRelatores;
   private DualListModel<Processo> listaSelecaoProcessos;
   private List<Relator> relatoresDisponiveis;
   private List<Relator> relatoresEscolhidos;
   private List<Processo> processosDisponiveis;
   private List<Processo> processosEscolhidos;
   private InstanciaJuizo instancia;
   private List<Sorteio> resultado;
   private Date dataInicial;
   private Date dataFinal;
   private String instanciaRelatorio;
   private String relatorio;
   private Subject subject;
   private Usuario usuario;
   
   public SorteioBean() {
      setFormId("ns");
      setTitulo();
      setMensagemConfirmacaoRemocaoRegistro("Deseja realmente remover o sorteio?");
      opcoesCampoPesquisa.add(campoPesquisa("qual campo?", ""));
      opcoesCampoPesquisa.add(campoPesquisa("id", "id"));
      opcoesCampoPesquisa.add(campoPesquisa("nº processo", "processo.numero"));
      opcoesCampoPesquisa.add(campoPesquisa("instância", "processo.instancia"));
      opcoesCampoPesquisa.add(campoPesquisa("interessado", "processo.interessado"));
      opcoesCampoPesquisa.add(campoPesquisa("relator", "processo.relator.nome"));
      opcoesCampoPesquisa.add(campoPesquisa("realizado em", "data"));
      camposPesquisa = JSF.selectOneMenu(opcoesCampoPesquisa);
      camposPesquisa.setValue("");
      camposPesquisa.setRequired(false);
   }
   
   @PostConstruct
   public void init() {
      instancia = null;
      relatoresDisponiveis = SorteioService.LISTA_RELATORES_VAZIA;
      relatoresEscolhidos = SorteioService.LISTA_RELATORES_VAZIA;
      listaSelecaoRelatores = new DualListModel<>(relatoresDisponiveis, relatoresEscolhidos);
      processosDisponiveis = SorteioService.LISTA_PROCESSOS_VAZIA;
      processosEscolhidos = SorteioService.LISTA_PROCESSOS_VAZIA;
      listaSelecaoProcessos = new DualListModel<>(processosDisponiveis, processosEscolhidos);
      subject = SecurityUtils.getSubject();
      usuario = (Usuario) subject.getSession().getAttribute("usuario");
   }
   
   public void setTitulo() {
      if (PAGINA_SORTEIO_REALIZAR.isRequestedURI()) {
         setTitulo("SORTEAR PROCESSOS");
      } else if (PAGINA_SORTEIO_PESQUISAR.isRequestedURI()) {
         setTitulo("PESQUISAR SORTEIO");
      } else if (PAGINA_SORTEIO_VALIDAR.isRequestedURI()) {
         setTitulo("VALIDAR SORTEIO");
      } else {
         
      }
   }
   
   public DualListModel<Relator> getListaSelecaoRelatores() {
      return listaSelecaoRelatores;
   }
   
   public void setListaSelecaoRelatores(DualListModel<Relator> listaSelecaoRelatores) {
      this.listaSelecaoRelatores = listaSelecaoRelatores;
   }
   
   public DualListModel<Processo> getListaSelecaoProcessos() {
      return listaSelecaoProcessos;
   }
   
   public void setListaSelecaoProcessos(DualListModel<Processo> listaSelecaoProcessos) {
      this.listaSelecaoProcessos = listaSelecaoProcessos;
   }
   
   public List<Relator> getRelatoresDisponiveis() {
      return relatoresDisponiveis;
   }
   
   public void setRelatoresDisponiveis(List<Relator> relatoresDisponiveis) {
      this.relatoresDisponiveis = relatoresDisponiveis;
   }
   
   public List<Relator> getRelatoresEscolhidos() {
      return relatoresEscolhidos;
   }
   
   public void setRelatoresEscolhidos(List<Relator> relatoresEscolhidos) {
      this.relatoresEscolhidos = relatoresEscolhidos;
   }
   
   public List<Processo> getProcessosDisponiveis() {
      return processosDisponiveis;
   }
   
   public void setProcessosDisponiveis(List<Processo> processosDisponiveis) {
      this.processosDisponiveis = processosDisponiveis;
   }
   
   public List<Processo> getProcessosEscolhidos() {
      return processosEscolhidos;
   }
   
   public void setProcessosEscolhidos(List<Processo> processosEscolhidos) {
      this.processosEscolhidos = processosEscolhidos;
   }
   
   public InstanciaJuizo getInstancia() {
      return instancia;
   }
   
   public void setInstancia(InstanciaJuizo instancia) {
      this.instancia = instancia;
   }
   
   public List<Sorteio> getResultado() {
      return resultado;
   }
   
   public void setResultado(List<Sorteio> resultado) {
      this.resultado = resultado;
   }
   
   public Date getDataInicial() {
      return dataInicial;
   }
   
   public void setDataInicial(Date dataInicial) {
      this.dataInicial = dataInicial;
   }
   
   public Date getDataFinal() {
      return dataFinal;
   }
   
   public void setDataFinal(Date dataFinal) {
      this.dataFinal = dataFinal;
   }
   
   public String getInstanciaRelatorio() {
      return instanciaRelatorio;
   }
   
   public void setInstanciaRelatorio(String instanciaRelatorio) {
      this.instanciaRelatorio = instanciaRelatorio;
   }
   
   public String getRelatorio() {
      if (isNotEmptyOrNull(dataInicial, dataFinal)) {
         relatorio = String.format(
            "/sep/spring/relatorio/sorteio/%s/%s/%s",
            new DateTime(dataInicial).toString("yyyy-MM-dd"),
            new DateTime(dataFinal).toString("yyyy-MM-dd"),
            instanciaRelatorio
         );
      }
      return relatorio;
   }
   
   public void setRelatorio(String relatorio) {
      this.relatorio = relatorio;
   }
   
   public void carregarRelatores() {
      relatoresDisponiveis = SorteioService.buscarRelatores();
      relatoresEscolhidos = new ArrayList<>();
   }
   
   public void carregarMembrosCamaraJulgamento() {
      relatoresDisponiveis = SorteioService.buscarMembrosCamaraJulgamento();
      relatoresEscolhidos = new ArrayList<>();
   }
   
   public void carregarMembrosConselhoRegulador() {
      relatoresDisponiveis = SorteioService.buscarMembrosConselhoRegulador();
      relatoresEscolhidos = new ArrayList<>();
   }
   
   public void carregarRelator() {
      Relator relator = SorteioService.sortearRelator();
      relatoresDisponiveis = new ArrayList<>();
      relatoresEscolhidos = new ArrayList<>();
      relatoresDisponiveis.add(relator);
   }
   
   public void carregarMembroCamaraJulgamento() {
      Relator relator = SorteioService.sortearMembroCamaraJulgamento();
      relatoresDisponiveis = new ArrayList<>();
      relatoresEscolhidos = new ArrayList<>();
      relatoresDisponiveis.add(relator);
   }
   
   public void carregarMembroConselhoRegulador() {
      Relator relator = SorteioService.sortearMembroConselhoRegulador();
      relatoresDisponiveis = new ArrayList<>();
      relatoresEscolhidos = new ArrayList<>();
      relatoresDisponiveis.add(relator);
   }
   
   public void carregarProcessos() {
      processosDisponiveis = SorteioService.buscarProcessosSemRelator();
      processosEscolhidos = new ArrayList<>();
   }
   
   public void carregarProcessosCamaraJulgamento() {
      processosDisponiveis = SorteioService.buscarProcessosSemRelator(CAMARA_DE_JULGAMENTO);
      processosEscolhidos = new ArrayList<>();
   }
   
   public void carregarProcessosConselhoRegulador() {
      // processosDisponiveis =
      // SorteioService.buscarProcessosSemRelator(CONSELHO_REGULADOR);
      processosDisponiveis = SorteioService.buscarProcessosSorteioConselhoRegulador();
      processosEscolhidos = new ArrayList<>();
   }
   
   public void carregarProcesso() {
      Processo processo = SorteioService.sortearProcesso();
      processosDisponiveis = new ArrayList<>();
      if (processo != null) {
         processosDisponiveis.add(processo);
      }
   }
   
   public void carregarProcessoCamaraJulgamento() {
      Processo processo = SorteioService.sortearProcessoCamaraJulgamento();
      processosDisponiveis = new ArrayList<>();
      if (processo != null) {
         processosDisponiveis.add(processo);
      }
   }
   
   public void carregarProcessoConselhoRegulador() {
      Processo processo = SorteioService.sortearProcessoConselhoRegulador();
      processosDisponiveis = new ArrayList<>();
      if (processo != null) {
         processosDisponiveis.add(processo);
      }
   }
   
   public void carregarListaSelecaoRelatores() {
      listaSelecaoRelatores = new DualListModel<>(relatoresDisponiveis, relatoresEscolhidos);
   }
   
   public void carregarListaSelecaoProcessos() {
      listaSelecaoProcessos = new DualListModel<>(processosDisponiveis, processosEscolhidos);
   }
   
   public void sortear(ActionEvent event) {
      if (isNotEmptyOrNull(instancia, processosEscolhidos, relatoresEscolhidos)) {
         try {
            resultado = SorteioService.sortear(processosEscolhidos, relatoresEscolhidos);
            if (isNotEmptyOrNull(resultado)) {
               Date dataAtual = new Date();
               DistribuicaoProcesso distribuicao = new DistribuicaoProcesso();
               distribuicao.setCidade("Goiânia");
               distribuicao.setLocal("Auditório Augusto Brandão Cunha");
               distribuicao.setData(new Date());
               distribuicao.setResponsavel(usuario.getUsername());
               distribuicao.setInstancia(instancia);
               distribuicao.setSituacao(ATIVO);
               distribuicao.save();
               gerarCodigoValidacao(distribuicao);
               resultado.forEach(sorteio -> {
                  sorteio.setDistribuicao(distribuicao);
                  sorteio.save();
                  gerarCodigoValidacao(sorteio);
                  Auditoria auditoria = new Auditoria(sorteio, OperacaoAuditoria.ADICIONAR, usuario.getUsername());
                  auditoria.save();
               });
               String responsavel = usuario.getUsername();
               String cidade = distribuicao.getCidade();
               String local = distribuicao.getLocal();
               String data = formatarData(dataAtual, "dd/MM/yyyy 'às' HH:mm:ss");
               String dataArquivo = formatarData(dataAtual, "ddMMyyyyHHmmss");
               String arquivo = String.format("sorteio_%s_%s", dataArquivo, responsavel);
               String titulo = "SORTEIO - " + instancia.getLabel().toUpperCase();
               subject.getSession().setAttribute("resultado", resultado);
               subject.getSession().setAttribute("titulo", titulo);
               subject.getSession().setAttribute("responsavel", responsavel);
               subject.getSession().setAttribute("cidade", cidade);
               subject.getSession().setAttribute("local", local);
               subject.getSession().setAttribute("data", data);
               subject.getSession().setAttribute("arquivo", arquivo);
               subject.getSession().setAttribute("distribuicao", distribuicao.id());
               subject.getSession().setAttribute("comprovante", PAGINA_SORTEIO_RESULTADO_RELATORIO.url() + distribuicao.id());
               JSF.redirect(PAGINA_SORTEIO_RESULTADO);
            }
         } catch (ServiceException e) {
            JSF.error("FALHA: ", e.getMessage());
         }
      } else {
         mensagem = "A instância, os participantes e os processos são dados obrigatórios!";
         JSF.error("FALHA: ", mensagem);
      }
   }
   
   @Override
   public void selecionarInstancia(AjaxBehaviorEvent event) {
      instancia = (InstanciaJuizo) ((UIOutput) event.getSource()).getValue();
      if (instancia.equals(CAMARA_DE_JULGAMENTO)) {
         carregarMembrosCamaraJulgamento();
         carregarProcessosCamaraJulgamento();
      } else if (instancia.equals(CONSELHO_REGULADOR)) {
         carregarMembrosConselhoRegulador();
         carregarProcessosConselhoRegulador();
      } else {
         
      }
      carregarListaSelecaoRelatores();
      carregarListaSelecaoProcessos();
   }
   
   public void transferirRelator(TransferEvent event) {
      event.getItems().forEach(item -> {
         Relator relator = (Relator) item;
         if (event.isAdd()) {
            relatoresEscolhidos.add(relator);
            relatoresDisponiveis.remove(relator);
         } else {
            relatoresDisponiveis.add(relator);
            relatoresEscolhidos.remove(relator);
         }
      });
   }
   
   public void transferirProcesso(TransferEvent event) {
      event.getItems().forEach(item -> {
         Processo processo = (Processo) item;
         if (event.isAdd()) {
            processosEscolhidos.add(processo);
            processosDisponiveis.remove(processo);
         } else {
            processosDisponiveis.add(processo);
            processosEscolhidos.remove(processo);
         }
      });
   }
   
   public String getTotalRelatoresDisponiveis() {
      return "" + relatoresDisponiveis.size();
   }
   
   public String getTotalRelatoresEscolhidos() {
      return "" + relatoresEscolhidos.size();
   }
   
   public String getTotalProcessosDisponiveis() {
      return "" + processosDisponiveis.size();
   }
   
   public String getTotalProcessosEscolhidos() {
      return "" + processosEscolhidos.size();
   }
   
   public void onTabChange(TabChangeEvent event) {
      Tab tab = event.getTab();
      if (tab.getId().equals("relatorio")) {
         // TODO - desabilitar a aba do relatório quando o usuário voltar para a aba de pesquisa.
      }
   }
   
   public void visualizarComprovante(ActionEvent event) {
      JSF.tab("abas:comprovante").setDisabled(false);
      JSF.tabView("abas").setActiveIndex(1);
   }
   
   public void gerarRelatorioProcessosSorteados(ActionEvent event) {
      if (isEmptyOrNull(dataInicial, dataFinal)) {
         mensagem = "A data inicial e final são dados obrigatórios para geração do relatório!";
         JSF.error("FALHA: ", mensagem);
      } else {
         JSF.tab(":fo-relatorio:abas:relatorio").setDisabled(false);
         JSF.tabView(":fo-relatorio:abas").setActiveIndex(1);
      }
   }
   
   public void limparRelatorio(ActionEvent event) {
      dataInicial = null;
      dataFinal = null;
      instanciaRelatorio = null;
      relatorio = null;
   }
   
}
