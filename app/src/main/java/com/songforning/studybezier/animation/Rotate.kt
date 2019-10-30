package com.songforning.studybezier.animation

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.View

class Rotate : BaseAnimation {
    private var _animator:ObjectAnimator? = null

    @SuppressLint("ObjectAnimatorBinding")
    constructor(view: View, config: Config): super(view){
        _view = view
        _animator = ObjectAnimator.ofFloat(_view,"rotation",config.rotation)
        _animator?.duration = config.duration
    }

    override fun startAnimation() {
        _view?.let {
            _animator?.start()
        }
    }

    override fun stopAnimation() {
        _view?.let {
            _animator?.cancel()
        }
    }

    class Config {
        var rotation:Float = 0f
        var duration:Long = 1000L

        constructor(rotation: Float, duration: Long) {
            this.rotation = rotation
            this.duration = duration
        }
    }




}