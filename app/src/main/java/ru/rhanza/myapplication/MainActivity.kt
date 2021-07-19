package ru.rhanza.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button: FloatingActionButton = findViewById(R.id.fab)
        button.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    NextActivity::class.java
                )
            )
        })
    }
}