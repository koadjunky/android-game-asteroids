package eu.malycha.android.game.asteroids

import android.content.res.Resources
import android.graphics.*
import android.util.Log
import java.lang.Math.*

class Player(var image: Bitmap) {

    var x: Int = 0
    var y: Int = 0
    var vx: Int = 2
    var vy: Int = 3
    var angle: Double = 0.0
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

    fun crosshair_angle(): Double {
        val dx = (crosshair!!.x - x).toDouble()
        val dy = (y - crosshair!!.y).toDouble()
        val angle = atan2(dx, dy)
        return toDegrees(angle)
    }

    fun update() {
        x = (x + vx).mod(screenWidth)
        y = (y + vy).mod(screenHeight)
        if (crosshair!!.visible) {
            var angleDelta = crosshair_angle() - angle
            if (angleDelta > 180.0) {
                angleDelta -= 360.0
            } else if (angleDelta < -180.0) {
                angleDelta += 360.0
            }
            angle += angleDelta.coerceIn(-1.0, 1.0)
        }
    }

    fun Bitmap.rotate(degrees: Double): Bitmap {
        val matrix = Matrix().apply {
            postRotate(degrees.toFloat(), 0.0f, 0.0f)
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
