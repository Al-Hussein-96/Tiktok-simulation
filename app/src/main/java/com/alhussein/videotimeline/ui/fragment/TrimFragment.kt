package com.alhussein.videotimeline.ui.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.core.content.FileProvider
import com.alhussein.videotimeline.BuildConfig
import com.alhussein.videotimeline.R
import com.alhussein.videotimeline.download.DownloadResult
import com.alhussein.videotimeline.download.downloadFile
import com.alhussein.videotimeline.model.PostModel
import com.bumptech.glide.Glide
import io.ktor.client.*
import io.ktor.client.engine.android.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import kotlinx.android.synthetic.main.fragment_trim.*
import java.util.*
import android.app.ProgressDialog
import com.alhussein.videotimeline.utils.RealPath

import com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL

import com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS

import com.arthenica.mobileffmpeg.ExecuteCallback

import com.arthenica.mobileffmpeg.FFmpeg


class TrimFragment : BaseFragment(R.layout.fragment_trim) {
    private var fileToShare: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_share.setOnClickListener {
            fileToShare?.let { it1 -> shareVideo(it1) }
        }
        val post: PostModel? = arguments?.getParcelable<PostModel>("post")

        if (post != null) {
            println("Post Url: ${post.recording_details.recording_url}")

            try {
                downloadWithKtor(post.media_base_url + post.recording_details.recording_url)

            } catch (e: Exception) {

            }

            val coverImgUrl: String = post.media_base_url + post.recording_details.cover_img_url
            Glide
                .with(this)
                .load(coverImgUrl)
                .centerCrop()
                .into(image_view);
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
            File(context?.getExternalFilesDir(Environment.DIRECTORY_MOVIES), "cache.mp4") as File
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
                                button_share.isEnabled = true
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
//            videoView.setVideoURI(uri)
//            videoView.start()
            trimVideo(2,5)
        }
    }

    private fun trimVideo(start: Int, end: Int) {
        try {
            val inFile =
                File(
                    context?.getExternalFilesDir(Environment.DIRECTORY_MOVIES),
                    "cache.mp4"
                ) as File
            val outFile =
                File(
                    context?.getExternalFilesDir(Environment.DIRECTORY_MOVIES),
                    "cache1.mp4"
                ) as File

            // -i movie.mp4 -ss 00:00:03 -t 00:00:08 -async 1 cut.mp4
            var command = ""
            command = "-y " + "-i " + inFile + " -ss " + "00:00:03" +
                    " -to " + "00:00:08" + " -c" + " copy " + outFile
            val executionId = FFmpeg.executeAsync(
                command
            ) { executionId, returnCode ->
                if (returnCode == RETURN_CODE_SUCCESS) {
//                    onCutFinish()
                    println("Hello World")
                } else if (returnCode == RETURN_CODE_CANCEL) {
                } else {
                }
            }
        } catch (e: java.lang.Exception) {
        }
        return
    }


    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            TrimFragment()
    }
}