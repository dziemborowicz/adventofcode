class PuzzleY2025D2 : Puzzle {

  private lateinit var ranges: List<LongRange>

  override fun parse(input: String) {
    ranges = input.split(",").map {
      it.split("-").map { it.toLong() }.toLongRange()
    }
  }

  override fun solve1(): Long {
    return ranges.sumOf { range ->
      range.filter {
        val id = it.toString()
        id.take(id.length / 2) * 2 == id
      }.sum()
    }
  }

  override fun solve2(): Long {
    return ranges.sumOf { range ->
      range.filter {
        val id = it.toString()
        (1..id.length / 2).any { seqLength ->
          id.take(seqLength) * (id.length / seqLength) == id
        }
      }.sum()
    }
  }

  private operator fun String.times(times: Int): String {
    val string = this
    return buildString {
      repeat(times) {
        append(string)
      }
    }
  }

  companion object {
    val testInput1 =
      "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"
    val testAnswer1 = 1227775554

    val testInput2 = testInput1
    val testAnswer2 = 4174379265
  }
}
