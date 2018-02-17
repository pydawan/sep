package br.gov.go.agr.secretariageral.sep.jsf.bean;

import static br.gov.go.agr.secretariageral.sep.searching.OpcoesCampoPesquisa.campoPesquisa;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.gov.go.agr.secretariageral.sep.jsf.JSF;
import br.gov.go.agr.secretariageral.sep.model.Relator;

/**
 * @author thiago
 * @version v1.0.0 09/02/2017
 * @since v1.0.0
 */
@ViewScoped
@ManagedBean(name = "relator")
public class RelatorBean extends CadastroBean<Relator> implements Serializable {
   
   private static final long serialVersionUID = 1L;
   
   public RelatorBean() {
      setFormId("cr"); // cr = cadastro relator
      setTitulo("RELATORES");
      setMensagemConfirmacaoRemocaoRegistro("Deseja realmente remover o relator?");
      setMensagemConfirmacaoInativacaoRegistro("Deseja realmente inativar o relator?");
      opcoesCampoPesquisa.add(campoPesquisa("qual campo?", ""));
      opcoesCampoPesquisa.add(campoPesquisa("id", "id"));
      opcoesCampoPesquisa.add(campoPesquisa("nome", "nome"));
      opcoesCampoPesquisa.add(campoPesquisa("instância", "instancia"));
      opcoesCampoPesquisa.add(campoPesquisa("e-mail", "email"));
      opcoesCampoPesquisa.add(campoPesquisa("situação", "situacao"));
      camposPesquisa = JSF.selectOneMenu(opcoesCampoPesquisa);
      camposPesquisa.setValue("");
      camposPesquisa.setRequired(false);
   }
   
}
