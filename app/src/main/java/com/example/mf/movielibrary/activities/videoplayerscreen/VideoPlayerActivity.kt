package com.example.mf.movielibrary.activities.videoplayerscreen

import android.content.pm.ActivityInfo
import android.os.Bundle
import com.example.mf.movielibrary.R
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import files.YOUTUBE_API_KEY
import kotlinx.android.synthetic.main.activity_video_player.*

class VideoPlayerActivity : YouTubeBaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE


        videoPlayer.initialize(YOUTUBE_API_KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, youtubePlayer: YouTubePlayer?, wasRestored: Boolean) {
                youtubePlayer?.loadVideo("6ZfuNTqbHE8")
            }
            override fun onInitializationFailure(provider: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {

            }
        })
    }
}
