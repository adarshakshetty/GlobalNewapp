
package com.global.news.domain.repository

import androidx.lifecycle.LiveData
import com.global.news.data.model.NewsArticle
import com.global.news.data.model.NewsResponse
import com.global.news.utils.NetworkResult

interface INewsRepository {
    suspend fun getNews(countryCode: String, pageNumber: Int): NetworkResult<NewsResponse>

    suspend fun searchNews(searchQuery: String, pageNumber: Int): NetworkResult<NewsResponse>

    suspend fun saveNews(news: NewsArticle): Long

    fun getSavedNews(): LiveData<List<NewsArticle>>

    suspend fun deleteNews(news: NewsArticle)

    suspend fun deleteAllNews()
}
