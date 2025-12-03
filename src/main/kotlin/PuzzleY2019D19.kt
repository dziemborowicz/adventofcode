class PuzzleY2019D19 : Puzzle {

  private class Memory(values: List<Long> = listOf()) {
    private val data = values.mapIndexed { index, value -> index.toLong() to value }.toHashMap()

    operator fun get(index: Long): Long {
      return data.getOrDefault(index, 0)
    }

    operator fun set(index: Long, value: Long) {
      data[index] = value
    }
  }

  private lateinit var initialMemory: List<Long>

  override fun parse(input: String) {
    initialMemory = input.extractLongs()
  }

  override fun solve1(): Int {
    var count = 0
    for (x in 0..49) {
      for (y in 0..49) {
        if (isInTractorBeam(x, y)) {
          count++
        }
      }
    }
    return count
  }

  override fun solve2(): Int {
    val santaShipSize = 100

    var y = 10 // Beginning of tractor beam has strange gaps.
    var x = (0..Int.MAX_VALUE).first { x -> isInTractorBeam(x, y) && !isInTractorBeam(x + 1, y) }

    while (!isInTractorBeam(x - santaShipSize + 1, y + santaShipSize - 1)) {
      y++
      x = (x..Int.MAX_VALUE).first { x -> isInTractorBeam(x, y) && !isInTractorBeam(x + 1, y) }
    }

    return ((x - santaShipSize + 1) * 10_000) + y
  }

  private fun isInTractorBeam(x: Int, y: Int): Boolean {
    val inputs = dequeOf(x.toLong(), y.toLong())
    var output = 0L
    execute(Memory(initialMemory), input = { inputs.removeFirst() }, output = { output = it })
    return output == 1L
  }

  companion object {
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
}
