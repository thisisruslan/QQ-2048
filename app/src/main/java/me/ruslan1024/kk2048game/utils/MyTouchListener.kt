package me.ruslan1024.kk2048game.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import me.ruslan1024.kk2048game.data.MySide
import kotlin.math.abs


class MyTouchListener(context: Context) : View.OnTouchListener {
    private val gesture = GestureDetector(context, MyGestureListener())
    private var sideListener: ((MySide) -> Unit)? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return gesture.onTouchEvent(event)
    }

    inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        private val MIN_LENGHT = 100

        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onFling(
            oldEvent: MotionEvent,
            newEvent: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var bool = false
            val diffX = oldEvent.x - newEvent.x
            val diffY = oldEvent.y - newEvent.y

            if (abs(diffX) > abs(diffY)) {
                if (abs(diffX) > MIN_LENGHT && oldEvent.x > newEvent.x) {
                    timber("LEFT")
                    sideListener?.invoke(MySide.LEFT)
                    bool = true
                } else if (abs(diffX) > MIN_LENGHT) {
                    timber("RIGHT")
                    sideListener?.invoke(MySide.RIGHT)
                    bool = true
                }
            } else {
                if (abs(diffY) > MIN_LENGHT && oldEvent.y > newEvent.y) {
                    timber("UP")
                    sideListener?.invoke(MySide.UP)
                    bool = true
                } else if (abs(diffY) > MIN_LENGHT) {
                    timber("DOWN")
                    sideListener?.invoke(MySide.DOWN)
                    bool = true
                }
                // DOWN, UP
            }
            return bool
        }
    }

    fun setSideListener(f: (MySide) -> Unit) {
        sideListener = f
    }
}