package org.br.gsj.spark.gexfspark.graph

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.br.gsj.spark.gexfspark.utils.Utils._
import org.apache.spark.rdd.RDD
import org.br.gsj.spark.gexfspark.entities.Node
import org.br.gsj.spark.gexfspark.entities.Edge

class GraphLoader (val spark: SparkSession, val path: String) {
  
  
  
  private var nodes_rdd:RDD[Node] = null
  private var edges_rdd:RDD[Edge] = null
  
  createGraph(spark, path)
  
  
  
  private def createGraph(spark: SparkSession, path: String) = {
      createNodes(spark,path)
      createEdges(spark,path)
  }
  
  def nodes(): RDD[Node] = {
    nodes_rdd
  }
  
  def edges(): RDD[Edge] = {
    edges_rdd
  }
  
  private def createNodes(spark: SparkSession, path: String) = {
      var df_nodes = spark.sqlContext.read
      .format("com.databricks.spark.xml")
      .option("rowTag", "nodes")
      .load(path)
        df_nodes = df_nodes.withColumn("nodes", explode(df_nodes("node"))).drop("node")
        df_nodes = df_nodes.select(getStructCols("nodes", df_nodes): _*)
        df_nodes.show
        nodes_rdd = df_nodes.rdd.map(f => new Node(f.getAs("_id"),f.getAs("_label")))
  }
  
  private def createEdges(spark: SparkSession, path: String) ={
    var df_edges = spark.sqlContext.read
      .format("com.databricks.spark.xml")
      .option("rowTag", "edges")
      .load(path)
        df_edges = df_edges.withColumn("edges", explode(df_edges("edge"))).drop("edge")
        df_edges = df_edges.select(getStructCols("edges", df_edges): _*)
        edges_rdd = df_edges.rdd.map(f => new Edge(f.getAs("_source"),f.getAs("_target")))
  }
  
  
}