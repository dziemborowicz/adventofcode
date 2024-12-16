class PuzzleY2024D16 : Puzzle {

  private var minCostToEnd = Int.MAX_VALUE
  private var minPathsToEnd = mutableListOf<List<Index>>()

  override fun parse(input: String) {
    val maze = input.parseDenseCharGrid()
    val start = maze.indexOf('S')
    val end = maze.indexOf('E')

    val minCosts = hashMapOf<Pair<Index, Index>, Int>()
    val queue = dequeOf(Quadruple(start, Index.RIGHT, listOf(start), 0))
    while (queue.isNotEmpty()) {
      val (pos, dir, path, cost) = queue.removeFirst()
      val minCost = minCosts.getOrDefault(pos to dir, Int.MAX_VALUE)
      when {
        pos == end -> {
          if (cost == minCostToEnd) {
            minPathsToEnd += path
          } else if (cost < minCostToEnd) {
            minCostToEnd = cost
            minPathsToEnd = mutableListOf(path)
          }
        }

        cost <= minCost -> {
          minCosts[pos to dir] = cost
          if (maze[pos + dir] != '#') {
            queue += Quadruple(pos + dir, dir, path + (pos + dir), cost + 1)
          }
          queue += Quadruple(pos, dir.rotateClockwise(), path, cost + 1000)
          queue += Quadruple(pos, dir.rotateCounterclockwise(), path, cost + 1000)
        }
      }
    }
  }

  override fun solve1(): Int = minCostToEnd

  override fun solve2(): Int = minPathsToEnd.flatten().countDistinct()

  companion object {
    val testInput1_1 = """
      ###############
      #.......#....E#
      #.#.###.#.###.#
      #.....#.#...#.#
      #.###.#####.#.#
      #.#.#.......#.#
      #.#.#####.###.#
      #...........#.#
      ###.#.#####.#.#
      #...#.....#.#.#
      #.#.#.###.#.#.#
      #.....#...#.#.#
      #.###.#.#.#.#.#
      #S..#.....#...#
      ###############
      """.trimIndent()
    val testAnswer1_1 = 7036

    val testInput1_2 = """
      #################
      #...#...#...#..E#
      #.#.#.#.#.#.#.#.#
      #.#.#.#...#...#.#
      #.#.#.#.###.#.#.#
      #...#.#.#.....#.#
      #.#.#.#.#.#####.#
      #.#...#.#.#.....#
      #.#.#####.#.###.#
      #.#.#.......#...#
      #.#.###.#####.###
      #.#.#...#.....#.#
      #.#.#.#####.###.#
      #.#.#.........#.#
      #.#.#.#########.#
      #S#.............#
      #################
      """.trimIndent()
    val testAnswer1_2 = 11048

    val testInput2_1 = testInput1_1
    val testAnswer2_1 = 45

    val testInput2_2 = testInput1_2
    val testAnswer2_2 = 64
  }
}
