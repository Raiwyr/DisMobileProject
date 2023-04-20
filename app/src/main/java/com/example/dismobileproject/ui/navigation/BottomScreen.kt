package com.example.dismobileproject.ui.navigation

import com.example.dismobileproject.R

sealed class BottomScreen(val screenName: String, val iconId: Int, val titleResourceId: Int){
    object Home: BottomScreen("home", R.drawable.home_icon,  R.string.title_home)
    object Category: BottomScreen("category", R.drawable.catalog_icon,  R.string.title_category)
    object Profile: BottomScreen("profile", R.drawable.profile_icon,  R.string.title_profile)
}