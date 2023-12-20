import Index.Companion.DIRECTIONS
import Index.Companion.DOWN
import Index.Companion.LEFT
import Index.Companion.RIGHT
import Index.Companion.UP

class PuzzleY2023D23 : Puzzle {

  private val directions = mapOf(
    '^' to listOf(UP),
    '>' to listOf(RIGHT),
    'v' to listOf(DOWN),
    '<' to listOf(LEFT),
    '.' to DIRECTIONS,
  )

  private lateinit var grid: Grid<Char>

  override fun parse(input: String) {
    grid = input.parseDenseCharGrid()
  }

  override fun solve1(): Int {
    return longestPathIn(grid)
  }

  override fun solve2(): Int {
    val gridWithoutSlopes = grid.map { if (it != '#') '.' else '#' }
    return longestPathIn(gridWithoutSlopes)
  }

  private fun longestPathIn(grid: Grid<Char>): Int {
    val start = grid.indexOfFirst('.')
    val end = grid.indexOfLast('.')

    val nodes = (grid.indices.filter {
      grid[it] != '#' && it.neighborsIn(grid).count { grid[it] != '#' } > 2
    } + start + end).associateWith { mutableListOf<Pair<Index, Int>>() }

    for (node in nodes.keys) {
      val queue = dequeOf(node to 0)
      val visited = hashSetOf<Index>()
      while (queue.isNotEmpty()) {
        val (current, distance) = queue.removeFirst()
        if (current !in grid.indices) continue
        if (grid[current] == '#') continue
        if (!visited.add(current)) continue
        if (distance == 0 || current !in nodes.keys) {
          val neighbors = directions.getValue(grid[current]).map { current + it }
          queue.addAll(neighbors.map { it to (distance + 1) })
          continue
        }
        nodes.getValue(node) += (current to distance)
      }
    }

    val visited = hashSetOf<Index>()
    fun solve(index: Index, distance: Int): Int {
      if (index == end) return distance
      if (!visited.add(index)) return Int.MIN_VALUE
      return nodes.getValue(index)
        .maxOf { (nextIndex, nextDistance) -> solve(nextIndex, distance + nextDistance) }.also {
          visited.remove(index)
        }
    }
    return solve(start, 0)
  }

  companion object {
    val testInput1 = """
      #.#####################
      #.......#########...###
      #######.#########.#.###
      ###.....#.>.>.###.#.###
      ###v#####.#v#.###.#.###
      ###.>...#.#.#.....#...#
      ###v###.#.#.#########.#
      ###...#.#.#.......#...#
      #####.#.#.#######.#.###
      #.....#.#.#.......#...#
      #.#####.#.#.#########v#
      #.#...#...#...###...>.#
      #.#.#v#######v###.###v#
      #...#.>.#...>.>.#.###.#
      #####v#.#.###v#.#.###.#
      #.....#...#...#.#.#...#
      #.#########.###.#.#.###
      #...###...#...#...#.###
      ###.###.#.###v#####v###
      #...#...#.#.>.>.#.>.###
      #.###.###.#.###.#.#v###
      #.....###...###...#...#
      #####################.#
      """.trimIndent()
    val testAnswer1 = 94

    val testInput2 = testInput1
    val testAnswer2 = 154
  }
}
