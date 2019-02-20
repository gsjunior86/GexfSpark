package br.gsj.spark.gexfspark.utils
import org.apache.spark.graphx._
import scala.reflect.ClassTag

object Dijkstra extends Serializable {

  type SPMap = Map[VertexId, Int]

  private def makeMap(x: (VertexId, Int)*) = Map(x: _*)

  private def incrementMap(spmap: SPMap): SPMap = spmap.map { case (v, d) => v -> (d + 1) }

  def run[VD, ED: ClassTag](graph: Graph[VD, ED], landmarks: Seq[VertexId]): Graph[(VertexId,Long), ED] = {

//    val dijkstraGraph = graph.mapVertices { (vid, attr) =>
//      if (landmarks.contains(vid)) makeMap(vid -> 0) else makeMap()
//    }
    
    val dijkstraGraph = graph.mapVertices { (vid, attr) =>
      (vid,0L)
    }

    val initialMessage = makeMap()

    Pregel(dijkstraGraph, initialMessage)(null, null, null)

  }

}