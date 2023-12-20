class PuzzleY2023D25 : Puzzle {

  private data class Node(val label: String) {
    var neighbors = mutableSetOf<Node>()
  }

  private lateinit var nodes: List<Node>

  override fun parse(input: String) {
    val nodesMap = input.lines().flatMap { line ->
      Regex("\\w+").findAll(line).map { it.value }
    }.associateWith { Node(it) }

    input.lines().forEach { line ->
      val (node, neighbors) = line.split(": ")
      neighbors.split(' ').forEach {
        connect(nodesMap.getValue(node), nodesMap.getValue(it))
      }
    }

    nodes = nodesMap.values.toList()
  }

  override fun solve1(): Int {
    for ((a, b) in nodes.combinations(2)) {
      fun solve(left: Int): Int {
        if (left == 0) {
          val reachableSize = a.reachableNodes().size
          return reachableSize * (nodes.size - reachableSize)
        } else {
          val path = anyPath(a, b)
          for ((c, d) in path.windowed(2)) {
            disconnect(c, d)
            solve(left - 1).let { if (it != 0) return it }
            connect(c, d)
          }
          return 0
        }
      }
      solve(3).let { if (it != 0) return it }
    }
    fail()
  }

  override fun solve2(): Int {
    // No answer required. This solves the puzzle when all 50 stars are unlocked.
    return 0
  }

  private fun anyPath(a: Node, b: Node): List<Node> {
    val visited = hashSetOf<Node>()
    val queue = priorityQueueOf(a to listOf(a), comparator = compareBy { it.second.size })
    while (queue.isNotEmpty()) {
      val (node, path) = queue.poll()
      if (node == b) return path
      if (visited.add(node)) {
        queue.addAll(node.neighbors.map { it to path + it })
      }
    }
    fail()
  }

  private fun Node.reachableNodes(): Set<Node> {
    val result = hashSetOf<Node>()
    val queue = dequeOf(this)
    while (queue.isNotEmpty()) {
      val node = queue.removeFirst()
      if (result.add(node)) {
        queue.addAll(node.neighbors)
      }
    }
    return result
  }

  private fun connect(a: Node, b: Node) {
    a.neighbors.add(b)
    b.neighbors.add(a)
  }

  private fun disconnect(a: Node, b: Node) {
    a.neighbors.remove(b)
    b.neighbors.remove(a)
  }

  companion object {
    val testInput1 = """
      jqt: rhn xhk nvd
      rsh: frs pzl lsr
      xhk: hfx
      cmg: qnr nvd lhk bvb
      rhn: xhk bvb hfx
      bvb: xhk hfx
      pzl: lsr hfx nvd
      qnr: nvd
      ntq: jqt hfx bvb xhk
      nvd: lhk
      lsr: lhk
      rzs: qnr cmg lsr rsh
      frs: qnr lhk lsr
      """.trimIndent()
    val testAnswer1 = 54
  }
}
