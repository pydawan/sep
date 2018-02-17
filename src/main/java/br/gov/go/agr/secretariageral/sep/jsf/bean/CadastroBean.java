package br.gov.go.agr.secretariageral.sep.jsf.bean;

import static br.gov.go.agr.secretariageral.sep.searching.CriterioPesquisa.COMECA_COM;
import static br.gov.go.agr.secretariageral.sep.searching.CriterioPesquisa.CONTEM;
import static br.gov.go.agr.secretariageral.sep.searching.CriterioPesquisa.DIFERENTE_DE;
import static br.gov.go.agr.secretariageral.sep.searching.CriterioPesquisa.FORA_DO_INTERVALO;
import static br.gov.go.agr.secretariageral.sep.searching.CriterioPesquisa.IGUAL_A;
import static br.gov.go.agr.secretariageral.sep.searching.CriterioPesquisa.MAIOR_QUE;
import static br.gov.go.agr.secretariageral.sep.searching.CriterioPesquisa.MAIOR_QUE_OU_IGUAL_A;
import static br.gov.go.agr.secretariageral.sep.searching.CriterioPesquisa.MENOR_QUE;
import static br.gov.go.agr.secretariageral.sep.searching.CriterioPesquisa.MENOR_QUE_OU_IGUAL_A;
import static br.gov.go.agr.secretariageral.sep.searching.CriterioPesquisa.NAO_COMECA_COM;
import static br.gov.go.agr.secretariageral.sep.searching.CriterioPesquisa.NAO_CONTEM;
import static br.gov.go.agr.secretariageral.sep.searching.CriterioPesquisa.NAO_TERMINA_COM;
import static br.gov.go.agr.secretariageral.sep.searching.CriterioPesquisa.NENHUM;
import static br.gov.go.agr.secretariageral.sep.searching.CriterioPesquisa.NO_INTERVALO;
import static br.gov.go.agr.secretariageral.sep.searching.CriterioPesquisa.TERMINA_COM;
import static br.gov.go.agr.secretariageral.sep.searching.OpcoesCriterioPesquisa.criterioPesquisa;
import static br.org.verify.Verify.isNotEmptyOrNull;
import static br.org.verify.Verify.isNull;
import static jedi.db.models.QueryPage.orderBy;
import static jedi.db.models.QueryPage.pageFilters;
import static jedi.db.models.QueryPage.pageSize;
import static jedi.db.models.QueryPage.pageStart;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIOutput;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ComponentSystemEvent;

import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.go.agr.secretariageral.sep.jsf.JSF;
import br.gov.go.agr.secretariageral.sep.model.AcaoFormulario;
import br.gov.go.agr.secretariageral.sep.model.InstanciaJuizo;
import br.gov.go.agr.secretariageral.sep.model.SituacaoCadastro;
import br.gov.go.agr.secretariageral.sep.searching.OpcoesCampoPesquisa;
import br.gov.go.agr.secretariageral.sep.searching.OpcoesCriterioPesquisa;
import br.gov.go.agr.secretariageral.sep.searching.ParametroPesquisa;
import br.gov.go.agr.secretariageral.sep.searching.ParametrosPesquisa;
import jedi.app.auditoria.model.Auditoria;
import jedi.app.auditoria.model.OperacaoAuditoria;
import jedi.db.exceptions.DatabaseException;
import jedi.db.models.Manager;
import jedi.db.models.Model;
import jedi.db.models.QueryPageOrder;
import jedi.db.models.QuerySet;

/**
 * Define uma tela de cadastro genérica
 * com suporte a CRUD (Create, Read, Update, Delete), pesquisa e paginação de
 * resultados.
 * 
 * @author thiago
 * @version v1.0.2 10/02/2017
 * @sicne v1.0.0 06/02/2017
 */
public abstract class CadastroBean<T extends Model> {
   
