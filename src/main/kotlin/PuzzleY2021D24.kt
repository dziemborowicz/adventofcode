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

  override fun solve1(): Long {
    return find(9L downTo 1L)
  }

  override fun solve2(): Long {
    return find(1L..9L)
  }

  private fun find(digits: LongProgression): Long {
    val state = State()
    val visited = List(instructions.size) { mutableSetOf<Long>() }
    fun process(index: Int, modelNumber: Long): Long {
      if (index > instructions.lastIndex) {
        return if (state.z == 0L) modelNumber else 0L
      }
      val instruction = instructions[index]
      val a = instruction[1].first()
      if (instruction[0] == "inp") {
        // x and y are always reinitialized before being read after inp instructions, so we don't
        // need to track them in the visited set. And np always has w as its param.
        if (visited[index].add(state.z)) {
          if (modelNumber < 100) {
            println("Trying model number (${modelNumber.toString().padEnd(14, 'x')})...")
          }
          for (input in digits) {
            val prevValue = state[a]
            state[a] = input
            val result = process(index + 1, (10 * modelNumber) + input)
            state[a] = prevValue
            if (result != 0L) return result
          }
        }
        return 0L
      }
      val b = instruction[2].toLongOrNull() ?: state[instruction[2].first()]
      val prevValue = state[a]
      when (instruction[0]) {
        "add" -> state[a] += b
        "mul" -> state[a] *= b
        "div" -> state[a] /= b
        "mod" -> state[a] %= b
        "eql" -> state[a] = (state[a] == b).toLong()
        else -> fail()
      }
      val result = process(index + 1, modelNumber)
      state[a] = prevValue
      return result
    }
    return process(index = 0, modelNumber = 0)
  }
}
