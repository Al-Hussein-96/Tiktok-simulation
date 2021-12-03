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
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
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

    private fun downloadWithKtor() {
        val file = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "cache.mp4") as File
        val uri = let {
            FileProvider.getUriForFile(it, "${BuildConfig.APPLICATION_ID}.provider", file)
        }
        downloadFile(this, url, uri)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                downloadFile(this, url, uri)
            }
        }
    }


    private fun downloadFile(context: Context, url: String, file: Uri) {
        val ktor = HttpClient(Android)


        context.contentResolver.openOutputStream(file)?.let { outputStream ->
            CoroutineScope(Dispatchers.IO).launch {
                ktor.downloadFile(outputStream, url).collect {
                    withContext(Dispatchers.Main) {
                        when (it) {
                            is DownloadResult.Success -> {
                                viewFile(file)

                            }

                            is DownloadResult.Error -> {

                            }
                            is DownloadResult.Progress -> {
                                println("Ktor Download: $it")
                            }
                        }
                    }

                }
            }
        }
    }

    private fun viewFile(uri: Uri) {
        let {
            val shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, uri)
                type = "video/*"
            }
            startActivity(Intent.createChooser(shareIntent, "sharing file by whatsapp"))
        }
    }

    private fun downloadVideo() {
        val url =
            "https://shaadoow.net/recording/video/xSUut9RUy9E8sciseKvVueGTUNrnb1bszRasqN92.mp4"

        val request: DownloadManager.Request = DownloadManager.Request(Uri.parse(url))
        request.setDescription("A zip package with some files")
        request.setTitle("Zip package")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//            request.allowScanningByMediaScanner()

//        request.setDestinationInExternalFilesDir(this,cacheDir.absolutePath,"test.mp4")
        request.setDestinationInExternalFilesDir(
            this,
            this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath, "test213.mp4"
        )





        println(
            "MainActivity: " +
                    "download folder>>>>" + this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath
        )

        // get download service and enqueue file

        // get download service and enqueue file
        val manager: DownloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager

        manager.enqueue(request)


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



