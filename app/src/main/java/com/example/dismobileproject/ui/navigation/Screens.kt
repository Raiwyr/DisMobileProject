package com.example.dismobileproject.ui.navigation

import com.example.dismobileproject.R

sealed class Screen(val screenName: String, val titleResourceId: Int) {
    object Main: Screen("main", -1)

    object Search: Screen("search",R.string.title_search)
    object Products: Screen("products", R.string.title_search)
    object ProductDetail: Screen("product_detail", R.string.title_product_detail)
    object Filter: Screen("filter", R.string.title_filter)
    object SelectionParameter: Screen("selection_parameter", R.string.title_selection_parameter)
    object SelectionEvaluation: Screen("selection_evaluation", R.string.title_selection_evaluation)
    object SelectionResults: Screen("selection_results", R.string.title_selection_results)

    object Test: Screen("test", R.string.title_test)
    object ProfileTest: Screen("proftest", -1)
    object TestContainer: Screen("testcont", R.string.title_test_cont)
}