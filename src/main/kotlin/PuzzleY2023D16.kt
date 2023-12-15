import Index.Companion.DOWN
import Index.Companion.LEFT
import Index.Companion.RIGHT
import Index.Companion.UP
import Index.Companion.ZERO

class PuzzleY2023D16 : Puzzle {

  private lateinit var grid: Grid<Char>

  override fun parse(input: String) {
    grid = input.parseDenseCharGrid()
  }

  override fun solve1(): Int {
    return countEnergizedFrom(ZERO to RIGHT)
  }

  override fun solve2(): Int {
    val starts =
      grid.rowIndices.map { Index(it, 0) to RIGHT } +
        grid.rowIndices.map { Index(it, grid.columnIndices.last) to LEFT } +
        grid.columnIndices.map { Index(0, it) to DOWN } +
        grid.columnIndices.map { Index(grid.columnIndices.last, it) to UP }

    return starts.maxOf { countEnergizedFrom(it) }
  }

  private fun countEnergizedFrom(start: Pair<Index, Index>): Int {
    val queue = dequeOf(start)
    val visited = hashSetOf<Pair<Index, Index>>()
    while (queue.isNotEmpty()) {
      val (index, direction) = queue.removeFirst()
      if (index in grid.indices && visited.add(index to direction)) {
        queue += when (grid[index]) {
          '.' -> listOf((index + direction) to direction)

          '/' -> when (direction) {
            LEFT -> listOf((index + DOWN) to DOWN)
            RIGHT -> listOf((index + UP) to UP)
            UP -> listOf((index + RIGHT) to RIGHT)
            DOWN -> listOf((index + LEFT) to LEFT)
            else -> fail()
          }

          '\\' -> when (direction) {
            LEFT -> listOf((index + UP) to UP)
            RIGHT -> listOf((index + DOWN) to DOWN)
            UP -> listOf((index + LEFT) to LEFT)
            DOWN -> listOf((index + RIGHT) to RIGHT)
            else -> fail()
          }

          '-' -> when (direction) {
            LEFT, RIGHT -> listOf((index + direction) to direction)
            UP, DOWN -> listOf((index + LEFT) to LEFT, (index + RIGHT) to RIGHT)
            else -> fail()
          }

          '|' -> when (direction) {
            LEFT, RIGHT -> listOf((index + UP) to UP, (index + DOWN) to DOWN)
            UP, DOWN -> listOf((index + direction) to direction)
            else -> fail()
          }

          else -> fail()
        }
      }
    }
    return visited.map { it.first }.toSet().size
  }

  companion object {
    val testInput1 = """
      .|...\....
      |.-.\.....
      .....|-...
      ........|.
      ..........
      .........\
      ..../.\\..
      .-.-/..|..
      .|....-|.\
      ..//.|....
      """.trimIndent()
    val testAnswer1 = 46

    val testInput2 = testInput1
    val testAnswer2 = 51
  }
}
