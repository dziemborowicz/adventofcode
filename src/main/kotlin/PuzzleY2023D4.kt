class PuzzleY2023D4 : Puzzle {

  private data class Game(val winning: Set<Int>, val have: Set<Int>, var count: Int = 1)

  private lateinit var games: List<Game>

  override fun parse(input: String) {
    games = input.lines().map { line ->
      val (winning, have) = line.split(": ")[1].split(" | ")
      Game(winning.extractInts().toSet(), have.extractInts().toSet())
    }
  }

  override fun solve1(): Int {
    return games.sumOf { game ->
      if (game.numWinning > 0) {
        2 pow (game.numWinning - 1)
      } else {
        0
      }
    }
  }

  override fun solve2(): Int {
    val games = games.map { it.copy() }
    for ((index, game) in games.withIndex()) {
      for (nextGame in games.subList(index + 1, index + 1 + game.numWinning)) {
        nextGame.count += game.count
      }
    }
    return games.sumOf { it.count }
  }

  private val Game.numWinning: Int
    get() = (winning intersect have).size

  companion object {
    val testInput1 = """
      Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
      Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
      Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
      Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
      Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
      Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
      """.trimIndent()
    val testAnswer1 = 13

    val testInput2 = testInput1
    val testAnswer2 = 30
  }
}
