package com.alhussein.videotimeline.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alhussein.videotimeline.model.Post
import com.alhussein.videotimeline.ui.fragment.PostFragment

class PostsAdapter(fragment: Fragment, var listData: List<Post> = mutableListOf()) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return listData.size
    }

    override fun createFragment(position: Int): Fragment {
        return PostFragment.newInstance(listData[position])
    }


}