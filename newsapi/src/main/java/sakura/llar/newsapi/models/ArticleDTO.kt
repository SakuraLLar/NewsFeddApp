package sakura.llar.newsapi.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import sakura.llar.newsapi.utils.DataTimeUTCSerializer
import java.util.Date

@Serializable
data class ArticleDTO (
    @SerialName("source") val source: Source,
    @SerialName("author") val author: String,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("url") val url: String,
    @SerialName("urlToImage") val urlToImage: String,
    @SerialName("publishedAt")
    @Serializable(with = DataTimeUTCSerializer::class)
    val publishedAt: Date,
    @SerialName("content") val content: String
)
