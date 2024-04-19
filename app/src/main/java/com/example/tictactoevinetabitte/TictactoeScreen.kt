package com.example.tictactoevinetabitte

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat


// Aktivitātes izstrādes pamatā izmantots:
// https://github.com/usmaanz/Tic-Tac-Toe/blob/master/app/src/main/java/com/usmaan/tictactoe/GameActivity.kt
class TictactoeScreen : AppCompatActivity() {
    // izveido mainīgos visiem textView objektiem
    private lateinit var gameRules: GameRules
    private lateinit var cell1: TextView
    private lateinit var cell2: TextView
    private lateinit var cell3: TextView
    private lateinit var cell4: TextView
    private lateinit var cell5: TextView
    private lateinit var cell6: TextView
    private lateinit var cell7: TextView
    private lateinit var cell8: TextView
    private lateinit var cell9: TextView
    private lateinit var playersTurn: TextView
    private lateinit var playAgainButton: Button
    private lateinit var pl1Name: String
    private lateinit var pl2Name: String
    private var currTurn: Int = 2
    private var draw: Int = 10
    private var mode: Int = -1
    private var win: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tictactoe_screen)
        // Piešķir vērtības visiem iepriekš definētiem mainīgājiem
        cell1 = findViewById(R.id.cell1)
        cell2 = findViewById(R.id.cell2)
        cell3 = findViewById(R.id.cell3)
        cell4 = findViewById(R.id.cell4)
        cell5 = findViewById(R.id.cell5)
        cell6 = findViewById(R.id.cell6)
        cell7 = findViewById(R.id.cell7)
        cell8 = findViewById(R.id.cell8)
        cell9 = findViewById(R.id.cell9)
        playersTurn = findViewById(R.id.textView)
        playAgainButton = findViewById(R.id.button3)
        // Saņem pirmā un otrā spēlētāja vārdus, kā arī spēles režīmu
        // https://www.geeksforgeeks.org/how-to-send-data-from-one-activity-to-second-activity-in-android/
        val intent = intent
        pl1Name = intent.getStringExtra("player1").toString()
        pl2Name = intent.getStringExtra("player2").toString()
        mode = intent.getIntExtra("mode", 1)
        // Izveido gameRules klases objektu un izvada paziņojumu spēles sākumā
        gameRules = GameRules()
        greetingMessage()
        // https://developer.android.com/guide/topics/resources/string-resource
        playersTurn.text = getString(R.string.first_message, pl1Name)
        // Nospiežot uz katru šūnu, tā izsauc metodi onCellClicked
        // un nodod tai attiecīgās šūnas pozīcīju kā parametru
        cell1.setOnClickListener {
            onCellClicked(
                cell1,
                ViewsPosition(0, 0)
            )
        }
        cell2.setOnClickListener {
            onCellClicked(
                cell2,
                ViewsPosition(0, 1)
            )
        }
        cell3.setOnClickListener {
            onCellClicked(
                cell3,
                ViewsPosition(0, 2)
            )
        }
        cell4.setOnClickListener {
            onCellClicked(
                cell4,
                ViewsPosition(1, 0)
            )
        }
        cell5.setOnClickListener {
            onCellClicked(
                cell5,
                ViewsPosition(1, 1)
            )
        }
        cell6.setOnClickListener {
            onCellClicked(
                cell6,
                ViewsPosition(1, 2)
            )
        }
        cell7.setOnClickListener {
            onCellClicked(
                cell7,
                ViewsPosition(2, 0)
            )
        }
        cell8.setOnClickListener {
            onCellClicked(
                cell8,
                ViewsPosition(2, 1)
            )
        }
        cell9.setOnClickListener {
            onCellClicked(
                cell9,
                ViewsPosition(2, 2)
            )
        }
        // "Play again" poga, atjauno sākuma vērtības
        // nepieciešamajiem mainīgajiem, spēles masīvam un visām šūnām
        playAgainButton.setOnClickListener {
            currTurn = 2
            draw = 10
            win = false
            gameRules.resetPossiblePositions()
            resetCells()
            playersTurn.text = getString(R.string.display_turn,pl1Name)
        }
    }

    // Poga "Home" atgriež atpakaļ uz pašu pirmo aktivitāti - sākuma logu
    fun backToMainActivity(v: View) {
        val intent = Intent(v.context, MainActivity::class.java)
        startActivity(intent)
    }

    private fun onCellClicked(viewCell: TextView, viewPosition: ViewsPosition) {
        // Ja nospiestās šūnas tekts ir tukšs, tad tekstu nomaina uz attiecīgā spēlētāja simbolu,
        // izdara gājienu un pārbauda vai jau neizveidojās uzvaras līnija, ja izveidojās,
        // tad bloķē šūnas un rāda uzvarētāju ar uzvaras ziņu
        if (viewCell.text.isEmpty()) {
            if (mode == 1) {
                viewCell.text = gameRules.playerSymbols
                val winLine = gameRules.makeMove(viewPosition)
                displayPlayersTurn()
                if (winLine != null) {
                    win = true
                    disableCells()
                    showWinner(winLine)
                    winMessage()
                }
                draw -= 1
            } else {
                // Nepilnīgi strādājoša PvC režīma kods, nolēmu to atstāt, lai parādītu, ka es tomēr centos ieviest PvC

                // Sākumā savu soli veic spēlētājs
                viewCell.text = gameRules.pvcPlayerSym
                val winLine = gameRules.makeMovePlayer(viewPosition)
                currTurn = 3 - currTurn
                draw -= 1
                playersTurn.text = getString(R.string.display_turn,pl1Name)
                if (winLine != null) {
                    win = true
                    disableCells()
                    showWinner(winLine)
                    winMessage()
                }

                // tad arī dators veic soli, abi izdara gājēnu pēc noklikšķināšanas uz šunu
                val compMove = gameRules.computerMove()
                currTurn = 3 - currTurn
                draw -= 1
                updateCells(compMove)
                val compWinLine = gameRules.makeMoveComputer(compMove)
                if (compWinLine != null) {
                    win = true
                    disableCells()
                    showWinner(compWinLine)
                    winMessage()
                }

            }
            // Gājiena beigās ir pārbaude uz neizšķirtu
            if (draw == 1) {
                winMessage()
            }
        }
    }
    // Funkcija, kas ataino datora veikto soli
    // *šī faila kods* un prompt: computer move shows incorrectly
    private fun updateCells(position: ViewsPosition) {
        val cellToUpdate = when (position) {
            ViewsPosition(1, 1) -> cell1
            ViewsPosition(1, 2) -> cell2
            ViewsPosition(1, 3) -> cell3
            ViewsPosition(2, 1) -> cell4
            ViewsPosition(2, 2) -> cell5
            ViewsPosition(2, 3) -> cell6
            ViewsPosition(3, 1) -> cell7
            ViewsPosition(3, 2) -> cell8
            ViewsPosition(3, 3) -> cell9
            else -> cell1
        }
        if (cellToUpdate.text.isEmpty()) {
            cellToUpdate.text = gameRules.pvcComputerSym
        }
    }

    // Šūnu sākotnējo vērtību atjaunošana
    private fun resetCells() {
        cell1.text = ""
        cell2.text = ""
        cell3.text = ""
        cell4.text = ""
        cell5.text = ""
        cell6.text = ""
        cell7.text = ""
        cell8.text = ""
        cell9.text = ""
        cell1.isEnabled = true
        cell2.isEnabled = true
        cell3.isEnabled = true
        cell4.isEnabled = true
        cell5.isEnabled = true
        cell6.isEnabled = true
        cell7.isEnabled = true
        cell8.isEnabled = true
        cell9.isEnabled = true
        cell1.background = null
        cell2.background = null
        cell3.background = null
        cell4.background = null
        cell5.background = null
        cell6.background = null
        cell7.background = null
        cell8.background = null
        cell9.background = null
    }
    // Šūnu bloķēšana, lai apstrādātu nospiešanu uz tām pēc spēles beigām
    private fun disableCells() {
        cell1.isEnabled = false
        cell2.isEnabled = false
        cell3.isEnabled = false
        cell4.isEnabled = false
        cell5.isEnabled = false
        cell6.isEnabled = false
        cell7.isEnabled = false
        cell8.isEnabled = false
        cell9.isEnabled = false
    }
    // Uzvaras paziņojums spēlētājiem
    // https://www.geeksforgeeks.org/how-to-create-an-alert-dialog-box-in-android/
    private fun winMessage() {
        val message = AlertDialog.Builder(this)
        val text = if (currTurn == 1) {
            "Player $pl1Name points: ${gameRules.firstPlayerPoints}" + "\n" +
                    "Player $pl2Name points: ${gameRules.secondPlayerPoints}"
        } else {
            "Player $pl1Name points: ${gameRules.firstPlayerPoints}" + "\n" +
                    "Player $pl2Name points: ${gameRules.secondPlayerPoints}"
        }
        message.setTitle(
            if (currTurn == 1 && win) {
                "$pl1Name wins!  $pl2Name lost!"
            } else if (currTurn == 2 && win){
                "$pl2Name wins!  $pl1Name lost!"
            } else {
                    "It's a Draw!"
            }
        )
        message.setMessage(text)
        message.setCancelable(false)
        message.setNegativeButton("Ok") { dialog, _ ->
            dialog.cancel()
        }
        val endgameMessage = message.create()
        endgameMessage.show()

    }
    // Paziņojums uzsākot spēli
    // https://www.geeksforgeeks.org/how-to-create-an-alert-dialog-box-in-android/
    private fun greetingMessage() {
        val message = AlertDialog.Builder(this)
        val text = "Welcome $pl1Name and $pl2Name!\n Enjoy playing Tic Tac Toe!"
        message.setMessage(text)
        message.setCancelable(false)
        message.setNegativeButton("Ok") { dialog, _ ->
            dialog.cancel()
        }
        val endgameMessage = message.create()
        endgameMessage.show()
    }
    // Funkcija dinamiski parāda, kam tagad ir gājiens
    private fun displayPlayersTurn() {
        // *šī faila tā brīža kods* un Prompt: I have a problem that in textView playersturns text
        // does not change when I click on cells how can I fix that in kotlin
        val newText = getString(R.string.display_turn, if (currTurn == 1) pl1Name else pl2Name)
        playersTurn.text = newText
        //
        currTurn = 3 - currTurn
    }

    // Parāda, kura no līnijām ir uzvaras, pa virsu uzzīmējot attiecīgo līniju
    private fun showWinner(winLine: WinLine) {
        val (winningCells, background) = when (winLine) {

            WinLine.FIRSTROW -> Pair(listOf(cell1, cell2, cell3), R.drawable.row_line)
            WinLine.SECONDROW -> Pair(listOf(cell4, cell5, cell6), R.drawable.row_line)
            WinLine.THIRDROW -> Pair(listOf(cell7, cell8, cell9), R.drawable.row_line)
            WinLine.FIRSTCOLUMN -> Pair(listOf(cell1, cell4, cell7), R.drawable.column_line)
            WinLine.SECONDCOLUMN -> Pair(listOf(cell2, cell5, cell8), R.drawable.column_line)
            WinLine.THIRDCOLUMN -> Pair(listOf(cell3, cell6, cell9), R.drawable.column_line)
            WinLine.DIAGONAL1 -> Pair(listOf(cell1, cell5, cell9), R.drawable.diagonal_down)
            WinLine.DIAGONAL2 -> Pair(listOf(cell7, cell5, cell3), R.drawable.diagonal_up)

        }
        winningCells.forEach { cell ->
            cell.background = ContextCompat.getDrawable(this, background)
        }
    }
}