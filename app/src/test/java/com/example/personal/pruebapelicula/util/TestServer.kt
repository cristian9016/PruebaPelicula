package com.example.personal.getgithubprofile.util

import com.example.personal.pruebapelicula.data.model.Pelicula
import com.example.personal.pruebapelicula.data.model.Serie
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import okhttp3.HttpUrl
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.json.JSONObject
import org.mockito.Mock
import java.util.*

object TestServer {

    val httpUrl: HttpUrl by lazy { prepareServer() }
    private val gson: Gson = Gson()

    @Mock
    val result = JSONObject("" +
            "{" +
            "results: [" +
            "{" +
            "id:12345," +
            "title:'Pelicula de prueba'," +
            "}]" +
            "}")

    val movie = Pelicula(12345, "Pelicula de prueba", "prueba unitaria de pelicula", "", "", Date().toString(), 9.5f, 0)


    private fun prepareServer(): HttpUrl {
        val server = MockWebServer()

        val dispatcher: Dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse = when (request.path) {
                "/3/movie/popular?api_key=f5e9bf27655f6d95196bba24f231bd3a&language=es-ES" -> {
                    MockResponse().setResponseCode(200)
                            .setBody(gson.toJson(result))
                }
//                "/3/tv/popular" -> {
//                    val movie = Serie(12345, "Serie de prueba", "prueba unitaria de pelicula", "", "", Date().toString(), 9.5f, 0)
//                    MockResponse().setResponseCode(200)
//                            .setBody(gson.toJson(movie))
//                }
                else -> MockResponse().setResponseCode(404)
            }
        }

        server.setDispatcher(dispatcher)

        return server.url("/")
    }

}