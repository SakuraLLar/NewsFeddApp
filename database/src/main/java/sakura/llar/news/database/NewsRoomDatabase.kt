package sakura.llar.news.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import sakura.llar.news.database.dao.ArticleDao
import sakura.llar.news.database.models.ArticleDBO
import sakura.llar.news.database.utils.Converters

class NewsDatabase internal constructor(private val database: NewsRoomDatabase) {

    val articlesDao: ArticleDao
            get() = database.articlesDao()
}

@Database(entities = [ArticleDBO::class], version = 1)
@TypeConverters(Converters::class)
internal abstract class NewsRoomDatabase : RoomDatabase() {

    abstract fun articlesDao(): ArticleDao
}

fun NewsDatabase(applicationContext: Context): NewsDatabase {
    val newsRoomDatabase = Room.databaseBuilder(
        checkNotNull(applicationContext.applicationContext),
        NewsRoomDatabase::class.java,
        "news"
    ).build()
    return NewsDatabase(newsRoomDatabase)
}