package com.alhussein.videotimeline.ui.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import com.alhussein.videotimeline.BuildConfig
import com.alhussein.videotimeline.R
import com.alhussein.videotimeline.download.DownloadResult
import com.alhussein.videotimeline.download.downloadFile
import io.ktor.client.*
import io.ktor.client.engine.android.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import kotlinx.android.synthetic.main.fragment_trim.*


class TrimFragment : BaseFragment(R.layout.fragment_trim) {
    private var fileToShare: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val postUrl = arguments?.getString("post_url")
        println("Post Url: $postUrl")


        if (postUrl != null) {
            downloadWithKtor(postUrl)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_share.setOnClickListener {
            fileToShare?.let { it1 -> shareVideo(it1) }
        }
    }

    private fun shareVideo(uri: Uri) {
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            type = "video/*"
        }
        startActivity(Intent.createChooser(shareIntent, "sharing file by whatsapp"))
    }

    private fun downloadWithKtor(url: String) {
        val file =
            File(context?.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "cache.mp4") as File
        val uri = let {
            FileProvider.getUriForFile(
                requireContext(),
                "${BuildConfig.APPLICATION_ID}.provider",
                file
            )
        }
        context?.let { downloadFile(it, url, uri) }
    }


    private fun downloadFile(context: Context, url: String, file: Uri) {
        val ktor = HttpClient(Android)


        context.contentResolver.openOutputStream(file)?.let { outputStream ->
            CoroutineScope(Dispatchers.IO).launch {
                ktor.downloadFile(outputStream, url).collect {
                    withContext(Dispatchers.Main) {
                        when (it) {
                            is DownloadResult.Success -> {
                                fileToShare.apply {
                                    fileToShare = file
                                }
                                viewFile(file)
                            }

                            is DownloadResult.Error -> {

                            }
                            is DownloadResult.Progress -> {
                                download_progress.text = "Downloading ${it.progress}"
                            }
                        }
                    }

                }
            }
        }
    }

    private fun viewFile(uri: Uri) {
        let {
            videoView.setVideoURI(uri)
            videoView.start()

        }
    }


    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            TrimFragment()
    }
}