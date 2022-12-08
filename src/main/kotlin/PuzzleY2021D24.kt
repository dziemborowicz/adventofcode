class PuzzleY2021D24 : Puzzle {

  private data class State(
    var w: Long = 0L,
    var x: Long = 0L,
    var y: Long = 0L,
    var z: Long = 0L,
  ) {

    operator fun get(register: Char): Long {
      return when (register) {
        'w' -> w
        'x' -> x
        'y' -> y
        'z' -> z
        else -> fail()
      }
    }

    operator fun set(register: Char, value: Long) {
      return when (register) {
        'w' -> w = value
        'x' -> x = value
        'y' -> y = value
        'z' -> z = value
        else -> fail()
      }
    }
  }

  private lateinit var instructions: List<List<String>>

  override fun parse(input: String) {
    instructions = input.lines().map { it.split(' ') }
  }

  override fun solve1(): String {
    return find(9L downTo 1L)
  }

  override fun solve2(): String {
    return find(1L..9L)
  }

  private fun find(digits: LongProgression): String {
    val visited = hashSetOf<Pair<Int, Long>>()
    tailrec fun process(index: Int, state: State, modelNumber: String): String? {
      if (index > instructions.lastIndex) return if (state.z == 0L) modelNumber else null
      val instruction = instructions[index]
      val a = instruction[1].first()
      if (instruction[0] == "inp") {
        // x and y are always reinitialized before being read after inp instructions, so we don't
        // need to track them in the visited set. And np always has w as its param.
        if (visited.add(Pair(index, state.z))) {
          if (modelNumber.length == 2) {
            println("Searching (${modelNumber.padEnd(14, 'x')})...")
          }
          for (input in digits) {
            val nextState = state.copy()
            nextState[a] = input
            @Suppress("NON_TAIL_RECURSIVE_CALL")
            val result = process(index + 1, nextState, modelNumber + input)
            if (result != null) return result
          }
        }
        return null
      }
      val b = instruction[2].toLongOrNull() ?: state[instruction[2].first()]
      val nextState = state.copy()
      when (instruction[0]) {
        "add" -> nextState[a] += b
        "mul" -> nextState[a] *= b
        "div" -> nextState[a] /= b
        "mod" -> nextState[a] %= b
        "eql" -> nextState[a] = (nextState[a] == b).toLong()
        else -> fail()
      }
      return process(index + 1, nextState, modelNumber)
    }
    return process(0, State(), "")!!
  }

  companion object {
    val testInput1 = """
      inp w
      mul x 0
      add x z
      mod x 26
      div z 1
      add x 13
      eql x w
      eql x 0
      mul y 0
      add y 25
      mul y x
      add y 1
      mul z y
      mul y 0
      add y w
      add y 10
      mul y x
      add z y
      inp w
      mul x 0
      add x z
      mod x 26
      div z 1
      add x 11
      eql x w
      eql x 0
      mul y 0
      add y 25
      mul y x
      add y 1
      mul z y
      mul y 0
      add y w
      add y 16
      mul y x
      add z y
      inp w
      mul x 0
      add x z
      mod x 26
      div z 1
      add x 11
      eql x w
      eql x 0
      mul y 0
      add y 25
      mul y x
      add y 1
      mul z y
      mul y 0
      add y w
      add y 0
      mul y x
      add z y
      inp w
      mul x 0
      add x z
      mod x 26
      div z 1
      add x 10
      eql x w
      eql x 0
      mul y 0
      add y 25
      mul y x
      add y 1
      mul z y
      mul y 0
      add y w
      add y 13
      mul y x
      add z y
      inp w
      mul x 0
      add x z
      mod x 26
      div z 26
      add x -14
      eql x w
      eql x 0
      mul y 0
      add y 25
      mul y x
      add y 1
      mul z y
      mul y 0
      add y w
      add y 7
      mul y x
      add z y
      inp w
      mul x 0
      add x z
      mod x 26
      div z 26
      add x -4
      eql x w
      eql x 0
      mul y 0
      add y 25
      mul y x
      add y 1
      mul z y
      mul y 0
      add y w
      add y 11
      mul y x
      add z y
      inp w
      mul x 0
      add x z
      mod x 26
      div z 1
      add x 11
      eql x w
      eql x 0
      mul y 0
      add y 25
      mul y x
      add y 1
      mul z y
      mul y 0
      add y w
      add y 11
      mul y x
      add z y
      inp w
      mul x 0
      add x z
      mod x 26
      div z 26
      add x -3
      eql x w
      eql x 0
      mul y 0
      add y 25
      mul y x
      add y 1
      mul z y
      mul y 0
      add y w
      add y 10
      mul y x
      add z y
      inp w
      mul x 0
      add x z
      mod x 26
      div z 1
      add x 12
      eql x w
      eql x 0
      mul y 0
      add y 25
      mul y x
      add y 1
      mul z y
      mul y 0
      add y w
      add y 16
      mul y x
      add z y
      inp w
      mul x 0
      add x z
      mod x 26
      div z 26
      add x -12
      eql x w
      eql x 0
      mul y 0
      add y 25
      mul y x
      add y 1
      mul z y
      mul y 0
      add y w
      add y 8
      mul y x
      add z y
      inp w
      mul x 0
      add x z
      mod x 26
      div z 1
      add x 13
      eql x w
      eql x 0
      mul y 0
      add y 25
      mul y x
      add y 1
      mul z y
      mul y 0
      add y w
      add y 15
      mul y x
      add z y
      inp w
      mul x 0
      add x z
      mod x 26
      div z 26
      add x -12
      eql x w
      eql x 0
      mul y 0
      add y 25
      mul y x
      add y 1
      mul z y
      mul y 0
      add y w
      add y 2
      mul y x
      add z y
      inp w
      mul x 0
      add x z
      mod x 26
      div z 26
      add x -15
      eql x w
      eql x 0
      mul y 0
      add y 25
      mul y x
      add y 1
      mul z y
      mul y 0
      add y w
      add y 5
      mul y x
      add z y
      inp w
      mul x 0
      add x z
      mod x 26
      div z 26
      add x -12
      eql x w
      eql x 0
      mul y 0
      add y 25
      mul y x
      add y 1
      mul z y
      mul y 0
      add y w
      add y 10
      mul y x
      add z y
      """.trimIndent()
    val testAnswer1 = 98998519596997

    val testInput2 = testInput1
    val testAnswer2 = 31521119151421
  }
}
