/**
 * @author thiago
 * @version v1.0.0
 * @since v1.0.0
 */
Array.prototype.contains = function(obj) {
   if (obj != null) {
      if (typeof (obj) === 'number') {
         for (var i = 0; i < this.length; i++) {
            if (this[i] == obj) {
               return true;
            }
         }
      } else if (typeof (obj) === 'string') {
         var pattern = '({0})+'.format(obj);
         var regex = new RegExp(pattern, 'g');
         return regex.test(this.join(''));
      }
   }
   return false;
};

Array.prototype.index = function() {
   return this;
};


String.prototype.reverse = function() {
   var reversed = '';
   for (var i = this.length - 1; i > -1; i--) {
      reversed += this.charAt(i);
   }
   return reversed;
};

String.prototype.upper = function() {
   return this.toUpperCase();
};

String.prototype.lower = function() {
   return this.toLowerCase();
};


String.prototype.capitalize = function() {
   return this.charAt(0).upper() + this.substring(1, this.length);
};

String.prototype.isdigit = function() {
   return /^(\d)+$/.test(this);
};

String.prototype.isalnum = function() {
   return /[\w\d]+/.test(this);
};

String.prototype.isalpha = function() {
   return /^[a-zA-z]+$/.test(this);
};

String.prototype.isspace = function() {
   return /^\s+$/.test(this);
};

String.prototype.istitle = function() {
   return false;
};

String.prototype.format = function() {
   var result = '';
   var pattern = '';
   var regex = '';
   if (arguments.length == 1) {
      result = this;
      args = arguments[0];
      if (typeof (arguments[0]) === 'string') {
         args = [ arguments[0] ];
      }
      for (var i in args) {
         pattern = '\\{' + i + '\\}';
         regex = new RegExp(pattern);
         result = result.replace(regex, args[i]);
      }
   } else if (arguments.length > 1) {
      result = this;
      for (var i in arguments) {
         if (!typeof (arguments[i]) === 'string') {
            return '';
         } else {
            pattern = '\\{' + i + '\\}';
            regex = new RegExp(pattern);
            result = result.replace(regex, arguments[i]);
         }
      }
   }
   return result;
};

String.prototype.isupper = function() {
   return this == this.toUpperCase();
};

String.prototype.islower = function() {
   return this == this.toLocaleLowerCase();
};

String.prototype.ispalindrome = function() {
   return this == this.reverse();
};

String.prototype.times = function(n) {
   var result = '';
   if (n != null && n > 0) {
      for (var i = 0; i < n; i++) {
         result += this;
      }
   }
   return result;
};

String.prototype.startswith = function(s) {
   var result = false;
   if (s != null && typeof (s) === 'string') {
      var pattern = '^(' + s + ')';
      var regex = new RegExp(pattern);
      return regex.test(this);
   }
   return result;
};

String.prototype.endswith = function(s) {
   var result = false;
   if (s != null && typeof (s) === 'string') {
      var pattern = '(' + s + ')$';
      var regex = new RegExp(pattern);
      return regex.test(this);
   }
   return result;
};

String.prototype.ljust = function(width, fillchar) {
   if (width > this.length) {
      return this + fillchar.times(width - this.length);
   }
   return this;
};

String.prototype.rjust = function(width, fillchar) {
   if (width > this.length) {
      return fillchar.times(width - this.length) + this;
   }
   return this;
};

String.prototype.center = function(width, fillchar) {
   switch (arguments.length) {
      case 1:
         if (width != null && typeof (width) === 'number' && width > this.length) {
            var times = width - this.length;
            times = Math.round(times / 2);
            return ' '.times(times) + this + ' '.times(times);
         }
         break;
      case 2:
         var width = arguments[0];
         var fillchar = arguments[1];
         if (width != null && typeof (width) === 'number' && width > this.length && fillchar != null && typeof (fillchar) === 'string') {
            var times = width - this.length;
            times = Math.round(times / 2);
            return fillchar.times(times) + this + fillchar.times(times);
         }
         break;
   }
   if (arguments.length == 1) {
   } else if (arguments.length == 2) {
      var width = arguments[0];
      var fillchar = arguments[1];
      if (arguments[0] != null && typeof (arguments[0]) === 'number' && arguments[0] > this.length && fillchar != null
         && typeof (fillchar) === 'string') {
         var times = width - this.length;
         times = Math.round(times / 2);
         return fillchar.times(times) + this + fillchar.times(times);
      }
   }
   return this;
};

