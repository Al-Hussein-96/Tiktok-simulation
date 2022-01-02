package com.alhussein.videotimeline.data.repository

import android.net.Uri
import com.alhussein.videotimeline.data.datasource.ExoPlayerDataSource
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExoPlayerRepository @Inject constructor(private val exoPlayerDataSource: ExoPlayerDataSource) {
    fun setMediaSource(uri: Uri) {
        exoPlayerDataSource.setMediaSource(uri)
    }

    fun prepare() {
        exoPlayerDataSource.prepare()
    }

    fun stop() {
        exoPlayerDataSource.stop()
    }

    fun release() {
        exoPlayerDataSource.release()
    }

    fun play() {
        exoPlayerDataSource.play()
    }

    fun pause() {
        exoPlayerDataSource.pause()
    }

    fun restart() {
        exoPlayerDataSource.restart()
    }

    fun bindPlayer(playerView: PlayerView) {
        exoPlayerDataSource.bindPlayer(playerView)
    }

}