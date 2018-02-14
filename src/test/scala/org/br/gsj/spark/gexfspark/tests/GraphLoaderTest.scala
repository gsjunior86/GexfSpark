package org.br.gsj.spark.gexfspark.tests

import org.apache.spark.sql.SparkSession
import org.br.gsj.spark.gexfspark.graph.GraphLoader

object GraphLoaderTest {
  def main(args: Array[String]): Unit = {
    
    val spark = SparkSession.builder()
    .appName("GexfSparkTest").master("local[*]").getOrCreate()
    
    val path = "/home/gsjunior/Documents/gexf/hierarchy4.gexf"
    
    GraphLoader.getNodes(spark, path)
    
  }
}