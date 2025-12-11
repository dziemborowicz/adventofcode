class PuzzleY2025D11 : Puzzle {

  private lateinit var neighbors: Map<String, List<String>>
  private val waysCache = cacheOf<Long>()

  override fun parse(input: String) {
    neighbors = input.extractStringLists().associate { it.first() to it.drop(1) }
  }

  override fun solve1(): Long {
    return ways("you", "out")
  }

  override fun solve2(): Long {
    val waysSvrToDac = ways("svr", "dac")
    val waysDacToFft = ways("dac", "fft")
    val waysFftToOut = ways("fft", "out")

    val waysSvrToFft = ways("svr", "fft")
    val waysFftToDac = ways("fft", "dac")
    val waysDacToOut = ways("dac", "out")

    return (waysSvrToDac * waysDacToFft * waysFftToOut) + (waysSvrToFft * waysFftToDac * waysDacToOut)
  }

  private fun ways(start: String, end: String): Long {
    return waysCache.getOrPut(start, end) {
      if (start == end) {
        1L
      } else {
        neighborsOf(start).sumOf { neighbor -> ways(neighbor, end) }
      }
    }
  }

  private fun neighborsOf(node: String): List<String> = neighbors[node] ?: emptyList()

  companion object {
    val testInput1 = """
      aaa: you hhh
      you: bbb ccc
      bbb: ddd eee
      ccc: ddd eee fff
      ddd: ggg
      eee: out
      fff: out
      ggg: out
      hhh: ccc fff iii
      iii: out
      """.trimIndent()
    val testAnswer1 = 5

    val testInput2 = """
      svr: aaa bbb
      aaa: fft
      fft: ccc
      bbb: tty
      tty: ccc
      ccc: ddd eee
      ddd: hub
      hub: fff
      eee: dac
      dac: fff
      fff: ggg hhh
      ggg: out
      hhh: out
      """.trimIndent()
    val testAnswer2 = 2
  }
}
