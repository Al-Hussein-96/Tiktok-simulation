package com.alhussein.videotimeline

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.view.WindowManager
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.navigation.findNavController
import com.alhussein.videotimeline.download.DownloadResult
import com.alhussein.videotimeline.download.downloadFile

import dagger.hilt.android.AndroidEntryPoint
import io.ktor.client.*
import io.ktor.client.engine.android.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.io.File


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val url = "https://shaadoow.net/recording/video/xSUut9RUy9E8sciseKvVueGTUNrnb1bszRasqN92.mp4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupContentWindow()

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



