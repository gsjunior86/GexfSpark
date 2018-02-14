package org.br.gsj.spark.gexfspark.graph

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.br.gsj.spark.gexfspark.utils.Utils._

object GraphLoader {
  
  def getNodes(spark: SparkSession, path: String) ={
    var df = spark.sqlContext.read
      .format("com.databricks.spark.xml")
      .option("rowTag", "nodes")
      .load(path)
    df = df.withColumn("nodes", explode(df("node"))).drop("node")
    df = df.select(getStructCols("nodes", df): _*)
    df.show
    
  }
  
  def getEdges(spark: SparkSession, path: String) ={
    
  }
  
}