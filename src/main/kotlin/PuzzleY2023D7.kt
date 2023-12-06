class PuzzleY2023D7 : Puzzle {

  private val cardTypes = listOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')

  private val handTypes = listOf(
    listOf(1, 1, 1, 1, 1),
    listOf(1, 1, 1, 2),
    listOf(1, 2, 2),
    listOf(1, 1, 3),
    listOf(2, 3),
    listOf(1, 4),
    listOf(5),
  )

  private data class HandBid(val hand: String, val bid: Int)

  private lateinit var handBids: List<HandBid>

  override fun parse(input: String) {
    handBids = input.lines().map { line ->
      val (hand, bid) = line.split(' ')
      HandBid(hand, bid.toInt())
    }
  }

  override fun solve1(): Int = solve(withJoker = false)

  override fun solve2(): Int = solve(withJoker = true)

  private fun solve(withJoker: Boolean): Int {
    return handBids.sortedWith(
      compareBy(
        { handStrengthOf(it.hand, withJoker) },
        { cardStrengthOf(it.hand[0], withJoker) },
        { cardStrengthOf(it.hand[1], withJoker) },
        { cardStrengthOf(it.hand[2], withJoker) },
        { cardStrengthOf(it.hand[3], withJoker) },
        { cardStrengthOf(it.hand[4], withJoker) },
      )
    ).withIndex().sumOf { (index, hand) -> (index + 1) * hand.bid }
  }

  private fun handStrengthOf(hand: String, withJoker: Boolean): Int {
    return if (withJoker) {
      cardTypes.maxOf { handStrengthOf(hand.replace('J', it), withJoker = false) }
    } else {
      val handType = hand.toList().toCounter().counts().map { it.count }.sorted()
      handTypes.indexOf(handType)
    }
  }

  private fun cardStrengthOf(card: Char, withJoker: Boolean): Int {
    return if (card == 'J' && withJoker) {
      -1
    } else {
      cardTypes.indexOf(card)
    }
  }

  companion object {
    val testInput1 = """
      32T3K 765
      T55J5 684
      KK677 28
      KTJJT 220
      QQQJA 483
      """.trimIndent()
    val testAnswer1 = 6440

    val testInput2 = testInput1
    val testAnswer2 = 5905
  }
}
