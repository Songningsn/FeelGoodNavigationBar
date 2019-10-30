package com.songforning.studybezier.animation

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View

open class BaseAnimation : AnimationInterface {
    protected var _view:View
    protected var _animation:ObjectAnimator? = null

    constructor(_view: View) {
        this._view = _view
    }


    override fun startAnimation() {
        _animation?.start()
    }

    override fun stopAnimation() {
        _animation?.cancel()
    }

    fun addUpdateListener(listener : ValueAnimator.AnimatorUpdateListener) : BaseAnimation{
        _animation?.addUpdateListener(listener)
        return this
    }

}