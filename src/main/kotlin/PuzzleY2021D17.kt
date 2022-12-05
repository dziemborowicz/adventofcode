class PuzzleY2021D17 : Puzzle {

  private lateinit var xRange: IntRange
  private lateinit var yRange: IntRange
  private lateinit var validVelocityStarts: Set<Pair<Int, Int>>

  override fun parse(input: String) {
    val regex = Regex("target area: x=(-?\\d+..-?\\d+), y=(-?\\d+..-?\\d+)")
    val result = regex.matchEntire(input) ?: fail()
    xRange = result.groupValues[1].toIntRange()
    yRange = result.groupValues[2].toIntRange()
    validVelocityStarts = (0..xRange.last + 1).flatMap { xVelocityStart ->
      var step = 0
      var x = 0
      var xVelocity = xVelocityStart
      val stepInTargetAreaXVelocityStartPairs = mutableListOf<Pair<Int, Int>>()
      while (x <= xRange.last && step <= 1000) {
        if (x in xRange) stepInTargetAreaXVelocityStartPairs.add(Pair(step, xVelocityStart))
        step++
        x += xVelocity
        when {
          xVelocity < 0 -> xVelocity++
          xVelocity > 0 -> xVelocity--
          else -> {}
        }
      }
      stepInTargetAreaXVelocityStartPairs
    }.groupBy({ it.first }, { it.second }).flatMap { (stepInTargetArea, xVelocityStarts) ->
      (-1000..1000).flatMap { yVelocityStart ->
        val yLast =
          (stepInTargetArea * (yVelocityStart + yVelocityStart - stepInTargetArea + 1)) / 2
        if (yLast in yRange) {
          xVelocityStarts.map { it to yVelocityStart }
        } else {
          listOf()
        }
      }
    }.toSet()
  }

  override fun solve1(): Int {
    return validVelocityStarts.maxOf { (_, yVelocityStart) ->
      (yVelocityStart * (yVelocityStart + 1)) / 2
    }
  }

  override fun solve2(): Int {
    return validVelocityStarts.count()
  }

  companion object {
    val testInput1 = "target area: x=20..30, y=-10..-5"
    val testAnswer1 = 45

    val testInput2 = testInput1
    val testAnswer2 = 112
  }
}
