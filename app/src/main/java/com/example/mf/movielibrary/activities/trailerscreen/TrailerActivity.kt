package com.example.mf.movielibrary.activities.trailerscreen

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.models.videomodels.VideoTrailers
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import files.PARCELABLE_OBJECT
import files.YOUTUBE_API_KEY
import kotlinx.android.synthetic.main.activity_video_player.*

class TrailerActivity : YouTubeBaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        val trailer = intent.getParcelableExtra(PARCELABLE_OBJECT) as VideoTrailers

        videoPlayer.initialize(YOUTUBE_API_KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, youtubePlayer: YouTubePlayer?, wasRestored: Boolean) {
                youtubePlayer?.loadVideo(trailer.key)
            }
            override fun onInitializationFailure(provider: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {

            }
        })
    }
}
