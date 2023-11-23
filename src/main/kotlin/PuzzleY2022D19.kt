import com.google.common.truth.Truth.assertThat

class PuzzleY2022D19 : Puzzle {

  private data class State(
    val timeLeft: Int,

    val ore: Int = 0,
    val clay: Int = 0,
    val obsidian: Int = 0,
    val geodes: Int = 0,

    val oreRobots: Int = 0,
    val clayRobots: Int = 0,
    val obsidianRobots: Int = 0,
    val geodeRobots: Int = 0,
  )

  private data class Blueprint(
    val id: Int,

    val oreRobotOre: Int,

    val clayRobotOre: Int,

    val obsidianRobotOre: Int,
    val obsidianRobotClay: Int,

    val geodeRobotOre: Int,
    val geodeRobotObsidian: Int,
  )

  private lateinit var blueprints: List<Blueprint>

  override fun parse(input: String) {
    blueprints = parseBlueprints(input)
  }

  override fun solve1(): Int {
    return blueprints.sumOf { it.id * maxGeodesPossible(it, time = 24) }
  }

  override fun solve2(): Int {
    return blueprints.take(3).productOf { maxGeodesPossible(it, time = 32) }
  }

  companion object {
    private fun parseBlueprints(input: String): List<Blueprint> {
      return input.lines().map { line ->
        val numbers = line.extractInts()
        Blueprint(
          id = numbers[0],
          oreRobotOre = numbers[1],
          clayRobotOre = numbers[2],
          obsidianRobotOre = numbers[3],
          obsidianRobotClay = numbers[4],
          geodeRobotOre = numbers[5],
          geodeRobotObsidian = numbers[6],
        )
      }
    }

    private fun maxGeodesPossible(blueprint: Blueprint, time: Int): Int {
      val maxOreToBuildAnyRobot = maxOf(
        blueprint.oreRobotOre,
        blueprint.clayRobotOre,
        blueprint.obsidianRobotOre,
        blueprint.geodeRobotOre,
      )

      var maxGeodes = Int.MIN_VALUE
      val visited = hashSetOf<State>()
      val queue = dequeOf(State(timeLeft = time, oreRobots = 1))
      while (queue.isNotEmpty()) {
        val curr = queue.removeFirst()

        if (curr.timeLeft <= 0) {
          maxGeodes = maxOf(maxGeodes, curr.geodes)
          continue
        }

        val key = curr.copy(
          ore = if (curr.oreRobots >= maxOreToBuildAnyRobot) Int.MAX_VALUE else curr.ore,
          oreRobots = if (curr.oreRobots >= maxOreToBuildAnyRobot) Int.MAX_VALUE else curr.oreRobots,
          clay = if (curr.clayRobots >= blueprint.obsidianRobotClay) Int.MAX_VALUE else curr.clay,
          clayRobots = if (curr.clayRobots >= blueprint.obsidianRobotClay) Int.MAX_VALUE else curr.clayRobots,
          obsidian = if (curr.obsidianRobots >= blueprint.geodeRobotObsidian) Int.MAX_VALUE else curr.obsidian,
          obsidianRobots = if (curr.obsidianRobots >= blueprint.geodeRobotObsidian) Int.MAX_VALUE else curr.obsidianRobots,
        )

        if (!visited.add(key)) {
          continue
        }

        var canBuildGeodeRobot = false
        if (curr.ore >= blueprint.geodeRobotOre && curr.obsidian >= blueprint.geodeRobotObsidian) {
          queue.add(
            curr.copy(
              geodeRobots = curr.geodeRobots + 1,

              ore = curr.ore + curr.oreRobots - blueprint.geodeRobotOre,
              clay = curr.clay + curr.clayRobots,
              obsidian = curr.obsidian + curr.obsidianRobots - blueprint.geodeRobotObsidian,
              geodes = curr.geodes + curr.geodeRobots,

              timeLeft = curr.timeLeft - 1,
            )
          )
          canBuildGeodeRobot = true
        }

        if (!canBuildGeodeRobot && curr.ore >= blueprint.oreRobotOre && curr.oreRobots < maxOreToBuildAnyRobot) {
          queue.add(
            curr.copy(
              oreRobots = curr.oreRobots + 1,

              ore = curr.ore + curr.oreRobots - blueprint.oreRobotOre,
              clay = curr.clay + curr.clayRobots,
              obsidian = curr.obsidian + curr.obsidianRobots,
              geodes = curr.geodes + curr.geodeRobots,

              timeLeft = curr.timeLeft - 1,
            )
          )
        }

        if (!canBuildGeodeRobot && curr.ore >= blueprint.clayRobotOre && curr.clayRobots < blueprint.obsidianRobotClay) {
          queue.add(
            curr.copy(
              clayRobots = curr.clayRobots + 1,

              ore = curr.ore + curr.oreRobots - blueprint.clayRobotOre,
              clay = curr.clay + curr.clayRobots,
              obsidian = curr.obsidian + curr.obsidianRobots,
              geodes = curr.geodes + curr.geodeRobots,

              timeLeft = curr.timeLeft - 1,
            )
          )
        }

        if (!canBuildGeodeRobot && curr.ore >= blueprint.obsidianRobotOre && curr.clay >= blueprint.obsidianRobotClay && curr.obsidianRobots < blueprint.geodeRobotObsidian) {
          queue.add(
            curr.copy(
              obsidianRobots = curr.obsidianRobots + 1,

              ore = curr.ore + curr.oreRobots - blueprint.obsidianRobotOre,
              clay = curr.clay + curr.clayRobots - blueprint.obsidianRobotClay,
              obsidian = curr.obsidian + curr.obsidianRobots,
              geodes = curr.geodes + curr.geodeRobots,

              timeLeft = curr.timeLeft - 1,
            )
          )
        }

        if (!canBuildGeodeRobot) {
          queue.add(
            curr.copy(
              ore = curr.ore + curr.oreRobots,
              clay = curr.clay + curr.clayRobots,
              obsidian = curr.obsidian + curr.obsidianRobots,
              geodes = curr.geodes + curr.geodeRobots,

              timeLeft = curr.timeLeft - 1,
            )
          )
        }
      }
      return maxGeodes
    }

    val testInput1 = """
     Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
     Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.
     """.trimIndent()
    val testAnswer1 = 33

    fun test2() {
      val blueprints = parseBlueprints(testInput1)
      assertThat(maxGeodesPossible(blueprints[0], time = 32)).isEqualTo(56)
      assertThat(maxGeodesPossible(blueprints[1], time = 32)).isEqualTo(62)
    }
  }
}
