package com.alhussein.videotimeline.data.datasource

import android.net.Uri
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import javax.inject.Inject
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DataSource

import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import javax.inject.Singleton

@Singleton
class ExoPlayerDataSource @Inject constructor(private val exoPlayer: ExoPlayer) {

    fun setMediaSource(uri: Uri) {
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        val hlsMediaSource: HlsMediaSource = HlsMediaSource.Factory(dataSourceFactory)
            .setAllowChunklessPreparation(true)
            .createMediaSource(MediaItem.fromUri(uri))
        exoPlayer.setMediaSource(hlsMediaSource)
    }

    fun prepare() {
        exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
        exoPlayer.playWhenReady = true
        exoPlayer.prepare()
    }

    fun stop() {
        exoPlayer.stop()
    }

    fun release() {
        exoPlayer.stop()
        exoPlayer.release()
    }

    fun play() {
//        exoPlayer.playWhenReady = true
//        exoPlayer.play()
    }

    fun pause() {
        exoPlayer.playWhenReady = false
    }

    fun restart() {
        exoPlayer.seekToDefaultPosition()
        exoPlayer.playWhenReady = true
    }

    fun bindPlayer(playerView: StyledPlayerView) {
        playerView.player = exoPlayer
    }

}