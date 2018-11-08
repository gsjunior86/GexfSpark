package org.br.gsj.spark.gexfspark.tests

import org.scalatest.FunSuite
import org.apache.spark.sql.SparkSession

abstract class SparkTest extends FunSuite{
  
    val spark = SparkSession.builder()
    .appName("GexfSparkTest").master("local[*]").getOrCreate()
  
}