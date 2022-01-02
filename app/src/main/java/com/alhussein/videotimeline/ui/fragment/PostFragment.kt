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
import com.alhussein.videotimeline.ui.exoplayer.Player
import com.alhussein.videotimeline.utils.Constants
import com.alhussein.videotimeline.viewmodel.PostViewModel
import com.alhussein.videotimeline.viewmodel.TimelineViewModel
import com.google.android.exoplayer2.ExoPlayer
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

    private val player by lazy {
        Player(
            simpleExoplayerView = binding.postLayout.playerViewPost,
            playBtn = binding.postLayout.imageViewOptionShare,
            context = requireContext(),
            url = postUrl,
            onVideoEnded = {
                it.restartPlayer()
            }
        )
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
        postUrl = postsData?.recording_details?.streaming_hls

        lifecycle.addObserver(player)
        player.init()
    }


    private fun setData() {


        binding.postLayout.imageViewOptionShare.setOnClickListener {

            val bundle = bundleOf("post" to postsData)

            it.findNavController().navigate(R.id.action_timeLineFragment_to_trimFragment, bundle)

//                downloadWithKtor(postUrl!!)
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