package com.example.androidtictactoe

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var buttons: Array<Button>
    private lateinit var gameStatusText: TextView
    private lateinit var playAgainButton: Button
    private var currentPlayer = "X"
    private val board = Array(9) { "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupGame()
    }

    private fun setupGame() {
        gameStatusText = findViewById(R.id.gameStatusText)
        playAgainButton = findViewById(R.id.playAgainButton)

        buttons = Array(9) { index ->
            findViewById<Button>(resources.getIdentifier("button$index", "id", packageName)).apply {
                setOnClickListener { onCellClicked(index) }
            }
        }

        playAgainButton.setOnClickListener { resetGame() }
    }

    private fun onCellClicked(index: Int) {
        if (board[index].isEmpty()) {
            board[index] = currentPlayer
            buttons[index].text = currentPlayer

            when {
                checkWin() -> endGame("${currentPlayer}_WINS")
                checkDraw() -> endGame("DRAW")
                else -> {
                    currentPlayer = if (currentPlayer == "X") "O" else "X"
                    updateGameStatus()
                }
            }
        }
    }

    private fun checkWin(): Boolean {
        val winningCombinations = arrayOf(
            arrayOf(0, 1, 2), arrayOf(3, 4, 5), arrayOf(6, 7, 8), // Rows
            arrayOf(0, 3, 6), arrayOf(1, 4, 7), arrayOf(2, 5, 8), // Columns
            arrayOf(0, 4, 8), arrayOf(2, 4, 6) // Diagonals
        )

        return winningCombinations.any { (a, b, c) ->
            board[a].isNotEmpty() && board[a] == board[b] && board[b] == board[c]
        }
    }

    private fun checkDraw(): Boolean {
        return board.none { it.isEmpty() }
    }

    private fun endGame(result: String) {
        gameStatusText.text = when (result) {
            "X_WINS" -> getString(R.string.x_wins)
            "O_WINS" -> getString(R.string.o_wins)
            else -> getString(R.string.draw)
        }

        buttons.forEach { it.isEnabled = false }
        playAgainButton.visibility = View.VISIBLE
    }

    private fun resetGame() {
        board.fill("")
        buttons.forEach {
            it.text = ""
            it.isEnabled = true
        }
        currentPlayer = "X"
        updateGameStatus()
        playAgainButton.visibility = View.GONE
    }

    private fun updateGameStatus() {
        gameStatusText.text = if (currentPlayer == "X") {
            getString(R.string.player_x_turn)
        } else {
            getString(R.string.player_o_turn)
        }
    }
}