class PuzzleY2021D25 : Puzzle {

  private lateinit var seafloor: Grid<Char>

  override fun parse(input: String) {
    seafloor = input.parseDenseCharGrid()
  }

  override fun solve1(): Int {
    var step = 1
    while (true) {
      val moved = move('>', 1, 0) or move('v', 0, 1)
      if (!moved) {
        return step
      } else {
        step++
      }
    }
  }

  private fun move(c: Char, x: Int, y: Int): Boolean {
    val willMove = Grid(seafloor.numRows, seafloor.numColumns, false)

    for (index in seafloor.indices) {
      if (seafloor[index] == c && seafloor.getWrapped(index.row + y, index.column + x) == '.') {
        willMove[index] = true
      }
    }

    var moved = false
    for (index in seafloor.indices) {
      if (willMove[index]) {
        seafloor[index] = '.'
        seafloor.setWrapped(index.row + y, index.column + x, c)
        moved = true
      }
    }
    return moved
  }

  override fun solve2(): Int {
    // No answer required. This solves the puzzle when all 50 stars are unlocked.
    return 0
  }

  companion object {
    val testInput1 = """
      v...>>.vv>
      .vv>>.vv..
      >>.>v>...v
      >>v>>.>.v.
      v>v.vv.v..
      >.>>..v...
      .vv..>.>v.
      v.v..>>v.v
      ....v..v.>
      """.trimIndent()
    val testAnswer1 = 58
  }
}
