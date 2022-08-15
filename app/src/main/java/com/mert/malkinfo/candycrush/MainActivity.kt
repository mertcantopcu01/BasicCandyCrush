package com.mert.malkinfo.candycrush

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import com.mert.malkinfo.candycrush.uiltel.OnSwipeListener
import java.util.Arrays.asList

class MainActivity : AppCompatActivity() {

    var shapes = intArrayOf(
        R.drawable.mavi,
        R.drawable.yesil,
        R.drawable.turuncu,
        R.drawable.mor,
        R.drawable.kirmizi,
        R.drawable.sari,
    )
    var widthOfBlock : Int = 0
    var noOfBlock :Int = 8
    var widthOfScreen :Int = 0
    lateinit var shape :ArrayList<ImageView>
    var shapeToBeDragged :Int = 0
    var shapeToBeReplaced :Int = 0
    var noShape : Int = R.drawable.transparent

    lateinit var mHandler :Handler
    private  lateinit var scoreResult :TextView
    var score = 0
    var interval = 100L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scoreResult = findViewById(R.id.score)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        widthOfScreen = displayMetrics.widthPixels

        var heightOfScreen = displayMetrics.heightPixels

        widthOfBlock = widthOfScreen / noOfBlock

        shape = ArrayList()
        createBoard()

        for (imageView in shape){
            imageView.setOnTouchListener(object :OnSwipeListener(this){
                override fun onSwipeRight() {
                    super.onSwipeRight()
                    shapeToBeDragged = imageView.id
                    shapeToBeReplaced = shapeToBeDragged+ 1
                    shapeInterChacge()
                }

                override fun onSwipeLeft() {
                    super.onSwipeLeft()
                    shapeToBeDragged = imageView.id
                    shapeToBeReplaced = shapeToBeDragged - 1
                    shapeInterChacge()
                }

                override fun onSwipeTop() {
                    super.onSwipeTop()
                    shapeToBeDragged = imageView.id
                    shapeToBeReplaced = shapeToBeDragged - noOfBlock
                    shapeInterChacge()
                }

                override fun onSwipeBottom() {
                    super.onSwipeBottom()
                    shapeToBeDragged = imageView.id
                    shapeToBeReplaced = shapeToBeDragged + noOfBlock
                    shapeInterChacge()
                }
            })
        }
        mHandler = Handler()
        startRepat()
    }



    private fun shapeInterChacge() {
        var background : Int = shape.get(shapeToBeReplaced).tag as Int
        var background1 : Int = shape.get(shapeToBeDragged).tag as Int

        shape.get(shapeToBeDragged).setImageResource(background)
        shape.get(shapeToBeReplaced).setImageResource(background1)
        shape.get(shapeToBeDragged).setTag(background)
        shape.get(shapeToBeReplaced).setTag(background1)
    }
    private fun checkRowForThree(){
        for (i in 0..61){
            var chosedShape = shape.get(i).tag
            var isBlank : Boolean = shape.get(i).tag == noShape
            val notValid = arrayOf(6,7,14,15,22,23,30,31,38,39,46,47,54,55)
            val list = asList(*notValid)
            if (!list.contains(i)){
                var x = i

                if (shape.get(x++).tag as Int == chosedShape
                    &&!isBlank && shape.get(x++).tag as Int == chosedShape
                    &&shape.get(x).tag as Int == chosedShape
                ){
                    score = score +3
                    scoreResult.text = "$score"
                    shape.get(x).setImageResource(noShape)
                    shape.get(x).setTag(noShape)
                    x--
                    shape.get(x).setImageResource(noShape)
                    shape.get(x).setTag(noShape)
                    x--
                    shape.get(x).setImageResource(noShape)
                    shape.get(x).setTag(noShape)

                }
            }
        }
        moveDownShapes()
    }


    private fun checkColumnForThree(){
        for (i in 0..47) {
            var chosedShape = shape.get(i).tag
            var isBlank: Boolean = shape.get(i).tag == noShape

                var x = i

                if (shape.get(x).tag as Int == chosedShape
                    && !isBlank && shape.get(x+noOfBlock).tag as Int == chosedShape
                    && shape.get(x+2*noOfBlock).tag as Int == chosedShape
                ) {
                    score = score + 3
                    scoreResult.text = "$score"
                    shape.get(x).setImageResource(noShape)
                    shape.get(x).setTag(noShape)
                    x = x + noOfBlock
                    shape.get(x).setImageResource(noShape)
                    shape.get(x).setTag(noShape)
                    x = x + noOfBlock
                    shape.get(x).setImageResource(noShape)
                    shape.get(x).setTag(noShape)


            }

        }

        moveDownShapes()

    }

    private fun moveDownShapes() {
        val firstRow = arrayOf(1,2,3,4,5,6,7)
        val list = asList(*firstRow)
        for ( i in 55 downTo 0){
            if (shape.get(i+noOfBlock).tag as Int == noShape){
                shape.get(i+noOfBlock).setImageResource(shape.get(i).tag as Int)
                shape.get(i+noOfBlock).setTag(shape.get(i).tag as Int)

                shape.get(i).setImageResource(noShape)
                shape.get(i).setTag(noShape)
                if (list.contains(i)&& shape.get(i).tag == noShape){
                    var randomColor : Int = Math.abs(Math.random() * shapes.size).toInt()
                    shape.get(i).setImageResource(shapes[randomColor])
                    shape.get(i).setTag(shapes[randomColor])
                }

            }
        }
        for (i in 0..7){
            if (shape.get(i).tag as Int == noShape){
                var randomColor : Int = Math.abs(Math.random() * shapes.size).toInt()
                shape.get(i).setImageResource(shapes[randomColor])
                shape.get(i).setTag(shapes[randomColor])

            }
        }
    }
    val repeatChecker :Runnable = object :Runnable{
        override fun run() {
            try{
                checkColumnForThree()
                checkRowForThree()
                moveDownShapes()

            }
            finally {
                mHandler.postDelayed(this,interval)
            }
        }

    }

    private fun startRepat() {

        repeatChecker.run()

    }
    private fun createBoard() {

        val gridLayout = findViewById<GridLayout>(R.id.board)
        gridLayout.rowCount = noOfBlock
        gridLayout.columnCount = noOfBlock
        gridLayout.layoutParams.width = widthOfScreen
        gridLayout.layoutParams.height = widthOfScreen

        for ( i in 0 until noOfBlock * noOfBlock){
            val imageView = ImageView(this )
            imageView.id = i
            imageView.layoutParams = android.view.ViewGroup.LayoutParams(widthOfBlock,widthOfBlock)

            imageView.maxHeight = widthOfBlock
            imageView.maxWidth = widthOfBlock

            var random :Int = Math.floor(Math.random()*shapes.size).toInt()

            imageView.setImageResource(shapes[random])
            imageView.setTag(shapes[random])

            shape.add(imageView)
            gridLayout.addView(imageView)

        }

    }


}