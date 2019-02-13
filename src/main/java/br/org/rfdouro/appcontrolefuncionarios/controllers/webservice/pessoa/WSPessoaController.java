/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.rfdouro.appcontrolefuncionarios.controllers.webservice.pessoa;

import br.org.rfdouro.appcontrolefuncionarios.annotations.JPAHandler;
import br.org.rfdouro.appcontrolefuncionarios.annotations.VerificaAcesso;
import br.org.rfdouro.appcontrolefuncionarios.controllers.DefaultController;
import br.org.rfdouro.appcontrolefuncionarios.models.Pessoa;
import br.org.rfdouro.appcontrolefuncionarios.util.Util;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author romulo.douro
 */
@JPAHandler
@RestController
@RequestMapping(value = "/ws/pessoa")
public class WSPessoaController extends DefaultController {

 @VerificaAcesso(value = "pessoa")
 @GetMapping(value = {"", "/{id}"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
 public String page(@PathVariable(value = "id", required = false) Long id) {
  String ret = "";
  ObjectMapper objectMapper = new ObjectMapper();
  this.setRepositorio();
  try {
   if (id == null) {
    List l = this.repositorio.buscaGenerica("select p from Pessoa p order by p.nome");
    ret = objectMapper.writeValueAsString(l);
   } else {
    Object o = this.repositorio.get(id, Pessoa.class);
    ret = objectMapper.writeValueAsString(o);
   }
  } catch (Exception ex) {
   ret = Util.getMsgErro(ex);
  }
  return ret;
 }

 @VerificaAcesso(value = "pessoa")
 @PostMapping(value = {""}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
 public String cadastra(@RequestParam(value = "nome", required = true) String nome) throws JsonProcessingException {
  Map<String, String> m = new HashMap<String, String>();
  String mensagem = "Registro inserido";
  ObjectMapper om = new ObjectMapper();
  try {
   this.setRepositorio();
   Pessoa p = new Pessoa();
   p.setNome(nome);
   this.repositorio.adiciona(p, false);
  } catch (Exception ex) {
   mensagem = Util.getMsgErro(ex);
  }
  m.put("mensagem", mensagem);
  return om.writeValueAsString(m);
 }

 @VerificaAcesso(value = "pessoa")
 @DeleteMapping(value = {"/{id}"})
 public String exclui(@PathVariable(value = "id", required = true) Long id) {
  String mensagem = "Registro excluído";
  try {
   this.setRepositorio();
   Pessoa p = (Pessoa) this.repositorio.get(id, Pessoa.class);
   this.repositorio.exclui(p, false);
  } catch (Exception ex) {
   mensagem = Util.getMsgErro(ex);
  }
  return mensagem;
 }

}
