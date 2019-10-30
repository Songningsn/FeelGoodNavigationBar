package com.songforning.studybezier.animation

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.CycleInterpolator
import android.view.animation.Interpolator
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

//上下往复运动
class CosMove : BaseAnimation{
    constructor(_view: View, config:Config) : super(_view) {
        _animation = ObjectAnimator.ofFloat(_view,"translationX",config.targetX)
        _animation?.addUpdateListener {
            val time = it.animatedFraction
            val y = config.distanceY - cos(time * 2 * PI) * config.distanceY
            _view.translationY = y.toFloat()
        }
        _animation?.duration = config.duration
    }

    class Config {
        var targetX = 0f
        var distanceY = 0f;
        var duration = 1000L

        constructor(targetX: Float, distanceY:Float, duration: Long) {
            this.targetX = targetX
            this.distanceY = distanceY
            this.duration = duration
        }
    }

}