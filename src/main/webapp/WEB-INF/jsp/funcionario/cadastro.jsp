<%-- 
    Document   : index
    Created on : 4 de out de 2018, 10:30:37
    Author     : romulo.douro
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />

<div id="app">


 <!-- Panel -->

 <div class="panel panel-default">

  <div class="panel-heading">
   <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
   <h4 class="modal-title" id="modalCadastroLabel">Novo Funcionário</h4>
  </div>
  <div class="panel-body">
   <div class="row">
    <div class="col-lg-6">
     <input type="text" class="form-control" v-model="funcionario.nome">
    </div><!-- /.col-lg-6 -->
    <div class="col-lg-6">
     <div class="input-group">
      <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
      <date-picker-bs v-model="funcionario.nascimento"></date-picker-bs>
     </div>
    </div><!-- /.col-lg-6 -->
   </div><!-- /.row -->
  </div>
  <div class="panel-footer">
   <button type="button" class="btn btn-primary" v-on:click="salva">Salvar</button>
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
   var _this = this;
   $('#dtNascimento').datepicker({
    language: "pt-BR"
   }).on('changeDate', function (ev) {
    _this.funcionario.nascimento = $('#dtNascimento').val();
   });
   //this.getLista();
  },
  methods: {
   salva: function () {
    mostraSplash();
    $.ajax({
     type: "POST",
     contentType: "application/json",
     dataType: 'json',
     url: "ws/funcionario",
     data: JSON.stringify(this.funcionario), // importante
     success: function (data) {
      bootbox.alert({
       title: 'Mensagem',
       message: data.mensagem,
       callback: function () {
        //v.getLista();
       }
      });
     },
     error: function (e) {
      bootbox.alert({
       title: 'ERRO',
       message: e,
       callback: function () {
        //v.getLista();
       }
      });
     }
    });
    /*$.post("ws/funcionario", this.funcionario, function (data) {
     console.log(data);
     bootbox.alert({
     title: 'Mensagem',
     message: data.mensagem,
     callback: function () {
     //v.getLista();
     }
     });
     });*/
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
           //v.getLista();
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

