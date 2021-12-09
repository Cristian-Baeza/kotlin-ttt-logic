import models.BoardSpaceType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TicTacToeLogicTest {

    private val game: TicTacToeLogic = TicTacToeLogic()
    private var playerOne = BoardSpaceType.PLAYER_ONE
    private var playerTwo = BoardSpaceType.PLAYER_TWO

    @Test
    fun `currentPlayerTest- returns playerOne if spaces used on board is even`() {
        game.board[0] = playerOne
        game.board[1] = playerTwo

        val expected = playerOne
        assertEquals(expected, game.currentPlayer())
    }

    @Test
    fun `currentPlayerTest- returns playerTwo if spaces used on board is odd`() {
        game.board[0] = playerOne
        game.board[1] = playerTwo
        game.board[2] = playerOne

        val expected = playerTwo
        assertEquals(expected, game.currentPlayer())
    }

    @Test
    fun `playMoveTest- when playerOne picks space, if the space is available the move is played`() {
        game.playMove(0)

        val expected = playerOne
        assertEquals(expected, game.board[0])
    }

    @Test
    fun `playMoveTest- picking space that is not available, nothing happens`() {
        game.board[1] = playerTwo
        game.playMove(1)

        val expected = playerTwo
        assertEquals(expected, game.board[1])
    }

    @Test
    fun `boardFullTest- returns true if board is full of player moves`() {
        game.board[0] = playerOne
        game.board[1] = playerOne
        game.board[2] = playerOne
        game.board[3] = playerOne
        game.board[4] = playerOne
        game.board[5] = playerOne
        game.board[6] = playerOne
        game.board[7] = playerOne
        game.board[8] = playerOne

        val expected = true
        assertEquals(expected, game.boardFull())
    }

    @Test
    fun `boardFullTest- returns false if board still has empty spaces`() {
        game.board[0] = playerOne
        game.board[1] = playerOne
        game.board[2] = playerOne

        val expected = false
        assertEquals(expected, game.boardFull())
    }

    // WINNER
    @Test
    fun `winnerTest- returns null if game is not over`() {
        game.board[0] = playerOne
        game.board[1] = playerTwo
        game.board[2] = playerOne

        val expected = null
        assertEquals(expected, game.winner())
    }

    @Test
    fun `winnerTest- returns playerOne if has winning combo `() {
        game.board[0] = playerOne
        game.board[1] = playerOne
        game.board[2] = playerOne

        val expected = playerOne
        assertEquals(expected, game.winner())
    }

    @Test
    fun `winnerTest- returns playerTwo if has winning combo `() {
        game.board[6] = playerTwo
        game.board[4] = playerTwo
        game.board[2] = playerTwo

        val expected = playerTwo
        assertEquals(expected, game.winner())
    }

    @Test
    fun `winnerTest- returns tie if game is tied `() {
        game.board[0] = playerOne
        game.board[1] = playerOne
        game.board[2] = playerTwo
        game.board[3] = playerTwo
        game.board[4] = playerOne
        game.board[5] = playerOne
        game.board[6] = playerOne
        game.board[7] = playerTwo
        game.board[8] = playerTwo

        val expected = BoardSpaceType.TIE
        assertEquals(expected, game.winner())
    }

    @Test
    fun `gameOverTest- returns true if board is full`() {
        game.board[0] = playerOne
        game.board[1] = playerOne
        game.board[2] = playerOne
        game.board[3] = playerOne
        game.board[4] = playerOne
        game.board[5] = playerOne
        game.board[6] = playerOne
        game.board[7] = playerOne
        game.board[8] = playerOne

        val expected = true
        assertEquals(expected, game.gameOver())
    }

    @Test
    fun `gameOverTest- returns true there is winner`() {
        game.board[6] = playerTwo
        game.board[4] = playerTwo
        game.board[2] = playerTwo

        val expected = true
        assertEquals(expected, game.gameOver())
    }

    @Test
    fun `gameOverTest- returns false if there is no winner or board is not full`() {
        game.board[0] = playerOne
        game.board[1] = playerTwo
        game.board[2] = playerOne

        val expected = false
        assertEquals(expected, game.gameOver())
    }

    @Test
    fun `openSpaces- returns array of available spots by index`() {
        game.board[1] = playerTwo
        game.board[2] = playerOne
        game.board[3] = playerOne
        game.board[4] = playerOne

        val expected = mutableListOf(0, 5, 6, 7, 8)
        assertEquals(expected, game.openSpaces())
    }

    @Test
    fun `nonCurrentPlayer- returns the player who's turn it isn't`() {
        game.board[0] = playerOne
        game.board[1] = playerTwo
        game.board[2] = playerOne

        val expected = playerOne
        assertEquals(expected, game.nonCurrentPlayer())
    }

    @Test
    fun `bestMove- returns winning move - horizontal win`() {
        game.board[1] = playerOne
        game.board[2] = playerOne

        val expected = 0
        assertEquals(expected, game.bestMove())
    }

    @Test
    fun `bestMove- returns winning move - vertical win`() {
        game.board[3] = playerOne
        game.board[6] = playerOne

        val expected = 0
        assertEquals(expected, game.bestMove())
    }

    @Test
    fun `bestMove- returns winning move - diagonal win`() {
        game.board[4] = playerOne
        game.board[8] = playerOne

        val expected = 0
        assertEquals(expected, game.bestMove())
    }

    @Test
    fun `bestMove- wins over blocking when it is playerTwo turn`() {
        //   X X 2
        //   X 4 5
        //   O O 8
        // the best move should be space 8. As it is currently player 2's turn and that move will win
        game.board[0] = playerOne
        game.board[1] = playerOne
        game.board[3] = playerOne
        game.board[6] = playerTwo
        game.board[7] = playerTwo

        val expected = 8
        assertEquals(expected, game.bestMove())
    }

    @Test
    fun `bestMove- wins over blocking when it is playerOne turn`() {
        //   X X 2
        //   3 4 5
        //   O O 8
        // the best move should be space 2. As it is currently player 1's turn and that move will win
        game.board[0] = playerOne
        game.board[1] = playerOne
        game.board[6] = playerTwo
        game.board[7] = playerTwo

        val expected = 2
        assertEquals(expected, game.bestMove())
    }
}
