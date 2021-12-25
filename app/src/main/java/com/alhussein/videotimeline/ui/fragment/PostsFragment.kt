package com.alhussein.videotimeline.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alhussein.videotimeline.R
import com.alhussein.videotimeline.databinding.FragmentPostBinding
import com.alhussein.videotimeline.databinding.PostsFragmentBinding
import com.alhussein.videotimeline.state.PostsUiState
import com.alhussein.videotimeline.viewmodel.PostsViewModel
import com.alhussein.videotimeline.viewmodel.TimelineViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PostsFragment : Fragment() {
    private lateinit var binding: PostsFragmentBinding

    private val postsViewModel by activityViewModels<PostsViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PostsFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                println("lifeCycleOwner: RESUMED")

                postsViewModel.uiFlow.collect {
                    when (it) {
                        is PostsUiState.Success -> {
                            binding.progressCircular.visibility = View.GONE

                            println("Success StateFlow ${it.posts.size}")
                        }
                        is PostsUiState.Error -> {
                            binding.progressCircular.visibility = View.GONE

                            println("Success StateFlow")
                        }
                        else -> {
                            binding.progressCircular.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() = PostsFragment()
    }
}