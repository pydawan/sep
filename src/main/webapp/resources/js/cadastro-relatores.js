/**
 * @author thiago
 * @version v1.0.0 20/02/2017
 * @since v1.0.0
 */

// filtros
var filtroColuna = "#fo-cr\\:registros\\:col-id\\:filter";
var filtroNome = "#fo-cr\\:registros\\:col-nome\\:filter";
var filtroInstancia = "#fo-cr\\:registros\\:col-instancia_input";
var filtroEmail = "#fo-cr\\:registros\\:col-email\\:filter";
var filtroSituacao = "#fo-cr\\:registros\\:col-situacao_input";

// campos
var campoNome = '#fo-registro\\:nome';
var campoEmail = '#fo-registro\\:email';
var campoInstancia = "#fo-registro\\:instancia_input";
var campoInstancia = "#fo-registro\\:situacao_input";
var campoInstancia = "#fo-registro\\:observacoes";

// botões
var botaoSalvar = '#fo-registro\\:salvar';
var botaoLimpar = '#fo-registro\\:limpar';

function camposRequired() {
   var camposRequired = [];
   var $camposFormulario = $('#fo-registro').find(':input');
   $camposFormulario.each(function(i, element) {
      if ($(element).attr('data-p-required') === 'true') {
         console.log(element);
         camposRequired.push(element);
      }
   });
   return camposRequired;
}

function habilitarBotao(id) {
   id = id == null ? '' : id;
   if (id !== '') {
      $(id).removeAttr('disabled');
      $(id).attr('aria-disabled', false);
      $(id).removeClass('ui-state-disabled');
   }
}

function desabilitarBotao(id) {
   id = id == null ? '' : id;
   if (id !== '') {
      $(id).attr('disabled', 'disabled');
      $(id).attr('aria-disabled', true);
      $(id).addClass('ui-state-disabled');
   }
}

function validarFormulario() {
   var valido = false;
   var campos = camposRequired();
   var valor = '';
   for (var i = 0; i < campos.length; i++) {
      valor = $(campos[i]).val();
      valido = valor !== '';
      valido = valor.notContains('_');
      if (!valido) {
         break;
      }
   }
   if (valido) {
      habilitarBotao(botaoSalvar);
   } else {
      desabilitarBotao(botaoSalvar);
   }
}

function carregarFormulario() {
   PF('di-registro').show();
   desabilitarBotao(botaoSalvar);
}

function limpar() {
   desabilitarBotao(botaoSalvar);
}

$(document).on('keyup', campoNome, function() {
   validarFormulario();
});

$(document).ready(function() {
   configurar_pesquisa('fo-cr');
});
