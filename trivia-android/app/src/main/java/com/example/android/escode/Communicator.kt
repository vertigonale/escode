package com.example.android.escode

interface Communicator {

    fun passLevelIndex(levelIndex: Int)
    fun passHeartLevelCount(heartLevel: Int)
    fun passHeartTotalCount(heartTotal: Int)
}