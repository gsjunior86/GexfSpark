package org.br.gsj.spark.gexfspark.tests

import org.apache.spark.sql.SparkSession

object GraphLoaderTest {
  def main(args: Array[String]): Unit = {
    
    val spark = SparkSession.builder()
    .appName("GexfSparkTest").master("local[*]").getOrCreate()
  }
}