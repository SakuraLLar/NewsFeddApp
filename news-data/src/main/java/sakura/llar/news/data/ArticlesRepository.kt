package sakura.llar.news.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import sakura.llar.news.data.model.Article
import sakura.llar.news.database.NewsDatabase
import sakura.llar.newsapi.NewsApi
import sakura.llar.newsapi.models.ArticleDTO
import sakura.llar.newsapi.models.ResponseDTO
import java.io.IOException

class ArticlesRepository(
    private val database: NewsDatabase,
    private val api: NewsApi
) {

    fun getAll(): Flow<RequestResult<List<Article>>> {
        val cachedAllArticles: Flow<List<Article>> = database.articlesDao
            .getAll()
            .map { articles -> articles.map { it.toArticle() } }

        val remoteArticles = flow {
            emit(api.everything())
        }.map { result ->
            if (result.isSuccess) {
                val response: ResponseDTO<ArticleDTO> = result.getOrThrow()
                RequestResult.Success(response.articles)
            } else {
                RequestResult.Error(null)
                throw result.exceptionOrNull() ?: IOException("Unknown error")
            }
        }.mapNotNull { requestResult ->requestResult as? RequestResult.Success }
            .map { requestResult ->
            requestResult.data.map { articlesDto -> articlesDto.toArticleDbo() }
        }.onEach { articlesDbos ->
            database.articlesDao.insert(articlesDbos)
        }

        cachedAllArticles.map {

        }

        return cachedAllArticles.combine(remoteArticles) {

        }
    }

    suspend fun search(query: String): Flow<Article> {
        api.everything()
        TODO("Not implemented")
    }
}


sealed class RequestResult<E>(internal val data: E?) {

    class InProgress<E>(data: E?) : RequestResult<E>(data)
    class Success<E>(data: E?) : RequestResult<E>(data)
    class Error<E>(data: E?) : RequestResult<E>(data)
}

internal fun <T: Any> RequestResult<T?>.requireData(): T = checkNotNull(data)
