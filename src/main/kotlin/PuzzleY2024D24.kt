class PuzzleY2024D24 : Puzzle {

  private data class Gate(val lhs: String, val op: String, val rhs: String, val out: String)

  private lateinit var wires: Map<String, Int>
  private lateinit var gates: List<Gate>

  override fun parse(input: String) {
    val (wires, gates) = input.splitByBlank()

    this.wires = wires.lines().associate { line ->
      val (wire, value) = line.split(": ")
      wire to value.toInt()
    }

    this.gates = gates.lines().map { line ->
      val (lhs, op, rhs, _, out) = line.split(" ")
      Gate(lhs, op, rhs, out)
    }
  }

  override fun solve1(): Long {
    val wires = this.wires.toMutableMap()

    fun computeWire(wire: String): Int {
      wires[wire]?.let { return it }

      val gate = gates.single { it.out == wire }

      val left = computeWire(gate.lhs)
      val right = computeWire(gate.rhs)

      val result = when (gate.op) {
        "AND" -> left and right
        "OR" -> left or right
        "XOR" -> left xor right
        else -> fail()
      }

      wires[wire] = result
      return result
    }

    val zWires = gates.map { it.out }.filter { it.isOutputWire }.sortedDescending()
    val binaryString = zWires.map { computeWire(it) }.joinToString("")
    return binaryString.toLong(2)
  }

  override fun solve2(): String {
    val badWires = mutableSetOf<String>()

    val lastZ = gates.map { it.out }.filter { it.isOutputWire }.max()

    for ((lhs, op, rhs, out) in gates) {
      if (out.isOutputWire && op != "XOR" && out != lastZ) {
        badWires.add(out)
      }

      if (!out.isOutputWire && !(lhs.isInputWire && rhs.isInputWire) && op == "XOR") {
        badWires.add(out)
      }

      if (lhs.isInputWire && rhs.isInputWire && lhs != "x00" && rhs != "x00") {
        if (op == "XOR") {
          val isConsumedByXor = gates.any { (it.lhs == out || it.rhs == out) && it.op == "XOR" }
          if (!isConsumedByXor) {
            badWires.add(out)
          }
        }

        if (op == "AND") {
          val isConsumedByOr = gates.any { (it.lhs == out || it.rhs == out) && it.op == "OR" }
          if (!isConsumedByOr) {
            badWires.add(out)
          }
        }
      }
    }

    return badWires.sorted().joinToString(",")
  }

  private val String.isInputWire: Boolean
    get() = startsWith('x') || startsWith('y')

  private val String.isOutputWire: Boolean
    get() = startsWith('z')

  companion object {
    val testInput1_1 = """
      x00: 1
      x01: 1
      x02: 1
      y00: 0
      y01: 1
      y02: 0
      
      x00 AND y00 -> z00
      x01 XOR y01 -> z01
      x02 OR y02 -> z02
      """.trimIndent()
    val testAnswer1_1 = 4L

    val testInput1_2 = """
      x00: 1
      x01: 0
      x02: 1
      x03: 1
      x04: 0
      y00: 1
      y01: 1
      y02: 1
      y03: 1
      y04: 1
      
      ntg XOR fgs -> mjb
      y02 OR x01 -> tnw
      kwq OR kpj -> z05
      x00 OR x03 -> fst
      tgd XOR rvg -> z01
      vdt OR tnw -> bfw
      bfw AND frj -> z10
      ffh OR nrd -> bqk
      y00 AND y03 -> djm
      y03 OR y00 -> psh
      bqk OR frj -> z08
      tnw OR fst -> frj
      gnj AND tgd -> z11
      bfw XOR mjb -> z00
      x03 OR x00 -> vdt
      gnj AND wpb -> z02
      x04 AND y00 -> kjc
      djm OR pbm -> qhw
      nrd AND vdt -> hwm
      kjc AND fst -> rvg
      y04 OR y02 -> fgs
      y01 AND x02 -> pbm
      ntg OR kjc -> kwq
      psh XOR fgs -> tgd
      qhw XOR tgd -> z09
      pbm OR djm -> kpj
      x03 XOR y03 -> ffh
      x00 XOR y04 -> ntg
      bfw OR bqk -> z06
      nrd XOR fgs -> wpb
      frj XOR qhw -> z04
      bqk OR frj -> z07
      y03 OR x01 -> nrd
      hwm AND bqk -> z03
      tgd XOR rvg -> z12
      tnw OR pbm -> gnj
      """.trimIndent()
    val testAnswer1_2 = 2024L
  }
}
