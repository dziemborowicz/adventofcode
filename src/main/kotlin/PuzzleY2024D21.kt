class PuzzleY2024D21 : Puzzle {

  private val numericKeypad = """
    789
    456
    123
     0A
    """.trimIndent().parseDenseCharGrid()

  private val directionalKeypad = """
     ^A
    <v>
    """.trimIndent().parseDenseCharGrid()

  private val numInputs = cacheOf<Long>()

  private lateinit var codes: List<String>

  override fun parse(input: String) {
    codes = input.lines()
  }

  override fun solve1(): Long = solve(depth = 3)

  override fun solve2(): Long = solve(depth = 26)

  private fun solve(depth: Int): Long {
    return codes.sumOf {
      val prefix = it.dropLast(1).toLong()
      val numInputs = "A$it".windowed(2).sumOf { (a, b) ->
        numInputs(numericKeypad, a, b, depth)
      }
      prefix * numInputs
    }
  }

  private fun numInputs(keypad: Grid<Char>, from: Char, to: Char, depth: Int): Long {
    if (depth == 0) return 1L
    return numInputs.getOrPut(keypad, from, to, depth) {
      sequences(from, to, keypad).minOf {
        "A$it".windowed(2).sumOf { (a, b) ->
          numInputs(directionalKeypad, a, b, depth - 1)
        }
      }
    }
  }

  private fun sequences(from: Char, to: Char, keypad: Grid<Char>): List<String> {
    val queue = dequeOf(keypad.indexOf(from) to "")
    val result = mutableListOf<String>()
    var minLength = Int.MAX_VALUE
    while (queue.isNotEmpty()) {
      val (index, sequence) = queue.removeFirst()
      if (index !in keypad.indices || keypad[index] == ' ' || sequence.length + 1 > minLength) {
        continue
      } else if (keypad[index] == to) {
        minLength = sequence.length + 1
        result += sequence + "A"
      } else {
        queue += (index + Index.UP) to (sequence + "^")
        queue += (index + Index.RIGHT) to (sequence + ">")
        queue += (index + Index.DOWN) to (sequence + "v")
        queue += (index + Index.LEFT) to (sequence + "<")
      }
    }
    return result
  }

  companion object {
    val testInput1 = """
      029A
      980A
      179A
      456A
      379A
      """.trimIndent()
    val testAnswer1 = 126384
  }
}
