package com.alhussein.videotimeline.data.datasource

import android.net.Uri
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.MediaSource
import javax.inject.Inject
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DataSource

import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.HttpDataSource
import timber.log.Timber
import javax.inject.Singleton

class ExoPlayerDataSource @Inject constructor(private val exoPlayer: ExoPlayer) {

    fun setMediaSource(uri: Uri) {
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        val hlsMediaSource: HlsMediaSource = HlsMediaSource.Factory(dataSourceFactory)
            .setAllowChunklessPreparation(true)
            .createMediaSource(MediaItem.fromUri(uri))
        exoPlayer.setMediaSource(hlsMediaSource)
    }

    fun prepare() {
        Timber.i("ExoPlayerLifeCycle %s", "Prepare")
        exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
        exoPlayer.playWhenReady = true
        exoPlayer.prepare()
    }

    fun stop() {
        Timber.i("ExoPlayerLifeCycle %s", "Stop")

        exoPlayer.stop()
    }

    fun release() {
        Timber.i("ExoPlayerLifeCycle %s", "Release")

        exoPlayer.clearMediaItems()
        exoPlayer.stop()

//        exoPlayer.release()
    }

    fun play() {
        Timber.i("ExoPlayerLifeCycle %s", "Play")

//        exoPlayer.playWhenReady = true
//        exoPlayer.play()
    }

    fun pause() {
        Timber.i("ExoPlayerLifeCycle %s", "Pause")

        exoPlayer.playWhenReady = false
    }

    fun restart() {
        Timber.i("ExoPlayerLifeCycle %s", "Restart")

        exoPlayer.seekToDefaultPosition()
        exoPlayer.playWhenReady = true
    }

    fun bindPlayer(playerView: PlayerView) {
        Timber.i("ExoPlayerLifeCycle %s", "BindPlayer")

        playerView.player = exoPlayer
    }

}