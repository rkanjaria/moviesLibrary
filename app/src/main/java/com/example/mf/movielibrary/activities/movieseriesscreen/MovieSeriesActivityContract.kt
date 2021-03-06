package com.example.mf.movielibrary.activities.movieseriesscreen

import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView
import com.example.mf.movielibrary.models.castmodel.Cast
import com.example.mf.movielibrary.models.moviemodel.Movie
import com.example.mf.movielibrary.models.movieseriesdetailsmodel.Season
import com.example.mf.movielibrary.models.reviewmodels.UserReview
import com.example.mf.movielibrary.models.videomodels.VideoTrailers

/**
 * Created by RK on 04-01-2018.
 */
object MovieSeriesActivityContract {

    interface MovieSeriesView : BaseView {
        fun setCastRecyclerview(castList: List<Cast>)
        fun setSimilarMoviesRecyclerview(moviesList: List<Movie?>, totalPages: Int)
        fun setSeasonRecyclerview(seasonsList: List<Season>)
        fun showPlayTrailerLayout(videoTrailersList: List<VideoTrailers>)
        fun highlightFavoriteIcon()
        fun unhighlightFavoriteIcon()
        fun setReviewRecyclerview(reviewList: List<UserReview>)
    }

    interface MovieSeriesPresenter : BasePresenter<MovieSeriesView> {
        fun callgetMovieOrSeriesCastApi(movieOrSeries: String?, movieId: Int)
        fun getMovieGenres(genreIds: List<Int>?): String
        fun launchActorActivity(castModel: Cast)
        fun callGetSimilarMovieOrSeriesApi(moviesOrSeries: String?, movieOrSeriesId: Int)
        fun launchMovieSeriesActivity(movieModel: Movie?, movieOrSeries: String?)
        fun callGetTvDetailsApi(movieOrSeriesId: Int)
        fun launchSeasonActivity(season: Season, movieOrSeriesId: Int)
        fun callGetMoviesOrSeriesTrailersApi(moviesOrSeries: String, movieOrSeriesId: Int)
        fun launchTrailerActivity(videoTrailer: VideoTrailers?)
        fun addOrRemoveFavourites(movieModel: Movie, movieOrSeries: String)
        fun callGetMoviesOrSeriesReviewsApi(moviesOrSeries: String, movieOrSeriesId: Int)
        fun launchReviewActivity(reviewList: List<UserReview>)
        fun launchCastActivity(castList: List<Cast>)
    }
}