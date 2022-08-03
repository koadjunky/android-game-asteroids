package eu.malycha.android.game.asteroids

import java.lang.Math.atan2
import java.lang.Math.hypot

class Vector2d(val x: Double, val y: Double) {

    val angle: Double
        get() = atan2(y, x)

    val length: Double
        get() = hypot(x, y)

    operator fun unaryMinus() = Vector2d(-x, -y)
    operator fun unaryPlus() = Vector2d(x, y)
    operator fun plus(v: Vector2d) = Vector2d(x+v.x, y+v.y)
    operator fun minus(v: Vector2d) = Vector2d(x-v.x, y-v.y)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Vector2d

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }
}