   private static final List<? extends Model> listaVazia = new ArrayList<>(0);
   private static final String GLOBAL_FILTER_ID = ":fo-%s:globalFilter";
   private static final String TABELA_REGISTROS_ID = ":fo-%s:registros";
   private static final String VALOR_PESQUISA_TEXTO_ID = ":fo-%s:registros:valorPesquisaTexto";
   private static final String VALOR_PESQUISA_CALENDARIO_ID = ":fo-%s:registros:valorPesquisaCalendario";
   private static final String VALOR_PESQUISA_SELECAO_ID = ":fo-%s:registros:valorPesquisaSelecao";
   private String mensagem;
   protected String formId;
   protected Class<? extends Model> classeRegistro;
   protected Manager objects;
   protected String titulo;
   protected String acao;
   protected String entidade;
   protected T registro;
   protected LazyDataModel<T> registros;
   protected DataTable tabelaRegistros;
   protected SelectOneMenu camposPesquisa;
   protected SelectOneMenu criteriosPesquisa;
   protected SelectOneMenu situacoesCadastroPesquisa;
   protected SelectOneMenu instanciasCadastroPesquisa;
   protected SelectOneMenu campoValorPesquisaSelecao;
   // age no carregamento da página.
   protected ParametrosPesquisa filtroPesquisaEstatico;
   // age através da interação do usuário.
   protected ParametrosPesquisa filtroPesquisaDinamico;
   protected OpcoesCampoPesquisa opcoesCampoPesquisa;
   protected OpcoesCriterioPesquisa opcoesCriterioPesquisa;
   protected boolean valorPesquisaTexto;
   protected boolean valorPesquisaCalendario;
   protected boolean valorPesquisaSelecao;
   // protected String globalFilter;
   protected ParametroPesquisa parametroPesquisa;
   protected String campoPesquisaSelecionado;
   protected String criterioPesquisaSelecionado;
   protected String valorPesquisa;
   protected String mensagemConfirmacaoRemocaoRegistro;
   protected String mensagemConfirmacaoInativacaoRegistro;
   protected String tituloDialog;
   protected OperacaoAuditoria operacaoAuditoria = OperacaoAuditoria.INDETERMINADA;
   protected String usuarioAuditoria = "";
   protected T registroAuditoria;
   protected Auditoria auditoria;
   
   @ManagedProperty(value = "#{usuarioMB}")
   protected UsuarioBean usuarioBean;
   
   @PostConstruct
   public void init() {
      usuarioAuditoria = usuarioBean.getUsuario().getUsername();
   }
   
