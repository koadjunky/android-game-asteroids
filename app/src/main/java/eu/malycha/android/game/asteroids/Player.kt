package eu.malycha.android.game.asteroids

import android.content.res.Resources
import android.graphics.*
import java.lang.Math.*
import kotlin.math.sign

class Player(var image: Bitmap) {

    val max_acc: Double = 0.2

    var x: Double = 0.0
    var y: Double = 0.0
    var vx: Double = 2.0
    var vy: Double = 3.0
    var angle: Double = 0.0
    private val w: Int = image.width
    private val h: Int = image.height
    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels

    private val speed: Double
        get() = hypot(vx, vy)

    var crosshair: Crosshair? = null
    private val paint: Paint

    init {
        x = screenWidth / 2.0
        y = screenHeight / 2.0
        paint = Paint()
        paint.color = Color.WHITE
        paint.strokeWidth = 1f
    }

    private fun crosshairAngle(): Double {
        val dx = (crosshair!!.x - x).toDouble()
        val dy = (y - crosshair!!.y).toDouble()
        val angle = atan2(dx, dy)
        return toDegrees(angle)
    }

    private fun angleDelta(angle1: Double, angle2: Double): Double {
        val delta = angle2 - angle1
        return if (delta > 180.0) {
            delta - 360.0
        } else if (delta <= -180.0) {
            delta + 360.0
        } else {
            delta
        }
    }

    private fun crosshairDistance(): Double {
        val dx = crosshair!!.x - x
        val dy = y - crosshair!!.y
        return hypot(dx, dy)
    }

    private fun acceleration(): Double {
        if (!crosshair!!.visible) return 0.0
        val t = speed / max_acc
        val s = 0.5 * max_acc * t * t
        val d = crosshairDistance()
        return if (s < d) max_acc else -max_acc
    }

    private fun updateAngle() {
        if (crosshair!!.visible) {
            val delta = angleDelta(angle, crosshairAngle())
            angle += delta.coerceIn(-2.0, 2.0)
        }
    }

    private fun updateSpeed() {
        if (crosshair!!.visible) {
            val delta = abs(angleDelta(angle, crosshairAngle()))
            if (delta < 45.0) {
                val a = acceleration()
                vx += a * sin(toRadians(angle))
                vy += -a * cos(toRadians(angle))
            }
        } else {
            vx -= sign(vx) * min(abs(vx), max_acc)
            vy -= sign(vy) * min(abs(vy), max_acc)
        }
    }

    private fun updatePosition() {
        x += vx
        while (x < 0) x += screenWidth
        while (x > screenWidth) x -= screenWidth
        y += vy
        while (y < 0) y += screenHeight
        while (y > screenHeight) y -= screenHeight
        if (crosshairDistance() < 10) {
            crosshair!!.visible = false
        }
    }

    fun update() {
        updateAngle()
        updateSpeed()
        updatePosition()
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
