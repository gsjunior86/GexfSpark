package br.gsj.spark.gexfspark.utils

import scala.collection.mutable.TreeSet

object ScalaAPI {
  
  def main(args: Array[String]): Unit = {
    
    val spSet: TreeSet[Long] = TreeSet.empty
    
    spSet += 1L
    spSet += 3L

    
    println(spSet)
    
  }
  
}