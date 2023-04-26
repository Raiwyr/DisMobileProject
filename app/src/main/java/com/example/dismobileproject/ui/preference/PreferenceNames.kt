package com.example.dismobileproject.ui.preference

sealed class PreferenceNames(val name: String){
    object FAIL_NAME: PreferenceNames("UserInfo")
    object IS_LOGGED: PreferenceNames("is_logged")
    object USER_ID: PreferenceNames("user_id")
}
