package com.alhussein.videotimeline.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.alhussein.videotimeline.App
import com.alhussein.videotimeline.R
import com.alhussein.videotimeline.databinding.FragmentPostBinding
import com.alhussein.videotimeline.databinding.FragmentTimeLineBinding
import com.alhussein.videotimeline.model.Post
import com.alhussein.videotimeline.utils.Constants
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import kotlinx.android.synthetic.main.layout_post_view.*
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class PostFragment : Fragment() {
    private var postUrl: String? = null
    private var postsData: Post? = null
    private var exoPlayer: ExoPlayer? = null
    private val simpleCache = App.simpleCache

    private lateinit var binding: FragmentPostBinding

//    private var cacheDataSourceFactory: CacheDataSourceFactory? = null

    private var toPlayVideoPosition: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPostBinding.inflate(inflater, container, false)

        return binding.root
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
        Timber.i("onPause: ${postsData?.id}")
        pauseVideo()
        super.onPause()
    }

    override fun onResume() {
        restartVideo()
        super.onResume()
    }

    override fun onDestroy() {
        Timber.i("onDestroyed: ${postsData?.id}")
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
        exoPlayer?.stop()
        exoPlayer?.release()
    }

    private fun prepareMedia(linkUrl: String) {

        Timber.i("prepareMedia: ${linkUrl}")
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