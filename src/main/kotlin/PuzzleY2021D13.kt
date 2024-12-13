class PuzzleY2021D13 : Puzzle {

  sealed interface Fold
  data class FoldLeft(val x: Int) : Fold
  data class FoldUp(val y: Int) : Fold

  private lateinit var dots: List<List<Int>>
  private lateinit var grid: Grid<Boolean>
  private lateinit var folds: List<Fold>

  override fun parse(input: String) {
    val parts = input.lines().splitByBlank()
    dots = parts[0].extractIntLists()
    grid = Grid(dots.maxOf { it.second() } + 1, dots.maxOf { it.first() } + 1, false).also { grid ->
      dots.forEach { grid[it.second(), it.first()] = true }
    }
    folds = parts[1].map { instruction ->
      val regex = Regex("fold along ([xy])=(\\d+)")
      val match = regex.matchEntire(instruction)!!
      if (match.groups[1]!!.value == "x") {
        FoldLeft(match.groups[2]!!.value.toInt())
      } else {
        FoldUp(match.groups[2]!!.value.toInt())
      }
    }
  }

  override fun solve1(): Int {
    val grid = grid.fold(folds.first())
    return grid.count { it }
  }

  override fun solve2(): String {
    return folds.fold(grid) { grid, fold -> grid.fold(fold) }.toStringFromAsciiArt()
  }

  private fun Grid<Boolean>.fold(fold: Fold): Grid<Boolean> {
    return when (fold) {
      is FoldLeft -> {
        Grid(this.numRows, fold.x) { (row, column) ->
          this[row, column] || this.getWrapped(row, -column - 1)
        }
      }
      is FoldUp -> {
        Grid(fold.y, this.numColumns) { (row, column) ->
          this[row, column] || this.getWrapped(-row - 1, column)
        }
      }
    }
  }

  companion object {
    val testInput1 = """
      6,10
      0,14
      9,10
      0,3
      10,4
      4,11
      6,0
      6,12
      4,1
      0,13
      10,12
      3,4
      3,0
      8,4
      1,10
      2,14
      8,10
      9,0
      
      fold along y=7
      fold along x=5
      """.trimIndent()
    val testAnswer1 = 17
  }
}
