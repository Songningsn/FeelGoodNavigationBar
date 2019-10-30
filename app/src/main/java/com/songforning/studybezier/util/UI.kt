package com.songforning.studybezier.util

import android.content.Context

fun dp2px(context:Context, dp:Int) : Int{
    val scale = context.resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}
