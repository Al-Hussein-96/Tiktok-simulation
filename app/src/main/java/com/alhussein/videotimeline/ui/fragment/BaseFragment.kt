package com.alhussein.videotimeline.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import com.alhussein.videotimeline.R


open class BaseFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        ViewCompat.setOnApplyWindowInsetsListener(view) { v, windowInsets ->
            v.updatePadding(top = windowInsets.systemWindowInsetTop)
            windowInsets.consumeSystemWindowInsets()
        }
    }
}