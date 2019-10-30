package com.songforning.studybezier.animation

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.View

class BezierAnimation:BaseAnimation {
    @SuppressLint("ObjectAnimatorBinding")
    constructor(_view: View, config:Config) : super(_view) {
        _animation = ObjectAnimator.ofFloat(_view,"currentTransformX", config.target)
        _animation?.addUpdateListener {
            val fraction = it.animatedFraction

        }
        _animation?.duration = config.duration
    }

    class Config {

        var duration:Long = 1000L
        var target:Float = 0f

        constructor(target: Float, duration: Long) {
            this.duration = duration
            this.target = target
        }
    }
}