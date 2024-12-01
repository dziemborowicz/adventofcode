class PuzzleY2019D4 : Puzzle {

  private lateinit var range: IntRange

  override fun parse(input: String) {
    range = input.toIntRange()
  }

  override fun solve1(): Int = range.count {
    it.digits.windowed(2).all { (a, b) -> a <= b } && it.digits.toCounter().counts()
      .any { it.count >= 2 }
  }

  override fun solve2(): Int = range.count {
    it.digits.windowed(2).all { (a, b) -> a <= b } && it.digits.toCounter().counts()
      .any { it.count == 2 }
  }
}
