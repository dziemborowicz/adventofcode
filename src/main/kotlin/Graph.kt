fun <T> Grid<T>.connectedSubgraph(
  index: Index,
  includeDiagonals: Boolean = false,
  comparison: (T, T) -> Boolean = { a, b -> a == b },
): Set<Index> {
  val subgraph = hashSetOf<Index>()
  val queue = dequeOf(index)
  while (queue.isNotEmpty()) {
    val current = queue.removeFirst()
    if (comparison(this[index], this[current])) {
      if (subgraph.add(current)) {
        if (includeDiagonals) {
          queue.addAll(current.neighborsWithDiagonalsIn(this))
        } else {
          queue.addAll(current.neighborsIn(this))
        }
      }
    }
  }
  return subgraph
}

fun <T> Grid<T>.connectedSubgraphPoints(
  index: Index,
  includeDiagonals: Boolean = false,
  comparison: (T, T) -> Boolean = { a, b -> a == b },
): Set<Point> = connectedSubgraph(
  index, includeDiagonals, comparison
).mapTo(hashSetOf()) { it.toPointIn(this) }

fun <T> Grid<T>.connectedSubgraphs(
  includeDiagonals: Boolean = false,
  comparison: (T, T) -> Boolean = { a, b -> a == b },
): List<Set<Index>> {
  val subgraphs = mutableListOf<Set<Index>>()
  val visited = hashSetOf<Index>()
  for (i in indices) {
    if (visited.add(i)) {
      val subgraph = hashSetOf<Index>()
      val queue = dequeOf(i)
      while (queue.isNotEmpty()) {
        val j = queue.removeFirst()
        if (comparison(this[i], this[j])) {
          visited.add(j)
          if (subgraph.add(j)) {
            if (includeDiagonals) {
              queue.addAll(j.neighborsWithDiagonalsIn(this))
            } else {
              queue.addAll(j.neighborsIn(this))
            }
          }
        }
      }
      subgraphs += subgraph
    }
  }
  return subgraphs
}

fun <T> Grid<T>.connectedSubgraphsPoints(
  includeDiagonals: Boolean = false,
  comparison: (T, T) -> Boolean = { a, b -> a == b },
): List<Set<Point>> = connectedSubgraphs(
  includeDiagonals, comparison
).map { it.mapTo(hashSetOf()) { it.toPointIn(this) } }
