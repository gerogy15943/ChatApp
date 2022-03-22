package com.example.telegramclone2.utils

sealed class MyEvent{
    data class LoggedEvent(val state: Boolean): MyEvent()
}
