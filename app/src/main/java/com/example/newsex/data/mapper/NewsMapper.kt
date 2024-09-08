package com.example.newsex.data.mapper

import com.example.newsex.domain.entity.TopHeadlinesModel
import com.example.newsex.data.datasource.TopHeadlinesResponse

class NewsMapper {

    fun transformTopHeadlinesModel(model: TopHeadlinesResponse?): List<TopHeadlinesModel> {
        return model?.articles?.map {
            TopHeadlinesModel(
                author = it.author.orEmpty(),
                content = it.content.orEmpty(),
                description = it.description.orEmpty(),
                publishedAt = it.publishedAt.orEmpty(),
                title = it.title.orEmpty(),
                url = it.url.orEmpty(),
                urlToImage = it.urlToImage.orEmpty(),
                id = it.source?.id.orEmpty(),
                name = it.source?.name.orEmpty()
            )
        }?:listOf()
    }
}