package org.br.gsj.spark.gexfspark.tests

import org.apache.spark.sql.SparkSession
import org.br.gsj.spark.gexfspark.graph.GraphLoader
import org.br.gsj.spark.gexfspark.graph.GraphLoader
import org.br.gsj.spark.gexfspark.enums.FileType



object GraphLoaderTest {
  def main(args: Array[String]): Unit = {
    
    val spark = SparkSession.builder()
    .appName("GexfSparkTest").master("local[*]").getOrCreate()
    
    val path = "src/test/resources/ht2009_15min.gexf"
    
    val gl = new GraphLoader(spark,path,FileType.Gexf)
    
    println("Number of nodes: " + gl.loadGraphXGraph().numVertices)
    println("Number of edges: " + gl.loadGraphXGraph().numEdges)

    
  }
}