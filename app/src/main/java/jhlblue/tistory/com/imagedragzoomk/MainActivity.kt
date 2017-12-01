package jhlblue.tistory.com.imagedragzoomk

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private var buttonWithoutRect : Any? = null
    private var buttonWithRect : Any? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonWithoutRect = findViewById(R.id.button_without_rect)
        buttonWithRect = findViewById(R.id.button_with_rect)
        (buttonWithoutRect as Button).setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, ZoomWithoutRect::class.java))
        })

        (buttonWithRect as Button).setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, ZoomWithRect::class.java))
        })

    }
}