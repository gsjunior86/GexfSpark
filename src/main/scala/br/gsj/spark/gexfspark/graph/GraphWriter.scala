package br.gsj.spark.gexfspark.graph

import org.apache.spark.sql.{SparkSession,DataFrame}
import br.gsj.spark.gexfspark.enums.FileType
import org.apache.spark.rdd.RDD
import br.gsj.spark.gexfspark.entities.Node
import br.gsj.spark.gexfspark.entities.Edge
import org.apache.spark.sql.types._
import org.apache.spark.sql.Row
import br.gsj.spark.gexfspark.enums.GexfType
import org.apache.spark.sql.functions._

/**
 * 
 * Write a graph in Gexf Format
 * 
 * @author Geraldo de Souza Junior
 * 
 */

class GraphWriter(spark: SparkSession) {
  
  import spark.implicits._
  
  /**
   * 
   * Creates a DataFrame XML from and RDD[Node] and RDD[Edge]
   * 
   */
  
  def createXmlDataFrame(nodes: RDD[Node], edges: RDD[Edge], gexfType : GexfType): DataFrame =  {
    
    val gexf_schema = StructType(List(
        
                    StructField("id",IntegerType, true),
                    StructField("_defaultedgetype",StringType, true),
                    StructField("_mode",StringType, true)))
                    
    val gexf_rdd = spark.sparkContext.parallelize(Seq(Row(666,gexfType.defaultedgetype,gexfType.mode)))
    
    var gexf_df = spark.createDataFrame(gexf_rdd, gexf_schema)
    
    val nodes_schema = StructType(List(
        StructField("id",IntegerType, true),
        StructField("_id",StringType, true),
        StructField("_label",StringType, true)
        ))
        
     val edges_schema = StructType(List(
        StructField("id",IntegerType, true),
        StructField("_source",StringType, true),
        StructField("_target",StringType, true)
        ))
        
     var nodes_rdd = nodes.map{f => Row(666,f.id,f.label) }
             
     var nodes_df = spark.createDataFrame(nodes_rdd,nodes_schema)
    
     nodes_df = nodes_df.withColumn("nodes", struct($"_id",$"_label") ).drop($"_label").drop($"_id")     
     nodes_df = nodes_df.groupBy("id").agg(collect_set("nodes").as("node"))
     
     val edges_rdd = edges.map{f => Row(666,f.source,f.target)}
     var edges_df = spark.createDataFrame(edges_rdd, edges_schema)
     
     edges_df = edges_df.withColumn("edges", struct($"_source",$"_target") ).drop($"_source").drop($"_target")   
     edges_df = edges_df.groupBy("id").agg(collect_set("edges").as("edge"))

     
     gexf_df.join(nodes_df, Seq("id"),"right").join(edges_df, Seq("id"),"right").drop("id")
     .withColumn("nodes", struct($"node")).drop("node")
     .withColumn("edges", struct($"edge")).drop("edge")
    
    
  }
  
  def writeGexfFile(nodes: RDD[Node], edges: RDD[Edge], gexfType : GexfType, path: String, partitions: Integer) = {
    
    val gexf_df = createXmlDataFrame(nodes,edges,gexfType)   
 
     if(partitions == null){
       gexf_df.write.
        format("com.databricks.spark.xml")
        .option("rowTag", "graph")
        .option("rootTag", "gexf")
        .save(path)   
     }else{
       gexf_df.repartition(partitions).write.
        format("com.databricks.spark.xml")
        .option("rowTag", "graph")
        .option("rootTag", "gexf")
        .save(path)
     }
    
  }
 
  def writeGexfFile(nodes: RDD[Node], edges: RDD[Edge], gexfType : GexfType, path: String){
    writeGexfFile(nodes,edges,gexfType,path,null);       
  }
  
}