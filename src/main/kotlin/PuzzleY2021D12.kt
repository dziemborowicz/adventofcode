class PuzzleY2021D12 : Puzzle {

  private data class Cave(val name: String) {
    val isStart = name == "start"
    val isEnd = name == "end"
    val isBig = name.none { it.isLowerCase() }
    val isSmall = !isBig
    val neighbors = mutableSetOf<Cave>()
  }

  private lateinit var caves: MutableMap<String, Cave>

  override fun parse(input: String) {
    caves = mutableMapOf<String, Cave>()
    for (edge in input.lines().map { it.split('-').asPair() }) {
      val first = caves.computeIfAbsent(edge.first) { Cave(edge.first) }
      val second = caves.computeIfAbsent(edge.second) { Cave(edge.second) }
      first.neighbors.add(second)
      second.neighbors.add(first)
    }
  }

  override fun solve1(): Int {
    val visited = mutableSetOf<Cave>()
    fun countPaths(cave: Cave): Int {
      if (cave.isEnd) return 1
      if (cave.isSmall && cave in visited) return 0
      visited.add(cave)
      return cave.neighbors.sumOf { countPaths(it) }.also { visited.remove(cave) }
    }
    return countPaths(caves.getValue("start"))
  }

  override fun solve2(): Int {
    val visited = mutableSetOf<Cave>()
    var visitedSmallTwice = false
    fun countPaths(cave: Cave): Int {
      if (cave.isEnd) return 1
      var visitingSmallAgain = false
      if (cave.isSmall && cave in visited) {
        if (cave.isStart || cave.isEnd || visitedSmallTwice) {
          return 0
        } else {
          visitedSmallTwice = true
          visitingSmallAgain = true
        }
      }
      visited.add(cave)
      return cave.neighbors.sumOf { countPaths(it) }.also {
        if (visitingSmallAgain) {
          visitedSmallTwice = false
        } else {
          visited.remove(cave)
        }
      }
    }
    return countPaths(caves.getValue("start"))
  }

  companion object {
    val testInput1_1 = """
      start-A
      start-b
      A-c
      A-b
      b-d
      A-end
      b-end
      """.trimIndent()
    val testAnswer1_1 = 10

    val testInput1_2 = """
      dc-end
      HN-start
      start-kj
      dc-start
      dc-HN
      LN-dc
      HN-end
      kj-sa
      kj-HN
      kj-dc
      """.trimIndent()
    val testAnswer1_2 = 19

    val testInput1_3 = """
      fs-end
      he-DX
      fs-he
      start-DX
      pj-DX
      end-zg
      zg-sl
      zg-pj
      pj-he
      RW-he
      fs-DX
      pj-RW
      zg-RW
      start-pj
      he-WI
      zg-he
      pj-fs
      start-RW
      """.trimIndent()
    val testAnswer1_3 = 226

    val testInput2_1 = testInput1_1
    val testAnswer2_1 = 36

    val testInput2_2 = testInput1_2
    val testAnswer2_2 = 103

    val testInput2_3 = testInput1_3
    val testAnswer2_3 = 3509
  }
}
