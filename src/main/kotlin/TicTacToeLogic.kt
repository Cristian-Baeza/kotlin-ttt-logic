import models.BoardSpaceType

class TicTacToeLogic {

    private var playerOne = BoardSpaceType.PLAYER_ONE
    private var playerTwo = BoardSpaceType.PLAYER_TWO
    private var available = BoardSpaceType.AVAILABLE
    private var tie = BoardSpaceType.TIE

    var board = arrayOf(
        available, available, available, available, available, available, available,
        available, available
    )

    private val winCombos = arrayOf(
        arrayOf(0, 1, 2), // top_row
        arrayOf(3, 4, 5), // middle_row
        arrayOf(6, 7, 8), // bottom_row
        arrayOf(0, 3, 6), // left_column
        arrayOf(1, 4, 7), // center_column
        arrayOf(2, 5, 8), // right_column
        arrayOf(0, 4, 8), // left_diagonal
        arrayOf(6, 4, 2) // right_diagonal
    )

    private fun turnCount(): Int {
        return board.size - board.count { it == available }
    }

    fun currentPlayer(): BoardSpaceType {
        return if (turnCount() % 2 == 0) playerOne else playerTwo
    }

    fun playMove(spaceToPlay: Int) {
        if (board[spaceToPlay] == available)
            board[spaceToPlay] = currentPlayer()
    }

    fun boardFull(): Boolean {
        return !board.contains(available)
    }

    fun winner(): BoardSpaceType? {
        var winner: BoardSpaceType? = null

        for (win_combo in winCombos) {
            val row = ArrayList<BoardSpaceType>()
            for (index in win_combo) {
                row.add(board[index])
            }
            if (row.all { it == row[0] }) {
                if (row[0] != available) {
                    winner = row[0]
                }
            }
        }
        if (boardFull() && winner == null)
            winner = tie
        return winner
    }

    fun gameOver(): Boolean {
        return boardFull() || (winner() != null)
    }

    fun bestMove(): Int {
        var move = cpuCheckForWinsOrBlocks(currentPlayer())
        if (move == null) {
            move = cpuCheckForWinsOrBlocks(nonCurrentPlayer())
        }
        if (move == null) {
            move = cpuTakeOpenSpace()
        }
        return move
    }

    // HELPER METHODS FOR bestMove method
    fun openSpaces(): MutableList<Int> {
        val arrayOfOpenSpaces = mutableListOf<Int>()
        for ((index) in board.withIndex()) {
            if (board[index] == available)
                arrayOfOpenSpaces.add(index)
        }
        return arrayOfOpenSpaces
    }

    fun cpuTakeOpenSpace(): Int {
        return openSpaces().random()
    }

    fun nonCurrentPlayer(): BoardSpaceType {
        return if (currentPlayer() == playerOne) playerTwo else playerOne
    }

    private fun cpuCheckForWinsOrBlocks(playerSymbol: BoardSpaceType): Int? {
        var space: Int? = null
        for (win_combo in winCombos) {
            val row = ArrayList<BoardSpaceType>()
            for (index in win_combo) {
                row.add(board[index])
            }
            if (row.count { e -> e == playerSymbol } == 2) {
                if (row.contains(available)) {
                    space = win_combo[row.indexOf(available)]
                }
            }
        }
        return space
    }

}
