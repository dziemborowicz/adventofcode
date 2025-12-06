import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

class PuzzleY2019D23 : Puzzle {

  private class Network {
    val numComputers = 50
    val addresses = (0L..<numComputers).toList()
    val natAddress = 255L
    val buffers = (addresses + natAddress).associateWith { address -> dequeOf(address) }
    val natBuffer = buffers.getValue(natAddress).also { it.removeFirst() }
    val waitingBuffers = HashSet<Long>()
  }

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

  override fun solve1(): Long {
    return solveWithNat {
      var result: Long
      while (true) {
        if (natBuffer.size >= 2) {
          natBuffer.removeFirst() // x
          result = natBuffer.removeFirst() // y
          break
        }
        yield()
      }
      result
    }
  }

  override fun solve2(): Long {
    return solveWithNat {
      var x = 0L
      var y = 0L
      val yValues = mutableSetOf<Long>()

      while (true) {
        while (natBuffer.size >= 2) {
          x = natBuffer.removeFirst()
          y = natBuffer.removeFirst()
        }

        if (waitingBuffers.size == numComputers) {
          if (!yValues.add(y)) break
          buffers.getValue(0L) += listOf(x, y)
          waitingBuffers.clear()
        }

        yield()
      }

      y
    }
  }

  private fun solveWithNat(nat: suspend Network.() -> Long): Long {
    return runBlocking {
      with(Network()) {
        val jobs = addresses.map { address ->
          launch {
            val output = mutableListOf<Long>()
            execute(
              Memory(initialMemory),
              input = {
                val input = buffers.getValue(address).removeFirstOrNull()
                if (input != null) {
                  waitingBuffers -= address
                  input
                } else {
                  waitingBuffers += address
                  -1L
                }
              },
              output = {
                output += it
                if (output.size == 3) {
                  val buffer = buffers.getValue(output.first())
                  buffer += output.second()
                  buffer += output.third()
                  output.clear()
                }
              },
            )
          }
        }

        nat().also {
          jobs.forEach { it.cancelAndJoin() }
        }
      }
    }
  }

  private suspend fun execute(
    memory: Memory,
    input: () -> Long,
    output: (Long) -> Unit,
  ): Long {
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
      yield()
    }
  }
}
