//registra uma diretiva customizada chamada v-telefone-mask
Vue.directive('telefoneMask', {
 //quando o elemento tiver um valor
 bind: function (el) {
  // mascara o elemento
  //tem por base jquerymask do igor escobar
  $(el).mask('(00)0-0000-0000');
 }
});

//registra uma diretiva customizada chamada v-dinheiro-mask
Vue.directive('dinheiroMask', {
 //quando o elemento tiver um valor
 bind: function (el) {
  // mascara o elemento
  //tem por base jquerymask do igor escobar
  $(el).mask('#.00', {reverse: true});
 }
});

//registra uma diretiva customizada chamada v-date-picker-mcss
Vue.directive('datePickerMcss', {
 bind: function (el) {
  //torna o componente somente leitura
  $(el).attr('readonly', 'readonly');
 },
 //quando o elemento for inserido no DOM
 inserted: function (el, binding) {
  var aoFechar, aoSelecionar, mostraBotaoOk = true, mostraBotaoCancela = true;
  try {
   aoFechar = binding.value.aoFechar;
  } catch (e) {
  }
  try {
   aoSelecionar = binding.value.aoSelecionar;
  } catch (e) {
  }
  try {
   mostraBotaoOk = (binding.value.mostraBotaoOk == null ? true : binding.value.mostraBotaoOk);
  } catch (e) {
  }
  try {
   mostraBotaoCancela = (binding.value.mostraBotaoCancela == null ? true : binding.value.mostraBotaoCancela);
  } catch (e) {
  }

  // mascara o elemento
  //tem por base materializecss
  $(el).datepicker({
   showDoneBtn: mostraBotaoOk,
   showCancelBtn: mostraBotaoCancela,
   format: 'dd/mm/yyyy',
   yearRange: 70,
   onSelect: function () {
    if (aoSelecionar) {
     eval(aoSelecionar);
     this.close();
    }
   },
   onClose: function () {
    if (aoFechar) {
     eval(aoFechar);
    }
   },
   i18n: {
    cancel: 'Cancelar',
    months:
            [
             'Jan',
             'Fev',
             'Mar',
             'Abr',
             'Mai',
             'Jun',
             'Jul',
             'Ago',
             'Set',
             'Out',
             'Nov',
             'Dez'
            ],
    monthsShort:
            [
             'Jan',
             'Fev',
             'Mar',
             'Abr',
             'Mai',
             'Jun',
             'Jul',
             'Ago',
             'Set',
             'Out',
             'Nov',
             'Dez'
            ],
    weekdays:
            [
             'Domingo',
             'Segunda',
             'Terça',
             'Quarta',
             'Quinta',
             'Sexta',
             'Sábado'
            ],
    weekdaysShort:
            [
             'Dom',
             'Seg',
             'Ter',
             'Qua',
             'Qui',
             'Sex',
             'Sáb'
            ],
    weekdaysAbbrev: ['Do', 'Se', 'Te', 'Qa', 'Qi', 'Sx', 'Sa']
   }
  });
 }
});


function findVModelName(vnode) {
 return vnode.data.directives.find(function (o) { //Search the vModelName attached to the element
  return o.name === 'model';
 }).expression;
}

function setVModelValue(value, vnode) {
 var x = findVModelName(vnode);
 vnode.context[x] = value;
 console.log('x = ' + vnode.context[x]);
}

function getVModelValue(vnode) {
 var x = findVModelName(vnode);
 return vnode.context[x];
}

//registra uma diretiva customizada chamada v-date-picker-bs
Vue.directive('datePickerBs', {
 bind: function (el, binding, vnode) {
  //torna o componente somente leitura
  $(el).attr('readonly', 'readonly');
 },
 //quando o elemento for inserido no DOM
 inserted: function (el, binding, vnode) {
  var _this = this;

  $(el).datepicker({
   language: "pt-BR",
   autoclose: true,
   toggleActive: true,
   todayHighlight: true
  }).on('changeDate', function (e) {
   //vnode.context.$emit('input', e.date);
   setVModelValue($(el).val(), vnode);
   //vnode.context.$emit('input', e.format(0));
   console.log(_this.update);
  });
 },
 update: function (el, binding, vnode, oldVnode) {
  setVModelValue($(el).val(), vnode);
  //vnode.context.$emit('input', $(el).val());
  /*$(el).val(getVModelValue(oldVnode));
   setVModelValue($(el).val(), oldVnode);*/
  //setVModelValue($(el).val(), vnode);
  //console.log(' = '+$(el).val());
 },
 componentUpdated: function (el, binding, vnode, oldVnode) {
  //vnode.context.$emit('input', $(el).val());
  /*
  $(el).val(getVModelValue(oldVnode));
  setVModelValue($(el).val(), oldVnode);*/
  //setVModelValue($(el).val(), vnode);
  //console.log(' = '+$(el).val());
  //console.log(' = '+$(el).val());
  //console.log('cupd = ' + getVModelValue(vnode));
 }
});

