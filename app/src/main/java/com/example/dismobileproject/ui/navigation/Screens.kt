package com.example.dismobileproject.ui.navigation

import com.example.dismobileproject.R

sealed class Screen(val screenName: String, val iconId: Int, val titleResourceId: Int) {
    object Main: Screen("main", -1, -1)
    object Home: Screen("home", R.drawable.icon, R.string.title_home)
    object Category: Screen("category", R.drawable.icon, R.string.title_category)
    object Profile: Screen("profile", R.drawable.icon, R.string.title_profile)
    object Search: Screen("search", -1, R.string.title_search)
    object Products: Screen("products", -1, R.string.title_search)
    object ProductDetail: Screen("product_detail", -1, R.string.title_product_detail)
    object Filter: Screen("filter", -1, R.string.title_filter)
    object SelectionParameter: Screen("selection_parameter", -1, R.string.title_selection_parameter)
    object SelectionEvaluation: Screen("selection_evaluation", -1, R.string.title_selection_evaluation)
    object SelectionResults: Screen("selection_results", -1, R.string.title_selection_results)

    object Test: Screen("test", -1,R.string.title_test)
    object ProfileTest: Screen("proftest", -1,-1)
    object TestContainer: Screen("testcont", -1, R.string.title_test_cont)
}