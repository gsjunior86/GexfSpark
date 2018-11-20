# GexfSpark - A Spark library for the GEXF file format

=================

Latest Version: no release yet

Author: *Geraldo de Souza Junior*
https://www.linkedin.com/in/gsjunior86/


About
------------------
GEXF File Format Spark Library. It loads Gexf and other graph formats and convert it into GraphX object.
Also, uses the DataFrame API to write Gexf and other graph formats.

Usage
------------------
#### Loading Graphs:
You can load a Gexf file into GraphX's RDD by calling:

```scala

import br.gsj.spark.gexfspark.graph.GraphLoader;
import br.gsj.spark.gexfspark.enums.FileType;


    val spark = SparkSession.builder()
    .appName("GexfSpark").master("local[*]").getOrCreate()
    
    val path = "gexf_file.gexf"
    val graph = new GraphLoader(spark,path,FileType.Gexf)
    val graphX = graph.loadGraphXGraph
```

You can get Dataframes with the edges and nodes by calling
```scala 
graph.edges
```
and 
```scala
graph.nodes
```

#### Writing Graphs:
You can write gexf graphs by creating a **RDD[br.gsj.spark.gexfspark.entities.Edge]** and **RDD[br.gsj.spark.gexfspark.entities.Node]**

Look at the example below:
```scala
import br.gsj.spark.gexfspark.entities.Node
import br.gsj.spark.gexfspark.entities.Edge
import br.gsj.spark.gexfspark.enums.GexfType

val nodes_rdd = spark.sparkContext.parallelize( List(1,2,3,4,5).map(f => new Node(f.toString())))
val edges_rdd = spark.sparkContext.parallelize( List( (1,2),(1,3),(2,4),(2,5),(4,5),(3,5))
        .map(f => new Edge(f._1.toString(),f._2.toString()) ))
    
val gw = new GraphWriter(spark)
gw.writeGexfFile(nodes_rdd, edges_rdd, new GexfType, "path_to_save.gexf")
```

The GexfType object contains graph properties (defaultEdgeType and mode so far). You can override these properties in your own reference 

Licence
------------------
Copyritght Geraldo de Souza Junior 2018

Licensed under GPL 3.0
https://www.gnu.org/licenses/gpl-3.0.html
