//cria um filtro para formatar a data usando moment
//usado na exibição de datas do tipo inteiro
// ex.: {{objDate | formatDate}}
Vue.filter('formatDate', function (value) {
 if (value && (moment() instanceof moment)) {
  return moment(value).format('DD/MM/YYYY');
 }
});

