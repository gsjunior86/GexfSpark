package br.gsj.spark.gexfspark.utils

import  org.apache.spark.sql.DataFrame
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.functions._

object Utils {
  
  
  def getStructCols(colname: String, df: DataFrame) = {
      val parent = df.schema.fields.filter(_.name == colname).head
      val fields = parent.dataType match {
        case x: StructType => x.fields
        case _ => Array.empty[StructField]
      }
      fields.map(x => col(s"$colname.${x.name}"))
  }
  
}