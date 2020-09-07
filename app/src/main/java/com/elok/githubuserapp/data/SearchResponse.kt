package com.elok.githubuserapp.data

data class SearchResponse(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)