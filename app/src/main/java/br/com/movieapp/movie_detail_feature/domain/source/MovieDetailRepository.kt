package br.com.movieapp.movie_detail_feature.domain.source

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.core.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
    suspend fun getMovieDetails(movieId: Int): MovieDetails
    suspend fun getMovieMoviesSimilar(movieId: Int, pagingConfig: PagingConfig):
            Flow<PagingData<Movie>>
}