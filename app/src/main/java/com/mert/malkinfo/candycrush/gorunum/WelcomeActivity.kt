package com.mert.malkinfo.candycrush.gorunum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.mert.malkinfo.candycrush.R

class WelcomeActivity : AppCompatActivity() {
    lateinit var handler : Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        handler = Handler()
        handler.postDelayed({

            val intent = Intent(this, PlayActivity::class.java)
            startActivity(intent)
            finish()

        },       3000)
    }
}