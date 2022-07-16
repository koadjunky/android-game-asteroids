package eu.malycha.android.game.asteroids

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.widget.Button
import androidx.annotation.RequiresApi

class MainActivity : Activity() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.window.insetsController?.hide(WindowInsets.Type.statusBars())
        wireStartButton()
    }

    private fun wireStartButton() {
        val button = findViewById<Button>(R.id.start_game)
        button.setOnClickListener {
            switchActivities()
        }
    }

    private fun switchActivities() {
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }
}
