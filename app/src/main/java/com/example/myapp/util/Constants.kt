package com.example.myapp.util

import androidx.compose.runtime.Stable

class Constants {

    companion object {
        /**
         * Notification channel details
         */
        const val NOTIFICATION_CHANNEL_ID = "MyForegroundServiceChannel"
        const val NOTIFICATION_ID = 1
        const val REQUEST_CODE = 0x01;


        /**
         *
         * Error Messages
         */
        const val API_INTERNET_MESSAGE = "No Internet Connection"
        const val API_SOMETHING_WENT_WRONG_MESSAGE = "Something went wrong"
        const val API_FAILED_CODE = "500"
        const val API_SUCCESS_CODE = "9999"
        const val API_FAILURE_CODE = "5555"
        const val API_INTERNET_CODE = "500"


        /**
         * Offline Values Lists
         * */
        const val USER_PREFERENCES = "USER_LOGIN"
        const val OFFLINE_DATABASE = "app_database"
        const val NOTE_TABLE = "NOTE_TABLE"

        /**
         * View types
         * */
        const val CAMPAIGN_VIEW_TYPE_TYPE = 0
        const val FOLLOW_UP_VIEW_TYPE_TYPE = 1
        const val DASHBOARD_MENU_VIEW_TYPE = 2

        /**
         * API Lists
         */

        @Stable
        const val BASE_URL = "http://10.0.2.2:3000/api/v1/"

        @Stable
        const val API_LOGON = "login"

        const val API_KEY = ""

        @Stable
        const val PLATFORM = "android"

    }
}