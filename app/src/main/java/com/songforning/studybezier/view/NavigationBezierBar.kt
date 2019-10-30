package com.songforning.studybezier.view

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.songforning.studybezier.animation.BezierAnimation
import com.songforning.studybezier.animation.CosMove
import com.songforning.studybezier.animation.Rotate
import com.songforning.studybezier.util.dp2px
import kotlin.math.roundToInt

//第一步 展示底部几个按钮
//第二步 按钮点击旋转平移
//第三步 背景贝塞尔曲线及移动
//第四步 按钮上下移动并且根据上下移动的值调整贝塞尔曲线的参数
class NavigationBezierBar : ViewGroup {
    val BTN_START_INDEX = 2

    var backgroundView:BezierBackgroundView? = null
    var ballImageView:BallImageView? = null

    var rotateAnimation:Rotate? = null

    val iconResList = ArrayList<Int>()

    var currentIndex = 0
    var itemWidth = 0f
    val DURATION = 500L


    constructor(context:Context) : super(context) {
        init(null)
    }
    constructor(context:Context, attributeSet: AttributeSet ) : super(context, attributeSet){
        init(attributeSet)
    }

    fun init(attributeSet: AttributeSet?){


        //构造ballImageView
        ballImageView = BallImageView(context)
        rotateAnimation = Rotate(ballImageView!!,Rotate.Config(360.0f,DURATION))
        addView(ballImageView,0)

        //设置背景图
        backgroundView = BezierBackgroundView(context)
        addView(backgroundView,1)
    }

    fun addItemResource(item : Int) {
        val itemView =  ImageView(context)
        itemView.setImageResource(item)
        val layoutParams = LayoutParams(dp2px(context,30), dp2px(context,30))
        itemView.layoutParams = layoutParams

        itemView.setOnClickListener {
            select(indexOfChild(it) - BTN_START_INDEX)
        }
        iconResList.add(item)
        addView(itemView,childCount)
        backgroundView?.addItem(itemView)
    }

    private fun select(index:Int) {
        currentIndex = index
        update()
    }

    private fun update(){
        CosMove(ballImageView!!, CosMove.Config(currentIndex * itemWidth , (height- ballImageView!!.height).toFloat(),DURATION)).startAnimation()
        rotateAnimation?.startAnimation()
        BezierAnimation(backgroundView!!, BezierAnimation.Config(currentIndex.toFloat(),DURATION))
            .addUpdateListener(ValueAnimator.AnimatorUpdateListener {
                val ballY = ballImageView?.translationY
                backgroundView!!.setCurrentTransformY(ballY!!.toFloat())
            })
            .startAnimation()
        ballImageView?.setImageResourse(iconResList[currentIndex])
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(widthMeasureSpec,heightMeasureSpec)

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if(childCount < 1)return
        val wrapperWidth = (r - l) / (childCount - BTN_START_INDEX)
        itemWidth = wrapperWidth.toFloat()
        val wrapperHeight = b - t

        //定位背景
        backgroundView?.layout(0, dp2px(context,15),r,b-t)
        //定位球型图标
        if(ballImageView != null && currentIndex < iconResList.size){
            val marginH = (wrapperWidth - ballImageView!!.measuredWidth) / 2
            val right = marginH + ballImageView!!.measuredWidth
            ballImageView?.layout(marginH,0,right,ballImageView!!.measuredHeight)
        }


        for(i in BTN_START_INDEX until childCount) {
            val item = getChildAt(i)
            val itemWidth = item.measuredWidth
            val itemHeight = item.measuredHeight
            val marginH = (wrapperWidth - itemWidth) / 2
            val marginV = (wrapperHeight - itemHeight) / 2
            val itemLeft = l + (wrapperWidth * (i- BTN_START_INDEX )) + marginH
            val itemRight = itemLeft + itemWidth
            val itemTop = marginV
            val itemBottom = itemTop + itemHeight
            item.layout( itemLeft ,itemTop,itemRight,itemBottom)
        }
        update()
    }

    class BezierBackgroundView : View {
        constructor(context: Context?) : super(context) {init()}
        constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {init()}
        val itemList= ArrayList<View>()

        val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        val mPath = Path()
        private var currentTransformX:Float = 0f
        private var currentTransformY:Float = 0f


        fun init(){
            mPaint.color = Color.parseColor("#FFFFFF")
            mPath.fillType = Path.FillType.WINDING
        }

        fun addItem (item: View) {
            itemList.add(item)
        }


        fun setCurrentTransformX(value:Float){
            currentTransformX = value
            if(currentTransformX.roundToInt() < itemList.size){
                for(i in 0 until itemList.size) {
                    if(i == currentTransformX.roundToInt())itemList[i].visibility = INVISIBLE
                    else itemList[i].visibility = VISIBLE
                }
            }
            invalidate()
        }

        fun getCurrentTransformX() : Float {
            return currentTransformX
        }

        fun setCurrentTransformY(value:Float) {
            currentTransformY = value
            invalidate()
        }

        fun getCurrentTransformY():Float {
            return currentTransformY
        }


        @SuppressLint("DrawAllocation")
        override fun onDraw(canvas: Canvas?) {
            val itemWidth = (width / 4).toFloat()
            val itemLeft = (itemWidth * currentTransformX)
            val itemHeight = height - (currentTransformY / 2f)


            mPath.reset()
            mPath.moveTo(0f,0f)
            mPath.lineTo(itemLeft, 0f)

            //左边曲线
            mPath.quadTo(itemLeft + itemWidth/ 6f, itemHeight / 24f,
                itemLeft + itemWidth / 4f, itemHeight / 2f)
            //中间曲线
            mPath.cubicTo(itemLeft + itemWidth/6f * 2f, itemHeight / 6f * 5f,
                        itemLeft + itemWidth / 6f * 4f, itemHeight / 6f * 5f,
                        itemLeft + itemWidth / 4f * 3f, itemHeight / 2f)
            //右边曲线
            mPath.quadTo(itemLeft + itemWidth/ 6f * 5f, itemHeight / 24f,
                itemLeft + itemWidth, 0f)

            mPath.lineTo(width.toFloat(), 0f)
            mPath.lineTo(width.toFloat(), height.toFloat())
            mPath.lineTo(0f, height.toFloat())
            mPath.close()
            canvas?.drawPath(mPath,mPaint)
        }


    }

}