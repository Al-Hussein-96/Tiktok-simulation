package com.alhussein.videotimeline.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.alhussein.videotimeline.data.repository.ExoPlayerRepository
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.StyledPlayerView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val exoPlayerRepository: ExoPlayerRepository) :
    ViewModel() {

    fun setMediaSource(uri: Uri) {
        exoPlayerRepository.setMediaSource(uri)
    }

    fun prepare() {
        exoPlayerRepository.prepare()
    }

    fun stop() {
        exoPlayerRepository.stop()
    }

    fun release() {
        exoPlayerRepository.release()
    }

    fun play() {

        exoPlayerRepository.play()
    }

    fun pause() {
        exoPlayerRepository.pause()
    }

    fun restart() {
        exoPlayerRepository.restart()
    }

    fun bindPlayer(playerView: PlayerView) {
        exoPlayerRepository.bindPlayer(playerView)
    }

}