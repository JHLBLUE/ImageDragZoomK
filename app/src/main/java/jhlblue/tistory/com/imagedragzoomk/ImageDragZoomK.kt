package jhlblue.tistory.com.imagedragzoomk

import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView


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
    private var touchView: Any? = null

    private var newX: Int = 0
    private var newY: Int = 0
    private var newWidth: Int = 0
    private var newHeight: Int = 0

    private var bitmap: Any? = null

    fun setCropImageSize(width: Int, height: Int) {
        this.cropImageWidth = width / 2
        this.cropImageHeight = height / 2
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
            if (touchView == null) {
                touchView = LayoutInflater.from(context).inflate(R.layout.view_touch, null);
                (originalImage.parent as ViewGroup).addView((touchView as View))
            }
            (touchView as View).translationX = (xPosition - cropImageWidth / times).toFloat()
            (touchView as View).translationY = (yPosition - cropImageHeight / times).toFloat()
            (touchView as View).invalidate()
            (touchView as View).bringToFront()

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

        }
    }
}