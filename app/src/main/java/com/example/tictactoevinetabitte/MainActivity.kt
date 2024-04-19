package com.example.tictactoevinetabitte

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
// Aktivitātes "MainActivity.kt" un "NameChoice.kt" un attiecīgie xlm faili veidoti, pamatojoties uz eksistējo projektu:
// https://www.youtube.com/watch?v=Fa5egLurW5U un https://www.youtube.com/watch?v=qBAYVKT8ITI&t=1s
// https://github.com/Practical-Coding3/TicTacToeApp/blob/master/app/src/main/java/net/practicalcoding/tictactoegame/MainActivity.java
    private lateinit var pvcButton: Button
    private lateinit var pvpButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pvcButton = findViewById(R.id.button)
        pvpButton = findViewById(R.id.button2)
        // pārejot uz nākamo aktivitāti, tai nododas 0, ja ir uzspiesta poga "Single player"
        // un 1, ja ir nospiests Multiplayer
        pvcButton.setOnClickListener{
            // https://teamtreehouse.com/community/how-to-pass-an-integer-between-activities
            val intent = Intent(this, NameChoice::class.java)
            intent.putExtra("mode", 0 )
            startActivity(intent)
        }
        pvpButton.setOnClickListener{
            val intent = Intent(this, NameChoice::class.java)
            intent.putExtra("mode", 1 )
            startActivity(intent)
        }
    }
}