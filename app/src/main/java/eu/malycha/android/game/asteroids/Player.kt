package eu.malycha.android.game.asteroids

import android.content.res.Resources
import android.graphics.*
import java.lang.Math.*

class Player(var image: Bitmap) {

    val max_acc: Double = 0.2

    var x: Int = 0
    var y: Int = 0
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
        x = screenWidth / 2
        y = screenHeight / 2
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

    private fun crosshairDistance(): Double {
        val dx = (crosshair!!.x - x).toDouble()
        val dy = (y - crosshair!!.y).toDouble()
        return hypot(dx, dy)
    }

    private fun acceleration(): Double {
        if (!crosshair!!.visible) return 0.0
        val t = speed / max_acc
        val s = 0.5 * max_acc * t * t
        val d = crosshairDistance()
        return if (s < d) max_acc else -max_acc
    }

    fun update() {
        val a = acceleration()
        vx += a * sin(toRadians(angle))
        vy += -a * cos(toRadians(angle))
        // TODO: Recalculate everything to Double, apply toInt() as last step
        x = (x + vx).toInt().mod(screenWidth)
        y = (y + vy).toInt().mod(screenHeight)
        if (crosshair!!.visible) {
            var angleDelta = crosshairAngle() - angle
            if (angleDelta > 180.0) {
                angleDelta -= 360.0
            } else if (angleDelta < -180.0) {
                angleDelta += 360.0
            }
            angle += angleDelta.coerceIn(-2.0, 2.0)
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
