package org.br.gsj.spark.gexfspark.tests

import org.apache.spark.sql.SparkSession
import br.gsj.spark.gexfspark.graph.GraphLoader
import br.gsj.spark.gexfspark.enums.FileType
import org.apache.spark.graphx._
import scala.collection.immutable.List

object BetweenessMain {


  private def makeMap2(x: (VertexId, Int)*) = Map(x: _*)

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().appName("betweeness_test").master("local[*]").getOrCreate()

    val sc = spark.sparkContext

    //    val path = "src/test/resources/gexf/ht2009_15min.gexf"
    //
    //    val graphLoader = new GraphLoader(spark,path,FileType.Gexf)
    //
    //    val graph = graphLoader.loadGraphXGraph()
    //

    val vertex_rdd = sc.parallelize(Seq(
      (1L, "Mark"), (2L, "Alice"), (3L, "Bridget"), (4L, "Doug"), (5L, "Charles"), (6L, "Michael")))

    val edges_rdd = sc.parallelize(Seq(
      Edge(1L, 2L, ""), Edge(2L, 3L, ""), Edge(2L, 4L, ""), Edge(2L, 5L, ""), Edge(5L, 6L, "")))

    val graph = Graph(vertex_rdd, edges_rdd)



  }
}