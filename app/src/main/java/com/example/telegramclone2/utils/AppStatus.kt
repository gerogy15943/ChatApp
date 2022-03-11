package com.example.telegramclone2.utils

enum class AppStatus(val state: String) {
    ONLINE("в сети"),
    OFFLINE("был недавно"),
    TYPING("печатает")
}