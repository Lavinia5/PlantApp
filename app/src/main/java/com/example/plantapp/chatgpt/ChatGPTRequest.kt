package com.example.plantapp.chatgpt

import okhttp3.*
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException

data class ChatGPTRequest(val model: String, val messages: List<Message>)
data class Message(val role: String, val content: String)
data class ChatGPTResponse(val choices: List<Choice>)
data class Choice(val message: Message)

class ChatGPTAPI(private val apiKey: String) {
    private val client = OkHttpClient()
    private val gson = Gson()
    private val url = "https://api.openai.com/v1/chat/completions"
    val chatGPTAPI = ChatGPTAPI("cheia-ta-api")

    fun sendMessage(messages: List<Message>, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        val requestBody = gson.toJson(ChatGPTRequest("gpt-3.5-turbo", messages))

        val request = Request.Builder()
            .url(url)
            .post(RequestBody.create("application/json".toMediaTypeOrNull(), requestBody))
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("Content-Type", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onError(e.message ?: "Request failed")
            }
            override fun onResponse(call: Call, response: Response) {
                try {
                    val responseBody = response.body?.string()
                    if (responseBody != null) {
                        val chatResponse = gson.fromJson(responseBody, ChatGPTResponse::class.java)
                        val reply = chatResponse.choices.firstOrNull()?.message?.content ?: "No reply"
                        onSuccess(reply)
                    } else {
                        onError("Response body is null")
                    }
                } catch (e: Exception) {
                    onError("Parsing error: ${e.message}")
                }
            }

        })
    }
}
