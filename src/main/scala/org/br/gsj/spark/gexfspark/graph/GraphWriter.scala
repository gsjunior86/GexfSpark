package org.br.gsj.spark.gexfspark.graph

import org.apache.spark.sql.SparkSession
import org.br.gsj.spark.gexfspark.enums.FileType
import org.apache.spark.rdd.RDD
import org.br.gsj.spark.gexfspark.entities.Node
import org.br.gsj.spark.gexfspark.entities.Edge
import org.apache.spark.sql.types._
import org.apache.spark.sql.Row
import org.br.gsj.spark.gexfspark.enums.GexfType
import org.apache.spark.sql.functions._



class GraphWriter(spark: SparkSession) {
  
  import spark.implicits._
  
  
  case class Graph (defaultedgetype: String, mode: String) extends java.io.Serializable
  case class File(xlms: String, version: String, graph: Graph) extends java.io.Serializable
  
  def writeGexfFile(nodes: RDD[Node], edges: RDD[Edge], path: String){
    
    val gexf_schema = StructType(List(
        
                    StructField("_defaultedgetype",StringType, true),
                    StructField("_mode",StringType, true)))
                    
    val gexf_rdd = spark.sparkContext.parallelize(Seq(Row("undirected","static")))
    
    var gexf_df = spark.createDataFrame(gexf_rdd, gexf_schema)
    
    val nodes_schema = StructType(List(
        StructField("_id",StringType, true),
        StructField("_label",StringType, true)
        ))
        
     var nodes_rdd = nodes.map{f => Row(f.id,f.label) }
    
     nodes_rdd.foreach(println)
         
     var nodes_df = spark.createDataFrame(nodes_rdd,nodes_schema)
    
     nodes_df = nodes_df.withColumn("nodes", struct($"_id",$"_label") ).drop($"_label").drop($"_id")
          
     gexf_df.show
     gexf_df.printSchema()
     
  
//     
//     gexf_df = gexf_df.join(nodes_df, gexf_df("id") === nodes_df("id"))
//     
//     gexf_df.show
   
//    gexf_df = gexf_df.withColumn("nodes", struct($"_id",$"_label")).drop($"_id").drop($"_label")
    
    
    
//    gexf_df.repartition(1).write.
//    format("com.databricks.spark.xml")
//    .option("rowTag", "graph")
//    .option("rootTag", "gexf")
//    .save("src/test/resources/gexf_test.xml")
//     data_frame.show()
//     data_frame.printSchema()
            
            
  }
  
}