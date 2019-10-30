package com.songforning.studybezier.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.songforning.studybezier.R
import kotlinx.android.synthetic.main.item_navigation.view.*

class BallImageView : FrameLayout {
    constructor(context : Context) :super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    fun init (){
        LayoutInflater.from(context).inflate(R.layout.item_navigation,this)
    }
    fun setImageResourse(id : Int) {
        ivIcon.setImageResource(id)
    }

}