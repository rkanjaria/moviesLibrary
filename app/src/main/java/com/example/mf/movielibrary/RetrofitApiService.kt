package com.example.mf.movielibrary

import com.example.mf.movielibrary.models.actormodel.Actor
import com.example.mf.movielibrary.models.actormodel.ActorImagesResult
import com.example.mf.movielibrary.models.castmodel.CastResult
import com.example.mf.movielibrary.models.collectionmodels.CollectionsResult
import com.example.mf.movielibrary.models.episodemodels.EpisodeImageResult
import com.example.mf.movielibrary.models.genremodel.GenreResult
import com.example.mf.movielibrary.models.moviemodel.MoviesResult
import com.example.mf.movielibrary.models.movieseriesdetailsmodel.MovieSeriesDetailsResult
import com.example.mf.movielibrary.models.reviewmodels.ReviewResult
import com.example.mf.movielibrary.models.seasonmodels.SeasonResult
import com.example.mf.movielibrary.models.videomodels.VideoResult
import files.API_KEY
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by RK on 01-12-2017.
 */
interface RetrofitApiService {

    @GET("{movie_or_series}/{request_type}")
    fun doGetMoviesOrSeriesApiCall(@Path("movie_or_series") movieOrSeries: String,
                                   @Path("request_type") requestType: String,
                                   @Query("api_key") apiKey: String = API_KEY,
                                   @Query("page") page: Int): Observable<MoviesResult>

    @GET("{movie_or_series}/{movie_or_series_id}/credits")
    fun doGetMovieOrSeriesCastApiCall(@Path("movie_or_series") movieOrSeries: String,
                                      @Path("movie_or_series_id") movieOrSeriesId: Int,
                                      @Query("api_key") apiKey: String = API_KEY): Observable<CastResult>

    @GET("genre/{movie_or_series}/list")
    fun doGetGenreListApiCall(@Path("movie_or_series") movieOrSeries: String,
                              @Query("api_key") apiKey: String = API_KEY): Observable<GenreResult>

    @GET("person/{actor_id}")
    fun doGetActorApiCall(@Path("actor_id") actorId: Int,
                          @Query("api_key") apiKey: String = API_KEY): Observable<Actor>

    @GET("{movie_or_series}/{movie_or_series_id}/recommendations")
    fun doGetSimilarMoviesOrSeriesApiCall(@Path("movie_or_series") movieOrSeries: String,
                                          @Path("movie_or_series_id") movieOrSeriesId: Int,
                                          @Query("api_key") apiKey: String = API_KEY): Observable<MoviesResult>


    @GET("person/{actor_id}/{movie_or_series}")
    fun doGetActorsMoviesOrSeriesApiCall(@Path("actor_id") actorId: Int,
                                         @Path("movie_or_series") movieOrSeries: String,
                                         @Query("api_key") apiKey: String = API_KEY): Observable<MoviesResult>

    @GET("person/{actor_id}/combined_credits")
    fun doGetActorsMoviesAndSeriesApiCall(@Path("actor_id") actorId: Int,
                                          @Query("api_key") apiKey: String = API_KEY): Observable<MoviesResult>

    @GET("person/{actor_id}/images")
    fun doGetActorsImagesApiCall(@Path("actor_id") actorId: Int,
                                 @Query("api_key") apiKey: String = API_KEY): Observable<ActorImagesResult>

    @GET("tv/{tv_id}")
    fun doGetTvSeasonsApiCall(@Path("tv_id") tvId: Int,
                              @Query("api_key") apiKey: String = API_KEY): Observable<MovieSeriesDetailsResult>

    @GET("tv/{tv_id}/season/{season_number}")
    fun doGetSeasonsDetailsApiCall(@Path("tv_id") tvId: Int,
                                   @Path("season_number") seasonNumber: Int,
                                   @Query("api_key") apiKey: String = API_KEY): Observable<SeasonResult>

    @GET("search/{movie_or_series}")
    fun doSearchMovieOrSeriesApiCall(@Path("movie_or_series") movieOrSeries: String,
                                     @Query("api_key") apiKey: String = API_KEY,
                                     @Query("query") searchQuery: String,
                                     @Query("page") page: Int): Observable<MoviesResult>

    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}/images")
    fun doGetEpisodeImagesApiCall(@Path("tv_id") tvId: Int,
                                  @Path("season_number") seasonNumber: Int,
                                  @Path("episode_number") episodeNumber: Int,
                                  @Query("api_key") apiKey: String = API_KEY): Observable<EpisodeImageResult>


    @GET("{movie_or_series}/{movie_or_tv_id}/videos")
    fun doGetMovieOrSeriesTrailers(@Path("movie_or_series") movieOrSeries: String,
                                   @Path("movie_or_tv_id") movieOrSeriesId: Int,
                                   @Query("api_key") apiKey: String = API_KEY): Observable<VideoResult>

    @GET("list/{list_id}")
    fun doGetListApiCall(@Path("list_id") listId: Int,
                         @Query("api_key") apiKey: String = API_KEY): Observable<CollectionsResult>

    @GET("{movie_or_series}/{movie_or_tv_id}/reviews")
    fun doGetMovieOrSeriesReviews(@Path("movie_or_series") movieOrSeries: String,
                                  @Path("movie_or_tv_id") movieOrSeriesId: Int,
                                  @Query("api_key") apiKey: String = API_KEY): Observable<ReviewResult>



    @GET("discover/{movie_or_series}")
    fun doSearchMovieOrSeriesByGenreApiCall(@Path("movie_or_series") movieOrSeries: String,
                                            @Query("sort_by") popularity : String = "popularity.desc",
                                            @Query("page") page: Int,
                                            @Query("with_genres") genres: Int,
                                            @Query("api_key") apiKey: String = API_KEY): Observable<MoviesResult>

}