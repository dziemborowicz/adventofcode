import Index.Companion.DIRECTIONS_WITH_DIAGONALS
import Index.Companion.DOWN_LEFT
import Index.Companion.DOWN_RIGHT
import Index.Companion.UP_LEFT
import Index.Companion.UP_RIGHT

class PuzzleY2024D4 : Puzzle {

  private lateinit var wordSearch: Grid<Char>

  override fun parse(input: String) {
    wordSearch = input.parseDenseCharGrid()
  }

  override fun solve1(): Int {
    return wordSearch.indices.sumOf { index ->
      DIRECTIONS_WITH_DIAGONALS.count { direction ->
        try {
          (0..3).map { wordSearch[index + (it * direction)] }.asString() == "XMAS"
        } catch (e: IndexOutOfBoundsException) {
          false
        }
      }
    }
  }

  override fun solve2(): Int {
    return wordSearch.indices.count { index ->
      try {
        val diagonal1 =
          "" + wordSearch[index + UP_LEFT] + wordSearch[index] + wordSearch[index + DOWN_RIGHT]
        val diagonal2 =
          "" + wordSearch[index + DOWN_LEFT] + wordSearch[index] + wordSearch[index + UP_RIGHT]
        (diagonal1 == "MAS" || diagonal1 == "SAM") && (diagonal2 == "MAS" || diagonal2 == "SAM")
      } catch (e: IndexOutOfBoundsException) {
        false
      }
    }
  }

  companion object {
    val testInput1 = """
      MMMSXXMASM
      MSAMXMSMSA
      AMXSXMAAMM
      MSAMASMSMX
      XMASAMXAMM
      XXAMMXXAMA
      SMSMSASXSS
      SAXAMASAAA
      MAMMMXMMMM
      MXMXAXMASX
      """.trimIndent()
    val testAnswer1 = 18

    val testInput2 = testInput1
    val testAnswer2 = 9
  }
}
