import Grid.Index
import PuzzleY2022D24.Cell.BLIZZARD_MOVING_DOWN
import PuzzleY2022D24.Cell.BLIZZARD_MOVING_LEFT
import PuzzleY2022D24.Cell.BLIZZARD_MOVING_RIGHT
import PuzzleY2022D24.Cell.BLIZZARD_MOVING_UP
import PuzzleY2022D24.Cell.WALL
import java.util.EnumSet

class PuzzleY2022D24 : Puzzle {

  private enum class Cell {
    BLIZZARD_MOVING_UP,
    BLIZZARD_MOVING_DOWN,
    BLIZZARD_MOVING_LEFT,
    BLIZZARD_MOVING_RIGHT,
    WALL,
  }

  private lateinit var start: Index
  private lateinit var end: Index

  private val blizzardsAtTime = hashMapOf<Int, Grid<EnumSet<Cell>>>()

  private fun blizzardsAtTime(time: Int) =
    blizzardsAtTime.getOrPut(time) { evolve(blizzardsAtTime[time - 1]!!) }

  override fun parse(input: String) {
    val inputGrid = input.parseDenseCharGrid()

    start = inputGrid.indices.first { inputGrid[it] == '.' }
    end = inputGrid.indices.last { inputGrid[it] == '.' }

    blizzardsAtTime[0] = Grid(inputGrid.numRows, inputGrid.numColumns) { row, column ->
      when (inputGrid[row, column]) {
        '^' -> EnumSet.of(BLIZZARD_MOVING_UP)
        'v' -> EnumSet.of(BLIZZARD_MOVING_DOWN)
        '<' -> EnumSet.of(BLIZZARD_MOVING_LEFT)
        '>' -> EnumSet.of(BLIZZARD_MOVING_RIGHT)
        '#' -> EnumSet.of(WALL)
        else -> EnumSet.noneOf(Cell::class.java)
      }
    }
  }

  override fun solve1(): Int {
    return solve(start, end) - 1
  }

  override fun solve2(): Int {
    val timeAtEnd = solve(start, end, startTime = 0)
    val timeBackAtStart = solve(end, start, startTime = timeAtEnd)
    return solve(start, end, startTime = timeBackAtStart) - 1
  }

  private fun solve(start: Index, end: Index, startTime: Int = 0): Int {
    val visited = hashSetOf<Pair<Index, Int>>()
    val queue = dequeOf(start to startTime)
    while (queue.isNotEmpty()) {
      val (index, time) = queue.removeFirst()
      if (index == end) return time
      if (visited.add(index to time)) {
        val blizzards = blizzardsAtTime(time)
        for (neighbor in index.adjacentIn(blizzards) + index) {
          if (blizzards[neighbor].isEmpty()) {
            queue.add(neighbor to (time + 1))
          }
        }
      }
    }
    fail()
  }

  private fun evolve(blizzards: Grid<EnumSet<Cell>>): Grid<EnumSet<Cell>> {
    return Grid(blizzards.numRows, blizzards.numColumns) { row, column ->
      if (WALL in blizzards[row, column]) {
        return@Grid blizzards[row, column]
      }

      val cell = EnumSet.noneOf(Cell::class.java)
      if (BLIZZARD_MOVING_UP in blizzards.firstNonWall(row, column) { it.down() }) {
        cell.add(BLIZZARD_MOVING_UP)
      }
      if (BLIZZARD_MOVING_DOWN in blizzards.firstNonWall(row, column) { it.up() }) {
        cell.add(BLIZZARD_MOVING_DOWN)
      }
      if (BLIZZARD_MOVING_LEFT in blizzards.firstNonWall(row, column) { it.right() }) {
        cell.add(BLIZZARD_MOVING_LEFT)
      }
      if (BLIZZARD_MOVING_RIGHT in blizzards.firstNonWall(row, column) { it.left() }) {
        cell.add(BLIZZARD_MOVING_RIGHT)
      }
      cell
    }
  }

  private inline fun Grid<EnumSet<Cell>>.firstNonWall(
    row: Int,
    column: Int,
    direction: (Index) -> Index,
  ): EnumSet<Cell> {
    var index = Index(row, column)
    do {
      index = direction(index)
    } while (WALL in getWrapped(index))
    return getWrapped(index)
  }

  companion object {
    val testInput1 = """
      #.######
      #>>.<^<#
      #.<..<<#
      #>v.><>#
      #<^v^^>#
      ######.#
      """.trimIndent()
    val testAnswer1 = 18

    val testInput2 = testInput1
    val testAnswer2 = 54
  }
}
