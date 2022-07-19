package eu.malycha.android.game.asteroids

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas

class Crosshair(var image: Bitmap) {
    var x: Int = 0
    var y: Int = 0
    private val w: Int
    private val h: Int
    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels
    var visible: Boolean = false

    init {
        w = image.width
        h = image.height
    }

    fun draw(canvas: Canvas) {
        if (visible) {
            canvas.drawBitmap(image, x.toFloat(), y.toFloat(), null)
        }
    }

    fun updateTouch(touch_x: Int, touch_y: Int) {
        visible = true
        x = touch_x - w/2
        y = touch_y - h/2
    }
}
