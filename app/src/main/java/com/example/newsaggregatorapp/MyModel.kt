package com.example.newsaggregatorapp

import android.util.Log
import org.json.JSONObject
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

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
    var url: String? = null


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
        val str = content.toString()
        return str.substring(0, str.indexOf('['))
    }
    fun setArticleContent(content: String) {
        this.content = content
    }

    //***********************************

    fun getArticleSourceName(): String {
        val source = JSONObject(sourceName)
        return "source: " + source.getString("name")
    }
    fun setArticleSourceName(sourceName: String) {
        this.sourceName = sourceName
    }

    //***********************************

    fun getArticlePublishedAt(): String {
        return publishedAt.toString()
    }
    fun setArticlePublishedAt(publishedAt: String) {
        val Formatter = DateTimeFormatter.ofPattern("'published at 'h:mma dd MMM").withLocale( Locale.UK).withZone(ZoneId.of("UTC"))
        //Instant.parse(publishedAt)
        Log.d("=================", publishedAt)
        val smth = Instant.parse(publishedAt)
        this.publishedAt = Formatter.format(smth)
    }

    //***********************************

    fun getArticleURL(): String {
        return url.toString()
    }
    fun setArticleURL(url: String) {
        this.url = url
    }
}