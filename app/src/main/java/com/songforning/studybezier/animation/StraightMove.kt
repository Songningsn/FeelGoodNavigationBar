package com.songforning.studybezier.animation

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.View

class StraightMove : BaseAnimation {
    @SuppressLint("ObjectAnimatorBinding")
    constructor(_view: View, config:Config ) : super(_view){
        _animation = if(config.orientation == Config.ORIENTATION_X){
            ObjectAnimator.ofFloat(_view,"translationX", config.target)
        }else {
            ObjectAnimator.ofFloat(_view,"translationY", config.target)
        }
        _animation?.duration = config.duration
    }

    class Config {
        companion object {
            const val ORIENTATION_X = 0
            const val ORIENTATION_Y = 1
        }


        var orientation:Int = ORIENTATION_X
        var duration:Long = 1000L
        var target:Float = 0f

        constructor(orientation: Int, target: Float, duration: Long) {
            this.orientation = orientation
            this.duration = duration
            this.target = target
        }
    }
}