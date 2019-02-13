<%-- 
    Document   : index
    Created on : 4 de out de 2018, 10:30:37
    Author     : romulo.douro
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />

<div id="app">

 <h1>Lista de Funcionários <button type="button" class="btn btn-primary pull-right" data-toggle="modal" data-target="#modalCadastro">Cadastrar</button><a href="${cp}/funcionario/cadastro" data-div="divConteudo">cadastro</a></h1>

 <table class="table table-striped table-responsive">
  <thead>
   <tr><th>Nome</th><th>Nascimento</th><th></th></tr>
  </thead>
  <tbody>
   <tr v-for="p in listaFuncionarios">
    <td>{{p.nome}}</td>
    <td>{{p.nascimento | formatDate}}</td>
    <td><button class="btn btn-danger" v-on:click.prevent="exclui(p.id)"><i class="fa fa-close"></i></button></td>
   </tr>
  </tbody>
 </table>

 <!-- Modal -->
 <div class="modal fade" id="modalCadastro" tabindex="-1" role="dialog" aria-labelledby="modalCadastroLabel">
  <div class="modal-dialog" role="document">
   <div class="modal-content">
    <div class="modal-header">
     <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
     <h4 class="modal-title" id="modalCadastroLabel">Novo Funcionário</h4>
    </div>
    <div class="modal-body">
     <div class="row">
      <div class="col-lg-6">
       <input type="text" class="form-control" v-model="funcionario.nome">
      </div><!-- /.col-lg-6 -->
      <div class="col-lg-6">
       <div class="input-group">
        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
        <!--input type="text" class="form-control datepicker" placeholder="Nascimento" v-model.lazy="funcionario.nascimento" -->
        <!--input type="text" v-date-picker-bs class="form-control datepicker" v-model="funcionario.nascimento"-->
        <input type="text" class="form-control datepicker" id="dtNascimento" 
               v-bind:value="funcionario.nascimento"
               v-on:input="funcionario.nascimento = $event.target.value">
        <input type="text" v-date-picker-bs class="form-control datepicker" v-model="funcionario.nascimento">
        <date-picker-bs v-model="funcionario.nascimento"></date-picker-bs>
        <input type="text" v-model="funcionario.nascimento">
       </div>
      </div><!-- /.col-lg-6 -->
     </div><!-- /.row -->
    </div>
    <div class="modal-footer">
     <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
     <button type="button" class="btn btn-primary" v-on:click="salva">Salvar</button>
    </div>
   </div>
  </div>
 </div>

</div>

<script>
 var v = new Vue({
  el: "#app",
  data: {
   funcionario: {
    nome: null,
    nascimento: null
   },
   listaFuncionarios: []
  },
  mounted: function () {
   //executa a customização da navegação
   customizaNavegacaoJQuery();
   var _this = this;
   $('#dtNascimento').datepicker({
    language: "pt-BR"
   }).on('changeDate', function (ev) {
    _this.funcionario.nascimento = $('#dtNascimento').val();
   });
   this.getLista();
  },
  methods: {
   salva: function () {
    mostraSplash();
    $.post("ws/funcionario", this.funcionario, function (data) {
     console.log(data);
     bootbox.alert({
      title: 'Mensagem',
      message: data.mensagem,
      callback: function () {
       v.getLista();
      }
     });
    });
    return false;
   },
   exclui: function (idE) {
    bootbox.confirm({
     title: 'Confirmação',
     message: "Confirma a exclusão deste registro?",
     buttons: {
      confirm: {
       label: 'Sim',
       className: 'btn-success'
      },
      cancel: {
       label: 'Não',
       className: 'btn-danger'
      }
     },
     callback: function (result) {
      if (result) {
       $.ajax({
        url: "ws/funcionario/" + idE,
        data: null,
        method: 'DELETE',
        success: function (data) {
         bootbox.alert({
          title: 'Mensagem',
          message: data,
          callback: function () {
           v.getLista();
          }
         });
        }
       });
      }
     }
    });
   },
   getLista: function () {
    $.get("ws/funcionario", null, function (data) {
     v.listaFuncionarios = data;
     escondeSplash();
    });
    return false;
   }
  }
 });

</script>

