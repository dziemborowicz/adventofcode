class PuzzleY2019D18 : Puzzle {

  private class Node(
    val position: Index,
    val value: Char,
    val neighbors: MutableMap<Node, Int> = mutableMapOf(),
  )

  private lateinit var grid: Grid<Char>
  private var numKeysToFind: Int = 0

  override fun parse(input: String) {
    grid = input.parseDenseCharGrid()
    numKeysToFind = grid.count { it.isKey }
  }

  override fun solve1(): Int {
    return solve(grid)
  }

  override fun solve2(): Int {
    val grid = grid.copy()
    val start = grid.indexOf('@')

    grid[start] = '#'
    start.neighborsWithDiagonalsIn(grid).forEach { grid[it] = '@' }
    start.neighborsIn(grid).forEach { grid[it] = '#' }

    return solve(grid)
  }

  private fun solve(grid: Grid<Char>): Int {
    val nodes = nodesFrom(grid)
    val starts = nodes.filter { it.value == '@' }

    var minSteps = Int.MAX_VALUE
    val cache = cacheOf<Int>()
    val queue = dequeOf(Triple(starts, 0, 0))
    while (queue.isNotEmpty()) {
      var (nodes, keys, steps) = queue.removeFirst()

      if (!cache.setMin(nodes, keys, value = steps)) {
        continue
      }

      for (node in nodes) {
        if (node.value.isKey) {
          keys = keys.withKey(node.value)
        }
      }

      if (keys.numKeys == numKeysToFind) {
        minSteps = minOf(minSteps, steps)
        continue
      }

      for (i in nodes.indices) {
        for ((neighbor, stepsAway) in nodes[i].neighbors) {
          if (neighbor.value.isAccessibleWith(keys)) {
            val newNodes = nodes.toMutableList().also { it[i] = neighbor }
            queue += Triple(newNodes, keys, steps + stepsAway)
          }
        }
      }
    }

    return minSteps
  }

  private fun nodesFrom(grid: Grid<Char>): List<Node> {
    val nodes = grid.indices.filter {
      grid[it] != '.' && grid[it] != '#'
    }.associateWith {
      Node(it, grid[it])
    }

    for (node in nodes.values) {
      val queue = dequeOf(node.position to 0)
      val visited = hashSetOf<Index>()
      while (queue.isNotEmpty()) {
        val (position, steps) = queue.removeFirst()
        if (!visited.add(position)) {
          continue
        }

        val otherNode = nodes[position]
        if (otherNode != null && otherNode !== node) {
          node.neighbors[otherNode] = steps
          continue
        }

        queue += position.neighborsIn(grid).filter {
          grid[it] != '#'
        }.map {
          it to (steps + 1)
        }
      }
    }

    return nodes.values.toList()
  }

  private fun Char.isAccessibleWith(keys: Int): Boolean {
    return when {
      this == '#' -> false
      this == '.' -> true
      this == '@' -> true
      this.isKey -> true
      this.isDoor -> keys.canUnlock(this)
      else -> fail()
    }
  }

  private val Char.isKey: Boolean
    get() = this in 'a'..'z'

  private val Char.isDoor: Boolean
    get() = this in 'A'..'Z'

  private val Int.numKeys: Int
    get() = countOneBits()

  private fun Int.canUnlock(door: Char): Boolean {
    check(door.isDoor)
    return getBitBoolean(door - 'A')
  }

  private fun Int.withKey(key: Char): Int {
    check(key.isKey)
    return setBit(key - 'a')
  }

  companion object {
    val testInput1_1 = """
      #########
      #b.A.@.a#
      #########
      """.trimIndent()
    val testAnswer1_1 = 8

    val testInput1_2 = """
      ########################
      #f.D.E.e.C.b.A.@.a.B.c.#
      ######################.#
      #d.....................#
      ########################
      """.trimIndent()
    val testAnswer1_2 = 86

    val testInput1_3 = """
      ########################
      #...............b.C.D.f#
      #.######################
      #.....@.a.B.c.d.A.e.F.g#
      ########################
      """.trimIndent()
    val testAnswer1_3 = 132

    val testInput1_4 = """
      #################
      #i.G..c...e..H.p#
      ########.########
      #j.A..b...f..D.o#
      ########@########
      #k.E..a...g..B.n#
      ########.########
      #l.F..d...h..C.m#
      #################
      """.trimIndent()
    val testAnswer1_4 = 136

    val testInput1_5 = """
      ########################
      #@..............ac.GI.b#
      ###d#e#f################
      ###A#B#C################
      ###g#h#i################
      ########################
      """.trimIndent()
    val testAnswer1_5 = 81

    val testInput2_1 = """
      #######
      #a.#Cd#
      ##...##
      ##.@.##
      ##...##
      #cB#Ab#
      #######
      """.trimIndent()
    val testAnswer2_1 = 8

    val testInput2_2 = """
      ###############
      #d.ABC.#.....a#
      ######...######
      ######.@.######
      ######...######
      #b.....#.....c#
      ###############
      """.trimIndent()
    val testAnswer2_2 = 24

    val testInput2_3 = """
      #############
      #DcBa.#.GhKl#
      #.###...#I###
      #e#d#.@.#j#k#
      ###C#...###J#
      #fEbA.#.FgHi#
      #############
      """.trimIndent()
    val testAnswer2_3 = 32

    val testInput2_4 = """
      #############
      #g#f.D#..h#l#
      #F###e#E###.#
      #dCba...BcIJ#
      #####.@.#####
      #nK.L...G...#
      #M###N#H###.#
      #o#m..#i#jk.#
      #############
      """.trimIndent()
    val testAnswer2_4 = 72
  }
}
