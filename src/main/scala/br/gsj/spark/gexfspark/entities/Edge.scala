package br.gsj.spark.gexfspark.entities

/**
 * 
 * The representation of an Edge
 * 
 * @author Geraldo de Souza Junior
 * 
 */

import scala.collection.mutable.HashMap

case class Edge (source: String, target: String, var other_attributes: HashMap[String,String] ) {
  
  if (other_attributes == null){
    other_attributes = new HashMap[String,String]()
  }
  
  def this(source: String, target: String) ={
    this(source,target,null)
  }
}