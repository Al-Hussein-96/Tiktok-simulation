package com.alhussein.videotimeline.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.alhussein.videotimeline.R
import com.alhussein.videotimeline.adapter.PostsAdapter
import com.alhussein.videotimeline.databinding.FragmentHomeBinding
import com.alhussein.videotimeline.databinding.FragmentTimeLineBinding
import com.alhussein.videotimeline.model.Post
import com.alhussein.videotimeline.intent.MainIntent
import com.alhussein.videotimeline.viewmodel.MainViewModel
import com.alhussein.videotimeline.viewstate.MainState
import com.alhussein.videotimeline.utils.Constants
import com.alhussein.videotimeline.work.PreCachingService
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.client.*
import io.ktor.client.engine.android.*
import kotlinx.android.synthetic.main.fragment_time_line.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TimeLineFragment : Fragment() {
    private val mainViewModel by activityViewModels<MainViewModel>()

    private lateinit var binding: FragmentTimeLineBinding
    private lateinit var postsPagerAdapter: PostsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTimeLineBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        lifecycleScope.launch {
            mainViewModel.postIntent.send(MainIntent.FetchPosts)
        }


//        postsData.observe(viewLifecycleOwner, Observer { value ->
//            when (value) {
//                is ResultData.Loading -> {
//                }
//                is ResultData.Success -> {
//                    if (!value.data.isNullOrEmpty()) {
//                        val dataList = value.data
//                        postsPagerAdapter = PostsAdapter(this, dataList)
//                        view_pager_posts.adapter = postsPagerAdapter
//
//                        startPreCaching(dataList)
//                    }
//                }
//            }
//        })
        observerViewModel()

    }

    private fun observerViewModel() {
        lifecycleScope.launch {
            mainViewModel.state.collect {
                when (it) {
                    is MainState.Idle -> {

                    }
                    is MainState.Loading -> {
                        // TODO show loading for data
                    }
                    is MainState.Posts -> {
                        postsPagerAdapter =
                            it.posts?.let { it1 -> PostsAdapter(this@TimeLineFragment, it1) }!!
                        view_pager_posts.adapter = postsPagerAdapter
                        startPreCaching(it.posts)

                    }

                }
            }
        }
    }

    private fun startPreCaching(dataList: List<Post>) {
        val urlList = arrayOfNulls<String>(dataList.size)
        dataList.mapIndexed { index, storiesDataModel ->
            urlList[index] =
                storiesDataModel.media_base_url + storiesDataModel.recording_details.recording_url
        }
        val inputData = Data.Builder().putStringArray(Constants.KEY_POST_LIST_DATA, urlList).build()
        val preCachingWork = OneTimeWorkRequestBuilder<PreCachingService>().setInputData(inputData)
            .build()
        WorkManager.getInstance(requireContext())
            .enqueue(preCachingWork)
    }


}