package sakura.llar.news.main

import kotlinx.coroutines.flow.Flow
import sakura.llar.news.data.ArticlesRepository
//import sakura.llar.news.data.RequestResult
import sakura.llar.news.data.model.Article

class GetAllArticlesUseCase(private val repository: ArticlesRepository) {

    operator fun invoke(): Flow<Article> {
        return repository.getAll()
    }
}