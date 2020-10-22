package com.moji.sevenchallenge.helpers

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

object GsonHelper{
    private val gson by lazy { Gson() }

    fun toJson(src: Any?): String {
        return gson.toJson(src)
    }

    @Throws(JsonSyntaxException::class)
    fun <T> fromJson(json: String, classOfT: Class<T>): T? {
        return gson.fromJson(json, classOfT)
    }
}