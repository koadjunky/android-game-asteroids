package eu.malycha.android.game.asteroids

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import androidx.annotation.RequiresApi

class MainActivity : Activity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.window.insetsController?.hide(WindowInsets.Type.statusBars())
    }
}
