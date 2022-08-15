package com.mert.malkinfo.candycrush.uiltel

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

open class OnSwipeListener(contecxt : Context?) : View.OnTouchListener
{
    var gestureDelector : GestureDetector
    override fun onTouch(p0: View?, motionEvent:  MotionEvent?): Boolean {
        return gestureDelector.onTouchEvent(motionEvent)
    }
    inner class GestureListiner : GestureDetector.SimpleOnGestureListener(){

        val SWIPE_THRESOLD = 100
        val SWIPE_VELOCITY_THRESOLD = 100
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var result = false
            val yDiff = e2.y -e1.y
            val xDiff = e2.x -e1.x
            //Yukarı ya da aşşağı ve sağa ya da sola hareket
            if (Math.abs(xDiff)> Math.abs(yDiff)){

                if(Math.abs(xDiff)>SWIPE_THRESOLD &&
                    Math.abs(velocityX)>SWIPE_VELOCITY_THRESOLD){
                    if(xDiff > 0 ){
                        onSwipeRight()
                    }
                    else{
                        onSwipeLeft()
                    }
                    result = true
                }

            }
            else if (Math.abs(yDiff)>SWIPE_THRESOLD &&
                Math.abs(velocityY)>SWIPE_VELOCITY_THRESOLD){
                if(yDiff > 0 ){

                    onSwipeBottom()
                }
                else{
                    onSwipeTop()

                }
                result = true
            }
            return result
        }
    }

    open fun onSwipeBottom() {}

    open fun onSwipeTop() {}

    open fun onSwipeLeft() {}

    open fun onSwipeRight() {}

    init {
        gestureDelector = GestureDetector(contecxt, GestureListiner())
    }
}