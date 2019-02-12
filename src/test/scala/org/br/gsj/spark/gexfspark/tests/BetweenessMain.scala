package org.br.gsj.spark.gexfspark.tests

import org.apache.spark.sql.SparkSession
import br.gsj.spark.gexfspark.graph.GraphLoader
import br.gsj.spark.gexfspark.enums.FileType

object BetweenessMain {
  def main(args: Array[String]): Unit = {
    
    val spark = SparkSession.builder().appName("betweeness_test").master("local[*]").getOrCreate()
    
    val sc = spark.sparkContext
   
    val path = "src/test/resources/gexf/ht2009_15min.gexf"
    
    val graphLoader = new GraphLoader(spark,path,FileType.Gexf)
    
    val graph = graphLoader.loadGraphXGraph()
    
    print("Nodes: " + graph.numVertices)
    print("Edges: " + graph.numEdges)
    
    

  }
}