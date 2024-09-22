package sakura.llar.news.main

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import sakura.llar.news.data.ArticlesRepository
import sakura.llar.news.data.RequestResult
import sakura.llar.news.data.map
import javax.inject.Inject
import sakura.llar.news.data.model.Article as DataArticle

class GetAllArticlesUseCase @Inject constructor(
    private val repository: ArticlesRepository,
) {

    operator fun invoke(): Flow<RequestResult<List<Article>>> {
        return repository.getAll()
            .map { requestResult ->
                requestResult.map { articles -> articles.map { it.toUiArticle() } }
            }
    }
}

private fun DataArticle.toUiArticle(): Article {
    TODO("Not yet implemented")
}