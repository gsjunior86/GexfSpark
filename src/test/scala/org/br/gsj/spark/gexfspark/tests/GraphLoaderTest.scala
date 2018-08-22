package org.br.gsj.spark.gexfspark.tests

import org.apache.spark.sql.SparkSession
import org.br.gsj.spark.gexfspark.graph.GraphLoader
import org.br.gsj.spark.gexfspark.graph.GraphLoader
import org.br.gsj.spark.gexfspark.enums.FileType
import org.apache.spark.sql.types._
import org.apache.spark.sql.Row
import org.br.gsj.spark.gexfspark.graph.GraphWriter
import org.br.gsj.spark.gexfspark.entities.Node
import org.br.gsj.spark.gexfspark.entities.Edge



object GraphLoaderTest {
  def main(args: Array[String]): Unit = {
    
    val spark = SparkSession.builder()
    .appName("GexfSparkTest").master("local[*]").getOrCreate()
    
    val path = "src/test/resources/sp_data_school_day_1_g.gexf"
    
//    val gl = new GraphLoader(spark,path,FileType.Gexf)
//    
//    println("Number of nodes: " + gl.loadGraphXGraph().numVertices)
//    println("Number of edges: " + gl.loadGraphXGraph().numEdges)
//
//    
        
    val nodes_rdd = spark.sparkContext.parallelize( List(1,2,3,4,5).map(f => new Node(f.toString())))
    val edges_rdd = spark.sparkContext.parallelize( List( (1,2),(1,3),(2,4),(2,5),(4,5),(3,5))
        .map(f => new Edge(f._1.toString(),f._2.toString()) ))
    
    val gw = new GraphWriter(spark)
    gw.writeGexfFile(nodes_rdd, edges_rdd, "")
    
    
//        var df_edges = spark.sqlContext.read
//        .format("com.databricks.spark.xml")
//        .option("rowTag", "graph")
//                .option("rowTag", "gexf")
//
//        .load(path)
//        
//        df_edges.show
//        
//        df_edges.printSchema()
    
  }
}