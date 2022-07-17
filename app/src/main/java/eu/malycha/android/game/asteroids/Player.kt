package eu.malycha.android.game.asteroids

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log

class Player(var image: Bitmap) {

    var x: Int = 0
    var y: Int = 0
    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels

    init {
        x = screenWidth / 2
        y = screenHeight / 2
    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(image, x.toFloat(), y.toFloat(), null)
    }
}
