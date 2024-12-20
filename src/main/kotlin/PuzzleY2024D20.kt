import com.google.common.truth.Truth.assertThat

class PuzzleY2024D20 : Puzzle {

  private lateinit var racetrack: Grid<Char>

  override fun parse(input: String) {
    racetrack = input.parseDenseCharGrid()
  }

  override fun solve1(): Int = solve(racetrack, cheatTime = 2, minSaving = 100)

  override fun solve2(): Int = solve(racetrack, cheatTime = 20, minSaving = 100)

  companion object {
    private fun solve(racetrack: Grid<Char>, cheatTime: Int, minSaving: Int): Int {
      val distances = racetrack.map { null as Int? }
      var current: Index? = racetrack.indexOf('S')
      var distance = 0
      while (current != null) {
        distances[current] = distance
        current = current.neighborsIn(racetrack).singleOrNull {
          racetrack[it] != '#' && distances[it] == null
        }
        distance++
      }

      val cheats =
        (-cheatTime..cheatTime).permutationsWithReplacement(2).map { (r, c) -> Index(r, c) }
          .filter { it manhattanDistanceTo Index.ZERO <= cheatTime }
      return racetrack.indices.sumOf { cheatStart ->
        val cheatStartDistance = distances[cheatStart] ?: return@sumOf 0
        cheats.map { cheat -> cheatStart + cheat }.count { cheatEnd ->
          if (cheatEnd !in distances.indices) return@count false
          val cheatEndDistance = distances[cheatEnd] ?: return@count false
          cheatEndDistance - cheatStartDistance > minSaving + (cheatEnd manhattanDistanceTo cheatStart) - 1
        }
      }
    }

    private val testInput = """
        ###############
        #...#...#.....#
        #.#.#.#.#.###.#
        #S#...#.#.#...#
        #######.#.#.###
        #######.#.#...#
        #######.#.###.#
        ###..E#...#...#
        ###.#######.###
        #...###...#...#
        #.#####.#.###.#
        #.#...#.#.#...#
        #.#.#.#.#.#.###
        #...#...#...###
        ###############
        """.trimIndent()
    private val testRacetrack = testInput.parseDenseCharGrid()

    fun test1() {
      assertThat(solve(testRacetrack, cheatTime = 2, minSaving = 2)).isEqualTo(44)
      assertThat(solve(testRacetrack, cheatTime = 2, minSaving = 4)).isEqualTo(30)
      assertThat(solve(testRacetrack, cheatTime = 2, minSaving = 6)).isEqualTo(16)
      assertThat(solve(testRacetrack, cheatTime = 2, minSaving = 8)).isEqualTo(14)
      assertThat(solve(testRacetrack, cheatTime = 2, minSaving = 10)).isEqualTo(10)
      assertThat(solve(testRacetrack, cheatTime = 2, minSaving = 12)).isEqualTo(8)
      assertThat(solve(testRacetrack, cheatTime = 2, minSaving = 20)).isEqualTo(5)
      assertThat(solve(testRacetrack, cheatTime = 2, minSaving = 36)).isEqualTo(4)
      assertThat(solve(testRacetrack, cheatTime = 2, minSaving = 38)).isEqualTo(3)
      assertThat(solve(testRacetrack, cheatTime = 2, minSaving = 40)).isEqualTo(2)
      assertThat(solve(testRacetrack, cheatTime = 2, minSaving = 64)).isEqualTo(1)
    }

    fun test2() {
      assertThat(solve(testRacetrack, cheatTime = 20, minSaving = 50)).isEqualTo(285)
      assertThat(solve(testRacetrack, cheatTime = 20, minSaving = 52)).isEqualTo(253)
      assertThat(solve(testRacetrack, cheatTime = 20, minSaving = 54)).isEqualTo(222)
      assertThat(solve(testRacetrack, cheatTime = 20, minSaving = 56)).isEqualTo(193)
      assertThat(solve(testRacetrack, cheatTime = 20, minSaving = 58)).isEqualTo(154)
      assertThat(solve(testRacetrack, cheatTime = 20, minSaving = 60)).isEqualTo(129)
      assertThat(solve(testRacetrack, cheatTime = 20, minSaving = 62)).isEqualTo(106)
      assertThat(solve(testRacetrack, cheatTime = 20, minSaving = 64)).isEqualTo(86)
      assertThat(solve(testRacetrack, cheatTime = 20, minSaving = 66)).isEqualTo(67)
      assertThat(solve(testRacetrack, cheatTime = 20, minSaving = 68)).isEqualTo(55)
      assertThat(solve(testRacetrack, cheatTime = 20, minSaving = 70)).isEqualTo(41)
      assertThat(solve(testRacetrack, cheatTime = 20, minSaving = 72)).isEqualTo(29)
      assertThat(solve(testRacetrack, cheatTime = 20, minSaving = 74)).isEqualTo(7)
      assertThat(solve(testRacetrack, cheatTime = 20, minSaving = 76)).isEqualTo(3)
    }
  }
}
