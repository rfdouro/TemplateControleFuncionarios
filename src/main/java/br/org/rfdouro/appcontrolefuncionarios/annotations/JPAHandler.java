/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.rfdouro.appcontrolefuncionarios.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author romulo
 */
/**
 * é uma interface para annotation que indica que tendo deve haver uma criação
 * de conexão JPA
 * 
 * a utilização se dá em conjunto a um interceptor que é 
 * setado no Config.java
 *
 * @author romulo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE}) //posso usar essa interface em métodos apenas
public @interface JPAHandler {
}
