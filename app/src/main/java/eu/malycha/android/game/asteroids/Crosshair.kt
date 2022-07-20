package eu.malycha.android.game.asteroids

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas

class Crosshair(var image: Bitmap) {
    var x: Int = 0
    var y: Int = 0
    private val w: Int = image.width
    private val h: Int = image.height
    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels

    var visible: Boolean = false
    private val corner_x: Float
        get() = (x - w/2).toFloat()
    private val corner_y: Float
        get() = (y - h/2).toFloat()

    fun draw(canvas: Canvas) {
        if (visible) {
            canvas.drawBitmap(image, corner_x, corner_y, null)
        }
    }

    fun updateTouch(touch_x: Int, touch_y: Int) {
        visible = true
        x = touch_x
        y = touch_y
    }
}
