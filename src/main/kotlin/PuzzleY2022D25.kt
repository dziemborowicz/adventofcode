import com.google.common.truth.Truth.assertThat

class PuzzleY2022D25 : Puzzle {

  private lateinit var snafuNumbers: List<String>

  override fun parse(input: String) {
    snafuNumbers = input.lines()
  }

  override fun solve1(): String {
    return snafuNumbers.map { it.parseSnafuNumber() }.sum().toSnafuNumber()
  }

  override fun solve2(): Int {
    // No answer required. This solves the puzzle when all 50 stars are unlocked.
    return 0
  }

  companion object {

    private val snafuDigits = listOf(
      '=',
      '-',
      '0',
      '1',
      '2',
    )

    private fun String.parseSnafuNumber(): Long {
      var result = 0L
      var factor = 1L
      for (i in this.lastIndex downTo 0) {
        result += (snafuDigits.indexOf(this[i]) - 2) * factor
        factor *= 5L
      }
      return result
    }

    private fun Int.toSnafuNumber() = toLong().toSnafuNumber()

    private fun Long.toSnafuNumber(): String {
      var result = ""
      var n = this
      while (n != 0L) {
        val digit = (n + 2) % 5
        n = (n + 2) / 5
        result = snafuDigits[digit.toInt()] + result
      }
      return result
    }

    fun testSnafuNumbers() {
      val cases = listOf(
        1 to "1",
        2 to "2",
        3 to "1=",
        4 to "1-",
        5 to "10",
        6 to "11",
        7 to "12",
        8 to "2=",
        9 to "2-",
        10 to "20",
        15 to "1=0",
        20 to "1-0",
        2022 to "1=11-2",
        12345 to "1-0---0",
        314159265 to "1121-1110-1=0",
      )

      for ((num, str) in cases) {
        assertThat(str.parseSnafuNumber()).isEqualTo(num)
        assertThat(num.toSnafuNumber()).isEqualTo(str)
      }
    }

    val testInput1 = """
      1=-0-2
      12111
      2=0=
      21
      2=01
      111
      20012
      112
      1=-1=
      1-12
      12
      1=
      122
      """.trimIndent()
    val testAnswer1 = "2=-1=0"
  }
}
