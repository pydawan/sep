/**
 * @author thiago
 * @version v1.0.0 20/02/2017
 * @since v1.0.0
 */

// filtros
var filtroId = '#fo-pc\\:registros\\:col-id\\:filter';
var filtroNumero = '#fo-pc\\:registros\\:col-numero\\:filter';
var filtroInstancia = '#fo-pc\\:registros\\:col-instancia_input';
var filtroInteressado = '#fo-pc\\:registros\\:col-interessado\\:filter';
var filtroRelator = '#fo-pc\\:registros\\:col-relator\\:filter';
var filtroSorteado = '#fo-pc\\:registros\\:col-processo-sorteado_input';
var filtroSituacao = '#fo-pc\\:registros\\:col-situacao_input';

// campos
var campoNumero = '#fo-registro\\:numero';
var campoInstancia = '#fo-registro\\:instancia_input';
var campoInteressado = '#fo-registro\\:interessado';
var campoSituacao = '#fo-registro\\:situacaoCadastro_input';
var campoObservacoes = '#fo-registro\\:observacoes';

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
      valido = notEmpty(valor) && valor.notContains('_');
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

$(document).on('keyup', campoNumero, function() {
   validarFormulario();
});

$(document).on('keyup', campoInteressado, function() {
   validarFormulario();
});

$(document).ready(function() {
   configurar_pesquisa('fo-pc');
});
