import java.lang.Math.floorMod

class PuzzleY2022D20 : Puzzle {

  private data class Number(var index: Int, val value: Long)

  private lateinit var numbers: List<Long>

  override fun parse(input: String) {
    numbers = input.parseLongs()
  }

  override fun solve1(): Long {
    val mixedNumbers = mix(numbers)
    val grooveCoordinates = grooveCoordinates(mixedNumbers)
    return grooveCoordinates.sum()
  }

  override fun solve2(): Long {
    val decryptionKey = 811589153
    val mixedNumbers = mix(numbers.map { it * decryptionKey }, times = 10)
    val grooveCoordinates = grooveCoordinates(mixedNumbers)
    return grooveCoordinates.sum()
  }

  private fun mix(numbers: List<Long>, times: Int = 1): List<Long> {
    val wrappedNumbers = numbers.mapIndexed { index, value -> Number(index, value) }
    val mixedNumbers = wrappedNumbers.toDeque()
    repeat(times) {
      for (number in wrappedNumbers) {
        mixedNumbers.removeAt(number.index)
        mixedNumbers.add(floorMod(number.index + number.value, mixedNumbers.size), number)
        mixedNumbers.forEachIndexed { i, n -> n.index = i }
      }
    }
    return mixedNumbers.map { it.value }
  }

  private fun grooveCoordinates(numbers: List<Long>): List<Long> {
    val indexOfZero = numbers.indexOf(0L)
    return listOf(
      numbers[(indexOfZero + 1000) % numbers.size],
      numbers[(indexOfZero + 2000) % numbers.size],
      numbers[(indexOfZero + 3000) % numbers.size],
    )
  }

  companion object {
    val testInput1 = """
      1
      2
      -3
      3
      -2
      0
      4
      """.trimIndent()
    val testAnswer1 = 3

    val testInput2 = testInput1
    val testAnswer2 = 1623178306
  }
}
