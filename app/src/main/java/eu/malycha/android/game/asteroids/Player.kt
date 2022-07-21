package eu.malycha.android.game.asteroids

import android.content.res.Resources
import android.graphics.*
import android.util.Log

class Player(var image: Bitmap) {

    var x: Int = 0
    var y: Int = 0
    var angle: Float = 10.0f
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

    fun update() {
        angle += 1.0f
    }

    fun Bitmap.rotate(degrees: Float): Bitmap {
        val matrix = Matrix().apply {
            postRotate(degrees, 0.0f, 0.0f)
        }
        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    }

    fun draw(canvas: Canvas) {
        val rotated = image.rotate(angle)
        val corner_x = (x - rotated.width/2).toFloat()
        val corner_y = (y - rotated.height/2).toFloat()
        canvas.drawBitmap(rotated, corner_x, corner_y, null)
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
