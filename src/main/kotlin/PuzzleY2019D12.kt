import com.google.common.truth.Truth.assertThat
import kotlin.math.abs

class PuzzleY2019D12 : Puzzle {

  private lateinit var input: String

  override fun parse(input: String) {
    this.input = input
  }

  override fun solve1(): Long = totalEnergy(input, steps = 1000)

  override fun solve2(): Long = cycleLength(input)

  companion object {
    private fun totalEnergy(input: String, steps: Int): Long {
      val data = Grid(input.lines().map { it.extractLongs() })
      val dimensions = data.columns().map { initialPositions ->
        val positions = initialPositions.toMutableList()
        val velocities = positions.map { 0L }.toMutableList()
        repeat(steps) { updateDimension(positions, velocities) }
        positions to velocities
      }
      return data.rowIndices.sumOf { i ->
        dimensions.sumOf { abs(it.first[i]) } * dimensions.sumOf { abs(it.second[i]) }
      }
    }

    private fun cycleLength(input: String): Long {
      val data = Grid(input.lines().map { it.extractLongs() })
      return data.columns().map { initialPositions ->
        val positions = initialPositions.toMutableList()
        val velocities = positions.map { 0L }.toMutableList()
        var steps = 0L
        do {
          updateDimension(positions, velocities)
          steps++
        } while (positions != initialPositions || velocities.any { it != 0L })
        steps
      }.lcm()
    }

    private fun updateDimension(positions: MutableList<Long>, velocities: MutableList<Long>) {
      val indices = positions.indices
      for ((i, j) in indices.combinations(2)) {
        when {
          positions[i] > positions[j] -> {
            velocities[i]--
            velocities[j]++
          }

          positions[i] < positions[j] -> {
            velocities[i]++
            velocities[j]--
          }
        }
      }
      for (i in indices) {
        positions[i] += velocities[i]
      }
    }

    fun test1_1() {
      val input = """
        <x=-1, y=0, z=2>
        <x=2, y=-10, z=-7>
        <x=4, y=-8, z=8>
        <x=3, y=5, z=-1>
        """.trimIndent()
      assertThat(totalEnergy(input, steps = 10)).isEqualTo(179)
    }

    fun test1_2() {
      val input = """
        <x=-8, y=-10, z=0>
        <x=5, y=5, z=10>
        <x=2, y=-7, z=3>
        <x=9, y=-8, z=-3>
        """.trimIndent()
      assertThat(totalEnergy(input, steps = 100)).isEqualTo(1940)
    }

    val testInput2_1 = """
      <x=-1, y=0, z=2>
      <x=2, y=-10, z=-7>
      <x=4, y=-8, z=8>
      <x=3, y=5, z=-1>
      """.trimIndent()
    val testAnswer2_1 = 2772

    val testInput2_2 = """
      <x=-8, y=-10, z=0>
      <x=5, y=5, z=10>
      <x=2, y=-7, z=3>
      <x=9, y=-8, z=-3>
      """.trimIndent()
    val testAnswer2_2 = 4686774924
  }
}
