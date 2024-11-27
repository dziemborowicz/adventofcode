import com.google.common.truth.Truth.assertThat

class PuzzleY2019D1 : Puzzle {

  private lateinit var masses: List<Int>

  override fun parse(input: String) {
    masses = input.extractInts()
  }

  override fun solve1(): Int = masses.sumOf { fuelRequiredForMass(it) }

  override fun solve2(): Int = masses.sumOf { fuelRequiredForModuleAndFuel(it) }

  companion object {

    private fun fuelRequiredForMass(mass: Int): Int = (mass / 3) - 2

    private fun fuelRequiredForModuleAndFuel(moduleMass: Int): Int {
      var total = 0
      var mass = moduleMass
      do {
        mass = fuelRequiredForMass(mass)
        total += maxOf(mass, 0)
      } while (mass > 0)
      return total
    }

    fun test1() {
      assertThat(fuelRequiredForMass(12)).isEqualTo(2)
      assertThat(fuelRequiredForMass(14)).isEqualTo(2)
      assertThat(fuelRequiredForMass(1969)).isEqualTo(654)
      assertThat(fuelRequiredForMass(100756)).isEqualTo(33583)
    }

    fun test2() {
      assertThat(fuelRequiredForModuleAndFuel(14)).isEqualTo(2)
      assertThat(fuelRequiredForModuleAndFuel(1969)).isEqualTo(966)
      assertThat(fuelRequiredForModuleAndFuel(100756)).isEqualTo(50346)
    }
  }
}
