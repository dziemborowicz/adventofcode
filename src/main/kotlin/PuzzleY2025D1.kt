class PuzzleY2025D1 : Puzzle {

  private data class Rotation(val op: (Int, Int) -> Int, val amount: Int)

  private lateinit var rotations: List<Rotation>

  override fun parse(input: String) {
    rotations = input.lines().map { line ->
      val amount = line.drop(1).toInt()
      when (line.first()) {
        'L' -> Rotation(Int::minus, amount)
        'R' -> Rotation(Int::plus, amount)
        else -> fail()
      }
    }
  }

  override fun solve1(): Int {
    var count = 0
    var position = 50
    for (rotation in rotations) {
      position = rotation.op(position, rotation.amount).mod(100)
      if (position == 0) count++
    }
    return count
  }

  override fun solve2(): Int {
    var count = 0
    var position = 50
    for (rotation in rotations) {
      repeat(rotation.amount) {
        position = rotation.op(position, 1).mod(100)
        if (position == 0) count++
      }
    }
    return count
  }

  companion object {
    val testInput1 = """
      L68
      L30
      R48
      L5
      R60
      L55
      L1
      L99
      R14
      L82
      """.trimIndent()
    val testAnswer1 = 3

    val testInput2 = testInput1
    val testAnswer2 = 6
  }
}
