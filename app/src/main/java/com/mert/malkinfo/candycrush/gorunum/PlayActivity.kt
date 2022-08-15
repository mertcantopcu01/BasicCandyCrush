package com.mert.malkinfo.candycrush.gorunum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.mert.malkinfo.candycrush.MainActivity
import com.mert.malkinfo.candycrush.R

class PlayActivity : AppCompatActivity() {
    private lateinit var playbtn : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        val action = supportActionBar
        action?.hide()
        playbtn = findViewById(R.id.btnPlay)
        playbtn.setOnClickListener {
            startActivity(Intent
                (this@PlayActivity, MainActivity::class.java))
        }
    }
}