String.prototype.swapcase = function() {
   return this.isupper() ? this.lower() : this.upper();
};

String.prototype.join = function(collection) {
   if (typeof (collection) === 'object') {
      return collection.join(this);
   }
   return collection;
};

String.prototype.shuffle = function() {
   if (!this.empty()) {
      var chars = [];
      var random_number = 0;
      var sorted_random_numbers = [];
      var i = 0;
      while (i < this.length) {
         random_number = Math.floor(Math.random() * this.length);
         if (!sorted_random_numbers.contains(random_number)) {
            chars.push(this.charAt(random_number));
            sorted_random_numbers.push(random_number);
            i++;
         }
      }
      return ''.join(chars);
   }
   return this;
};

String.prototype.randomize = function() {
   if (!this.empty()) {
      var chars = [];
      var random_number = 0;
      var i = 0;
      while (i < this.length) {
         random_number = Math.floor(Math.random() * this.length);
         chars.push(this.charAt(random_number));
         i++;
      }
      return ''.join(chars);
   }
   return this;
};

String.prototype.lstrip = function() {
   return this.replace(/^\s+/, '');
};

String.prototype.rstrip = function() {
   return this.replace(/\s+$/, '');
};

String.prototype.strip = function() {
   return this.rstrip().lstrip();
};

String.prototype.count = function() {
   var value = null;
   var modifier = 'g';
   if (arguments.length) {
      value = arguments[0];
   }
   if (arguments.length == 2) {
      modifier = arguments[1];
   }
   if (value && typeof (value) === 'string') {
      return (this.length - this.replace(new RegExp(value, modifier), '').length) / value.length;
   }
   return 0;
};

String.prototype.empty = function() {
   return this.length == 0 ? true : false;
};

String.prototype.notEmpty = function() {
   return !this.empty();
}

String.prototype.zfill = function(size) {
   var times = size - this.length;

   return '0'.times(times) + this;
};

String.prototype.contains = function(e) {
   var array = this.split('');
   return array.contains(e);
};

String.prototype.notContains = function(e) {
   return !this.contains(e);
};

String.prototype.index = function() {
   return this;
};

String.prototype.splitlines = function() {
   return this.split('\n');
};

String.prototype.partition = function() {
   return this;
};

String.prototype.find = function() {
   return this;
};

String.prototype.expandtabs = function() {
   return this;
};

String.prototype.title = function() {
   return this;
};

String.prototype.encode = function() {
   return this;
};

String.prototype.decode = function() {
   return this;
};

String.prototype.translate = function() {
   return this;
};

String.prototype.mul = function(n) {
   return this.times(n);
};

String.prototype.ascii_lowercase = function() {
   return 'abcdefghijklmnopqrstuvwxyz';
};

String.prototype.ascii_uppercase = function() {
   return 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
};

String.prototype.ascii_letters = function() {
   return this.ascii_lowercase() + this.ascii_uppercase();
};

String.prototype.digits = function() {
   return '0123456789';
};

String.prototype.hexdigits = function() {
   return '0123456789abcdefABCDEF';
};

String.prototype.letters = function() {
   return this;
};

String.prototype.lowercase = function() {
   return this;
};

String.prototype.uppercase = function() {
   return this;
};

String.prototype.octdigits = function() {
   return '01234567';
};

String.prototype.punctuation = function() {
   return '!"#$%&\'()*+,-./:;<=>?@[\\]^_`{|}~';
};

String.prototype.printable = function() {
   return '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!"#$%&\'()*+,-./:;<=>?@[\\]^_`{|}~ \t\n\r\x0b\x0c';
};

String.prototype.whitespace = function() {
   return '\t\n\x0b\x0c\r ';
};

function not(object) {
   return !object;
}

