package org.br.gsj.spark.gexfspark.tests

import java.io.File

import scala.io.Source

import org.br.gsj.spark.gexfspark.entities.Edge
import org.br.gsj.spark.gexfspark.entities.Node
import org.br.gsj.spark.gexfspark.enums.GexfType
import org.br.gsj.spark.gexfspark.graph.GraphWriter




/**
 * 
 * Test if the Graph is being converted Properly
 * 
 * @author Geraldo de Souza Junior
 * 
 */
class GraphWriterTest extends SparkTest{
  
    
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