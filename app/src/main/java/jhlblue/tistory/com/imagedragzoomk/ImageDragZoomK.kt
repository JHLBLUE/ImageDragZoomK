package jhlblue.tistory.com.imagedragzoomk

import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout


/**
 * Created by jhlblue on 2017. 11. 20..
 */

class ImageDragZoomK(context: Context, handler: Handler, originalImage: ImageView, cropImage: ImageView) {

    private val DEFAULT_IMAGE_TIMES: Int = 2
    private var cropImageWidth: Int = 0
    private var cropImageHeight: Int = 0
    private var xPosition: Int = 0
    private var yPosition: Int = 0
    private var times: Int = 0

    private var originalImage: ImageView = originalImage
    private var cropImage: ImageView = cropImage

    private var context: Context = context
    private var handler: Handler = handler

    private var newX: Int = 0
    private var newY: Int = 0
    private var newWidth: Int = 0
    private var newHeight: Int = 0

    private var bitmap: Any? = null
    private var touchView :Any? = null

    constructor(context: Context, handler: Handler, originalImage: ImageView, cropImage: ImageView, touchView: View) : this(context,handler,originalImage,cropImage){
        Log.w("constructor called", "from ZoomWithRect")
        this.touchView = touchView
    }

    fun setCropImageSize(width: Int, height: Int, times:Int) {
        this.cropImageWidth = width / times
        this.cropImageHeight = height / times
    }

    fun setCropImageTimes(times: Int) {
        this.times = times
    }

    fun setTouchEvent(event: MotionEvent) {
        xPosition = event.getX().toInt()
        yPosition = event.getY().toInt()
        handler.post(cropRunnable)
    }

    private var cropRunnable = Runnable {
        if (cropImageWidth > 0 && cropImageHeight > 0) {
            originalImage.isDrawingCacheEnabled = true
            bitmap = originalImage.getDrawingCache()

            var imageWidth = (bitmap as Bitmap).width
            var imageHeight = (bitmap as Bitmap).height

            newX = xPosition - (cropImageWidth / times)
            newY = yPosition - (cropImageHeight / times)

            if (newX <= 1)
                newX = 1
            if (newY <= 1)
                newY = 1

            if (newX >= imageWidth - (cropImageWidth * 2 / times))
                newX = imageWidth - (cropImageWidth * 2 / times)
            if (newY >= imageHeight - (cropImageHeight * 2 / times))
                newY = imageHeight - (cropImageHeight * 2 / times)

            newWidth = cropImageWidth * 2 / times
            newHeight = cropImageWidth * 2 / times

            cropImage.setImageBitmap(Bitmap.createBitmap(bitmap as Bitmap, newX, newY, newWidth, newHeight))
            if(this.touchView != null){
                showRect()
            }
        }

    }
    fun showRect(){
        var layoutParams = LinearLayout.LayoutParams(newWidth, newHeight);
        var newCropX = newX + originalImage.x
        var newCropY = newY + originalImage.y

        (touchView as View).x = newCropX
        (touchView as View).y = newCropY
        (touchView as View).layoutParams = layoutParams
        (touchView as View).bringToFront()

    }

}