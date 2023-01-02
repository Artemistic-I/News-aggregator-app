package com.example.newsaggregatorapp

import java.time.format.DateTimeFormatter
//import org.joda.time.format.ISODateTimeFormat
/*
 * Data model class to store article data
 */
class MyModel {
    var title: String? = null
    var image: String? = null
    var summary: String? = null
    var content: String? = null
    var sourceName: String? = null
    var publishedAt: String? = null //time and date of publication


    fun getArticleTitle(): String {
        return title.toString()
    }
    fun setArticleTitle(name: String) {
        this.title = name
    }

    //***********************************

    fun getArticleImage(): String {
        return image.toString()
    }
    fun setArticleImage(image_drawable: String) {
        this.image = image_drawable
    }

    //***********************************

    fun getArticleSummary(): String {
        return summary.toString()
    }
    fun setArticleSummary(summary: String) {
        this.summary = summary
    }

    //***********************************

    fun getArticleContent(): String {
        return content.toString()
    }
    fun setArticleContent(content: String) {
        this.content = content
    }

    //***********************************

    fun getArticleSourceName(): String {
        return sourceName.toString()
    }
    fun setArticleSourceName(sourceName: String) {
        this.sourceName = sourceName
    }

    //***********************************

    fun getArticlePublishedAt(): String {
        return publishedAt.toString()
    }
    fun setArticlePublishedAt(publishedAt: String) {
        //val parser : DateTimeFormatter = ISODateTimeFormat.basicOrdinalDateTimeNoMillis()
        this.publishedAt = publishedAt
    }
}