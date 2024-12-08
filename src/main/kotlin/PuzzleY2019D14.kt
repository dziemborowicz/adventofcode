class PuzzleY2019D14 : Puzzle {

  private data class Reaction(
    val quantity: Long,
    val inputs: Map<String, Long> = hashMapOf(),
  )

  private lateinit var reactions: Map<String, Reaction>

  override fun parse(input: String) {
    reactions = input.lines().associate { line ->
      val chemicals = line.findAllRegex("""[A-Z]+""").map { it.value }
      val quantities = line.findAllRegex("""\d+""").map { it.value.toLong() }
      val inputs = chemicals.zip(quantities).dropLast().toMap().print()
      chemicals.last() to Reaction(quantities.last(), inputs)
    }
  }

  override fun solve1(): Long = oreRequired(fuelQuantity = 1L)

  override fun solve2(): Long {
    val oreAvailable = 1_000_000_000_000L
    return (1L..oreAvailable).binarySearchLowBy(oreAvailable) { oreRequired(it) }
  }

  private fun oreRequired(fuelQuantity: Long): Long {
    var oreRequired = 0L
    val available = LongCounter<String>()

    fun produce(chemical: String, quantity: Long) {
      if (chemical == "ORE") {
        oreRequired += quantity
      } else if (quantity > 0) {
        val consumed = minOf(available[chemical], quantity)
        available[chemical] -= consumed
        val required = quantity - consumed

        val reaction = reactions.getValue(chemical)
        val runs = required.ceilDiv(reaction.quantity)
        reaction.inputs.entries.forEach { (inputName, inputQuantity) ->
          produce(inputName, inputQuantity * runs)
        }
        available[chemical] += (runs * reaction.quantity) - required
      }
    }

    produce("FUEL", fuelQuantity)
    return oreRequired
  }

  companion object {
    val testInput1_1 = """
      10 ORE => 10 A
      1 ORE => 1 B
      7 A, 1 B => 1 C
      7 A, 1 C => 1 D
      7 A, 1 D => 1 E
      7 A, 1 E => 1 FUEL
      """.trimIndent()
    val testAnswer1_1 = 31

    val testInput1_2 = """
      9 ORE => 2 A
      8 ORE => 3 B
      7 ORE => 5 C
      3 A, 4 B => 1 AB
      5 B, 7 C => 1 BC
      4 C, 1 A => 1 CA
      2 AB, 3 BC, 4 CA => 1 FUEL
      """.trimIndent()
    val testAnswer1_2 = 165

    val testInput1_3 = """
      157 ORE => 5 NZVS
      165 ORE => 6 DCFZ
      44 XJWVT, 5 KHKGT, 1 QDVJ, 29 NZVS, 9 GPVTF, 48 HKGWZ => 1 FUEL
      12 HKGWZ, 1 GPVTF, 8 PSHF => 9 QDVJ
      179 ORE => 7 PSHF
      177 ORE => 5 HKGWZ
      7 DCFZ, 7 PSHF => 2 XJWVT
      165 ORE => 2 GPVTF
      3 DCFZ, 7 NZVS, 5 HKGWZ, 10 PSHF => 8 KHKGT
      """.trimIndent()
    val testAnswer1_3 = 13312

    val testInput1_4 = """
      2 VPVL, 7 FWMGM, 2 CXFTF, 11 MNCFX => 1 STKFG
      17 NVRVD, 3 JNWZP => 8 VPVL
      53 STKFG, 6 MNCFX, 46 VJHF, 81 HVMC, 68 CXFTF, 25 GNMV => 1 FUEL
      22 VJHF, 37 MNCFX => 5 FWMGM
      139 ORE => 4 NVRVD
      144 ORE => 7 JNWZP
      5 MNCFX, 7 RFSQX, 2 FWMGM, 2 VPVL, 19 CXFTF => 3 HVMC
      5 VJHF, 7 MNCFX, 9 VPVL, 37 CXFTF => 6 GNMV
      145 ORE => 6 MNCFX
      1 NVRVD => 8 CXFTF
      1 VJHF, 6 MNCFX => 4 RFSQX
      176 ORE => 6 VJHF
      """.trimIndent()
    val testAnswer1_4 = 180697

    val testInput1_5 = """
      171 ORE => 8 CNZTR
      7 ZLQW, 3 BMBT, 9 XCVML, 26 XMNCP, 1 WPTQ, 2 MZWV, 1 RJRHP => 4 PLWSL
      114 ORE => 4 BHXH
      14 VRPVC => 6 BMBT
      6 BHXH, 18 KTJDG, 12 WPTQ, 7 PLWSL, 31 FHTLT, 37 ZDVW => 1 FUEL
      6 WPTQ, 2 BMBT, 8 ZLQW, 18 KTJDG, 1 XMNCP, 6 MZWV, 1 RJRHP => 6 FHTLT
      15 XDBXC, 2 LTCX, 1 VRPVC => 6 ZLQW
      13 WPTQ, 10 LTCX, 3 RJRHP, 14 XMNCP, 2 MZWV, 1 ZLQW => 1 ZDVW
      5 BMBT => 4 WPTQ
      189 ORE => 9 KTJDG
      1 MZWV, 17 XDBXC, 3 XCVML => 2 XMNCP
      12 VRPVC, 27 CNZTR => 2 XDBXC
      15 KTJDG, 12 BHXH => 5 XCVML
      3 BHXH, 2 VRPVC => 7 MZWV
      121 ORE => 7 VRPVC
      7 XCVML => 6 RJRHP
      5 BHXH, 4 VRPVC => 5 LTCX
      """.trimIndent()
    val testAnswer1_5 = 2210736

    val testInput2_3 = testInput1_3
    val testAnswer2_3 = 82892753

    val testInput2_4 = testInput1_4
    val testAnswer2_4 = 5586022

    val testInput2_5 = testInput1_5
    val testAnswer2_5 = 460664
  }
}
