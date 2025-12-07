import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking

class PuzzleY2019D25 : Puzzle {

  private class Memory(values: List<Long> = listOf()) {
    private val data = values.mapIndexed { index, value -> index.toLong() to value }.toHashMap()

    operator fun get(index: Long): Long {
      return data.getOrDefault(index, 0)
    }

    operator fun set(index: Long, value: Long) {
      data[index] = value
    }
  }

  private class Console(initialMemory: List<Long>) {
    private val input = Channel<Char>()
    private var output = ""

    init {
      launchUnscoped {
        execute(
          Memory(initialMemory),
          input = { input.receive().code.toLong() },
          output = { output += it.toInt().toChar() },
        )
      }
    }

    fun readPrompt(): String = output.also { output = "" }

    suspend fun sendInput(line: String) = "$line\n".forEach { c -> input.send(c) }
  }

  private val badItems = hashSetOf(
    "escape pod",
    "giant electromagnet",
    "infinite loop",
    "molten lava",
  )

  private lateinit var initialMemory: List<Long>

  override fun parse(input: String) {
    initialMemory = input.extractLongs()
  }

  override fun solve1(): String = runBlocking {
    val console = Console(initialMemory)

    lateinit var pathToCockpit: List<String>
    val items = hashSetOf<String>()
    val visited = hashSetOf<Index>()

    suspend fun explore(position: Index, path: List<String>) {
      val prompt = console.readPrompt()

      if (prompt.contains("you are ejected back to the checkpoint")) {
        pathToCockpit = path
        return
      }

      if (visited.add(position)) {
        val itemsHere = itemsFrom(prompt).filter { it !in badItems }

        for (item in itemsHere) {
          console.sendInput("take $item")
          console.readPrompt()
          items += item
        }

        for (door in doorsFrom(prompt)) {
          if (position + door in visited) continue
          console.sendInput(door)
          explore(position + door, path + door)
        }
      }

      val doorBack = when (path.lastOrNull() ?: return) {
        "north" -> "south"
        "south" -> "north"
        "east" -> "west"
        "west" -> "east"
        else -> fail()
      }
      console.sendInput(doorBack)
      console.readPrompt()
    }

    explore(position = Index.ZERO, path = emptyList())

    // Move to just outside the cockpit.
    for (door in pathToCockpit.dropLast()) {
      console.sendInput(door)
      console.readPrompt()
    }

    // Drop all items.
    for (item in items) {
      console.sendInput("drop $item")
      console.readPrompt()
    }

    // Try every combination of items.
    for (itemSelection in items.combinations()) {
      for (item in itemSelection) {
        console.sendInput("take $item")
        console.readPrompt()
      }

      console.sendInput(pathToCockpit.last())
      val prompt = console.readPrompt()
      passwordFrom(prompt)?.let { return@runBlocking it }

      for (item in itemSelection) {
        console.sendInput("drop $item")
        console.readPrompt()
      }
    }

    fail()
  }

  override fun solve2(): Int {
    // No answer required. This solves the puzzle when all 50 stars are unlocked.
    return 0
  }

  companion object {
    private operator fun Index.plus(direction: String): Index {
      return when (direction) {
        "north" -> up()
        "south" -> down()
        "east" -> right()
        "west" -> left()
        else -> fail()
      }
    }

    private fun listFrom(prompt: String, header: String): List<String> {
      return prompt.lines().dropWhile { it != header }.drop(1).takeWhile { it.startsWith("- ") }
        .map { it.drop(2) }
    }

    private fun doorsFrom(prompt: String): List<String> {
      return listFrom(prompt, "Doors here lead:")
    }

    private fun itemsFrom(prompt: String): List<String> {
      return listFrom(prompt, "Items here:")
    }

    private fun passwordFrom(prompt: String): String? {
      val regex = Regex("""You should be able to get in by typing (\d+) on the keypad""")
      return regex.find(prompt)?.groupValues?.second()
    }

    private suspend fun execute(
      memory: Memory,
      input: suspend () -> Long,
      output: suspend (Long) -> Unit,
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
      }
    }
  }
}
