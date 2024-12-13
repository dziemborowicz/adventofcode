class PuzzleY2024D13 : Puzzle {

  private lateinit var machineDefinitions: List<List<String>>

  override fun parse(input: String) {
    machineDefinitions = input.lines().splitByBlank()
  }

  override fun solve1(): Rational {
    return machineDefinitions.sumOfRational { lines ->
      val (ax, ay) = lines[0].extractRationals()
      val (bx, by) = lines[1].extractRationals()
      val (px, py) = lines[2].extractRationals()

      val a = (py - px * by / bx) / (ay - ax * by / bx)
      val b = (px - a * ax) / bx

      when {
        a < 0 || a > 100 || a.isNotIntegral() -> Rational.ZERO
        b < 0 || b > 100 || b.isNotIntegral() -> Rational.ZERO
        else -> (3 * a) + b
      }
    }
  }

  override fun solve2(): Rational {
    return machineDefinitions.sumOfRational { lines ->
      val (ax, ay) = lines[0].extractRationals()
      val (bx, by) = lines[1].extractRationals()
      val (px, py) = lines[2].extractRationals().map { it + 10000000000000 }

      val a = (py - px * by / bx) / (ay - ax * by / bx)
      val b = (px - a * ax) / bx

      when {
        a < 0 || a.isNotIntegral() -> Rational.ZERO
        b < 0 || b.isNotIntegral() -> Rational.ZERO
        else -> (3 * a) + b
      }
    }
  }

  companion object {
    val testInput1 = """
      Button A: X+94, Y+34
      Button B: X+22, Y+67
      Prize: X=8400, Y=5400
      
      Button A: X+26, Y+66
      Button B: X+67, Y+21
      Prize: X=12748, Y=12176
      
      Button A: X+17, Y+86
      Button B: X+84, Y+37
      Prize: X=7870, Y=6450
      
      Button A: X+69, Y+23
      Button B: X+27, Y+71
      Prize: X=18641, Y=10279
      """.trimIndent()
    val testAnswer1 = 480
  }
}