function empty(object) {
   var empty = false;
   if ( (typeof(object) === 'string' || object instanceof String) ) {
      empty = object.length == 0;
   }
   return empty;
}

function notEmpty(object) {
   return !empty(object);
}

// redefinindo dígito de máscara numérica.
$.mask.definitions['9'] = '';
$.mask.definitions['x'] = '[0-9]';

function isCampoValorPesquisaSelecao(form) {
   if (form != null) {
      var id = form + ":registros:camposPesquisa_input";
      var $campoPesquisa = $('*[id="' + id + '"]');
      var opcao = $campoPesquisa.val();
      switch (opcao) {
         case 'situacao':
         case 'instancia':
         case 'processo.instancia':
         case 'relator.instancia':
         case 'sorteio.processo.instancia':
         case 'sorteio.processo.relator.instancia':
            return true;
      }
   }
   return false;
}

function configurar_pesquisa(form) {

   // expressões regulares.
   const REGEX_LOCAL_TIMESTAMP = /^\d{2}\/\d{2}\/\d{4} \d{2}:\d{2}:\d{2}$/igm;
   const REGEX_SQL_TIMESTAMP = /^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}$/igm;

   // elementos.
   var registros = '*[id="' + form + ':registros"]';
   var global_filter = registros.replace('"]', '') + ':globalFilter"]';
   var campos_pesquisa = registros.replace('"]', '') + ':camposPesquisa_input"]';
   var criterios_pesquisa = registros.replace('"]', '') + ':criteriosPesquisa_input"]';
   var valor_pesquisa_texto = registros.replace('"]', '') + ':valorPesquisaTexto"]';
   var valor_pesquisa_calendario = registros.replace('"]', '') + ':valorPesquisaCalendario"]';
   var valor_pesquisa_calendario_texto = registros.replace('"]', '') + ':valorPesquisaCalendario_input"]';
   var valor_pesquisa_selecao = registros.replace('"]', '') + ':valorPesquisaSelecao_input"]';
   var campo_pesquisa = '';
   var criterio_pesquisa = '';
   var valor_pesquisa = '';
   var filtro = '';

   // colunas.
   var col_id = registros.replace('"]', '') + ':col-id:filter"]';
   var col_nome = registros.replace('"]', '') + ':col-nome:filter"]';
   var col_instancia = registros.replace('"]', '') + ':col-instancia_input"]';
   var col_email = registros.replace('"]', '') + ':col-email:filter"]';
   var col_situacao = registros.replace('"]', '') + ':col-situacao_input"]';
   var col_numero = registros.replace('"]', '') + ':col-numero:filter"]';
   var col_interessado = registros.replace('"]', '') + ':col-interessado:filter"]';
   var col_relator = registros.replace('"]', '') + ':col-relator:filter"]';
   //    var col_processo_numero = registros.replace('"]', '') + ':col-processo-numero:filter"]';
   var col_processo_numero = registros.replace('"]', '') + ':col-processo-numero"]';
   var col_processo_instancia = registros.replace('"]', '') + ':col-processo-relator:filter"]';
   var col_processo_interessado = registros.replace('"]', '') + ':col-processo-interessado:filter"]';
   var col_processo_relator = registros.replace('"]', '') + ':col-processo-relator:filter"]';
   var col_data = registros.replace('"]', '') + ':col-data_input"]';
   var col_distribuicao_instancia = registros.replace('"]', '') + ':col-distribuicao-instancia_input"]';
   var col_distribuicao_situacao = registros.replace('"]', '') + ':col-distribuicao-situacao_input"]';
   var col_distribuicao_responsavel = registros.replace('"]', '') + ':col-distribuicao-responsavel:filter"]';
   var col_processo_sorteado = registros.replace('"]', '') + ':col-processo-sorteado_input"]';

   function parametros_pesquisa_informados() {
      if (campo_pesquisa != '' && criterio_pesquisa != '' && valor_pesquisa != '') {
         return true;
      }
      return false;
   }

   function local_timestamp(data) {
      var timestamp = '';
      var dia = '';
      var mes = '';
      var ano = '';
      var horas = '';
      var minutos = '';
      var segundos = '';
      if (data != null) {
         if (data instanceof Date) {
            dia = data.getDate();
            dia = dia <= 9 ? '0' + dia : dia;
            mes = data.getMonth() + 1;
            mes = mes <= 9 ? '0' + mes : mes;
            ano = data.getFullYear();
            horas = data.getHours();
            horas = horas <= 9 ? '0' + horas : horas;
            minutos = data.getMinutes();
            minutos = minutos <= 9 ? '0' + minutos : minutos;
            segundos = data.getSeconds();
            segundos = segundos <= 9 ? '0' + segundos : segundos;
         } else if ((typeof (data) === 'string' || data instanceof String) && data.match(REGEX_SQL_TIMESTAMP)) {
            var datetime = data.split(' ');
            var date = datetime[0].split('-');
            var time = datetime[1].split(':');
            dia = date[0];
            mes = date[1];
            ano = date[2];
            horas = time[0];
            minutos = time[1];
            segundos = time[2];
         } else {

         }
         timestamp = '{0}/{1}/{2} {3}:{4}:{5}'.format(dia, mes, ano, horas, minutos, segundos);
      }
      return timestamp;
   }

   function sql_timestamp(data) {
      var timestamp = '';
      var dia = '';
      var mes = '';
      var ano = '';
      var horas = '';
      var minutos = '';
      var segundos = '';
      if (data != null) {
         if (data instanceof Date) {
            dia = data.getDate();
            dia = dia <= 9 ? '0' + dia : dia;
            mes = data.getMonth() + 1;
            mes = mes <= 9 ? '0' + mes : mes;
            ano = data.getFullYear();
            horas = data.getHours();
            horas = horas <= 9 ? '0' + horas : horas;
            minutos = data.getMinutes();
            minutos = minutos <= 9 ? '0' + minutos : minutos;
            segundos = data.getSeconds();
            segundos = segundos <= 9 ? '0' + segundos : segundos;
         } else if ((typeof (data) === 'string' || data instanceof String) && data.match(REGEX_LOCAL_TIMESTAMP)) {
            var datetime = data.split(' ');
            var date = datetime[0].split('/');
            var time = datetime[1].split(':');
            dia = date[0];
            mes = date[1];
            ano = date[2];
            horas = time[0];
            minutos = time[1];
            segundos = time[2];
         } else {

         }
         timestamp = '{0}-{1}-{2} {3}:{4}:{5}'.format(ano, mes, dia, horas, minutos, segundos);
      }
      return timestamp;
   }

   function Pesquisa(campo_pesquisa, criterio_pesquisa, valor_pesquisa) {
      this.campo_pesquisa = campo_pesquisa;
      this.criterio_pesquisa = criterio_pesquisa;
      this.valor_pesquisa = valor_pesquisa;
      var filtro_pesquisa = '';
      if (campo_pesquisa != '' && criterio_pesquisa != '' && valor_pesquisa != '') {
         filtro_pesquisa = criterio_pesquisa;
         filtro_pesquisa = filtro_pesquisa.replace('%s', campo_pesquisa);
         filtro_pesquisa = filtro_pesquisa.replace('%s', valor_pesquisa);
      }
      $(global_filter).val(filtro_pesquisa);
      this.filtrar = function() {
         PF('registros').filter();
      }
   }

   Pesquisa.prototype.toString = function() {
      return filtro_pesquisa;
   };

   // pesquisa dinâmica por colunas.
   $(document).on('keyup', col_id, function() {
      var pesquisa = new Pesquisa("id", "%s__startswith='%s'", $(col_id).val());
      console.log(pesquisa);
      pesquisa.filtrar();
   });

   $(document).on('keyup', col_nome, function() {
      var pesquisa = new Pesquisa("nome", "%s__contains='%s'", $(col_nome).val());
      //        console.log(pesquisa);
      pesquisa.filtrar();
   });

   $(document).on('change', col_instancia, function() {
      var pesquisa = new Pesquisa("instancia", "%s__exact='%s'", $(col_instancia).val());
      //        console.log(pesquisa);
      pesquisa.filtrar();
   });

   $(document).on('change', col_distribuicao_instancia, function() {
      var pesquisa = new Pesquisa("distribuicao.instancia", "%s__exact='%s'", $(col_distribuicao_instancia).val());
      //        console.log(pesquisa);
      pesquisa.filtrar();
   });


   $(document).on('keyup', col_email, function() {
      var pesquisa = new Pesquisa("email", "%s__startswith='%s'", $(col_email).val());
      //        console.log(pesquisa);
      pesquisa.filtrar();
   });

   $(document).on('change', col_situacao, function() {
      var pesquisa = new Pesquisa("situacao", "%s__exact='%s'", $(col_situacao).val());
      console.log(pesquisa);
      pesquisa.filtrar();
   });

   $(document).on('change', col_distribuicao_situacao, function() {
      var pesquisa = new Pesquisa("distribuicao.situacao", "%s__exact='%s'", $(col_distribuicao_situacao).val());
      //        console.log(pesquisa);
      pesquisa.filtrar();
   });

   $(document).on('keyup', col_numero, function() {
      var pesquisa = new Pesquisa("numero", "%s__startswith='%s'", $(col_numero).val());
      //      console.log(pesquisa);
      pesquisa.filtrar();
   });

   $(document).on('keyup', col_interessado, function() {
      var pesquisa = new Pesquisa("interessado", "%s__startswith='%s'", $(col_interessado).val());
      //        console.log(pesquisa);
      pesquisa.filtrar();
   });

   $(document).on('keyup', col_relator, function() {
      var pesquisa = new Pesquisa("relator.nome", "%s__startswith='%s'", $(col_relator).val());
      //        console.log(pesquisa);
      pesquisa.filtrar();
   });

   $(document).on('keyup', col_processo_numero, function() {
      var pesquisa = new Pesquisa("processo.numero", "%s__startswith='%s'", $(col_processo_numero).val());
      console.log(pesquisa);
      pesquisa.filtrar();
   });

   $(document).on('keyup', col_processo_interessado, function() {
      var pesquisa = new Pesquisa("processo.interessado", "%s__startswith='%s'", $(col_processo_interessado).val());
      //        console.log(pesquisa);
      pesquisa.filtrar();
   });

   $(document).on('keyup', col_processo_relator, function() {
      var pesquisa = new Pesquisa("processo.relator.nome", "%s__startswith='%s'", $(col_processo_relator).val());
      //        console.log(pesquisa);
      pesquisa.filtrar();
   });

   $(document).on('keyup', col_distribuicao_responsavel, function() {
      var pesquisa = new Pesquisa("distribuicao.responsavel", "%s__startswith='%s'", $(col_distribuicao_responsavel).val());
      //        console.log(pesquisa);
      pesquisa.filtrar();
   });

   $(document).on('change', col_processo_sorteado, function() {
      var pesquisa = new Pesquisa("relator", "%s__isnull=%s", $(col_processo_sorteado).val());
      console.log(pesquisa);
      pesquisa.filtrar();
   });

   $(document).on('keyup', col_data, function() {
      var data = $(col_data).val();
      data = data.replace(/_/g, '');
      data = data == '//' ? '' : data;
      var pesquisa = new Pesquisa("distribuicao.data", "%s__startswith='%s'", data);
      console.log(pesquisa);
      pesquisa.filtrar();
   });

   $(document).on('focus', '.hasDatepicker', function() {
      $('.hasDatepicker').each(function() {
         var onSelect = $(this).datepicker('option', 'onSelect');
         $(this).datepicker('option', 'onSelect', function() {
            var data_inicial = sql_timestamp(this.value + ' 00:00:00');
            var data_final = sql_timestamp(this.value + ' 23:59:59');
            valor_pesquisa = "'{0}','{1}'".format(data_inicial, data_final);
            var pesquisa = new Pesquisa('distribuicao.data', "%s__range=[%s]", valor_pesquisa);
            console.log(pesquisa);
            pesquisa.filtrar();
         });
      });
   });

}

function fecharMensagem(form) {
   if (form != null && form != '') {
      window.setTimeout(function() {
         $('*[id="' + form + ':messages"]').hide(1000);
      }, 7000);
   }
}
