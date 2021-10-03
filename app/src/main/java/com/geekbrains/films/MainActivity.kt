package com.geekbrains.films

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button).setOnClickListener {
            val f = Film("123def", "The Silence of the Lambs")
            Toast.makeText(this, "f=$f", Toast.LENGTH_LONG).show()

            val m = mapOf(1 to "a", 2 to "b")

            d("------")
            for (i in m.keys) {
                d("$i -> ${m[i]}")
            }

            d("------")
            for ((k, v) in m) {
                d("$k -> $v")
            }
        }
    }
}