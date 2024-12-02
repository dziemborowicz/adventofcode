class PuzzleY2019D11 : Puzzle {

  private class Memory(values: List<Long> = listOf()) {
    private val data = values.mapIndexed { index, value -> index.toLong() to value }.toHashMap()

    operator fun get(index: Long): Long {
      return data.getOrDefault(index, 0)
    }

    operator fun set(index: Long, value: Long) {
      data[index] = value
    }
  }

  private lateinit var memory: List<Long>

  override fun parse(input: String) {
    memory = input.extractLongs()
  }

  override fun solve1(): Int {
    val hull = hashMapOf<Index, Long>()
    paint(hull)
    return hull.size
  }

  override fun solve2(): String {
    val hull = hashMapOf(Index.ZERO to 1L)
    paint(hull)

    return Grid(hull.keys.maxOf { it.row } + 1, hull.keys.maxOf { it.column } + 1) {
      hull.getOrDefault(it, 0L) != 0L
    }.toStringFromAsciiArt()
  }

  private fun paint(hull: MutableMap<Index, Long>) {
    var position = Index.ZERO
    var direction = Index.UP
    val outputs = mutableListOf<Long>()
    execute(Memory(memory), input = { hull.getOrDefault(position, 0) }, output = {
      outputs.add(it)
      if (outputs.size == 2) {
        hull[position] = outputs[0]
        direction = if (outputs[1] == 0L) {
          direction.rotateCounterclockwise()
        } else {
          direction.rotateClockwise()
        }
        position += direction
        outputs.clear()
      }
    })
  }

  private fun execute(memory: Memory, input: () -> Long, output: (Long) -> Unit): Long {
    var ip = 0L
    var relativeBase = 0L

    fun a(): Long {
      return when ((memory[ip] / 100) % 10) {
        0L -> memory[memory[ip + 1]]
        1L -> memory[ip + 1]
        2L -> memory[memory[ip + 1] + relativeBase]
        else -> fail()
      }
    }

    fun a(value: Long) {
      when ((memory[ip] / 100) % 10) {
        0L -> memory[memory[ip + 1]] = value
        1L -> fail("Cannot write memory in immediate mode.")
        2L -> memory[memory[ip + 1] + relativeBase] = value
        else -> fail()
      }
    }

    fun b(): Long {
      return when ((memory[ip] / 1000) % 10) {
        0L -> memory[memory[ip + 2]]
        1L -> memory[ip + 2]
        2L -> memory[memory[ip + 2] + relativeBase]
        else -> fail()
      }
    }

    fun b(value: Long) {
      when ((memory[ip] / 1000) % 10) {
        0L -> memory[memory[ip + 2]] = value
        1L -> fail("Cannot write memory in immediate mode.")
        2L -> memory[memory[ip + 2] + relativeBase] = value
        else -> fail()
      }
    }

    fun c(value: Long) {
      when ((memory[ip] / 10000) % 10) {
        0L -> memory[memory[ip + 3]] = value
        1L -> fail("Cannot write memory in immediate mode.")
        2L -> memory[memory[ip + 3] + relativeBase] = value
        else -> fail()
      }
    }

    while (true) {
      when (memory[ip] % 100) {
        // Add
        1L -> {
          c(a() + b())
          ip += 4
        }

        // Multiply
        2L -> {
          c(a() * b())
          ip += 4
        }

        // Input
        3L -> {
          a(input())
          ip += 2
        }

        // Output
        4L -> {
          output(a())
          ip += 2
        }

        // Jump-if-true
        5L -> {
          if (a() != 0L) {
            ip = b()
          } else {
            ip += 3
          }
        }

        // Jump-if-false
        6L -> {
          if (a() == 0L) {
            ip = b()
          } else {
            ip += 3
          }
        }

        // Less than
        7L -> {
          c(if (a() < b()) 1 else 0)
          ip += 4
        }

        // Equals
        8L -> {
          c(if (a() == b()) 1 else 0)
          ip += 4
        }

        // Adjust relative base
        9L -> {
          relativeBase += a()
          ip += 2
        }

        // Halt
        99L -> return memory[0]

        else -> fail()
      }
    }
  }
}
