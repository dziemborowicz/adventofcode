class PuzzleY2021D21 : Puzzle {

  private var player1StartingPosition: Int = -1
  private var player2StartingPosition: Int = -1

  override fun parse(input: String) {
    val lines = input.lines()
    val regex = Regex("Player \\d+ starting position: (\\d+)")
    player1StartingPosition = regex.matchEntire(lines[0])!!.groupValues[1].toInt() - 1
    player2StartingPosition = regex.matchEntire(lines[1])!!.groupValues[1].toInt() - 1
  }

  override fun solve1(): Int {
    var player1Position = player1StartingPosition
    var player1Score = 0
    var player2Position = player2StartingPosition
    var player2Score = 0
    var numRolls = 0
    var player1Turn = true
    while (player1Score < 1000 && player2Score < 1000) {
      val rollSum = (numRolls % 100) + ((numRolls + 1) % 100) + ((numRolls + 2) % 100) + 3
      numRolls += 3
      if (player1Turn) {
        player1Position = (player1Position + rollSum) % 10
        player1Score += player1Position + 1
        player1Turn = false
      } else {
        player2Position = (player2Position + rollSum) % 10
        player2Score += player2Position + 1
        player1Turn = true
      }
    }
    return minOf(player1Score, player2Score) * numRolls
  }

  override fun solve2(): Long {
    val rollSumToNumWays = mapOf(
      3 to 1,
      4 to 3,
      5 to 6,
      6 to 7,
      7 to 6,
      8 to 3,
      9 to 1,
    )

    var player1Position = player1StartingPosition
    var player1Score = 0
    var player1Wins = 0L
    var player2Position = player2StartingPosition
    var player2Score = 0
    var player2Wins = 0L
    var player1Turn = true

    fun play(numUniverses: Long) {
      for ((rollSum, numWays) in rollSumToNumWays) {
        val prevPlayer1Position = player1Position
        val prevPlayer1Score = player1Score
        val prevPlayer2Position = player2Position
        val prevPlayer2Score = player2Score
        val prevPlayer1Turn = player1Turn

        if (player1Turn) {
          player1Position = (player1Position + rollSum) % 10
          player1Score += player1Position + 1
          player1Turn = false
        } else {
          player2Position = (player2Position + rollSum) % 10
          player2Score += player2Position + 1
          player1Turn = true
        }

        when {
          player1Score >= 21 -> player1Wins += numUniverses * numWays
          player2Score >= 21 -> player2Wins += numUniverses * numWays
          else -> play(numUniverses * numWays)
        }

        player1Position = prevPlayer1Position
        player1Score = prevPlayer1Score
        player2Position = prevPlayer2Position
        player2Score = prevPlayer2Score
        player1Turn = prevPlayer1Turn
      }
    }

    play(1)

    return maxOf(player1Wins, player2Wins)
  }

  companion object {
    val testInput1 = """
      Player 1 starting position: 4
      Player 2 starting position: 8
      """.trimIndent()
    val testAnswer1 = 739785

    val testInput2 = testInput1
    val testAnswer2 = 444356092776315
  }
}
