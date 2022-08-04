package eu.malycha.android.game.asteroids

import org.junit.Test
import org.junit.Assert.*
import java.lang.Math.*

class Vector2dTest {

    @Test
    fun angle_is_correct() {
        assertEquals(0.0, Vector2d(0.0, 0.0).angle, 0.01)
        assertEquals(0.0, Vector2d(1.0, 0.0).angle, 0.01)
        assertEquals(PI, Vector2d(-1.0, 0.0).angle, 0.01)
        assertEquals(PI/2, Vector2d(0.0, 1.0).angle, 0.01)
        assertEquals(-PI/2, Vector2d(0.0, -1.0).angle, 0.01)
        assertEquals(PI/6, Vector2d(sqrt(3.0), 1.0).angle, 0.01)
        assertEquals(-PI/6, Vector2d(sqrt(3.0), -1.0).angle, 0.01)
    }

    @Test
    fun length_is_correct() {
        assertEquals(0.0, Vector2d(0.0, 0.0).length, 0.01)
        assertEquals(1.0, Vector2d(1.0, 0.0).length, 0.01)
        assertEquals(1.0, Vector2d(-1.0, 0.0).length, 0.01)
        assertEquals(1.0, Vector2d(0.0, 1.0).length, 0.01)
        assertEquals(1.0, Vector2d(0.0, -1.0).length, 0.01)
        assertEquals(5.0, Vector2d(3.0, 4.0).length, 0.01)
    }

    @Test
    fun unary_minus_is_correct() {
        assertEquals(Vector2d(-3.0, -2.0), -Vector2d(3.0, 2.0))
        assertEquals(Vector2d(-3.0, 2.0), -Vector2d(3.0, -2.0))
        assertEquals(Vector2d(3.0, -2.0), -Vector2d(-3.0, 2.0))
        assertEquals(Vector2d(3.0, 2.0), -Vector2d(-3.0, -2.0))
    }

    @Test
    fun unary_plus_is_correct() {
        assertEquals(Vector2d(3.0, 2.0), +Vector2d(3.0, 2.0))
        assertEquals(Vector2d(3.0, -2.0), +Vector2d(3.0, -2.0))
        assertEquals(Vector2d(-3.0, 2.0), +Vector2d(-3.0, 2.0))
        assertEquals(Vector2d(-3.0, -2.0), +Vector2d(-3.0, -2.0))
    }

    @Test
    fun plus_is_correct() {
        assertEquals(Vector2d(5.0, 6.0), Vector2d(3.0, 2.0) + Vector2d(2.0, 4.0))
        assertEquals(Vector2d(-1.0, 2.0), Vector2d(-3.0, -2.0) + Vector2d(2.0, 4.0))
        assertEquals(Vector2d(-5.0, -6.0), Vector2d(-3.0, -2.0) + Vector2d(-2.0, -4.0))
        assertEquals(Vector2d(1.0, -2.0), Vector2d(3.0, 2.0) + Vector2d(-2.0, -4.0))
    }

    @Test
    fun minus_is_correct() {
        assertEquals(Vector2d(1.0, -2.0), Vector2d(3.0, 2.0) - Vector2d(2.0, 4.0))
        assertEquals(Vector2d(-5.0, -6.0), Vector2d(-3.0, -2.0) - Vector2d(2.0, 4.0))
        assertEquals(Vector2d(-1.0, 2.0), Vector2d(-3.0, -2.0) - Vector2d(-2.0, -4.0))
        assertEquals(Vector2d(5.0, 6.0), Vector2d(3.0, 2.0) - Vector2d(-2.0, -4.0))
    }
}
