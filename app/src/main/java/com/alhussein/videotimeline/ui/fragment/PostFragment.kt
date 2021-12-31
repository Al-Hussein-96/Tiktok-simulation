package com.alhussein.videotimeline.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.alhussein.videotimeline.App
import com.alhussein.videotimeline.R
import com.alhussein.videotimeline.databinding.FragmentPostBinding
import com.alhussein.videotimeline.databinding.FragmentTimeLineBinding
import com.alhussein.videotimeline.model.Post
import com.alhussein.videotimeline.utils.Constants
import com.alhussein.videotimeline.viewmodel.PostViewModel
import com.alhussein.videotimeline.viewmodel.TimelineViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import timber.log.Timber

@AndroidEntryPoint
class PostFragment : Fragment() {
    private val postViewModel by activityViewModels<PostViewModel>()


    private var postUrl: String? = null
    private var postsData: Post? = null

    private lateinit var binding: FragmentPostBinding


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


    private fun setData() {


        postUrl = postsData?.recording_details?.streaming_hls
        postUrl?.let { prepareMedia(it) }


        postViewModel.bindPlayer(binding.postLayout.playerViewPost)







        binding.postLayout.imageViewOptionShare.setOnClickListener {

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
        postViewModel.restart()
    }

    private fun pauseVideo() {
        postViewModel.pause()
    }

    private fun releasePlayer() {
        postViewModel.release()
    }

    private fun prepareMedia(linkUrl: String) {

        Timber.i("prepareMedia: $linkUrl")
        val uri = Uri.parse(linkUrl)

        postViewModel.setMediaSource(uri)
        postViewModel.prepare()


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