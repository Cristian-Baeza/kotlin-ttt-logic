import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TicTacToeLogicTest {

  private val ticTacToe: TicTacToeLogic = TicTacToeLogic()

  @Test
  fun greet() {
    val expected = "Welcome to TicTacToe!"
    assertEquals(expected, ticTacToe.greet())
  }
}
