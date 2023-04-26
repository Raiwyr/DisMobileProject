package com.example.dismobileproject.ui.navigation

import com.example.dismobileproject.R

sealed class Screen(val screenName: String) {
    object Main: Screen("main")

    object Search: Screen("search")
    object Products: Screen("products")
    object ProductDetail: Screen("product_detail")
    object Filter: Screen("filter")
    object SelectionParameter: Screen("selection_parameter")
    object SelectionEvaluation: Screen("selection_evaluation")
    object SelectionResults: Screen("selection_results")
    object Authorization: Screen("authorization")
    object Registration: Screen("registration")
    object NoAuthorization: Screen("no_authorization")
    object ShoppingCart: Screen("shopping_cart")
    object FormingOrder: Screen("forming_order")
    object Order: Screen("order")
}