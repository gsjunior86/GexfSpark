package org.br.gsj.spark.gexfspark.entities

import scala.collection.mutable.HashMap

case class Node (id:String, label: String, other_attributes: HashMap[String,String]) {
  
  
  def this(id: String, label: String) ={
    this(id,label,null)
  }
  
}