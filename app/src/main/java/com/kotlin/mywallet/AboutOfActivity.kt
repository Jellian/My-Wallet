package com.kotlin.mywallet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AboutOfActivity : AppCompatActivity() {

    private lateinit var genialButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_of)

        genialButton = findViewById(R.id.button_about_genial)

        genialButton.setOnClickListener {
            finish()
        }

    }
}
