package com.songforning.studybezier

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val resList = arrayOf(R.mipmap.p_1,R.mipmap.p_2,R.mipmap.p_3,R.mipmap.p_4)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navigation = ng_bar
        for(i in 0 until 4) {
            navigation.addItemResource(resList[i])
        }
        navigation.requestLayout()

    }
}
