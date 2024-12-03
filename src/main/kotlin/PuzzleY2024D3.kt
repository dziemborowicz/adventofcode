class PuzzleY2024D3 : Puzzle {

  private lateinit var instructions: String

  override fun parse(input: String) {
    this.instructions = input
  }

  override fun solve1(): Long {
    return Regex("""mul\((\d{1,3}),(\d{1,3})\)""").findAll(instructions).sumOf {
      it.groupValues[1].toLong() * it.groupValues[2].toLong()
    }
  }

  override fun solve2(): Long {
    var enabled = true
    return Regex("""mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)""").findAll(instructions).sumOf {
      when (it.value) {
        "do()" -> {
          enabled = true
          0L
        }

        "don't()" -> {
          enabled = false
          0L
        }

        else -> {
          if (enabled) {
            it.groupValues[1].toLong() * it.groupValues[2].toLong()
          } else {
            0L
          }
        }
      }
    }
  }

  companion object {
    val testInput1 = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
    val testAnswer1 = 161

    val testInput2 = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"
    val testAnswer2 = 48
  }
}
