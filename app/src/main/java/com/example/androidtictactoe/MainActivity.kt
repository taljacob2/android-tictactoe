package com.example.androidtictactoe

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUi()
    }

    private fun setupUi() {
        val button: Button = findViewById(R.id.buttonMain)
        button.setOnClickListener { finish() }
    }

    override fun onStop() {
        super.onStop()

        Log.d(MainActivity::class.java.name, "activity stopped")
    }
}