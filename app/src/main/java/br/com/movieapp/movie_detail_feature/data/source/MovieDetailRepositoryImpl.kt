package br.com.movieapp.movie_detail_feature.data.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.core.domain.model.MovieDetails
import br.com.movieapp.movie_detail_feature.domain.source.MovieDetailRemoteDataSource
import br.com.movieapp.movie_detail_feature.domain.source.MovieDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieDetailRemoteDataSource
) : MovieDetailRepository {
    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        return remoteDataSource.getMovieDetail(movieId = movieId)
    }

    override suspend fun getMovieMoviesSimilar(
        movieId: Int,
        pagingConfig: PagingConfig
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                remoteDataSource.getSimilarMoviesPagingSource(movieId = movieId)
            }
        ).flow
    }
}