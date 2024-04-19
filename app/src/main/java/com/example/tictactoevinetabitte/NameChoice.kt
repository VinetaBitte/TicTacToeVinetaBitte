package com.example.tictactoevinetabitte

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
// https://github.com/Practical-Coding3/TicTacToeApp/blob/master/app/src/main/java/net/practicalcoding/tictactoegame/PlayerSetup.java
class NameChoice : AppCompatActivity() {
    // Spelētāju vārdi ar vēlāku inicializāciju un spēles režīma mainīgais, kuru izmanto,
    // lai saņemtu un nodotu tālāk spēles režīma viedu
    private lateinit var pl1_name: EditText
    private lateinit var pl2_name: EditText
    private var mode: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name_choice)
        // spēlētāju vārdu mainīgo inicializācija ar norādēm uz ievades laukiem
        // un spēles režīma saņemšana no sākuma aktivitātes
        mode = intent.getIntExtra("mode",1)
        pl1_name = findViewById(R.id.editTextText)
        pl2_name = findViewById(R.id.editTextText2)
    }

    fun openTictactoeActivity(v: View) {
        val intent = Intent(v.context, TictactoeScreen::class.java)
        // Uz nākamo aktivitāti tiek padoti no ievades laukiem ievadītie spēlētāju vārdi un spēles režīms
        // Ja spēles režīms ir PvC, tad automātiski aizvieto otrā spēlētāja vārdu ar Computer
        if (mode == 1) {
            intent.putExtra("player1", pl1_name.text.toString())
            intent.putExtra("player2", pl2_name.text.toString())
            intent.putExtra("mode", mode)
            startActivity(intent)
        } else {
            intent.putExtra("player1", pl1_name.text.toString())
            intent.putExtra("player2", "Computer")
            intent.putExtra("mode", mode)
            startActivity(intent)
        }
    }
}