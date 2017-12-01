package jhlblue.tistory.com.imagedragzoomk

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class ZoomWithRect : AppCompatActivity() {
    private var originalImage: Any? = null
    private var zoomedImage: Any? = null
    private var handler: Handler = Handler()
    private var imageDragZoomK: Any? = null
    private var touchView : Any? = null
    private var viewGroup : Any? = null
    private var times:Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zoom_with_rect)
        originalImage = findViewById(R.id.origin_image)
        zoomedImage = findViewById(R.id.zoomed_image)

        viewGroup = findViewById(R.id.linear_layout_parent)
        touchView = LayoutInflater.from(this).inflate(R.layout.view_touch, null)
        (viewGroup as ViewGroup).addView(touchView as View)
        (touchView as View).bringToFront()

        imageDragZoomK = ImageDragZoomK(this, originalImage = originalImage as ImageView, cropImage = zoomedImage as ImageView, handler = handler, touchView = (touchView as View))
        (imageDragZoomK as ImageDragZoomK).setCropImageTimes(times)


        (originalImage as ImageView).setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View, event: MotionEvent): Boolean {
                if (event.action == MotionEvent.ACTION_MOVE || event.action == MotionEvent.ACTION_DOWN) {
                    (imageDragZoomK as ImageDragZoomK).setTouchEvent(event)
                    view.parent.requestDisallowInterceptTouchEvent(true)
                }
                return true
            }
        })
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        (imageDragZoomK as ImageDragZoomK).setCropImageSize((zoomedImage as ImageView).width, (zoomedImage as ImageView).height, times)
    }
}