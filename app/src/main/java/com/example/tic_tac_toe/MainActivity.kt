package com.example.tic_tac_toe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var currentPlayer = "X"
    private var gameBoard = Array(3) { Array(3) { "" } }
    private lateinit var statusText: TextView
    private lateinit var playAgainButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusText = findViewById(R.id.statusText)
        playAgainButton = findViewById(R.id.playAgainButton)

        playAgainButton.setOnClickListener { onPlayAgainClick() }
    }

    fun onCellClick(view: View) {
        val button = view as Button
        val tag = button.tag.toString().toInt()

        val row = tag / 3
        val col = tag % 3


        if (gameBoard[row][col] == "" && !isGameOver()) {
            gameBoard[row][col] = currentPlayer
            button.text = currentPlayer


            if (checkWinner()) {
                statusText.text = "Player $currentPlayer wins!"
                playAgainButton.visibility = View.VISIBLE
            }

            else if (isBoardFull()) {
                statusText.text = "It's a draw!"
                playAgainButton.visibility = View.VISIBLE
            } else {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
                statusText.text = "Player $currentPlayer's turn"
            }
        }
    }


    private fun checkWinner(): Boolean {

        for (i in 0..2) {
            if (gameBoard[i][0] == currentPlayer && gameBoard[i][1] == currentPlayer && gameBoard[i][2] == currentPlayer) return true
            if (gameBoard[0][i] == currentPlayer && gameBoard[1][i] == currentPlayer && gameBoard[2][i] == currentPlayer) return true
        }
        if (gameBoard[0][0] == currentPlayer && gameBoard[1][1] == currentPlayer && gameBoard[2][2] == currentPlayer) return true
        if (gameBoard[0][2] == currentPlayer && gameBoard[1][1] == currentPlayer && gameBoard[2][0] == currentPlayer) return true

        return false
    }

    private fun isBoardFull(): Boolean {
        for (row in gameBoard) {
            for (cell in row) {
                if (cell == "") return false
            }
        }
        return true
    }

    private fun onPlayAgainClick() {
        gameBoard = Array(3) { Array(3) { "" } } // איפוס הלוח
        currentPlayer = "X"
        statusText.text = "Player $currentPlayer's turn"
        playAgainButton.visibility = View.GONE

        for (i in 0..8) {
            val button = findViewById<Button>(resources.getIdentifier("button$i", "id", packageName))
            button.text = ""
        }
    }

    private fun isGameOver(): Boolean {
        return checkWinner() || isBoardFull()
    }
}
