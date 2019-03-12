package org.br.gsj.spark.gexfspark.tests

import org.apache.spark.sql.SparkSession
import org.apache.spark.graphx._

object GraphXTest {
  
  val friend_relationship = "is-friends-with"
  
  def main(args: Array[String]): Unit = {
    
    val spark = SparkSession.builder().appName("graphXTest").master("local[*]").getOrCreate()
    
    val sc = spark.sparkContext
    
    val vertex_rdd = sc.parallelize(
        Seq((1L,"Ann"),(2L,"Bill"),(3L,"Charles"),(4L,"Diane"),(5L,"Went to Gym this morning")))
    
    val edges_rdd = sc.parallelize(Seq(
        Edge(1L,2L,friend_relationship),
        Edge(2L,3L,friend_relationship),
        Edge(3L,4L,friend_relationship),
        Edge(3L,5L,"wrote-status"),
        Edge(4L,5L,"likes-status")
        ))
        
        
     val graph = Graph(vertex_rdd,edges_rdd)
    
  }
  
}