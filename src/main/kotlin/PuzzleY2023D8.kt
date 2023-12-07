class PuzzleY2023D8 : Puzzle {

  private lateinit var instructions: String
  private lateinit var map: Map<String, Pair<String, String>>

  override fun parse(input: String) {
    val (instructions, map) = input.lines().splitByBlank()
    this.instructions = instructions.single()
    this.map = map.associate { line ->
      val (source, left, right) = line.split(" = (", ", ", ")")
      source to (left to right)
    }
  }

  override fun solve1(): Long = navigate("AAA") { it == "ZZZ" }

  override fun solve2(): Long =
    map.keys.filter { it.endsWith("A") }.map { navigate(it) { it.endsWith("Z") } }.lcm()

  private fun navigate(source: String, destinationPredicate: (String) -> Boolean): Long {
    var step = 0L
    var location = source
    while (!destinationPredicate(location)) {
      location = if (instructions[(step++).mod(instructions.length)] == 'L') {
        map.getValue(location).first
      } else {
        map.getValue(location).second
      }
    }
    return step
  }

  companion object {
    val testInput1 = """
      RL
      
      AAA = (BBB, CCC)
      BBB = (DDD, EEE)
      CCC = (ZZZ, GGG)
      DDD = (DDD, DDD)
      EEE = (EEE, EEE)
      GGG = (GGG, GGG)
      ZZZ = (ZZZ, ZZZ)
      """.trimIndent()
    val testAnswer1 = 2

    val testInput2 = """
      LR
      
      11A = (11B, XXX)
      11B = (XXX, 11Z)
      11Z = (11B, XXX)
      22A = (22B, XXX)
      22B = (22C, 22C)
      22C = (22Z, 22Z)
      22Z = (22B, 22B)
      XXX = (XXX, XXX)
      """.trimIndent()
    val testAnswer2 = 6
  }
}
