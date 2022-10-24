package eu.malycha.android.game.asteroids

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.lang.Exception

class GameView(context: Context, attributes: AttributeSet): SurfaceView(context, attributes), SurfaceHolder.Callback {

    private val thread: GameThread
    private var player: Player? = null
    private var crosshair: Crosshair? = null

    private var touched: Boolean = false
    private var touched_x: Int = 0
    private var touched_y: Int = 0

    init {
        setZOrderOnTop(true)
        holder.addCallback(this)
        holder.setFormat(PixelFormat.TRANSPARENT)
        thread = GameThread(holder, this)
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        crosshair = Crosshair(BitmapFactory.decodeResource(resources, R.drawable.crosshair_20220718_234650))
        player = Player(BitmapFactory.decodeResource(resources, R.drawable.ship_20220717_110054))
        player!!.crosshair = crosshair
        thread.setRunning(true)
        thread.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        var retry = true
        while (retry) {
            try {
                thread.setRunning(false)
                thread.join()
                retry = false
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun update() {
        player!!.update()
        if (touched) {
            crosshair!!.updateTouch(touched_x, touched_y)
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawColor(0, PorterDuff.Mode.CLEAR)
        player!!.draw(canvas)
        crosshair!!.draw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        touched_x = event.x.toInt()
        touched_y = event.y.toInt()

        val action = event.action
        when (action) {
            MotionEvent.ACTION_DOWN -> touched = true
            MotionEvent.ACTION_MOVE -> touched = true
            MotionEvent.ACTION_UP -> touched = false
            MotionEvent.ACTION_CANCEL -> touched = false
            MotionEvent.ACTION_OUTSIDE -> touched = false
        }
        if (touched) {
            Log.i("GameView", "Touched x:${touched_x}, y:${touched_y}")
        }
        return true
    }
}
