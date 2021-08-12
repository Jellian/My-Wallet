package com.kotlin.mywallet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class PrivacyActivity : AppCompatActivity() {

    private lateinit var beduImageView: ImageView
    private lateinit var santanderImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy)

        beduImageView = findViewById(R.id.image_bedu)
        santanderImageView = findViewById(R.id.image_santander)

        beduImageView.setOnClickListener {
            finish()
        }

        santanderImageView.setOnClickListener {
            finish()
        }
    }
}