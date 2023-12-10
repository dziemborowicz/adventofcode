class PuzzleY2020D21 : Puzzle {

  private data class Food(val ingredients: Set<String>, val allergens: Set<String>)

  private lateinit var foods: List<Food>

  private lateinit var allAllergens: Set<String>
  private lateinit var allIngredients: Set<String>

  private lateinit var safeIngredients: Set<String>
  private lateinit var unsafeIngredients: Set<String>

  override fun parse(input: String) {
    foods = input.lines().map { line ->
      val regex = Regex("""([a-z ]+) \(contains ([a-z, ]+)\)""")
      val (ingredients, allergens) = regex.matchEntire(line)!!.destructured
      Food(ingredients.split(" ").toSet(), allergens.split(", ").toSet())
    }

    allAllergens = foods.flatMap { it.allergens }.toSet()
    allIngredients = foods.flatMap { it.ingredients }.toSet()

    safeIngredients = allIngredients.filter { ingredient ->
      allAllergens.none { allergen -> isValid(allergen, ingredient) }
    }.toSet()
    unsafeIngredients = allIngredients - safeIngredients
  }

  override fun solve1(): Int {
    return safeIngredients.sumOf { ingredient -> foods.count { ingredient in it.ingredients } }
  }

  override fun solve2(): String {
    return unsafeIngredients.permutations().map { (allAllergens zip it).associate { it } }
      .first { isValid(it) }.entries.sortedBy { it.key }.map { it.value }.joinToString(",")
  }

  private fun isValid(allergenToIngredientMap: Map<String, String>) =
    allergenToIngredientMap.entries.all { (allergen, ingredient) -> isValid(allergen, ingredient) }

  private fun isValid(allergen: String, ingredient: String) =
    foods.none { allergen in it.allergens && ingredient !in it.ingredients }

  companion object {
    val testInput1 = """
      mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
      trh fvjkl sbzzf mxmxvkd (contains dairy)
      sqjhc fvjkl (contains soy)
      sqjhc mxmxvkd sbzzf (contains fish)
      """.trimIndent()
    val testAnswer1 = 5

    val testInput2 = testInput1
    val testAnswer2 = "mxmxvkd,sqjhc,fvjkl"
  }
}
