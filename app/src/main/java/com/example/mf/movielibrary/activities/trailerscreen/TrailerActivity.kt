package com.example.mf.movielibrary.activities.trailerscreen

import android.os.Bundle
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.models.videomodels.VideoTrailers
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.example.mf.movielibrary.files.PARCELABLE_OBJECT
import com.example.mf.movielibrary.files.YOUTUBE_API_KEY
import kotlinx.android.synthetic.main.activity_video_player.*

class TrailerActivity : YouTubeBaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        val trailer = intent.getParcelableExtra(PARCELABLE_OBJECT) as VideoTrailers

        videoPlayer.initialize(YOUTUBE_API_KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, youtubePlayer: YouTubePlayer?, wasRestored: Boolean) {

                youtubePlayer?.setFullscreen(true)
                youtubePlayer?.setShowFullscreenButton(false)

                if (!wasRestored) {
                    youtubePlayer?.loadVideo(trailer.key)
                }
            }

            override fun onInitializationFailure(provider: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {

            }
        })
    }
}
