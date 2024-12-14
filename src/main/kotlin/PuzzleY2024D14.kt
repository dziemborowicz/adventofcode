import com.google.common.truth.Truth.assertThat

class PuzzleY2024D14 : Puzzle {

  private data class Robot(val id: Int, val initialPosition: Index, val velocity: Index)

  private lateinit var robots: List<Robot>

  override fun parse(input: String) {
    robots = input.toRobots()
  }

  override fun solve1(): Int = safetyFactor(robots, numRows = 103, numColumns = 101)

  override fun solve2(): Int {
    var grid = robots.toGrid(numRows = 103, numColumns = 101)
    var seconds = 0
    while (!grid.containsBlock()) {
      grid = evolve(grid)
      seconds++
    }
    return seconds
  }

  companion object {
    private fun String.toRobots(): List<Robot> {
      return lines().mapIndexed { id, line ->
        val (px, py, vx, vy) = line.extractInts()
        Robot(id, Index(py, px), Index(vy, vx))
      }
    }

    private fun List<Robot>.toGrid(numRows: Int, numColumns: Int): Grid<List<Robot>> {
      val result = Grid(numRows, numColumns) { mutableListOf<Robot>() }
      forEach { robot -> result[robot.initialPosition] += robot }
      @Suppress("UNCHECKED_CAST") return result as Grid<List<Robot>>
    }

    private fun safetyFactor(robots: List<Robot>, numRows: Int, numColumns: Int): Int {
      var grid = robots.toGrid(numRows, numColumns)
      repeat(100) { grid = evolve(grid) }
      val quadrants = listOf(
        grid.subGrid(0, 0, numRows / 2, numColumns / 2),
        grid.subGrid(0, numColumns / 2 + 1, numRows / 2, numColumns / 2),
        grid.subGrid(numRows / 2 + 1, 0, numRows / 2, numColumns / 2),
        grid.subGrid(numRows / 2 + 1, numColumns / 2 + 1, numRows / 2, numColumns / 2),
      )
      return quadrants.productOf { it.values().sumOf { it.size } }
    }

    private fun evolve(grid: Grid<List<Robot>>): Grid<List<Robot>> {
      val result = Grid(grid.numRows, grid.numColumns) { mutableListOf<Robot>() }
      grid.forEachIndexed { position, robots ->
        robots.forEach { robot ->
          val newPosition = (position + robot.velocity).wrappedIn(result)
          result[newPosition] += robot
        }
      }
      @Suppress("UNCHECKED_CAST") return result as Grid<List<Robot>>
    }

    private fun Grid<List<Robot>>.containsBlock(): Boolean {
      return indices.filter { this[it].isNotEmpty() }.any {
        it.neighborsWithDiagonals().all { it in this.indices && this[it].isNotEmpty() }
      }
    }

    fun test1() {
      val input = """
        p=0,4 v=3,-3
        p=6,3 v=-1,-3
        p=10,3 v=-1,2
        p=2,0 v=2,-1
        p=0,0 v=1,3
        p=3,0 v=-2,-2
        p=7,6 v=-1,-3
        p=3,0 v=-1,-2
        p=9,3 v=2,3
        p=7,3 v=-1,2
        p=2,4 v=2,-3
        p=9,5 v=-3,-3""".trimIndent()
      val robots = input.toRobots()
      assertThat(safetyFactor(robots, numRows = 7, numColumns = 11)).isEqualTo(12)
    }
  }
}
