package eu.malycha.android.game.asteroids

import android.graphics.Canvas
import android.view.SurfaceHolder
import java.lang.Exception

class GameThread(private val surfaceHolder: SurfaceHolder, private val gameView: GameView) : Thread() {
    private var running: Boolean = false

    private val targetFPS = 50

    fun setRunning(isRunning: Boolean) {
        this.running = isRunning
    }

    override fun run() {
        runAtFps({ tick() }, targetFPS)
    }

    private fun tick() {
        val canvas = lockCanvas()
        synchronized(surfaceHolder) {
            this.gameView.draw(canvas!!)
        }
        unlockCanvas(canvas)
    }

    private fun runAtFps(lmbd: () -> Unit, fps: Int) {
        var startTime: Long
        var timeMillis: Long
        var waitTime: Long
        val targetTime = (1000 / fps).toLong()

        while (running) {
            startTime = System.nanoTime()

            try {
                lmbd()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000
            waitTime = targetTime - timeMillis

            sleepSafe(waitTime)
        }
    }

    private fun sleepSafe(waitTime: Long) {
        try {
            if (waitTime > 0) sleep(waitTime)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun lockCanvas(): Canvas? {
        try {
            return this.surfaceHolder.lockCanvas()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private fun unlockCanvas(canvas: Canvas?) {
        if (canvas != null) {
            try {
                surfaceHolder.unlockCanvasAndPost(canvas)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}