package com.geekbrains.films

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    object obj {
        val f = Film("456afe", "Terminator").copy(originalTitle = "Terminator 2")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button).setOnClickListener {
            val f = Film("123def", "The Silence of the Lambs")
            Toast.makeText(this, "f=$f o.f=${obj.f}", Toast.LENGTH_LONG).show()
            d("f=$f o.f=${obj.f}")

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