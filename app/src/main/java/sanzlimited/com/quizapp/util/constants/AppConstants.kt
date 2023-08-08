package sanzlimited.com.quizapp.util.constants

import sanzlimited.com.quizapp.BuildConfig

object AppConstants {
    const val BASE_URL = BuildConfig.BASE_URL
    const val API_KEY = BuildConfig.API_KEY
}

enum class Categories(val categoryName: String) {
    LINUX("Linux"),
    DEVOPS("DevOps"),
    NETWORKING("Networking"),
    PROGRAMMING("Programming"),
    CLOUD("Cloud"),
    DOCKER("Docker"),
    KUBERNETES("Kubernetes"),
}