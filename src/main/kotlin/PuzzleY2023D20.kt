class PuzzleY2023D20 : Puzzle {

  private sealed interface Module {
    val label: String
    val inputs: List<String>
    val outputs: List<String>
    val pulsesReceived: MutableMap<String, Counter<Boolean>>
  }

  private data class Broadcaster(
    override val label: String,
    override val inputs: List<String>,
    override val outputs: List<String>,
    override val pulsesReceived: MutableMap<String, Counter<Boolean>> = mutableMapOf(),
  ) : Module

  private data class FlipFlop(
    override val label: String,
    override val inputs: List<String>,
    override val outputs: List<String>,
    override val pulsesReceived: MutableMap<String, Counter<Boolean>> = mutableMapOf(),
    var state: Boolean = LOW,
  ) : Module

  private data class Conjunction(
    override val label: String,
    override val inputs: List<String>,
    override val outputs: List<String>,
    override val pulsesReceived: MutableMap<String, Counter<Boolean>> = mutableMapOf(),
    val state: MutableMap<String, Boolean> = inputs.associateWith { LOW }.toMutableMap(),
  ) : Module

  private lateinit var input: String

  override fun parse(input: String) {
    this.input = input
  }

  private fun parseModules(): Map<String, Module> {
    val modules = Regex("\\w+").findAll(input).map { it.value }.toSet()

    val moduleTypes = input.lines().associate { line ->
      Regex("\\w+").find(line)!!.value to line.first()
    }

    val moduleOutputs = input.lines().associate { line ->
      val labels = Regex("\\w+").findAll(line).map { it.value }.toList()
      labels.first() to labels.drop(1)
    }

    val moduleInputs = modules.associateWith { label ->
      moduleOutputs.entries.filter { label in it.value }.map { it.key }
    }

    return modules.associateWith { label ->
      val type = moduleTypes[label] ?: '?'
      val inputs = moduleInputs[label] ?: listOf()
      val outputs = moduleOutputs[label] ?: listOf()
      when (type) {
        '%' -> FlipFlop(label, inputs, outputs)
        '&' -> Conjunction(label, inputs, outputs)
        else -> Broadcaster(label, inputs, outputs)
      }
    }
  }

  override fun solve1(): Int {
    val modules = parseModules()
    repeat(1000) { modules.pushButton() }
    return listOf(LOW, HIGH).productOf { pulse ->
      modules.values.sumOf { it.pulsesReceived.values.sumOf { it[pulse] } }
    }
  }

  override fun solve2(): Long {
    val modules = parseModules()
    val rx = modules.getValue(modules.getValue("rx").inputs.single()) as Conjunction
    val rxInputHigh = hashMapOf<String, Long>()
    var presses = 0L
    while (rx.inputs.any { it !in rxInputHigh }) {
      modules.pushButton()
      presses++
      rx.inputs.filter { it !in rxInputHigh && rx.hasReceivedHighPulseFrom(it) }
        .forEach { rxInputHigh[it] = presses }
    }
    return rxInputHigh.values.lcm()
  }

  private fun Map<String, Module>.pushButton() {
    val modules = this
    val queue = dequeOf(Triple("button", "broadcaster", LOW))
    while (queue.isNotEmpty()) {
      val (from, to, pulse) = queue.removeFirst()
      val module = modules.getValue(to)

      module.pulsesReceived.putIfAbsent(from, Counter())
      module.pulsesReceived.getValue(from)[pulse]++

      when (module) {
        is Broadcaster -> queue += module.outputs.map { Triple(to, it, pulse) }

        is FlipFlop -> if (pulse == LOW) {
          module.state = !module.state
          queue += module.outputs.map { Triple(to, it, module.state) }
        }

        is Conjunction -> {
          module.state[from] = pulse
          queue += if (module.state.values.all { it == HIGH }) {
            module.outputs.map { Triple(to, it, LOW) }
          } else {
            module.outputs.map { Triple(to, it, HIGH) }
          }
        }
      }
    }
  }

  private fun Module.hasReceivedHighPulseFrom(label: String): Boolean =
    pulsesReceived[label]?.contains(HIGH) == true

  companion object {
    private const val HIGH = true
    private const val LOW = false

    val testInput1_1 = """
      broadcaster -> a, b, c
      %a -> b
      %b -> c
      %c -> inv
      &inv -> a
      """.trimIndent()
    val testAnswer1_1 = 32000000

    val testInput1_2 = """
      broadcaster -> a
      %a -> inv, con
      &inv -> b
      %b -> con
      &con -> output
      """.trimIndent()
    val testAnswer1_2 = 11687500
  }
}
