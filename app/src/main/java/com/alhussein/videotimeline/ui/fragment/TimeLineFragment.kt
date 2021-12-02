package com.alhussein.videotimeline.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.alhussein.videotimeline.R
import com.alhussein.videotimeline.adapter.PostsAdapter
import com.alhussein.videotimeline.model.PostModel
import com.alhussein.videotimeline.model.ResultData
import com.alhussein.videotimeline.utils.Constants
import com.alhussein.videotimeline.viewmodel.TimeLineViewModel
import com.alhussein.videotimeline.work.PreCachingService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_time_line.*

@AndroidEntryPoint
class TimeLineFragment : BaseFragment(R.layout.fragment_time_line) {
    private val timeLineViewModel by activityViewModels<TimeLineViewModel>()

    private lateinit var postsPagerAdapter: PostsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val postsData = timeLineViewModel.getDataList()


        postsData.observe(viewLifecycleOwner, Observer { value ->
            when (value) {
                is ResultData.Loading -> {
                }
                is ResultData.Success -> {
                    if (!value.data.isNullOrEmpty()) {
                        val dataList = value.data
                        postsPagerAdapter = PostsAdapter(this, dataList)
                        view_pager_posts.adapter = postsPagerAdapter

                        startPreCaching(dataList)
                    }
                }
            }
        })

    }

    private fun startPreCaching(dataList: ArrayList<PostModel>) {
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