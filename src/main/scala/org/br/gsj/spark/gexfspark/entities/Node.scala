package org.br.gsj.spark.gexfspark.entities

import scala.collection.mutable.HashMap

class Node (val id:String, val label: String, val other_attributes: HashMap[String,String]) {
  
  
  def this(id: String, label: String) ={
    this(id,label,null)
  }
  
}