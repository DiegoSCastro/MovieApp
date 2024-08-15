package br.com.movieapp.movie_detail_feature.domain.usecase

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.core.domain.model.MovieDetails
import br.com.movieapp.core.util.ResultData
import br.com.movieapp.movie_detail_feature.domain.source.MovieDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

interface GetMovieDetailUseCase {
    operator fun invoke(params: Params): Flow<ResultData<Pair<Flow<PagingData<Movie>>, MovieDetails
            >>>

    data class Params(val movieId: Int)
}

class GetMovieDetailUseCaseImpl @Inject constructor(
    private val repository: MovieDetailRepository
) : GetMovieDetailUseCase {
    override fun invoke(params: GetMovieDetailUseCase.Params):
            Flow<ResultData<Pair<Flow<PagingData<Movie>>, MovieDetails>>> {
        return flow {
            try {
                emit(ResultData.Loading)
                val movieDetails: MovieDetails = repository.getMovieDetails(params.movieId)
                val moviesSimilar = repository.getMovieMoviesSimilar(
                    movieId = params.movieId,
                    pagingConfig = PagingConfig(
                        pageSize = 20,
                        initialLoadSize = 20,
                    )
                )
                emit(ResultData.Success(moviesSimilar to movieDetails))
            } catch (e: HttpException) {
                emit(ResultData.Failure(e))
            } catch (e: IOException) {
                emit(ResultData.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }
}