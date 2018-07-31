package org.br.gsj.spark.gexfspark.entities

import scala.collection.mutable.HashMap

case class Edge (source: String, target: String, other_attributes: HashMap[String,String] ) {
  
  def this(source: String, target: String) ={
    this(source,target,null)
  }
}