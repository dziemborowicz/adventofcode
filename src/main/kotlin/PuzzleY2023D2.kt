class PuzzleY2023D2 : Puzzle {

  private data class Game(
    val id: Int,
    val drawings: List<Map<String, Int>>,
  )

  private lateinit var games: List<Game>

  override fun parse(input: String) {
    games = input.lines().map { line ->
      val (id, drawings) = line.split(": ")
      Game(
        id.extractInts().single(),
        drawings.split("; ").map { drawing ->
          drawing.split(", ").associate {
            val (count, color) = it.split(" ")
            color to count.toInt()
          }
        },
      )
    }
  }

  override fun solve1(): Int {
    val availableCubes = mapOf("red" to 12, "green" to 13, "blue" to 14)
    return games.filter { game ->
      game.drawings.all { drawing ->
        drawing.all { (color, count) -> count <= (availableCubes[color] ?: 0) }
      }
    }.sumOf { it.id }
  }

  override fun solve2(): Int {
    return games.sumOf { game ->
      val maxCounts = hashMapOf<String, Int>()
      game.drawings.forEach { drawing ->
        drawing.forEach { (color, count) ->
          maxCounts.compute(color) { _, maxCount ->
            maxOf(count, maxCount ?: 0)
          }
        }
      }
      maxCounts.values.product()
    }
  }

  companion object {
    val testInput1 = """
      Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
      Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
      Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
      Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
      Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
      """.trimIndent()
    val testAnswer1 = 8

    val testInput2 = testInput1
    val testAnswer2 = 2286
  }
}
