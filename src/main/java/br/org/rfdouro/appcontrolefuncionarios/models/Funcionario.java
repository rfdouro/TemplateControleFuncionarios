/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.rfdouro.appcontrolefuncionarios.models;

import br.org.rfdouro.appcontrolefuncionarios.util.Util;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author romulo.douro
 */
@Entity
public class Funcionario implements Serializable {

 private static final long serialVersionUID = 1L;
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 public Long getId() {
  return id;
 }

 public void setId(Long id) {
  this.id = id;
 }

 @Override
 public int hashCode() {
  int hash = 0;
  hash += (id != null ? id.hashCode() : 0);
  return hash;
 }

 @Override
 public boolean equals(Object object) {
  // TODO: Warning - this method won't work in the case the id fields are not set
  if (!(object instanceof Funcionario)) {
   return false;
  }
  Funcionario other = (Funcionario) object;
  if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
   return false;
  }
  return true;
 }

 @Override
 public String toString() {
  return "br.org.rfdouro.appcontrolefuncionarios.models.Funcionario[ id=" + id + " ]";
 }
 
 /*outros campos*/
 @Column(length = 255)
 private String nome;
 @Temporal(TemporalType.DATE)
 private Calendar nascimento;

 public String getNome() {
  return nome;
 }

 public void setNome(String nome) {
  this.nome = nome;
 }

 public Calendar getNascimento() {
  return nascimento;
 }

 public void setNascimento(Calendar nascimento) {
  this.nascimento = nascimento;
 }
 
 public void setNascimento(String nascimento) throws ParseException {
  Calendar c = Calendar.getInstance();
  c.setTime(Util.tsdf.get().parse(nascimento));
  this.nascimento = c;
 }

}
