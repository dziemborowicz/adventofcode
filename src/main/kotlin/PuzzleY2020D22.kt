class PuzzleY2020D22 : Puzzle {

  private data class GameResult(val winner: Int, val score: Int)

  private lateinit var cards: List<List<Int>>

  override fun parse(input: String) {
    cards = input.lines().splitByBlank().map { it.drop(1).parseInts() }
  }

  override fun solve1(): Int = play(cards, recursive = false).score

  override fun solve2(): Int = play(cards, recursive = true).score

  private fun play(startingCards: List<List<Int>>, recursive: Boolean): GameResult {
    val cards = startingCards.map { it.toDeque() }
    val visited = mutableSetOf<List<List<Int>>>()
    while (cards.all { it.isNotEmpty() }) {
      if (!visited.add(cards.copy())) return GameResult(winner = 0, cards[0].score())
      val played = cards.map { it.removeFirst() }
      val winner =
        if (recursive && (played zip cards).all { (topCard, rest) -> topCard <= rest.size }) {
          val cardsCopy = (played zip cards).map { (topCard, rest) -> rest.take(topCard).toDeque() }
          play(cardsCopy, recursive = true).winner
        } else {
          played.indexOf(played.max())
        }
      val loser = if (winner == 0) 1 else 0
      cards[winner].add(played[winner])
      cards[winner].add(played[loser])
    }
    val winner = cards.indexOfFirst { it.isNotEmpty() }
    return GameResult(winner, cards[winner].score())
  }

  private fun List<Int>.score() = reversed().withIndex().sumOf { (i, card) -> (i + 1) * card }

  companion object {
    val testInput1 = """
      Player 1:
      9
      2
      6
      3
      1
      
      Player 2:
      5
      8
      4
      7
      10
      """.trimIndent()
    val testAnswer1 = 306

    val testInput2 = testInput1
    val testAnswer2 = 291

    fun testInfiniteGamePrevention() {
      PuzzleY2020D22().apply {
        val input = """
          Player 1:
          43
          19
          
          Player 2:
          2
          29
          14
          """.trimIndent()
        parse(input)
        solve2()
      }
    }
  }
}
