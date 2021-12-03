package com.alhussein.videotimeline.ui.fragment

import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.alhussein.videotimeline.App
import com.alhussein.videotimeline.BuildConfig
import com.alhussein.videotimeline.R
import com.alhussein.videotimeline.download.DownloadResult
import com.alhussein.videotimeline.download.downloadFile
import com.alhussein.videotimeline.model.PostModel
import com.alhussein.videotimeline.utils.Constants
import com.alhussein.videotimeline.viewmodel.TimeLineViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory
import com.google.android.exoplayer2.util.Util
import io.ktor.client.*
import io.ktor.client.engine.android.*
import kotlinx.android.synthetic.main.layout_post_view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


class PostFragment : Fragment(R.layout.fragment_post) {
    private var postUrl: String? = null
    private var postsDataModel: PostModel? = null
    private var simplePlayer: SimpleExoPlayer? = null
    private val simpleCache = App.simpleCache

    private var cacheDataSourceFactory: CacheDataSourceFactory? = null

    private var toPlayVideoPosition: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postsDataModel = arguments?.getParcelable(Constants.KEY_POST_DATA)
        setData()
    }

    private fun getPlayer(): SimpleExoPlayer? {
        if (simplePlayer == null) {
            prepareVideoPlayer()
        }
        return simplePlayer
    }

    private fun prepareVideoPlayer() {
        simplePlayer = ExoPlayerFactory.newSimpleInstance(context)
        cacheDataSourceFactory = CacheDataSourceFactory(
            simpleCache,
            DefaultHttpDataSourceFactory(
                Util.getUserAgent(
                    context,
                    "exo"
                )
            )
        )
    }

    private fun setData() {


        val simplePlayer = getPlayer()
        player_view_post.player = simplePlayer

        postUrl = postsDataModel?.media_base_url + postsDataModel?.recording_details?.recording_url
        postUrl?.let { prepareMedia(it) }



        image_view_option_share.setOnClickListener {

            val bundle = bundleOf("post_url" to postUrl)

            it.findNavController().navigate(R.id.action_timeLineFragment_to_trimFragment,bundle)

//                downloadWithKtor(postUrl!!)
        }


    }


    override fun onPause() {
        pauseVideo()
        super.onPause()
    }

    override fun onResume() {
        restartVideo()
        super.onResume()
    }

    override fun onDestroy() {
        releasePlayer()
        super.onDestroy()
    }

    private fun restartVideo() {
        if (simplePlayer == null) {
            postUrl?.let { prepareMedia(it) }
        } else {
            simplePlayer?.seekToDefaultPosition()
            simplePlayer?.playWhenReady = true
        }
    }

    private fun pauseVideo() {
        simplePlayer?.playWhenReady = false
    }

    private fun releasePlayer() {
        simplePlayer?.stop(true)
        simplePlayer?.release()
    }

    private fun prepareMedia(linkUrl: String) {
//        logError("prepareMedia linkUrl: $linkUrl")

        val uri = Uri.parse(linkUrl)

        val mediaSource =
            ProgressiveMediaSource.Factory(cacheDataSourceFactory).createMediaSource(uri)

        simplePlayer?.prepare(mediaSource, true, true)
        simplePlayer?.repeatMode = Player.REPEAT_MODE_ONE
        simplePlayer?.playWhenReady = true
        simplePlayer?.addListener(playerCallback)

        toPlayVideoPosition = -1
    }

    private val playerCallback: Player.EventListener? = object : Player.EventListener {
        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
//            logError("onPlayerStateChanged playbackState: $playbackState")
        }

        override fun onPlayerError(error: com.google.android.exoplayer2.ExoPlaybackException?) {
            super.onPlayerError(error)
        }
    }


    companion object {

        @JvmStatic
        fun newInstance(postModel: PostModel) = PostFragment().apply {
            arguments = Bundle().apply {
                putParcelable(Constants.KEY_POST_DATA, postModel)
            }
        }
    }
}