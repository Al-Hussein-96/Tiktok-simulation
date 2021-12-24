package com.alhussein.videotimeline.ui.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.alhussein.videotimeline.R

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val url = "https://shaadoow.net/recording/video/xSUut9RUy9E8sciseKvVueGTUNrnb1bszRasqN92.mp4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        setupContentWindow()

//        downloadVideo()

//        downloadWithKtor()


    }





    private fun setupContentWindow() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

    fun changeStatusBarColor(@ColorRes barColor: Int) {
        window.apply {
            statusBarColor = getColorFromRes(barColor)
        }
    }

    private fun getColorFromRes(barColor: Int): Int {
        return ContextCompat.getColor(this, barColor)
    }
}



