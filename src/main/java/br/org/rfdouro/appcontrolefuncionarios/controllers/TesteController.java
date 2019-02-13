/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.rfdouro.appcontrolefuncionarios.controllers;

import br.org.rfdouro.appcontrolefuncionarios.annotations.JPAHandler;
import br.org.rfdouro.appcontrolefuncionarios.models.Pessoa;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author romulo
 */
@Controller
@RequestMapping(value = "/teste")
public class TesteController extends DefaultController {

 @JPAHandler
 @ResponseBody
 @RequestMapping(value = {"", "/", "/index"}, method = RequestMethod.GET)
 public String index() {

  this.setRepositorio();
  
  Pessoa p = new Pessoa();
  p.setNome("joao");
  
  this.repositorio.adiciona(p, false);
  
  List l = this.repositorio.buscaGenerica("select p from Pessoa p");

  return "teste = " + l.size();
 }

}
