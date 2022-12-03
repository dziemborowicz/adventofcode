class PuzzleY2021D8 : Puzzle {

  private lateinit var displays: List<Pair<List<Set<Char>>, List<Set<Char>>>>

  override fun parse(input: String) {
    displays = input.lines().map { line ->
      val parts = line.split(" | ")
      Pair(parts[0].split(' ').map { it.toSet() }, parts[1].split(' ').map { it.toSet() })
    }
  }

  override fun solve1(): Int {
    return displays.sumOf { display ->
      display.second.count { it.size in listOf(2, 4, 3, 7) }
    }
  }

  override fun solve2(): Int {
    return displays.sumOf { display ->
      val digitsToSegments = mutableMapOf<Int, Set<Char>>()

      val candidates = display.first.toMutableList()

      digitsToSegments[1] = candidates.removeSingleIf { it.size == 2 }
      digitsToSegments[4] = candidates.removeSingleIf { it.size == 4 }
      digitsToSegments[7] = candidates.removeSingleIf { it.size == 3 }
      digitsToSegments[8] = candidates.removeSingleIf { it.size == 7 }

      digitsToSegments[6] =
        candidates.removeSingleIf { it.size == 6 && !it.containsAll(digitsToSegments[1]!!) }
      digitsToSegments[9] =
        candidates.removeSingleIf { it.size == 6 && it.containsAll(digitsToSegments[4]!!) }
      digitsToSegments[0] =
        candidates.removeSingleIf { it.size == 6 && !it.containsAll(digitsToSegments[4]!!) }

      digitsToSegments[3] =
        candidates.removeSingleIf { it.size == 5 && it.containsAll(digitsToSegments[1]!!) }
      digitsToSegments[5] =
        candidates.removeSingleIf { it.size == 5 && digitsToSegments[9]!!.containsAll(it) }
      digitsToSegments[2] =
        candidates.single()

      val digits = display.second.map {
        digitsToSegments.entries.first { (_, segments) -> segments equivalent it }.key
      }
      digits.reversed().mapIndexed { index, digit -> digit * 10.pow(index) }.sum()
    }
  }

  companion object {
    val testInput1 = """
      be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
      edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
      fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
      fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
      aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
      fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
      dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
      bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
      egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
      gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce
      """.trimIndent()
    val testAnswer1 = 26

    val testInput2 = testInput1
    val testAnswer2 = 61229
  }
}
