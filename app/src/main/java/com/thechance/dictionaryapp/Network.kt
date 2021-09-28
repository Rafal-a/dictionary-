package com.thechance.dictionaryapp

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.*
import java.io.IOException
import java.net.URL

object Network {

    private val client = OkHttpClient()
    val gson = Gson()
    private val builder = FormBody.Builder()
    var result = ""
    var words: String? = ""


    fun sendData(word: String, source: String, target: String): State<Response> {

        val url = UrlModifier.getUrlInfo(word, source, target)
        val request = Request.Builder()
            .url(url)
            .post(builder.build())
            .build()

        val response = client.newCall(request).execute()

        return if (response.isSuccessful) {
            val responseData = Gson()
                .fromJson(
                response.body?.string(), Response::class.java
            )


            State.Success(responseData)

        } else {

            State.Error(response.message)
        }

    }

    object TAG {
        const val LOG_TAG = "REQUEST"
    }


}