   @SuppressWarnings("unchecked")
   public CadastroBean() {
      Type gsc = getClass().getGenericSuperclass();
      ParameterizedType param = (ParameterizedType) gsc;
      Type arg = param.getActualTypeArguments()[0];
      classeRegistro = (Class<? extends Model>) arg;
      objects = new Manager(classeRegistro);
      filtroPesquisaEstatico = new ParametrosPesquisa();
      filtroPesquisaDinamico = new ParametrosPesquisa();
      opcoesCampoPesquisa = new OpcoesCampoPesquisa();
      opcoesCriterioPesquisa = new OpcoesCriterioPesquisa();
      campoPesquisaSelecionado = "";
      criterioPesquisaSelecionado = "";
      valorPesquisa = "";
      titulo = "";
      acao = AcaoFormulario.INDETERMINADA.getValor();
      entidade = classeRegistro.getSimpleName();
      try {
         registro = (T) classeRegistro.newInstance();
      } catch (InstantiationException | IllegalAccessException e) {
         e.printStackTrace();
      }
      // paginação de registros.
      registros = new LazyDataModel<T>() {
         
         private static final long serialVersionUID = 1L;
         
         @Override
         public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            String filtroPesquisaDinamico = filters.isEmpty() ? "" : filters.get("globalFilter").toString();
            QuerySet<T> lista = null;
            QueryPageOrder orderBy = sortField == null ? orderBy("-id") : orderBy(sortField, sortOrder.toString());
            if (filtroPesquisaEstatico.isEmpty()) {
               if (filtroPesquisaDinamico.isEmpty()) {
                  lista = objects.page(pageStart(first), pageSize(pageSize), orderBy);
                  setRowCount(objects.count());
               } else {
                  lista = objects.page(pageStart(first), pageSize(pageSize), orderBy, pageFilters(filtroPesquisaDinamico));
                  setRowCount(objects.count(filtroPesquisaDinamico));
               }
            } else {
               if (filtroPesquisaDinamico.isEmpty()) {
                  lista = objects.page(pageStart(first), pageSize(pageSize), orderBy, pageFilters(filtroPesquisaEstatico.toString()));
                  setRowCount(objects.count(filtroPesquisaEstatico.toString()));
               } else {
                  lista = objects.page(
                     pageStart(first),
                     pageSize(pageSize),
                     orderBy,
                     pageFilters(filtroPesquisaEstatico.toString(), "AND", filtroPesquisaDinamico)
                  );
                  setRowCount(objects.count(filtroPesquisaEstatico.toString(), "AND", filtroPesquisaDinamico));
               }
            }
            if (lista == null) {
               lista = (QuerySet<T>) listaVazia;
            }
            return lista;
         }
      };
      opcoesCriterioPesquisa.add(criterioPesquisa(NENHUM));
      opcoesCriterioPesquisa.add(criterioPesquisa(IGUAL_A));
      opcoesCriterioPesquisa.add(criterioPesquisa(DIFERENTE_DE));
      opcoesCriterioPesquisa.add(criterioPesquisa(MAIOR_QUE));
      opcoesCriterioPesquisa.add(criterioPesquisa(MENOR_QUE));
      opcoesCriterioPesquisa.add(criterioPesquisa(MAIOR_QUE_OU_IGUAL_A));
      opcoesCriterioPesquisa.add(criterioPesquisa(MENOR_QUE_OU_IGUAL_A));
      opcoesCriterioPesquisa.add(criterioPesquisa(CONTEM));
      opcoesCriterioPesquisa.add(criterioPesquisa(NAO_CONTEM));
      opcoesCriterioPesquisa.add(criterioPesquisa(COMECA_COM));
      opcoesCriterioPesquisa.add(criterioPesquisa(NAO_COMECA_COM));
      opcoesCriterioPesquisa.add(criterioPesquisa(TERMINA_COM));
      opcoesCriterioPesquisa.add(criterioPesquisa(NAO_TERMINA_COM));
      opcoesCriterioPesquisa.add(criterioPesquisa(NO_INTERVALO));
      opcoesCriterioPesquisa.add(criterioPesquisa(FORA_DO_INTERVALO));
      criteriosPesquisa = JSF.selectOneMenu(opcoesCriterioPesquisa);
      criteriosPesquisa.setValue("");
      criteriosPesquisa.setRequired(false);
      situacoesCadastroPesquisa = JSF.selectOneMenu(SituacaoCadastro.values());
      instanciasCadastroPesquisa = JSF.selectOneMenu(InstanciaJuizo.values());
      campoValorPesquisaSelecao = JSF.selectOneMenu(VALOR_PESQUISA_SELECAO_ID + formId);
      parametroPesquisa = new ParametroPesquisa();
   }
   
   public String getFormId() {
      return formId;
   }
   
   public void setFormId(String id) {
      this.formId = id;
   }
   
   public Class<? extends Model> getClasseRegistro() {
      return classeRegistro;
   }
   
   public String getTitulo() {
      return titulo;
   }
   
   public void setTitulo(String titulo) {
      this.titulo = titulo;
   }
   
   public String getAcao() {
      return acao;
   }
   
   public void setAcao(String acao) {
      this.acao = acao;
   }
   
   public String getEntidade() {
      return entidade;
   }
   
   public void setEntidade(String entidade) {
      this.entidade = entidade;
   }
   
   public T getRegistro() {
      return registro;
   }
   
   public void setRegistro(T registro) {
      this.registro = registro;
   }
   
   public LazyDataModel<T> getRegistros() {
      return registros;
   }
   
   public void setRegistros(LazyDataModel<T> registros) {
      this.registros = registros;
   }
   
   public DataTable getTabelaRegistros() {
      if (isNull(tabelaRegistros)) {
         String id = String.format(TABELA_REGISTROS_ID, formId);
         tabelaRegistros = JSF.dataTable(id);
      }
      return tabelaRegistros;
   }
   
   public void setTabelaRegistros(DataTable tabelaRegistros) {
      this.tabelaRegistros = tabelaRegistros;
   }
   
   public UsuarioBean getUsuarioBean() {
      return usuarioBean;
   }
   
   public void setUsuarioBean(UsuarioBean usuarioBean) {
      this.usuarioBean = usuarioBean;
   }
   
   public DataTable registros() {
      return getTabelaRegistros();
   }
   
   public SelectOneMenu getCamposPesquisa() {
      return camposPesquisa;
   }
   
   public void setCamposPesquisa(SelectOneMenu camposPesquisa) {
      this.camposPesquisa = camposPesquisa;
   }
   
   public SelectOneMenu getCriteriosPesquisa() {
      return criteriosPesquisa;
   }
   
   public void setCriteriosPesquisa(SelectOneMenu criteriosPesquisa) {
      this.criteriosPesquisa = criteriosPesquisa;
   }
   
   public SelectOneMenu getSituacoesCadastroPesquisa() {
      return situacoesCadastroPesquisa;
   }
   
   public void setSituacoesCadastroPesquisa(SelectOneMenu situacoesCadastroPesquisa) {
      this.situacoesCadastroPesquisa = situacoesCadastroPesquisa;
   }
   
   public SituacaoCadastro[] getSituacoesCadastro() {
      return SituacaoCadastro.values();
   }
   
   public void carregarValoresPesquisaSelecao(AjaxBehaviorEvent event) {
      String campo = "" + ((UIOutput) event.getSource()).getValue();
      if (campo.equals("situacao")) {
         carregarOpcoesPesquisaSituacao();
      } else if (campo.matches("^(\\w+\\.)*instancia$")) {
         carregarOpcoesPesquisaInstancia();
      } else {
         esconderCampoValorPesquisaSelecao();
      }
   }
   
   public void carregarOpcoesPesquisaSituacao() {
      JSF.clear(campoValorPesquisaSelecao);
      JSF.selectOneMenu(campoValorPesquisaSelecao, getSituacoesCadastro());
      mostrarCampoValorPesquisaSelecao();
   }
   
   public void carregarOpcoesPesquisaInstancia() {
      JSF.clear(campoValorPesquisaSelecao);
      JSF.selectOneMenu(campoValorPesquisaSelecao, getInstancias());
      mostrarCampoValorPesquisaSelecao();
   }
   
   @SuppressWarnings("unchecked")
   public void criarNovo() {
      try {
         if (classeRegistro != null) {
            registro = (T) classeRegistro.newInstance();
         }
      } catch (InstantiationException | IllegalAccessException e) {
         e.printStackTrace();
      }
   }
   
   public void adicionar(ActionEvent event) {
      acao = AcaoFormulario.ADICIONAR.getValor();
      criarNovo();
   }
   
   public void novo(ActionEvent event) {
      acao = AcaoFormulario.NOVO.getValor();
      criarNovo();
   }
   
   public void editar(T registro) {
      acao = AcaoFormulario.EDITAR.getValor();
      setRegistro(registro);
   }
   
   public void beforeAction() {
      
   }
   
   public void afterAction() {
      
   }
   
   public void beforeAdd() {
      
   }
   
   public void afterAdd() {
      
   }
   
   public void beforeEdit() {
      
   }
   
   public void afterEdit() {
      
   }
   
   public void beforeRemove() {
      
   }
   
   public void afterRemove() {
      
   }
   
   public void beforeSave() {
      
   }
   
   public void afterSave() {
      
   }
   
   public void salvar(ActionEvent event) {
      beforeAction();
      beforeSave();
      try {
         registro.save();
         registroAuditoria = objects.get("id", registro.id());
         if (acao.equals(AcaoFormulario.ADICIONAR.toString())) {
            operacaoAuditoria = OperacaoAuditoria.ADICIONAR;
         } else if (acao.equals(AcaoFormulario.EDITAR.toString())) {
            operacaoAuditoria = OperacaoAuditoria.EDITAR;
         } else if (acao.equals(AcaoFormulario.SALVAR.toString())) {
            operacaoAuditoria = OperacaoAuditoria.SALVAR;
         } else {
            operacaoAuditoria = OperacaoAuditoria.INDETERMINADA;
         }
         auditoria = new Auditoria(registroAuditoria, operacaoAuditoria, usuarioAuditoria);
         auditoria.save();
         if (acao.equals(AcaoFormulario.ADICIONAR.getValor())) {
            JSF.info("SUCESSO: ", "Registro adicionado com sucesso!");
         } else if (acao.equals(AcaoFormulario.EDITAR.getValor())) {
            JSF.info("SUCESSO: ", "Registro editado com sucesso!");
         } else {
            
         }
      } catch (DatabaseException e) {
         mensagem = e.getCause().getMessage();
         Pattern pattern = Pattern.compile("^Duplicate entry \'(.*)\' for key \'(\\w+)\'$");
         Matcher matcher = pattern.matcher(mensagem);
         if (matcher.matches()) {
            String campo = matcher.group(2);
            String valor = matcher.group(1);
            mensagem = String.format("Já existe um registro cadastrado com %s igual a %s!", campo, valor);
            mensagem = mensagem.replace("numero", "número");
            JSF.error("FALHA: ", mensagem);
         }
      }
      afterSave();
      afterAction();
      criarNovo();
   }
   
   public void limpar(ActionEvent event) {
      criarNovo();
   }
   
   public void confirmarRemocao(T registro) {
      acao = "" + AcaoFormulario.REMOVER;
      setRegistro(registro);
   }
   
   public void confirmarInativacao(T registro) {
      acao = "" + AcaoFormulario.INATIVAR;
      setRegistro(registro);
   }
   
   public void inativar(ActionEvent actionEvent) {
      try {
         registro.getClass().getMethod("inativar").invoke(registro, (Object[]) null);
         getTabelaRegistros().loadLazyData();
         JSF.info("SUCESSO: ", "Registro inativado com sucesso!");
      } catch (Exception e) {
         JSF.error("FALHA: ", "Falha na inativação do registro.");
      }
   }
   
   public void remover(ActionEvent actionEvent) {
      try {
         beforeAction();
         beforeRemove();
         if (acao.equals(AcaoFormulario.REMOVER.toString())) {
            operacaoAuditoria = OperacaoAuditoria.REMOVER;
         } else {
            operacaoAuditoria = OperacaoAuditoria.INDETERMINADA;
         }
         registroAuditoria = objects.get("id", registro.id());
         registro.delete();
         auditoria = new Auditoria(registroAuditoria, operacaoAuditoria, usuarioAuditoria);
         auditoria.save();
         afterRemove();
         afterAction();
         getTabelaRegistros().loadLazyData();
         JSF.info("SUCESSO: ", "Registro removido com sucesso!");
      } catch (Exception e) {
         JSF.error("FALHA: ", "Falha na remoção do registro.");
      }
   }
   
   public ParametrosPesquisa getFiltroPesquisaEstatico() {
      return filtroPesquisaEstatico;
   }
   
   public void setFiltroPesquisaEstatico(ParametrosPesquisa filtroPesquisaEstatico) {
      this.filtroPesquisaEstatico = filtroPesquisaEstatico;
   }
   
   public ParametrosPesquisa filtroPesquisaEstatico() {
      return getFiltroPesquisaEstatico();
   }
   
   public void filtroPesquisaEstatico(ParametrosPesquisa filtroPesquisaEstatico) {
      setFiltroPesquisaEstatico(filtroPesquisaEstatico);
   }
   
   public ParametrosPesquisa getFiltroPesquisaDinamico() {
      return filtroPesquisaDinamico;
   }
   
   public void setFiltroPesquisaDinamico(ParametrosPesquisa filtroPesquisaDinamico) {
      this.filtroPesquisaDinamico = filtroPesquisaDinamico;
   }
   
   public ParametrosPesquisa filtroPesquisaDinamico() {
      return getFiltroPesquisaDinamico();
   }
   
   public void filtroPesquisaDinamico(ParametrosPesquisa filtroPesquisaDinamico) {
      setFiltroPesquisaDinamico(filtroPesquisaDinamico);
   }
   
   public OpcoesCampoPesquisa getOpcoesCampoPesquisa() {
      return opcoesCampoPesquisa;
   }
   
   public OpcoesCampoPesquisa opcoesCampoPesquisa() {
      return opcoesCampoPesquisa;
   }
   
   public OpcoesCriterioPesquisa getOpcoesCriterioPesquisa() {
      return opcoesCriterioPesquisa;
   }
   
   public OpcoesCriterioPesquisa opcoesCriterioPesquisa() {
      return opcoesCriterioPesquisa;
   }
   
   public void selecionarCampoPesquisa(AjaxBehaviorEvent event) {
      
   }
   
   public void selecionarCriterioPesquisa(AjaxBehaviorEvent event) {
      Object valor = ((UIOutput) event.getSource()).getValue();
      criterioPesquisaSelecionado = valor.toString();
      System.out.println("Critério pesquisa selecionado: " + criterioPesquisaSelecionado);
      filtroPesquisaDinamico.clear();
   }
   
   public boolean isValorPesquisaTexto() {
      return valorPesquisaTexto;
   }
   
   public void setValorPesquisaTexto(boolean valorPesquisaTexto) {
      this.valorPesquisaTexto = valorPesquisaTexto;
   }
   
   public boolean isValorPesquisaCalendario() {
      return valorPesquisaCalendario;
   }
   
   public void setValorPesquisaCalendario(boolean valorPesquisaCalendario) {
      this.valorPesquisaCalendario = valorPesquisaCalendario;
   }
   
   public boolean isValorPesquisaSelecao() {
      return valorPesquisaSelecao;
   }
   
   public void setValorPesquisaSelecao(boolean valorPesquisaSelecao) {
      this.valorPesquisaSelecao = valorPesquisaSelecao;
   }
   
   public String getGlobalFilterId() {
      String id = String.format(GLOBAL_FILTER_ID, formId);
      return id;
   }
   
   public InputText getCampoValorPesquisaTexto() {
      String id = String.format(VALOR_PESQUISA_TEXTO_ID, formId);
      InputText inputText = JSF.inputText(id);
      return inputText;
   }
   
   public Calendar getCampoValorPesquisaCalendario() {
      String id = String.format(VALOR_PESQUISA_CALENDARIO_ID, formId);
      Calendar calendar = JSF.calendar(id);
      return calendar;
   }
   
   public SelectOneMenu getCampoValorPesquisaSelecao() {
      return campoValorPesquisaSelecao;
   }
   
   public void setCampoValorPesquisaSelecao(SelectOneMenu campoValorPesquisaSelecao) {
      this.campoValorPesquisaSelecao = campoValorPesquisaSelecao;
   }
   
   public String getCampoPesquisaSelecionado() {
      return campoPesquisaSelecionado;
   }
   
   public void setCampoPesquisaSelecionado(String campoPesquisaSelecionado) {
      this.campoPesquisaSelecionado = campoPesquisaSelecionado;
   }
   
   public String getCriterioPesquisaSelecionado() {
      return criterioPesquisaSelecionado;
   }
   
   public void setCriterioPesquisaSelecionado(String criterioPesquisaSelecionado) {
      this.criterioPesquisaSelecionado = criterioPesquisaSelecionado;
   }
   
   public String getValorPesquisa() {
      return valorPesquisa;
   }
   
   public void setValorPesquisa(String valorPesquisa) {
      this.valorPesquisa = valorPesquisa;
   }
   
   public void mostrarCampoValorPesquisaTexto() {
      valorPesquisaTexto = true;
      valorPesquisaCalendario = false;
      valorPesquisaSelecao = false;
      getCampoValorPesquisaCalendario().resetValue();
      getCampoValorPesquisaSelecao().resetValue();
   }
   
   public void mostrarCampoValorPesquisaSelecao() {
      valorPesquisaSelecao = true;
      valorPesquisaTexto = false;
      valorPesquisaCalendario = false;
      getCampoValorPesquisaTexto().resetValue();
      getCampoValorPesquisaCalendario().resetValue();
   }
   
   public void mostrarCampoValorPesquisaCalendario() {
      valorPesquisaCalendario = true;
      valorPesquisaSelecao = false;
      valorPesquisaTexto = false;
      getCampoValorPesquisaSelecao().resetValue();
      getCampoValorPesquisaTexto().resetValue();
   }
   
   public void esconderCampoValorPesquisa() {
      valorPesquisaTexto = false;
      valorPesquisaCalendario = false;
      valorPesquisaSelecao = false;
      getCampoValorPesquisaTexto().resetValue();
      getCampoValorPesquisaSelecao().resetValue();
      getCampoValorPesquisaCalendario().resetValue();
   }
   
   public void esconderCampoValorPesquisaSelecao() {
      valorPesquisaSelecao = false;
      getCampoValorPesquisaSelecao().resetValue();
   }
   
   public String getMensagemConfirmacaoRemocaoRegistro() {
      return mensagemConfirmacaoRemocaoRegistro;
   }
   
   public void setMensagemConfirmacaoRemocaoRegistro(String mensagemConfirmacaoRemocaoRegistro) {
      this.mensagemConfirmacaoRemocaoRegistro = mensagemConfirmacaoRemocaoRegistro;
   }
   
   public String getMensagemConfirmacaoInativacaoRegistro() {
      return mensagemConfirmacaoInativacaoRegistro;
   }
   
   public void setMensagemConfirmacaoInativacaoRegistro(String mensagemConfirmacaoInativacaoRegistro) {
      this.mensagemConfirmacaoInativacaoRegistro = mensagemConfirmacaoInativacaoRegistro;
   }
   
   public InstanciaJuizo[] getInstancias() {
      return InstanciaJuizo.values();
   }
   
   public void selecionarInstancia(AjaxBehaviorEvent event) {
      Object value = ((UIOutput) event.getSource()).getValue();
      String instancia = value.toString();
      try {
         Field field = classeRegistro.getDeclaredField("instancia");
         field.setAccessible(true);
         field.set(registro, instancia);
      } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
         
      }
   }
   
   public void selecionarSituacaoCadastro(AjaxBehaviorEvent event) {
      Object value = ((UIOutput) event.getSource()).getValue();
      String situacaoCadastro = value.toString();
      try {
         if (situacaoCadastro.equals("" + SituacaoCadastro.INATIVO)) {
            registro.getClass().getMethod("inativar").invoke(registro, (Object[]) null);
         } else {
            registro.getClass().getMethod("ativar").invoke(registro, (Object[]) null);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   public String getTituloDialog() {
      return String.format("%s %s", acao.toUpperCase(), entidade.toUpperCase());
   }
   
   public void setTituloDialog(String tituloDialog) {
      this.tituloDialog = tituloDialog;
   }
   
   @SuppressWarnings("unchecked")
   public List<T> pesquisar() {
      if (isNotEmptyOrNull(filtroPesquisaEstatico)) {
         Manager objects = new Manager(classeRegistro);
         return objects.reversePage(pageStart(0), pageSize(5), pageFilters(filtroPesquisaEstatico.toString()));
      }
      return (List<T>) listaVazia;
   }
   
   public void onLoad(ComponentSystemEvent event) {
      
   }
   
}
