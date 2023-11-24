class PuzzleY2020D4 : Puzzle {

  private lateinit var passports: List<Map<String, String>>

  override fun parse(input: String) {
    passports = input.lines().splitByBlank().map { lines ->
      lines.joinToString(" ").split(' ').associate { it.split(':').toPair() }
    }
  }

  override fun solve1(): Int {
    val requiredFields = listOf(
      "byr", // Birth Year
      "iyr", // Issue Year
      "eyr", // Expiration Year
      "hgt", // Height
      "hcl", // Hair Color
      "ecl", // Eye Color
      "pid", // Passport ID
    )
    return passports.count { passport -> requiredFields.all { passport.contains(it) } }
  }

  override fun solve2(): Int {
    val requiredFields = mapOf<String, (String) -> Boolean>(
      // Birth Year
      "byr" to { it.toInt() in 1920..2002 },
      // Issue Year
      "iyr" to { it.toInt() in 2010..2020 },
      // Expiration Year
      "eyr" to { it.toInt() in 2020..2030 },
      // Height
      "hgt" to {
        val match = Regex("(\\d+)(cm|in)").matchEntire(it) ?: return@to false
        val (num, unit) = match.destructured
        when (unit) {
          "cm" -> num.toInt() in 150..193
          "in" -> num.toInt() in 59..76
          else -> error()
        }
      },
      // Hair Color
      "hcl" to { it.matches(Regex("#[0-9a-f]{6}")) },
      // Eye Color
      "ecl" to { it in setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth") },
      // Passport ID
      "pid" to { it.matches(Regex("\\d{9}")) },
    )
    return passports.count { passport ->
      requiredFields.all { (key, validator) ->
        passport.contains(key) && validator(passport.getValue(key))
      }
    }
  }

  companion object {
    val testInput1 = """
      ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
      byr:1937 iyr:2017 cid:147 hgt:183cm
      
      iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
      hcl:#cfa07d byr:1929
      
      hcl:#ae17e1 iyr:2013
      eyr:2024
      ecl:brn pid:760753108 byr:1931
      hgt:179cm
      
      hcl:#cfa07d eyr:2025 pid:166559648
      iyr:2011 ecl:brn hgt:59in
      """.trimIndent()
    val testAnswer1 = 2

    val testInput2_1 = """
      eyr:1972 cid:100
      hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926
      
      iyr:2019
      hcl:#602927 eyr:1967 hgt:170cm
      ecl:grn pid:012533040 byr:1946
      
      hcl:dab227 iyr:2012
      ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277
      
      hgt:59cm ecl:zzz
      eyr:2038 hcl:74454a iyr:2023
      pid:3556412378 byr:2007
      """.trimIndent()
    val testAnswer2_1 = 0

    val testInput2_2 = """
      pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980
      hcl:#623a2f
      
      eyr:2029 ecl:blu cid:129 byr:1989
      iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm
      
      hcl:#888785
      hgt:164cm byr:2001 iyr:2015 cid:88
      pid:545766238 ecl:hzl
      eyr:2022
      
      iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719
      """.trimIndent()
    val testAnswer2_2 = 4
  }
}
