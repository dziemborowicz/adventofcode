class PuzzleY2020D2 : Puzzle {

  private lateinit var database: List<Pair<PasswordPolicy, String>>

  override fun parse(input: String) {
    database = input.lines().map { line ->
      val (passwordPolicy, password) = line.split(": ")
      PasswordPolicy.parse(passwordPolicy) to password
    }
  }

  override fun solve1(): Int {
    return database.count { (passwordPolicy, password) -> passwordPolicy.isValid1(password) }
  }

  override fun solve2(): Int {
    return database.count { (passwordPolicy, password) -> passwordPolicy.isValid2(password) }
  }

  private data class PasswordPolicy(val numbers: List<Int>, val char: Char) {

    fun isValid1(password: String): Boolean {
      return password.count { it == char } in numbers.toIntRange()
    }

    fun isValid2(password: String): Boolean {
      return numbers.count { password.getOrNull(it - 1) == char } == 1
    }

    companion object {
      fun parse(string: String): PasswordPolicy {
        val (count, char) = string.split(' ')
        return PasswordPolicy(count.extractInts(), char.toChar())
      }
    }
  }

  companion object {
    val testInput1 = """
      1-3 a: abcde
      1-3 b: cdefg
      2-9 c: ccccccccc
      """.trimIndent()
    val testAnswer1 = 2

    val testInput2 = testInput1
    val testAnswer2 = 1
  }
}
