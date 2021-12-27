package com.global.news.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.global.news.data.model.NewsArticle

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(newsArticle: NewsArticle): Long

    @Query("SELECT * FROM news_articles")
    fun getAllNews(): LiveData<List<NewsArticle>>

    @Delete
    suspend fun deleteNews(newsArticle: NewsArticle)

    @Query("Delete FROM news_articles")
    suspend fun deleteAllNews()
}