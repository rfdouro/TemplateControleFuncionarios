package br.org.rfdouro.appcontrolefuncionarios.filters;

import br.org.rfdouro.appcontrolefuncionarios.helpers.EntityManagerHelper;
import br.org.rfdouro.appcontrolefuncionarios.util.Util;
import java.io.IOException;
import javax.persistence.EntityManager;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * não usada a annotation WebFiler pois o mesmo será adicionado na classe
 * WebInitializer
 *
 * @author romulo.douro
 */
//@WebFilter(servletNames = {"dispatcher"})
//@WebFilter(urlPatterns = "/*")
public class FiltroLoginCookie implements Filter {

 //private int customSessionExpiredErrorCode = 901;
 public static boolean isDebug = true;

 @Override
 public void init(FilterConfig filterConfig) throws ServletException {
  try {
   EntityManagerHelper.inicializaDados(filterConfig.getServletContext());
  } catch (Exception ex) {

  }
 }

 @Override
 public void destroy() {
  EntityManagerHelper.closeEntityManagerFactory();
 }

 @Override
 public void doFilter(ServletRequest request, ServletResponse response,
         FilterChain chain) throws IOException, ServletException {
  
  HttpServletRequest req = (HttpServletRequest) request;
  String usuLogado = (String) req.getSession().getAttribute("USULOGADO");

  //aqui é para pegar o login por cookie
  if (usuLogado == null) {
   Cookie cLogin = Util.getCookie(req, "login");
   Cookie cSenha = Util.getCookie(req, "senha");
   if (cLogin != null && cSenha != null) {
    usuLogado = (String) cLogin.getValue();
    usuLogado = Util.decode(usuLogado);
    req.getSession().setAttribute("USULOGADO", usuLogado);
   }
  }
  
  chain.doFilter(request, response);
  
 }
}
