package com.alhussein.videotimeline.ui.fragment

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.alhussein.videotimeline.App
import com.alhussein.videotimeline.R
import com.alhussein.videotimeline.model.Post
import com.alhussein.videotimeline.utils.Constants
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import kotlinx.android.synthetic.main.layout_post_view.*
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource






class PostFragment : Fragment(R.layout.fragment_post) {
    private var postUrl: String? = null
    private var postsData: Post? = null
    private var exoPlayer: ExoPlayer? = null
    private val simpleCache = App.simpleCache

//    private var cacheDataSourceFactory: CacheDataSourceFactory? = null

    private var toPlayVideoPosition: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postsData = arguments?.getParcelable(Constants.KEY_POST_DATA)
        setData()
    }

    private fun getPlayer(): ExoPlayer? {
        if (exoPlayer == null) {
            prepareVideoPlayer()
        }
        return exoPlayer
    }

    private fun prepareVideoPlayer() {
        exoPlayer = context?.let { ExoPlayer.Builder(it).build() }



//        cacheDataSourceFactory = CacheDataSourceFactory(
//            simpleCache,
//            DefaultHttpDataSourceFactory(
//                Util.getUserAgent(
//                    context,
//                    "exo"
//                )
//            )
//        )
    }

    private fun setData() {


        val simplePlayer = getPlayer()
        player_view_post.player = simplePlayer

        postUrl = postsData?.recording_details?.streaming_hls
        postUrl?.let { prepareMedia(it) }



        image_view_option_share.setOnClickListener {

            val bundle = bundleOf("post" to postsData)

            it.findNavController().navigate(R.id.action_timeLineFragment_to_trimFragment, bundle)

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
        if (exoPlayer == null) {
            postUrl?.let { prepareMedia(it) }
        } else {
            exoPlayer?.seekToDefaultPosition()
            exoPlayer?.playWhenReady = true
        }
    }

    private fun pauseVideo() {
        exoPlayer?.playWhenReady = false
    }

    private fun releasePlayer() {
        exoPlayer?.stop(true)
        exoPlayer?.release()
    }

    private fun prepareMedia(linkUrl: String) {
//        logError("prepareMedia linkUrl: $linkUrl")

        val uri = Uri.parse(linkUrl)


        exoPlayer?.setMediaItem(MediaItem.fromUri(uri));

        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()

        val hlsMediaSource: HlsMediaSource = HlsMediaSource.Factory(dataSourceFactory)
            .setAllowChunklessPreparation(true)
            .createMediaSource(MediaItem.fromUri(uri))
        exoPlayer?.setMediaSource(hlsMediaSource)

        exoPlayer?.prepare()
        exoPlayer?.repeatMode = Player.REPEAT_MODE_ONE
        exoPlayer?.playWhenReady = true

//        val dataSourceFactory: DataSource.Factory = Factory()
//// Create a HLS media source pointing to a playlist uri.
//// Create a HLS media source pointing to a playlist uri.
//        val hlsMediaSource: HlsMediaSource = HlsMediaSource.Factory(dataSourceFactory)
//            .createMediaSource(MediaItem.fromUri(hlsUri))
//// Create a player instance.
//// Create a player instance.
//        val player: ExoPlayer = Builder(context).build()
//// Set the media source to be played.
//// Set the media source to be played.
//        player.setMediaSource(hlsMediaSource)
//
//
//        val mediaSource =
//
//            ProgressiveMediaSource.Factory(cacheDataSourceFactory).createMediaSource(uri)
//
//        simplePlayer?.prepare(mediaSource, true, true)
//        simplePlayer?.repeatMode = Player.REPEAT_MODE_ONE
//        simplePlayer?.playWhenReady = true
//        simplePlayer?.addListener(playerCallback)
//
//        toPlayVideoPosition = -1
    }

    private val playerCallback: Player.EventListener? = object : Player.EventListener {
        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
//            logError("onPlayerStateChanged playbackState: $playbackState")
        }


    }


    companion object {

        @JvmStatic
        fun newInstance(post: Post) = PostFragment().apply {
            arguments = Bundle().apply {
                putParcelable(Constants.KEY_POST_DATA, post)
            }
        }
    }
}