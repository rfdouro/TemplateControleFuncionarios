/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.rfdouro.appcontrolefuncionarios.controllers.funcionario;

import br.org.rfdouro.appcontrolefuncionarios.annotations.VerificaAcesso;
import br.org.rfdouro.appcontrolefuncionarios.controllers.DefaultController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 *
 * @author romulo.douro
 */
@Controller
@RequestMapping(value = "/funcionario")
public class FuncionarioController extends DefaultController {

 @VerificaAcesso(value = "funcionario")
 @RequestMapping(value = {"", "/", "/index", "/lista"}, method = RequestMethod.GET)
 public String lista() {
  return "funcionario/lista";
 }
 
 @VerificaAcesso(value = "funcionario")
 @RequestMapping(value = {"/cadastro"}, method = RequestMethod.GET)
 public String cadastro() {
  return "funcionario/cadastro";
 }

}
