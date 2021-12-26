package com.alhussein.videotimeline.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.alhussein.videotimeline.adapter.PostsAdapter
import com.alhussein.videotimeline.databinding.FragmentTimeLineBinding
import com.alhussein.videotimeline.model.Post
import com.alhussein.videotimeline.model.ResultData
import com.alhussein.videotimeline.state.PostsUiState
import com.alhussein.videotimeline.utils.Constants
import com.alhussein.videotimeline.viewmodel.TimelineViewModel
import com.alhussein.videotimeline.work.PreCachingService
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.client.*
import io.ktor.client.engine.android.*
import kotlinx.android.synthetic.main.fragment_time_line.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TimeLineFragment : Fragment() {
    private val timelineViewModel by activityViewModels<TimelineViewModel>()

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

        postsPagerAdapter = PostsAdapter(fragment = this)
        view_pager_posts.adapter = postsPagerAdapter




        observerData()
    }

    private fun observerData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                println("lifeCycleOwner: RESUMED")

                timelineViewModel.uiFlow.collect {
                    when (it) {
                        is PostsUiState.Success -> {
//                            binding.progressCircular.visibility = View.GONE
//                            binding.progressCircular.visibility = View.VISIBLE
                            postsPagerAdapter.listData = it.posts
                        }
                        is PostsUiState.Error -> {

                        }
                        else -> {
//                            binding.progressCircular.visibility = View.VISIBLE

                        }
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