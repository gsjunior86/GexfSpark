package org.br.gsj.spark.gexfspark.tests

import scala.collection.mutable.TreeSet

import org.apache.spark.graphx._
import org.apache.spark.sql.SparkSession

object BetweenessMain {

  private def makeMap2(x: (VertexId, Int)*) = Map(x: _*)

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().appName("betweeness_test").master("local[*]").getOrCreate()

    val sc = spark.sparkContext

    val employeeData = List(("Jack", 1000.0), ("Bob", 2000.0), ("Carl", 7000.0))
    val employeeRDD = sc.makeRDD(employeeData)
    val dummyEmployee = ("dummy", 0.0);

    val maxSalaryEmployee = employeeRDD.fold(dummyEmployee)((acc, employee) => {
      if (acc._2 < employee._2) employee else acc
    })
    println("employee with maximum salary is" + maxSalaryEmployee)

    //    val path = "src/test/resources/gexf/ht2009_15min.gexf"
    //
    //    val graphLoader = new GraphLoader(spark,path,FileType.Gexf)
    //
    //    val graph = graphLoader.loadGraphXGraph()
    //

    //    val vertex_rdd = sc.parallelize(Seq(
    //      (1L, "Mark"), (2L, "Alice"), (3L, "Bridget"), (4L, "Doug"), (5L, "Charles"), (6L, "Michael")))
    //
    //    val edges_rdd = sc.parallelize(Seq(
    //      Edge(1L, 2L, ""), Edge(2L, 3L, ""), Edge(2L, 4L, ""), Edge(2L, 5L, ""), Edge(5L, 6L, "")))
    //
    //    val graph = Graph(vertex_rdd, edges_rdd)
    //
    //   val spSet: TreeSet[Long] = TreeSet.empty
    //   val mapDistance: Map[VertexId, Int] = Map.empty

  }
}