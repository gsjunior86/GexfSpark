package br.gsj.spark.gexfspark.graph

import org.apache.spark.sql.SparkSession

import org.apache.spark.sql.functions._
import br.gsj.spark.gexfspark.utils.Utils._
import org.apache.spark.rdd.RDD
import br.gsj.spark.gexfspark.entities.Node
import br.gsj.spark.gexfspark.entities.Edge
import org.apache.spark.graphx.VertexId
import org.apache.spark.graphx.Graph
import br.gsj.spark.gexfspark.enums.FileType

/**
 * Transforms a graph from a filetype to GraphX
 *
 * @author Geraldo de Souza Junior
 *
 */

class GraphLoader(spark: SparkSession, path: String, fileType: FileType.Value) {

  var nodes = createNodes(spark, path)
  var edges = createEdges(spark, path)

  def loadGraphXGraph(): Graph[String, String] = {

    val nodes_graph: RDD[(VertexId, String)] = nodes.map(f => (f.id.toLong, f.label))
    
    val edges_graph = edges.map(f => org.apache.spark.graphx.Edge (f.source.toLong, f.target.toLong,""))
   
    Graph(nodes_graph, edges_graph)


  }

  private def createNodes(spark: SparkSession, path: String): RDD[Node] = {
    if (fileType.equals(FileType.Gexf)) {
      var df_nodes = spark.sqlContext.read
        .format("com.databricks.spark.xml")
        .option("rowTag", "nodes")
        .load(path)
      df_nodes = df_nodes.withColumn("nodes", explode(df_nodes("node"))).drop("node")
      df_nodes = df_nodes.select(getStructCols("nodes", df_nodes): _*)
      df_nodes.rdd.map(f => new Node(f.getAs("_id").toString, f.getAs("_label").toString))
    } else {
      null
    }
  }

  private def createEdges(spark: SparkSession, path: String): RDD[Edge] = {
    if (fileType.equals(FileType.Gexf)) {
      var df_edges = spark.sqlContext.read
        .format("com.databricks.spark.xml")
        .option("rowTag", "edges")
        .load(path)
      df_edges = df_edges.withColumn("edges", explode(df_edges("edge"))).drop("edge")
      df_edges = df_edges.select(getStructCols("edges", df_edges): _*)
      df_edges.rdd.map(f => new Edge(f.getAs("_source").toString, f.getAs("_target").toString))
    } else {
      null
    }
  }

}