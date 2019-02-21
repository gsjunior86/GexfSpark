package br.gsj.spark.gexfspark.utils
import org.apache.spark.graphx._
import scala.reflect.ClassTag
import scala.collection.mutable.TreeSet

object Dijkstra extends Serializable {

  val spSet: TreeSet[Long] = TreeSet.empty

  def run[VD, ED: ClassTag](graph: Graph[VD, ED], landmarks: Seq[VertexId]) = {
    val spGraph = graph.mapVertices { (id, attr) =>
      (false, if (landmarks.contains(id)) 0 else Double.MaxValue)
    }

    val vCount = spGraph.vertices.count() - 1

    for (i <- 1L to vCount) {
      val currentVertexId =
        spGraph.vertices.filter(!_._2._1).fold((0L, (false, Double.MaxValue)))((a, b) =>
          if (a._2._2 < b._2._2) a else b)
          ._1

      val newDistances = spGraph.aggregateMessages[Double](
        ctx => if (ctx.srcId == currentVertexId)
          ctx.sendToDst(ctx.srcAttr + ctx.attr),
        (a, b) => math.min(a, b))
    }

  }

}