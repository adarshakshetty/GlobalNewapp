
package com.global.news.domain.repository

import com.global.news.data.local.NewsDao
import com.global.news.data.model.NewsArticle
import com.global.news.data.model.NewsResponse
import com.global.news.data.remote.api.ApiHelper
import com.global.news.utils.NetworkResult
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val remoteDataSource: ApiHelper,
    private val localDataSource: NewsDao
) : INewsRepository {

    override suspend fun getNews(
        countryCode: String,
        pageNumber: Int
    ): NetworkResult<NewsResponse> {
        return try {
            val response = remoteDataSource.getNews(countryCode, pageNumber)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                NetworkResult.Success(result)
            } else {
                NetworkResult.Error("An error occurred")
            }
        } catch (e: Exception) {
            NetworkResult.Error("Error occurred ${e.localizedMessage}")
        }
    }

    override suspend fun searchNews(
        searchQuery: String,
        pageNumber: Int
    ): NetworkResult<NewsResponse> {
        return try {
            val response = remoteDataSource.searchNews(searchQuery, pageNumber)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                NetworkResult.Success(result)
            } else {
                NetworkResult.Error("An error occurred")
            }
        } catch (e: Exception) {
            NetworkResult.Error("Error occurred ${e.localizedMessage}")
        }
    }

    override suspend fun saveNews(news: NewsArticle) = localDataSource.insert(news)

    override fun getSavedNews() = localDataSource.getAllNews()

    override suspend fun deleteNews(news: NewsArticle) = localDataSource.deleteNews(news)

    override suspend fun deleteAllNews() = localDataSource.deleteAllNews()
}