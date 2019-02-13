/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.rfdouro.appcontrolefuncionarios.interceptors;

import br.org.rfdouro.appcontrolefuncionarios.annotations.JPAHandler;
import br.org.rfdouro.appcontrolefuncionarios.annotations.VerificaAcesso;
import br.org.rfdouro.appcontrolefuncionarios.helpers.EntityManagerHelper;
import br.org.rfdouro.appcontrolefuncionarios.util.Util;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author romulo
 */
public class JPAInterceptor implements HandlerInterceptor {

 @Override
 public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
  HandlerMethod hm = (HandlerMethod) handler;
  boolean classeAnotada = hm.getBean().getClass().isAnnotationPresent(JPAHandler.class);
  Method method = hm.getMethod();
  boolean metodoAnotado = (method.getDeclaringClass().isAnnotationPresent(Controller.class))
          && method.isAnnotationPresent(JPAHandler.class);
  if (classeAnotada || metodoAnotado) {
   //cria a conexão JPA e insere na request
   String msEX = "";
   EntityManager manager = EntityManagerHelper.getEntityManager(request.getServletContext());
   if (manager != null) {
    request.setAttribute("EntityManager", manager);
    try {
     EntityManagerHelper.beginTransaction(manager);
    } catch (Exception e) {
     msEX = "ERRO commit = " + Util.getMsgErro(e);
    }
   } else {
    throw new ServletException("ERRO DE ACESSO AO BANCO DE DADOS");
   }
  }

  return true;
  //return HandlerInterceptor.super.preHandle(request, response, handler); //To change body of generated methods, choose Tools | Templates.
 }

 @Override
 public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
  //HandlerInterceptor.super.afterCompletion(request, response, handler, ex); //To change body of generated methods, choose Tools | Templates.
 }

 @Override
 public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

  HandlerMethod hm = (HandlerMethod) handler;
  boolean classeAnotada = hm.getBean().getClass().isAnnotationPresent(JPAHandler.class);
  Method method = hm.getMethod();
  boolean metodoAnotado = (method.getDeclaringClass().isAnnotationPresent(Controller.class))
          && method.isAnnotationPresent(JPAHandler.class);
  if (classeAnotada || metodoAnotado) {
   //cria a conexão JPA e insere na request
   //Object o = request.getAttribute("sessao");

   String msEX = "";
   //EntityManager manager = EntityManagerHelper.getEntityManager(request.getServletContext());
   EntityManager manager = (EntityManager) request.getAttribute("EntityManager");
   request.getAttribute("sessao");
   if (manager != null) {
    try {
     EntityManagerHelper.commit(manager);
    } catch (Exception e) {
     msEX = "ERRO commit = " + Util.getMsgErro(e);
     try {
      EntityManagerHelper.rollback(manager);
     } catch (Exception ex) {
      msEX += "ERRO rollback = " + Util.getMsgErro(ex);
     }
    } finally {
     try {
      EntityManagerHelper.closeEntityManager(manager);
     } catch (Exception exf) {
      msEX += "ERRO close = " + Util.getMsgErro(exf);
     }
     if (!msEX.equals("")) {
      throw new ServletException(msEX);
     }
    }
   } else {
    throw new ServletException("ERRO DE ACESSO AO BANCO DE DADOS");
   }

  }

  //HandlerInterceptor.super.postHandle(request, response, handler, modelAndView); //To change body of generated methods, choose Tools | Templates.
 }

}
