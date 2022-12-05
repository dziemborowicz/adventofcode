class PuzzleY2021D10 : Puzzle {

  private lateinit var lines: List<String>

  override fun parse(input: String) {
    lines = input.lines()
  }

  override fun solve1(): Int {
    return lines.sumOf { syntaxErrorScore(it) }
  }

  override fun solve2(): Long {
    return lines.filter { syntaxErrorScore(it) == 0 }.map { line ->
      var score = 0L
      for (c in completion(line)) {
        score *= 5
        score += when (c) {
          ')' -> 1
          ']' -> 2
          '}' -> 3
          '>' -> 4
          else -> fail()
        }
      }
      score
    }.sorted().middle()
  }

  private fun syntaxErrorScore(line: String): Int {
    val stack = mutableListOf<Char>()
    for (c in line) {
      if (c.isChunkStart()) {
        stack.add(c)
      } else if (stack.removeLastOrNull()?.toChunkEnd() != c) {
        return when (c) {
          ')' -> 3
          ']' -> 57
          '}' -> 1197
          '>' -> 25137
          else -> fail()
        }
      }
    }
    return 0
  }

  private fun completion(line: String): String {
    val stack = mutableListOf<Char>()
    for (c in line) {
      if (c.isChunkStart()) {
        stack.add(c)
      } else {
        check(stack.removeLast().toChunkEnd() == c)
      }
    }
    return stack.reversed().map { it.toChunkEnd() }.joinToString("")
  }

  private fun Char.isChunkStart() = this in "([{<"

  private fun Char.toChunkEnd() = when (this) {
    '(' -> ')'
    '[' -> ']'
    '{' -> '}'
    '<' -> '>'
    else -> fail()
  }

  companion object {
    val testInput1 = """
      [({(<(())[]>[[{[]{<()<>>
      [(()[<>])]({[<{<<[]>>(
      {([(<{}[<>[]}>{[]{[(<()>
      (((({<>}<{<{<>}{[]{[]{}
      [[<[([]))<([[{}[[()]]]
      [{[{({}]{}}([{[{{{}}([]
      {<[[]]>}<{[{[{[]{()[[[]
      [<(<(<(<{}))><([]([]()
      <{([([[(<>()){}]>(<<{{
      <{([{{}}[<[[[<>{}]]]>[]]
      """.trimIndent()
    val testAnswer1 = 26397

    val testInput2 = testInput1
    val testAnswer2 = 288957
  }
}
