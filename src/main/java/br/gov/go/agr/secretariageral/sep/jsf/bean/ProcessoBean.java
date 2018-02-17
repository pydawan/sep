package br.gov.go.agr.secretariageral.sep.jsf.bean;

import static br.gov.go.agr.secretariageral.sep.searching.OpcoesCampoPesquisa.campoPesquisa;
import static br.org.jext.StringUtil.format;
import static jedi.db.sql.Sql.exp;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.gov.go.agr.secretariageral.sep.jsf.JSF;
import br.gov.go.agr.secretariageral.sep.model.AcaoFormulario;
import br.gov.go.agr.secretariageral.sep.model.InstanciaJuizo;
import br.gov.go.agr.secretariageral.sep.model.Processo;

/**
 * Define algumas operações básicas da tela de cadastro de processos.
 * 
 * @author thiago
 * @version v1.0.0 13/02/2017
 * @since v1.0.0
 */
@ViewScoped
@ManagedBean(name = "processo")
public class ProcessoBean extends CadastroBean<Processo> implements Serializable {
   
   private static final long serialVersionUID = 1L;
   
   public ProcessoBean() {
      setFormId("pc");
      setTitulo("PROCESSOS");
      setMensagemConfirmacaoRemocaoRegistro("Deseja realmente remover o processo?");
      setMensagemConfirmacaoInativacaoRegistro("Deseja realmente inativar o processo?");
      opcoesCampoPesquisa.add(campoPesquisa("qual campo?", ""));
      opcoesCampoPesquisa.add(campoPesquisa("id", "id"));
      opcoesCampoPesquisa.add(campoPesquisa("número", "numero"));
      opcoesCampoPesquisa.add(campoPesquisa("instância", "instancia"));
      opcoesCampoPesquisa.add(campoPesquisa("interessado", "interessado"));
      opcoesCampoPesquisa.add(campoPesquisa("relator", "relator.nome"));
      camposPesquisa = JSF.selectOneMenu(opcoesCampoPesquisa);
      camposPesquisa.setValue("");
      camposPesquisa.setRequired(false);
   }
   
   public String getMascaraNumero() {
      // return String.format("%s00029xxxxxx", anoAtual());
      return "xxxxxxxxxxxxxxx";
   }
   
   @Override
   public void salvar(ActionEvent event) {
      super.beforeSave();
      String numero = registro.getNumero();
      String instancia = registro.getInstancia();
      boolean jaExiste = Processo.objects.where(exp("numero = %s AND instancia = '%s'", numero, instancia)).count() > 0;
      if (jaExiste) {
         instancia = InstanciaJuizo.get(instancia).nome();
         String mensagemErro = format(
            "Já exite um processo cadastrado com o número %s %s %s!",
            numero,
            instancia.equals("Câmara de Julgamento") ? "na" : "no",
            instancia
         );
         JSF.error("FALHA: ", mensagemErro);
      } else {
         registro.save();
         if (acao.equals(AcaoFormulario.ADICIONAR.getValor())) {
            JSF.info("SUCESSO: ", "Registro adicionado com sucesso!");
         } else if (acao.equals(AcaoFormulario.EDITAR.getValor())) {
            JSF.info("SUCESSO: ", "Registro editado com sucesso!");
         } else {
            
         }
      }
      super.criarNovo();
      super.afterSave();
   }
   
}
