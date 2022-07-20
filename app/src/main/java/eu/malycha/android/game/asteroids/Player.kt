package eu.malycha.android.game.asteroids

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log

class Player(var image: Bitmap) {

    var x: Int = 0
    var y: Int = 0
    private val w: Int = image.width
    private val h: Int = image.height
    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels

    private val corner_x: Float
        get() = (x - w/2).toFloat()
    private val corner_y: Float
        get() = (y - h/2).toFloat()

    var crosshair: Crosshair? = null
    private val paint: Paint

    init {
        x = screenWidth / 2
        y = screenHeight / 2
        paint = Paint()
        paint.color = Color.WHITE
        paint.strokeWidth = 1f
    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(image, corner_x, corner_y, null)
        if (crosshair!!.visible) {
            canvas.drawLine(
                x.toFloat(),
                y.toFloat(),
                crosshair!!.x.toFloat(),
                crosshair!!.y.toFloat(),
                paint
            )
        }
    }
}
