class PuzzleY2021D4 : Puzzle {

  private lateinit var parts: List<List<String>>
  private lateinit var draw: List<Int>

  override fun parse(input: String) {
    parts = input.lines().splitByBlank()
    draw = parts.first().parseIntLists().first()
  }

  override fun solve1(): Int {
    val boards: List<List<MutableList<Int?>>> =
      parts.drop(1).map { it.parseIntLists().map { it.toMutableList() } }

    for (number in draw) {
      for (board in boards) {
        strike(board, number)
        if (isWon(board)) {
          return score(board) * number
        }
      }
    }
    throw AssertionError()
  }

  override fun solve2(): Int {
    @Suppress("UNCHECKED_CAST")
    val boards: MutableList<List<MutableList<Int?>>> =
      parts.drop(1)
        .map { it.parseIntLists().map { it.toMutableList() as MutableList<Int?> } }
        .toMutableList()

    for (number in draw) {
      val it = boards.iterator()
      while (it.hasNext()) {
        val board = it.next()
        strike(board, number)
        if (isWon(board)) {
          it.remove()
          if (boards.isEmpty()) {
            return score(board) * number
          }
        }
      }
    }
    throw AssertionError()
  }

  private fun strike(board: List<MutableList<Int?>>, number: Int) {
    for (row in board.indices) {
      for (col in board[row].indices) {
        if (board[row][col] == number) {
          board[row][col] = null
        }
      }
    }
  }

  private fun isWon(board: List<MutableList<Int?>>): Boolean {
    for (row in board) {
      if (row.all { it == null }) {
        return true
      }
    }

    for (col in board.first().indices) {
      if (board.all { it[col] == null }) {
        return true
      }
    }

    return false
  }

  private fun score(board: List<MutableList<Int?>>): Int {
    return board.sumOf { it.filterNotNull().sum() }
  }

  companion object {
    val testInput1 = """
      7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1
      
      22 13 17 11  0
       8  2 23  4 24
      21  9 14 16  7
       6 10  3 18  5
       1 12 20 15 19
      
       3 15  0  2 22
       9 18 13 17  5
      19  8  7 25 23
      20 11 10 24  4
      14 21 16 12  6
      
      14 21 17 24  4
      10 16 15  9 19
      18  8 23 26 20
      22 11 13  6  5
       2  0 12  3  7
      """.trimIndent()
    val testAnswer1 = 4512

    val testInput2 = testInput1
    val testAnswer2 = 1924
  }
}
