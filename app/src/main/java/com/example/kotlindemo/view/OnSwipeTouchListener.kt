package com.example.kotlindemo.view

import android.annotation.SuppressLint
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

open class OnSwipeTouchListener: View.OnTouchListener {



        private val gestureDetector = GestureDetector(GestureListener())

        fun onTouch(event: MotionEvent): Boolean {
            return gestureDetector.onTouchEvent(event)
        }

        private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

            private val SWIPE_THRESHOLD = 100
            private val SWIPE_VELOCITY_THRESHOLD = 100

            override fun onDown(e: MotionEvent): Boolean {
                return true
            }

            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                onTouch(e)
                return true
            }


            override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
//                val result = false
////                try {
////                    val diffY = e2.y - e1.y
////                    val diffX = e2.x - e1.x
////                    if (Math.abs(diffX) > Math.abs(diffY)) {
////                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
////                            if (diffX > 0) {
////                                onSwipeRight()
////                            } else {
////                                onSwipeLeft()
////                            }
////                        }
////                    } else {
////                        // onTouch(e);
////                    }
////                } catch (exception: Exception) {
////                    exception.printStackTrace()
////                }
////
////                return result


                val miniWidth=120
                val miniSpeed=0
                val distanceRight: Float=e2.x-e1.x
                val distanceLeft: Float=e1.x-e2.x
                val distanceDown: Float=e2.y-e1.y
                val distanceUp: Float=e1.y-e2.y
                if(distanceRight>miniWidth && abs(velocityX) >miniSpeed)
                {
//                    Log.e(tag, "onFling-"+"向右滑动");
                    onSwipeRight()
                }
                else if(distanceLeft>miniWidth && abs(velocityX)>miniSpeed)
                {
//                    Log.e(tag, "onFling-"+"向左滑动");
                    onSwipeLeft()
                }
                else if(distanceDown>miniWidth && abs(velocityX)>miniSpeed)
                {
//                    Log.e(tag, "onFling-"+"向下滑动");
                    onSwipeBottom()
                }
                else if(distanceUp>miniWidth && abs(velocityX)>miniSpeed)
                {
//                    Log.e(tag, "onFling-"+"向上滑动");
                    onSwipeTop()
                }
                return true

            }
        }

        @SuppressLint("ClickableViewAccessibility")
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            return gestureDetector.onTouchEvent(event)
        }

        open fun onSwipeRight() {}

        open fun onSwipeLeft() {}

        open fun onSwipeTop() {}

        open fun onSwipeBottom() {}

}