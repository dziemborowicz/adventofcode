class PuzzleY2021D25 : Puzzle {

  private lateinit var seafloor: List<MutableList<Char>>

  override fun parse(input: String) {
    seafloor = input.parse { it.toMutableList() }
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
    val willMove = List(seafloor.size) { MutableList(seafloor[0].size) { false } }

    for (row in seafloor.indices) {
      for (col in seafloor[row].indices) {
        if (seafloor[row][col] == c && seafloor.getWrapped(row + y).getWrapped(col + x) == '.') {
          willMove[row][col] = true
        }
      }
    }

    var moved = false
    for (row in seafloor.indices) {
      for (col in seafloor[row].indices) {
        if (willMove[row][col]) {
          seafloor[row][col] = '.'
          seafloor.getWrapped(row + y).setWrapped(col + x, c)
          moved = true
        }
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
