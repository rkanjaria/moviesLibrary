package com.example.mf.movielibrary.activities.movieseriesscreen

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Activity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.CastRecyclerAdapter
import com.example.mf.movielibrary.adapters.MovieRecyclerAdapter
import com.example.mf.movielibrary.adapters.ReviewAdapter
import com.example.mf.movielibrary.adapters.SeasonRecyclerAdapter
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.files.*
import com.example.mf.movielibrary.models.castmodel.Cast
import com.example.mf.movielibrary.models.moviemodel.Movie
import com.example.mf.movielibrary.models.movieseriesdetailsmodel.Season
import com.example.mf.movielibrary.models.reviewmodels.UserReview
import com.example.mf.movielibrary.models.videomodels.VideoTrailers
import kotlinx.android.synthetic.main.activity_movie_series.*
import kotlinx.android.synthetic.main.play_trailer_layout.*

class MovieSeriesActivity : BaseActivity<MovieSeriesActivityContract.MovieSeriesView, MovieSeriesActivityPresenter>(),
        MovieSeriesActivityContract.MovieSeriesView, CastRecyclerAdapter.OnCastAdapterListener,
        MovieRecyclerAdapter.OnMovieSeriesAdapterListener, SeasonRecyclerAdapter.SeasonAdapterListener, View.OnClickListener {

    private var movieOrSeriesId = 0
    private lateinit var trailersList: List<VideoTrailers>

    override var mPresenter = MovieSeriesActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_series)

        val movieModel = intent.getParcelableExtra(PARCELABLE_OBJECT) as Movie
        initToolbar(toolbar as Toolbar, true, "")

        movieOrSeriesId = movieModel.id

        if (movieModel.backDropPath != null) {
            backdropImage.loadImage(backdropUrl + movieModel.backDropPath, placeholder = R.color.darkGrey)
        }

        if (movieModel.posterPath != null) {
            posterImage.loadImage(photoUrl + movieModel.posterPath, placeholder = R.color.darkGrey)
        }
        movieTitle.text = movieModel.title
        movieYear.text = getDateWithCustomFormat(movieModel.releaseDate)
        movieRating.text = formatRating(movieModel.voteAverage)
        movieOverview.text = movieModel.overview
        movieGenre.text = mPresenter.getMovieGenres(movieModel.genreIds)

        callAllRequiredApis()

        if (intent.getStringExtra(MOVIE_OR_SERIES) == TV_SHOWS) {
            mPresenter.callGetTvDetailsApi(movieOrSeriesId)
        }

        playTrailer.setOnClickListener {
            mPresenter.launchTrailerActivity(trailersList[0])
        }

        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }

                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.title = movieModel.title
                    isShow = true
                } else if (isShow) {
                    collapsingToolbar.title = " "
                    isShow = false
                }
            }
        })

        if (database.favouriteMovieDao().doesAlreadyExists(movieOrSeriesId)) {
            favouriteIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite))
        }

        favouriteIcon.setOnClickListener {
            mPresenter.addOrRemoveFavourites(movieModel, intent.getStringExtra(MOVIE_OR_SERIES))
            setResult(Activity.RESULT_OK)
        }
    }

    override fun setCastRecyclerview(castList: List<Cast>) {
        castRecyclerview.setHasFixedSize(true)
        castRecyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        castRecyclerview.adapter = CastRecyclerAdapter(castList, this, true)
        castRecyclerview.visibility = View.VISIBLE
        castTitle.visibility = View.VISIBLE
    }

    override fun onCastClicked(castModel: Cast) {
        mPresenter.launchActorActivity(castModel)
    }

    override fun setSimilarMoviesRecyclerview(moviesList: List<Movie?>, totalPages: Int) {
        similarMoviesRecyclerview.setHasFixedSize(true)
        similarMoviesRecyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        similarMoviesRecyclerview.adapter = MovieRecyclerAdapter(moviesList, this, true)
        similarMoviesRecyclerview.visibility = View.VISIBLE
        recommendationsTitle.visibility = View.VISIBLE
    }

    override fun onMovieOrSeriesClicked(movieModel: Movie?) {
        mPresenter.launchMovieSeriesActivity(movieModel, intent.getStringExtra(MOVIE_OR_SERIES))
    }

    override fun setSeasonRecyclerview(seasonsList: List<Season>) {
        seasonsRecyclerview.setHasFixedSize(true)
        seasonsRecyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        seasonsRecyclerview.adapter = SeasonRecyclerAdapter(seasonsList, this)
        seasonsRecyclerview.visibility = View.VISIBLE
        seasonsTitle.visibility = View.VISIBLE
    }

    override fun setReviewRecyclerview(reviewList: List<UserReview>) {
        reviewsRecyclerview.visibility = View.VISIBLE
        reviewsTitle.visibility = View.VISIBLE
        reviewsRecyclerview.setHasFixedSize(true)
        reviewsRecyclerview.layoutManager = LinearLayoutManager(this)
        reviewsRecyclerview.adapter = ReviewAdapter(reviewList, true)

        if (reviewList.size > 1) readAllReviews.visibility = View.VISIBLE else readAllReviews.visibility = View.GONE
        readAllReviews.setOnClickListener { mPresenter.launchReviewActivity(reviewList) }
    }

    override fun onSeasonClicked(season: Season) {
        mPresenter.launchSeasonActivity(season, movieOrSeriesId)
    }

    override fun onMoreClicked(castList: List<Cast>) {
        mPresenter.launchCastActivity(castList)
    }

    override fun showPlayTrailerLayout(videoTrailersList: List<VideoTrailers>) {

        if (videoTrailersList.isNotEmpty()) {
            trailersList = videoTrailersList
            playTrailer.visibility = View.VISIBLE
        }
    }

    override fun highlightFavoriteIcon() {
        favouriteIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite))
        animateHeart(favouriteIcon)
    }

    override fun unhighlightFavoriteIcon() {
        animateHeart(favouriteIcon)
        favouriteIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border))
    }

    fun animateHeart(image: ImageView) {
        val objectAnimator = ObjectAnimator.ofPropertyValuesHolder(image,
                PropertyValuesHolder.ofFloat(View.SCALE_X, 1.5f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.5f))

        objectAnimator.repeatCount = 1
        objectAnimator.repeatMode = ObjectAnimator.REVERSE
        objectAnimator.setDuration(170)
        objectAnimator.start()

        /*val scaleAnim = AnimationUtils.loadAnimation(this, R.anim.scale)
        scaleAnim.interpolator = BounceInterpolator(0.1, 10.0)
        image.startAnimation(scaleAnim)*/
    }

    override fun onSnackBarButtonClicked() {
        callAllRequiredApis()
    }

    fun callAllRequiredApis() {
        mPresenter.callgetMovieOrSeriesCastApi(intent.getStringExtra(MOVIE_OR_SERIES), movieOrSeriesId)
        mPresenter.callGetSimilarMovieOrSeriesApi(intent.getStringExtra(MOVIE_OR_SERIES), movieOrSeriesId)
        mPresenter.callGetMoviesOrSeriesTrailersApi(intent.getStringExtra(MOVIE_OR_SERIES), movieOrSeriesId)
        mPresenter.callGetMoviesOrSeriesReviewsApi(intent.getStringExtra(MOVIE_OR_SERIES), movieOrSeriesId)
    }
}
