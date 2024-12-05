class PuzzleY2024D4 : Puzzle {

  private lateinit var wordSearch: Grid<Char>

  override fun parse(input: String) {
    wordSearch = input.parseDenseCharGrid()
  }

  override fun solve1(): Int {
    return (wordSearch.rows() + wordSearch.columns() + wordSearch.allDiagonals()).sumOf { list ->
      list.windowed(4).count { it.asString() matchesRegex "XMAS|SAMX" }
    }
  }

  override fun solve2(): Int {
    return wordSearch.windowed(3, 3).count { grid ->
      grid.diagonals().all { it.asString() matchesRegex "MAS|SAM" }
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
