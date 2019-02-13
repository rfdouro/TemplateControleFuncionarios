/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.rfdouro.appcontrolefuncionarios.controllers.webservice.funcionario;

import br.org.rfdouro.appcontrolefuncionarios.annotations.JPAHandler;
import br.org.rfdouro.appcontrolefuncionarios.annotations.VerificaAcesso;
import br.org.rfdouro.appcontrolefuncionarios.controllers.DefaultController;
import br.org.rfdouro.appcontrolefuncionarios.models.Funcionario;
import br.org.rfdouro.appcontrolefuncionarios.util.Util;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author romulo.douro
 */
@JPAHandler
@RestController
@RequestMapping(value = "/ws/funcionario")
public class WSFuncionarioController extends DefaultController {

 @VerificaAcesso(value = "funcionario")
 @GetMapping(value = {"", "/{id}"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
 public String page(@PathVariable(value = "id", required = false) Long id) {
  String ret = "";
  ObjectMapper objectMapper = new ObjectMapper();
  this.setRepositorio();
  try {
   if (id == null) {
    List l = this.repositorio.buscaGenerica("select p from Funcionario p order by p.nome");
    ret = objectMapper.writeValueAsString(l);
   } else {
    Object o = this.repositorio.get(id, Funcionario.class);
    ret = objectMapper.writeValueAsString(o);
   }
  } catch (Exception ex) {
   ret = Util.getMsgErro(ex);
  }
  return ret;
 }

 @VerificaAcesso(value = "funcionario")
 @PostMapping(value = {""}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
 /*public String cadastra(@RequestParam(value = "nome", required = true) String nome,
         @RequestParam(value = "nascimento", required = true) String nascimento) throws JsonProcessingException {*/
 public String cadastra(@RequestBody Funcionario funcionario) throws JsonProcessingException {
  Map<String, String> m = new HashMap<String, String>();
  String mensagem = "Registro inserido";
  ObjectMapper om = new ObjectMapper();
  try {
   this.setRepositorio();
   /*Funcionario f = new Funcionario();
   f.setNome(nome);
   f.setNascimento(Util.dateToCalendar(nascimento));*/
   this.repositorio.adiciona(funcionario, false);
  } catch (Exception ex) {
   mensagem = Util.getMsgErro(ex);
  }
  m.put("mensagem", mensagem);
  return om.writeValueAsString(m);
 }

 @VerificaAcesso(value = "funcionario")
 @DeleteMapping(value = {"/{id}"})
 public String exclui(@PathVariable(value = "id", required = true) Long id) {
  String mensagem = "Registro excluído";
  try {
   this.setRepositorio();
   Funcionario p = (Funcionario) this.repositorio.get(id, Funcionario.class);
   this.repositorio.exclui(p, false);
  } catch (Exception ex) {
   mensagem = Util.getMsgErro(ex);
  }
  return mensagem;
 }

}
