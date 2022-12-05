# Advent of Code

Solutions for Advent of Code puzzles.

See [adventofcode.com](https://adventofcode.com/) for more information.

## Introduction

Advent of Code puzzles typically have one input file and two parts.

The harness provided by this repository --

1. Downloads the input file;
2. Runs code to produce an answer for each part; and
3. Uploads each answer.

## Basic Usage

Set the `ADVENT_OF_CODE_SESSION` environment variable with the value of your
`session` cookie on the [adventofcode.com](https://adventofcode.com/) website. This
is necessary for the harness to download the input file and upload your answers.

For each puzzle, create a class with a name in the format `PuzzleY${year}D${day}`
(e.g., `PuzzleY2022D1`) that implements the `Puzzle` interface.

```
interface Puzzle {
  fun parse(input: String)
  fun solve1(): Any?
  fun solve2(): Any?
}
```

The `parse` function is called once after the input is downloaded, and `solve1` and
`solve2` are called to provide an answer to the first and second part of the
problem respectively. Any type may be returned from these functions; `toString` is
called on it to provide the answer that is uploaded.

Run the binary with the arguments `solve YEAR DAY` (e.g., `solve 2022 1`) to solve
the specified puzzle and upload your answer. If you only want to solve the first or
second part, you can specify that too (e.g., `solve 2022 1 1`).

## Running Tests

To run your solutions against sample inputs with expected answers, add a companion
object to your `Puzzle` class with `testInputN` and `testAnswerN` fields. For
example --

```
class PuzzleY2022D1 : Puzzle {

  // ...

  companion object {
    val testInput1 = """
      1000
      2000
      3000
      
      4000
      
      5000
      6000
      
      7000
      8000
      9000
      
      10000
      """.trimIndent()
    val testAnswer1 = 24000

    val testInput2 = testInput1
    val testAnswer2 = 45000
  }
}
```

`testInput1` and `testAnswer1` are used with `solve1`, and `testInput2` and
`testAnswer2` are used with `solve2`.

If you want to provide multiple inputs for one `solveN` function, you can provide
numbered or labeled inputs with matching expected answers:

```
class PuzzleY2022D1 : Puzzle {

  // ...

  companion object {
    val testInput1_1 = "abc"
    val testAnswer1_1 = 123

    val testInput1_2 = "def"
    val testAnswer1_2 = 456
    
    val testInput1_3 = "hij"
    val testAnswer1_3 = 789
    
    val testInput1_large = "klmnop"
    val testAnswer1_large = 789123

    val testInput2 = "zyx"
    val testAnswer2 = 987
  }
}
```

The tests are run automatically before attempting to solve the real input. Solving
the real input is not attempted unless all tests pass.

You can also include arbitrary test cases that also block submission if they fail
by adding arbitrary methods prefixed with `test`:

```
class PuzzleY2022D1 : Puzzle {

  // ...

  companion object {
    fun testSomething() {
      assertThat(mySum(1, 2)).isEqualTo(2)
    }

    fun testSomethingElse() {
      assertThat(myProduct(2, 3)).isEqualTo(6)
    }

    // ...
  }
}
```

This is a quick-and-dirty runner, so the first failure stops execution. But it can
be useful nonetheless.

## Clearing the Cache

Input files are cached to avoid repeatedly downloading potentially large files. You
can clear the cache by running the binary with `clearCache`.
