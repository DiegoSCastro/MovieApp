package br.com.movieapp.movie_detail_feature.data.source

import br.com.movieapp.core.data.remote.MovieService
import br.com.movieapp.core.data.remote.response.MovieDetailResponse
import br.com.movieapp.core.data.remote.response.MovieResponse
import br.com.movieapp.core.domain.model.MovieDetails
import br.com.movieapp.core.paging.MovieSimilarPagingSource
import br.com.movieapp.core.util.toBackdropUrl
import br.com.movieapp.movie_detail_feature.domain.source.MovieDetailRemoteDataSource
import javax.inject.Inject

class MovieDetailRemoteDataSourceImpl @Inject constructor(private val service: MovieService) :
    MovieDetailRemoteDataSource {
    override suspend fun getMovieDetail(movieId: Int): MovieDetails {
        val response: MovieDetailResponse = service.getMovie(movieId = movieId)
        val genres: List<String> = response.genres.map { it.name }
        return MovieDetails(
            id = response.id,
            title = response.title,
            overview = response.overview,
            genres = genres,
            releaseDate = response.releaseDate,
            backdropPathUrl = response.backdropPath.toBackdropUrl(),
            voteAverage = response.voteAverage,
            duration = response.runtime,
            voteCount = response.voteCount,
        )
    }

    override suspend fun getMoviesSimilar(page: Int, movieId: Int): MovieResponse {
        return service.getMoviesSimilar(page = page, movieId = movieId)
    }

    override fun getSimilarMoviesPagingSource(movieId: Int): MovieSimilarPagingSource {
        return MovieSimilarPagingSource(this, movieId = movieId)
    }
}