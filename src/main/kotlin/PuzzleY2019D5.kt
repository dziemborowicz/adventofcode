class PuzzleY2019D5 : Puzzle {

  private lateinit var memory: List<Int>

  override fun parse(input: String) {
    memory = input.extractInts()
  }

  override fun solve1(): Int {
    val outputs = mutableListOf<Int>()
    execute(memory.toMutableList(), input = { 1 }, output = { outputs += it })
    check(outputs.dropLast().all { it == 0 })
    return outputs.last()
  }

  override fun solve2(): Int {
    val outputs = mutableListOf<Int>()
    execute(memory.toMutableList(), input = { 5 }, output = { outputs += it })
    return outputs.single()
  }

  private fun execute(memory: MutableList<Int>, input: () -> Int, output: (Int) -> Unit): Int {
    var ip = 0

    fun a(): Int {
      return if ((memory[ip] / 100) % 10 == 0) {
        memory[memory[ip + 1]]
      } else {
        memory[ip + 1]
      }
    }

    fun b(): Int {
      return if ((memory[ip] / 1000) % 10 == 0) {
        memory[memory[ip + 2]]
      } else {
        memory[ip + 2]
      }
    }

    while (true) {
      when (memory[ip] % 100) {
        // Add
        1 -> {
          memory[memory[ip + 3]] = a() + b()
          ip += 4
        }

        // Multiply
        2 -> {
          memory[memory[ip + 3]] = a() * b()
          ip += 4
        }

        // Input
        3 -> {
          memory[memory[ip + 1]] = input()
          ip += 2
        }

        // Output
        4 -> {
          output(a())
          ip += 2
        }

        // Jump-if-true
        5 -> {
          if (a() != 0) {
            ip = b()
          } else {
            ip += 3
          }
        }

        // Jump-if-false
        6 -> {
          if (a() == 0) {
            ip = b()
          } else {
            ip += 3
          }
        }

        // Less than
        7 -> {
          memory[memory[ip + 3]] = if (a() < b()) 1 else 0
          ip += 4
        }

        // Equals
        8 -> {
          memory[memory[ip + 3]] = if (a() == b()) 1 else 0
          ip += 4
        }

        // Halt
        99 -> return memory[0]

        else -> fail()
      }
    }
  }
}
