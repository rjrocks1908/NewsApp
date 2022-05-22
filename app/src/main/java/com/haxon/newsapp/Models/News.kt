package com.haxon.newsapp.Models

data class News(val status: String, val totalResults: Int, val articles: List<Articles>)
