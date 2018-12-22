package murer.rudy.api

import com.google.gson.Gson


val gson: Gson = Gson()
fun <T> String.fromJson(clazz: Class<T>): T {
    return gson.fromJson(this, clazz)
}

fun Any.toJson(): String {
    return gson.toJson(this)
}