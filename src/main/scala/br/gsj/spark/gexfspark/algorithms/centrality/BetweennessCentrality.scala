package br.gsj.spark.gexfspark.algorithms.centrality

import org.apache.spark.sql.SparkSession

object BetweenessCentrality {
  
  
  def main(args: Array[String]): Unit = {
    
    val spark = SparkSession.builder().appName("testBetweenness").master("local[*]")
    .getOrCreate()
    
    val sc = spark.sparkContext
    
    val nodes_rdd = sc.parallelize(
        Seq(
            (1L,2L,3L,4L,5L,6L,7L,8L)
            )
         )
    
    
  }
  
}