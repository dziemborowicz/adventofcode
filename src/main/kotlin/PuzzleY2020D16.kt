class PuzzleY2020D16 : Puzzle {

  private data class Field(val label: String, val ranges: LongRangeSet)

  private lateinit var fields: List<Field>
  private lateinit var yourTicket: List<Long>
  private lateinit var nearbyTickets: List<List<Long>>
  private lateinit var validValues: LongRangeSet

  override fun parse(input: String) {
    val (fieldsString, yourTicketString, nearbyTicketsString) = input.lines().splitByBlank()

    fields = fieldsString.map { line ->
      val (label, range1, range2) = line.split(": ", " or ")
      val ranges = LongRangeSet().apply {
        addAll(range1.toLongRange())
        addAll(range2.toLongRange())
      }
      Field(label, ranges)
    }

    yourTicket = yourTicketString.drop(1).map { it.extractLongs() }.single()

    nearbyTickets = nearbyTicketsString.drop(1).map { it.extractLongs() }

    validValues = fields.fold(LongRangeSet()) { acc, field ->
      acc.addAll(field.ranges)
      acc
    }
  }

  override fun solve1(): Long {
    return nearbyTickets.sumOf { ticket -> ticket.filter { field -> field !in validValues }.sum() }
  }

  override fun solve2(): Long {
    val validNearbyTickets =
      nearbyTickets.filter { ticket -> ticket.all { field -> field in validValues } }

    val fieldToPossiblePositions = fields.associateWith { field ->
      fields.indices.filter { index ->
        validNearbyTickets.all { ticket -> ticket[index] in field.ranges }
      }.toMutableSet()
    }

    while (fieldToPossiblePositions.values.any { it.size != 1 }) {
      for ((field, possiblePositions) in fieldToPossiblePositions.entries) {
        if (possiblePositions.size == 1) {
          for (otherField in fields) {
            if (otherField !== field) {
              fieldToPossiblePositions.getValue(otherField) -= possiblePositions.single()
            }
          }
        }
      }
    }

    return fields.filter { it.label.startsWith("departure") }.productOf { field ->
      val position = fieldToPossiblePositions.getValue(field).single()
      yourTicket[position]
    }
  }

  companion object {
    val testInput1 = """
      class: 1-3 or 5-7
      row: 6-11 or 33-44
      seat: 13-40 or 45-50
      
      your ticket:
      7,1,14
      
      nearby tickets:
      7,3,47
      40,4,50
      55,2,20
      38,6,12
      """.trimIndent()
    val testAnswer1 = 71
  }
}
