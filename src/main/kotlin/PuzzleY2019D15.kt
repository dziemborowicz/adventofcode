import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PuzzleY2019D15 : Puzzle {

  private class Memory(values: List<Long> = listOf()) {
    private val data = values.mapIndexed { index, value -> index.toLong() to value }.toHashMap()

    operator fun get(index: Long): Long {
      return data.getOrDefault(index, 0)
    }

    operator fun set(index: Long, value: Long) {
      data[index] = value
    }
  }

  private lateinit var map: HashMap<Index, Char>

  override fun parse(input: String) = runBlocking {
    map = hashMapOf(Index.ZERO to '.')

    val inputChannel = Channel<Long>(BUFFERED)
    val outputChannel = Channel<Long>(BUFFERED)
    val droid = launch {
      execute(Memory(input.extractLongs()), inputChannel, outputChannel)
    }

    var position = Index.ZERO
    while (true) {
      val movements = navigateTo(null, from = position) ?: break
      for (movement in movements) {
        inputChannel.send(
          when (movement) {
            Index.UP -> 1L
            Index.DOWN -> 2L
            Index.LEFT -> 3L
            Index.RIGHT -> 4L
            else -> fail()
          }
        )

        when (outputChannel.receive()) {
          0L -> {
            map[position + movement] = '#'
          }

          1L -> {
            map[position + movement] = '.'
            position += movement
          }

          2L -> {
            map[position + movement] = 'O'
            position += movement
          }

          else -> fail()
        }
      }
    }

    droid.cancel()
  }

  override fun solve1(): Int = navigateTo('O', from = Index.ZERO)!!.size

  override fun solve2(): Int {
    val space = map.filter { it.value == '.' }.map { it.key }.toHashSet()
    val oxygen = map.filter { it.value == 'O' }.map { it.key }.toHashSet()
    val walls = map.filter { it.value == '#' }.map { it.key }.toHashSet()
    var minutes = 0
    while (space.isNotEmpty()) {
      oxygen += oxygen.flatMap { it.neighbors().filter { it !in walls } }
      space -= oxygen
      minutes++
    }
    return minutes
  }

  private fun navigateTo(target: Char?, from: Index): List<Index>? {
    val queue = dequeOf(from to listOf<Index>())
    val visited = hashSetOf<Index>()
    while (queue.isNotEmpty()) {
      val (position, path) = queue.removeFirst()
      if (map[position] == target) return path
      if (visited.add(position)) {
        position.neighbors().forEach {
          if (map[it] != '#') {
            queue += it to (path + (it - position))
          }
        }
      }
    }
    return null
  }

  private suspend fun execute(memory: Memory, input: Channel<Long>, output: Channel<Long>): Long {
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
          a(input.receive())
          ip += 2
        }

        // Output
        4L -> {
          output.send(a())
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
