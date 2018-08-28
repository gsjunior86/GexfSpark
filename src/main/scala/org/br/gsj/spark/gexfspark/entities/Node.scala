package org.br.gsj.spark.gexfspark.entities

import scala.collection.mutable.HashMap


/**
 * The representation of a node
 * 
 * @author Geraldo de Souza Junior
 * 
 */
case class Node (id:String, var label: String, var other_attributes: HashMap[String,String]) {
  
  if(label == null){
    label = id
  }
 
  if(other_attributes == null){
    other_attributes = new HashMap[String,String]()
  }

  def this(id: String, label: String) ={
    this(id,label,null)
  }
  
  def this(id: String) ={
    this(id,null,null)
  }

  
}