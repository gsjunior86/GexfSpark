package org.br.gsj.spark.gexfspark.tests

import org.scalatest.FunSuite
import org.apache.spark.sql.SparkSession
import org.br.gsj.spark.gexfspark.graph.GraphLoader
import org.br.gsj.spark.gexfspark.enums.FileType


/**
 * 
 * Tests if the graph is correctly loaded
 * 
 */
class GraphLoaderTest extends FunSuite{
  
  val spark = SparkSession.builder()
    .appName("GexfSparkTest").master("local[*]").getOrCreate()
    
  val path1 = "src/test/resources/gexf/sp_data_school_day_1_g.gexf"
  val gl1 = new GraphLoader(spark,path1,FileType.Gexf)
  
  val path2 = "src/test/resources/gexf/ht2009_15min.gexf"
  val gl2 = new GraphLoader(spark,path2,FileType.Gexf)
  
  test("Check if the number of vertices and edges of sp_data_school_day_1_g.gexf is correct"){
    val graph = gl1.loadGraphXGraph()
    
    assert(graph.numVertices == 236)
    assert(graph.numEdges == 5899)
  
    assert(gl1.nodes.count().toInt == 236)
    assert(gl1.edges.count().toInt == 5899)  
  }
  
  test("Check if the number of vertices and edges of ht2009_15min.gexf is correct"){
    val graph = gl2.loadGraphXGraph()
    
    assert(graph.numVertices == 113)
    assert(graph.numEdges == 2163)
  
    assert(gl2.nodes.count().toInt == 113)
    assert(gl2.edges.count().toInt == 2163)  
  }
  
}