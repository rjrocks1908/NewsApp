package com.haxon.newsapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.haxon.newsapp.Models.Articles
import com.haxon.newsapp.Models.News
import com.haxon.newsapp.Models.NewsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val tag = "RESPONSE"
    private final var TAG = "MainActivity"
    lateinit var adapter: NewsAdapter
    lateinit var newsList : RecyclerView
    private var articles = mutableListOf<Articles>()
    var pageNum = 1
    var totalResults = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newsList = findViewById(R.id.newsList)

        adapter = NewsAdapter(this@MainActivity, articles)
        newsList.adapter = adapter
        val layoutManager = LinearLayoutManager(this@MainActivity)
        newsList.layoutManager = layoutManager

        getNews();
    }

    private fun getNews() {
        val news = NewsService.newsInstance.getHeadlines("in", pageNum)
        news.enqueue(object : Callback<News>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if (news != null) {
                    Log.e(tag, news.toString())
                    totalResults = news.totalResults
                    articles.addAll(news.articles)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.e(tag, "Error in fetching news", t)
            }

        })
    }
}