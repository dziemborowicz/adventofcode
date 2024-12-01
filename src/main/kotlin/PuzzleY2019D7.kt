import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PuzzleY2019D7 : Puzzle {

  private lateinit var memory: List<Int>

  override fun parse(input: String) {
    memory = input.extractInts()
  }

  override fun solve1(): Int = solve(0..4)

  override fun solve2(): Int = solve(5..9)

  private fun solve(phaseSettings: IntRange): Int {
    return phaseSettings.permutations().maxOf {
      runBlocking {
        val channels = it.map { Channel<Int>(BUFFERED).apply { trySend(it) } }
        channels.first().trySend(0)
        coroutineScope {
          for (i in channels.indices) {
            launch {
              execute(memory.toMutableList(), channels[i], channels.getWrapped(i + 1))
            }
          }
        }
        channels.first().receive()
      }
    }
  }

  private suspend fun execute(
    memory: MutableList<Int>,
    input: Channel<Int>,
    output: Channel<Int>,
  ): Int {
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
          memory[memory[ip + 1]] = input.receive()
          ip += 2
        }

        // Output
        4 -> {
          output.send(a())
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

  companion object {
    val testInput1_1 = "3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0"
    val testAnswer1_1 = 43210

    val testInput1_2 = "3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0"
    val testAnswer1_2 = 54321

    val testInput1_3 =
      "3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0"
    val testAnswer1_3 = 65210

    val testInput2_1 =
      "3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5"
    val testAnswer2_1 = 139629729

    val testInput2_2 =
      "3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10"
    val testAnswer2_2 = 18216
  }
}
