package jhlblue.tistory.com.imagedragzoomk

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    private var originalImage: Any? = null
    private var zoomedImage: Any? = null
    private var handler: Handler = Handler()
    private var imageDragZoomK: Any? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        originalImage = findViewById(R.id.origin_image)
        zoomedImage = findViewById(R.id.zoomed_image)

        imageDragZoomK = ImageDragZoomK(this, originalImage = originalImage as ImageView, cropImage = zoomedImage as ImageView, handler = handler)
        (imageDragZoomK as ImageDragZoomK).setCropImageTimes(1)


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
        (imageDragZoomK as ImageDragZoomK).setCropImageSize((zoomedImage as ImageView).width, (zoomedImage as ImageView).height)
    }
}