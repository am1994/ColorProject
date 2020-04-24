package com.devmina.droid_ant.colorit.color_canvas

import android.content.Context
import android.graphics.*
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration


private const val STROKE_WIDTH = 6f //has to be float

class MyCanvasView @JvmOverloads constructor(
    context: Context,
    attrs:AttributeSet? = null,
    defStyleAttr:Int=0) : View(context,attrs,defStyleAttr){

    private lateinit var extraCanvas: Canvas


    private lateinit var  srcBitmap : Bitmap


    private  var image:Int = 0


    fun changeDrawColor(color: Int){
          paint.color = color
    }

    fun changeImages(imageSrc: Int){
        this.image = imageSrc
    }

    fun changeBrushSize(size: Int){
           paint.strokeWidth =  size.toFloat()
    }

    fun clearDrawing(){
       // extraCanvas.restore()

        setDrawingCacheEnabled(false)
        // don't forget that one and the match below,
        // or you just keep getting a duplicate when you save.

        onSizeChanged(width, height, width, height)
        invalidate()
        setDrawingCacheEnabled(true)



    }

//    fun getBitmap(): Bitmap {
//        //this.measure(100, 100);
//        //this.layout(0, 0, 100, 100);
//        /*this.isDrawingCacheEnabled = true
//        this.buildDrawingCache()*/
//       // val bitmap = this.drawToBitmap()
//
//        val bmp = Bitmap.createBitmap(this.drawingCache)
//        this.isDrawingCacheEnabled = false
//
//      /* val bitmap =  this.getDrawingCache();
//        this.setDrawingCacheEnabled(true);*/
//
//        return bmp
//    }

    private val paint= Paint(Paint.DITHER_FLAG).apply {
         color = Color.RED
          //Smooths out edges of what is drawing without effecting shape
        isAntiAlias=true
        //Dithering effects how colors with higher-precision than the device are down
        isDither=true
        style =Paint.Style.STROKE //default: FILL
        strokeJoin=Paint.Join.ROUND //default:MITTER
        strokeCap=Paint.Cap.ROUND //default:BUTT
        strokeWidth = STROKE_WIDTH//default Hairline_width(really thin)
    }

    private var path= Path()
    private val paths = ArrayList<Path>()
    private val undonePaths = ArrayList<Path>()
    private val mBitmapPaint =  Paint(Paint.DITHER_FLAG)
    private var motionTouchEventX=0f
    private var motionTouchEventY=0f

    private var currentX=0f
    private var currentY=0f

    private val touchTolerance=ViewConfiguration.get(context).scaledTouchSlop

    private lateinit var frame:Rect
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        if(::srcBitmap.isInitialized) srcBitmap.recycle()

        srcBitmap = BitmapFactory.decodeResource(resources,image)
        srcBitmap = Bitmap.createScaledBitmap(srcBitmap,width ,height,false)
        extraCanvas = Canvas(srcBitmap)



        // calculate a rectangle frame around the picture
        val inset = 40
        frame = Rect(inset,inset, width - inset, height - inset)



    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(Color.WHITE)
        canvas.drawBitmap(srcBitmap,0f,0f,mBitmapPaint)

        canvas.drawPath(path, paint)



    }


     private fun touchStart(){
         //undonePaths.clear()
         path.reset()
         path.moveTo(motionTouchEventX,motionTouchEventY)
         currentX=motionTouchEventX
         currentY=motionTouchEventY
     }


    private fun touchMove(){
        val dx=Math.abs(motionTouchEventX - currentX)
        val dy=Math.abs(motionTouchEventY - currentY)
        if(dx >= touchTolerance || dy >= touchTolerance){
            //QuadTo add a quadritac bezier from the last point
            //approaching control point(x1,y1) , and ending at (x2 ,y2)
            path.quadTo(currentX,currentY,(motionTouchEventX + currentX) / 2,(motionTouchEventY + currentY) /2)
            currentX=motionTouchEventX
            currentY=motionTouchEventY
            //Draw the path in the extra bitmap to cache it
            extraCanvas.drawPath(path,paint)
        }
        invalidate()
    }
    private fun touchUp(){
        //rest the path so doesn't get drawn
     // paths.add(path)
        path.reset()

      /*  path.lineTo(motionTouchEventX, motionTouchEventY);
        // commit the path to our offscreen
        extraCanvas.drawPath(path, paint);
        // kill this so we don't double draw
        extraCanvas.save()

        paths.add(path);
        path =  Path()*/

    }
    fun onClickUndo() {
        if (paths.size > 0) {
            undonePaths.add(paths.removeAt(paths.size - 1))
            invalidate()
        } else {
            Log.i("canvas","there is a problem")
        }
        //toast the user
    }

 
    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX=event.x
        motionTouchEventY=event.y

        when(event.action){
            MotionEvent.ACTION_DOWN ->touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }
        return true
    }
}