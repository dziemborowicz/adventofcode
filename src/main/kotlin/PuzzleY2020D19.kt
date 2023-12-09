class PuzzleY2020D19 : Puzzle {

  private lateinit var rules: Map<String, String>
  private lateinit var messages: List<String>

  override fun parse(input: String) {
    val (ruleLines, messageLines) = input.lines().splitByBlank()
    rules = ruleLines.associate { it.split(": ").toPair() }
    messages = messageLines
  }

  override fun solve1(): Int {
    return messages.count { it.matches(rules) }
  }

  override fun solve2(): Int {
    val rules = rules.toMutableMap()
    rules["8"] = "42 | 42 8"
    rules["11"] = "42 31 | 42 11 31"
    return messages.count { it.matches(rules) }
  }

  private fun String.matches(rules: Map<String, String>): Boolean {
    return consume(rules, rules.getValue("0"), this).any { it == "" }
  }

  private fun consume(rules: Map<String, String>, rule: String, string: String): Sequence<String> {
    return sequence {
      when {
        rule.startsWith('"') -> {
          val value = rule.removeSurrounding("\"")
          if (string.startsWith(value)) {
            yield(string.substring(value.length))
          }
        }

        rule.contains(" | ") -> {
          for (alternate in rule.split(" | ")) {
            yieldAll(consume(rules, alternate, string))
          }
        }

        else -> {
          val remainders = mutableListOf(sequenceOf(string))
          for ((i, subRule) in rule.split(' ').withIndex()) {
            remainders.add(sequence {
              for (remainder in remainders[i]) {
                yieldAll(consume(rules, rules.getValue(subRule), remainder))
              }
            })
          }
          yieldAll(remainders.last())
        }
      }
    }
  }

  companion object {
    val testInput1 = """
      0: 4 1 5
      1: 2 3 | 3 2
      2: 4 4 | 5 5
      3: 4 5 | 5 4
      4: "a"
      5: "b"
      
      ababbb
      bababa
      abbbab
      aaabbb
      aaaabbb
      """.trimIndent()
    val testAnswer1 = 2

    val testInput2 = """
      42: 9 14 | 10 1
      9: 14 27 | 1 26
      10: 23 14 | 28 1
      1: "a"
      11: 42 31
      5: 1 14 | 15 1
      19: 14 1 | 14 14
      12: 24 14 | 19 1
      16: 15 1 | 14 14
      31: 14 17 | 1 13
      6: 14 14 | 1 14
      2: 1 24 | 14 4
      0: 8 11
      13: 14 3 | 1 12
      15: 1 | 14
      17: 14 2 | 1 7
      23: 25 1 | 22 14
      28: 16 1
      4: 1 1
      20: 14 14 | 1 15
      3: 5 14 | 16 1
      27: 1 6 | 14 18
      14: "b"
      21: 14 1 | 1 14
      25: 1 1 | 1 14
      22: 14 14
      8: 42
      26: 14 22 | 1 20
      18: 15 15
      7: 14 5 | 1 21
      24: 14 1

      abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa
      bbabbbbaabaabba
      babbbbaabbbbbabbbbbbaabaaabaaa
      aaabbbbbbaaaabaababaabababbabaaabbababababaaa
      bbbbbbbaaaabbbbaaabbabaaa
      bbbababbbbaaaaaaaabbababaaababaabab
      ababaaaaaabaaab
      ababaaaaabbbaba
      baabbaaaabbaaaababbaababb
      abbbbabbbbaaaababbbbbbaaaababb
      aaaaabbaabaaaaababaa
      aaaabbaaaabbaaa
      aaaabbaabbaaaaaaabbbabbbaaabbaabaaa
      babaaabbbaaabaababbaabababaaab
      aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba
      """.trimIndent()
    val testAnswer2 = 12
  }
}
