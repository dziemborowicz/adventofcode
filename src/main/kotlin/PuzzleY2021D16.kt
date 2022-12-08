import java.math.BigInteger

class PuzzleY2021D16 : Puzzle {

  private sealed interface Packet {
    val version: Int
  }

  private sealed interface Operator : Packet {
    val params: List<Packet>
  }

  private class Literal(override val version: Int, val value: Long) : Packet
  private class Sum(override val version: Int, override val params: List<Packet>) : Operator
  private class Product(override val version: Int, override val params: List<Packet>) : Operator
  private class Minimum(override val version: Int, override val params: List<Packet>) : Operator
  private class Maximum(override val version: Int, override val params: List<Packet>) : Operator
  private class GreaterThan(override val version: Int, override val params: List<Packet>) : Operator
  private class LessThan(override val version: Int, override val params: List<Packet>) : Operator
  private class EqualTo(override val version: Int, override val params: List<Packet>) : Operator

  private lateinit var packet: Packet

  override fun parse(input: String) {
    val bits = BigInteger(input, 16).toString(2).padStart(input.length * 4, '0').asDeque()
    packet = parse(bits)
  }

  private fun parse(bits: ArrayDeque<Char>): Packet {
    val version = bits.takeAndRemove(3).toInt()
    val typeId = bits.takeAndRemove(3).toInt()
    return when (typeId) {
      4 -> parseLiteral(bits, version)
      else -> parseOperator(bits, version, typeId)
    }
  }

  private fun parseLiteral(bits: ArrayDeque<Char>, version: Int): Packet {
    val value = mutableListOf<Char>()
    while (true) {
      val chunk = bits.takeAndRemove(5)
      value += chunk.drop(1)
      if (chunk.first() == '0') break
    }
    return Literal(version, value.toLong())
  }

  private fun parseOperator(bits: ArrayDeque<Char>, version: Int, typeId: Int): Packet {
    val lengthTypeId = bits.takeAndRemove(1).toInt()
    val params = mutableListOf<Packet>()
    when (lengthTypeId) {
      0 -> {
        val subPacketsBitLength = bits.takeAndRemove(15).toInt()
        val bitsLeftAtStart = bits.size
        while (bitsLeftAtStart - bits.size < subPacketsBitLength) {
          params.add(parse(bits))
        }
      }
      1 -> {
        val subPacketsCount = bits.takeAndRemove(11).toInt()
        repeat(subPacketsCount) {
          params.add(parse(bits))
        }
      }
    }
    return when (typeId) {
      0 -> Sum(version, params)
      1 -> Product(version, params)
      2 -> Minimum(version, params)
      3 -> Maximum(version, params)
      5 -> GreaterThan(version, params)
      6 -> LessThan(version, params)
      7 -> EqualTo(version, params)
      else -> fail()
    }
  }

  override fun solve1(): Int {
    return versionSum(packet)
  }

  private fun versionSum(packet: Packet): Int {
    return if (packet is Operator) {
      packet.version + packet.params.sumOf { versionSum(it) }
    } else {
      packet.version
    }
  }

  override fun solve2(): Long {
    return evaluate(packet)
  }

  private fun evaluate(packet: Packet): Long {
    return when (packet) {
      is Literal -> packet.value
      is Sum -> packet.params.sumOf { evaluate(it) }
      is Product -> packet.params.productOf { evaluate(it) }
      is Minimum -> packet.params.minOf { evaluate(it) }
      is Maximum -> packet.params.maxOf { evaluate(it) }
      is GreaterThan -> if (evaluate(packet.params[0]) > evaluate(packet.params[1])) 1 else 0
      is LessThan -> if (evaluate(packet.params[0]) < evaluate(packet.params[1])) 1 else 0
      is EqualTo -> if (packet.params.map { evaluate(it) }.toSet().size == 1) 1 else 0
    }
  }

  private fun Iterable<Char>.toInt() = asString().toInt(2)

  private fun Iterable<Char>.toLong() = asString().toLong(2)

  companion object {
    val testInput1_1 = "8A004A801A8002F478"
    val testAnswer1_1 = 16

    val testInput1_2 = "620080001611562C8802118E34"
    val testAnswer1_2 = 12

    val testInput1_3 = "C0015000016115A2E0802F182340"
    val testAnswer1_3 = 23

    val testInput1_4 = "A0016C880162017C3686B18A3D4780"
    val testAnswer1_4 = 31

    val testInput2_1 = "C200B40A82"
    val testAnswer2_1 = 3

    val testInput2_2 = "04005AC33890"
    val testAnswer2_2 = 54

    val testInput2_3 = "880086C3E88112"
    val testAnswer2_3 = 7

    val testInput2_4 = "CE00C43D881120"
    val testAnswer2_4 = 9

    val testInput2_5 = "D8005AC2A8F0"
    val testAnswer2_5 = 1

    val testInput2_6 = "F600BC2D8F"
    val testAnswer2_6 = 0

    val testInput2_7 = "9C005AC2F8F0"
    val testAnswer2_7 = 0

    val testInput2_8 = "9C0141080250320F1802104A08"
    val testAnswer2_8 = 1
  }
}
