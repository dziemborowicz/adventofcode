class PuzzleY2019D17 : Puzzle {

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
  private lateinit var cameraOutput: Grid<Char>

  override fun parse(input: String) {
    initialMemory = input.extractLongs()

    var rawOutput = ""
    execute(Memory(initialMemory), input = { fail() }, output = {
      rawOutput += it.toInt().toChar()
    })

    cameraOutput = rawOutput.trim().parseDenseCharGrid()
  }

  override fun solve1(): Int {
    return cameraOutput.indices.filter { index ->
      cameraOutput[index] != '.' && index.neighborsIn(cameraOutput)
        .count { cameraOutput[it] != '.' } > 2
    }.sumOf { it.column * it.row }
  }

  override fun solve2(): Any? {
    var position = cameraOutput.indexOf('^')
    var forward = Index.UP

    // Compute efficient route through the scaffolding.
    val movements = mutableListOf<String>()
    var steps = 0
    while (true) {
      val left = forward.rotateCounterclockwise()
      val right = forward.rotateClockwise()

      when {
        cameraOutput.getOrDefault(position + forward, '.') != '.' -> {
          position += forward
          steps++
        }

        cameraOutput.getOrDefault(position + left, '.') != '.' -> {
          forward = left
          if (steps != 0) {
            movements += steps.toString()
            steps = 0
          }
          movements += "L"
        }

        cameraOutput.getOrDefault(position + right, '.') != '.' -> {
          forward = right
          if (steps != 0) {
            movements += steps.toString()
            steps = 0
          }
          movements += "R"
        }

        else -> {
          if (steps != 0) {
            movements += steps.toString()
          }
          break
        }
      }
    }

    // Generate the input.
    fun computeInput(
      mainMovementRoutine: String,
      movementFunctions: List<String> = listOf(),
      movementFunctionNames: List<String> = listOf("A", "B", "C"),
    ): List<String>? {
      val routine = mainMovementRoutine.split(",")
      if (routine.all { it.isMovementFunction }) {
        if (mainMovementRoutine.length <= 20 && movementFunctions.all { it.length <= 20 }) {
          return listOf(mainMovementRoutine) + movementFunctions + "n"
        } else {
          return null
        }
      } else if (movementFunctionNames.isEmpty()) {
        return null
      }

      val i = routine.indexOfFirst { !it.isMovementFunction }
      var j = i
      while (true) {
        if (j !in routine.indices || routine[j].isMovementFunction) {
          return null
        }

        val subroutine = routine.subList(i..j).joinToString(",")
        computeInput(
          mainMovementRoutine.replace(subroutine, movementFunctionNames.first()),
          movementFunctions + subroutine,
          movementFunctionNames.drop(1),
        )?.let { return it }
        j++
      }
    }

    val input = computeInput(movements.joinToString(","))!!.flatMap {
      "$it\n".map { it.code.toLong() }
    }.toDeque()

    // Pass input to the robot and get the output.
    var dustCollected = 0L
    val memory = Memory(initialMemory).also { it[0] = 2 }
    execute(memory, input = { input.removeFirst() }, output = { dustCollected = it })
    return dustCollected
  }

  private val String.isMovementFunction: Boolean
    get() = this == "A" || this == "B" || this == "C"

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
