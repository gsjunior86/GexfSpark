package br.gsj.spark.gexfspark.tests

import java.io.File

import scala.io.Source

import br.gsj.spark.gexfspark.entities.Edge
import br.gsj.spark.gexfspark.entities.Node
import br.gsj.spark.gexfspark.enums.GexfType
import br.gsj.spark.gexfspark.graph.GraphWriter
import org.apache.spark.sql.SparkSession
import org.scalatest.FunSuite




/**
 * 
 * Test if the Graph is being converted Properly
 * 
 * @author Geraldo de Souza Junior
 * 
 */
class GraphWriterTest extends FunSuite{
  
    val spark = SparkSession.builder()
    .appName("GexfSparkTest").master("local[*]").getOrCreate()  
  
     val nodes_rdd = spark.sparkContext.parallelize( List(1,2,3,4,5).map(f => new Node(f.toString())))
     val edges_rdd = spark.sparkContext.parallelize( List( (1,2),(1,3),(2,4),(2,5),(4,5),(3,5))
        .map(f => new Edge(f._1.toString(),f._2.toString()) ))
    
     val gw = new GraphWriter(spark)
    
     
     /**
      * 
      * Check the schema and columns
      * 
      */
     test("Check if the schema and the columns are correct"){
       
       val df = gw.createXmlDataFrame(nodes_rdd, edges_rdd, new GexfType)
       assert(df.columns.contains("_defaultedgetype"))
       assert(df.columns.contains("_mode"))
       assert(df.columns.contains("nodes"))
       assert(df.columns.contains("edges"))
       
       assert (loadExpectedSchema() === df.schema.json)
       
    }
     
     
    private def loadExpectedSchema() : String ={
        Source.fromFile("src/test/resources/schema/expected_schema.json", "UTF-8").getLines().toList(0)
    }

  
}