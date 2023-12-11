class PuzzleY2020D25 : Puzzle {

  private lateinit var publicKeys: List<Long>

  override fun parse(input: String) {
    publicKeys = input.parseLongs()
  }

  override fun solve1(): Long {
    val (a, b) = publicKeys
    return a.transform(loopSize(b))
  }

  override fun solve2(): Int = 0

  private fun loopSize(publicKey: Long): Int {
    var loopSize = 0
    var value = 1L
    while (value != publicKey) {
      value = (value * 7L).mod(20201227L)
      loopSize++
    }
    return loopSize
  }

  private fun Long.transform(loopSize: Int): Long {
    var value = 1L
    repeat(loopSize) { value = (value * this).mod(20201227L) }
    return value
  }

  companion object {
    val testInput1 = """
      5764801
      17807724
      """.trimIndent()
    val testAnswer1 = 14897079
  }
}
