package com.example.personal.getgithubprofile.util

import okhttp3.HttpUrl
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

object TestServer {

    val httpUrl: HttpUrl by lazy { prepareServer() }
    val bodyMovie = "{\"results\":[{\"id\":\"12345\",\"title\":\"Pelicula de prueba\"},{\"id\":\"12346\",\"title\":\"Pelicula de prueba 2\"}]}"
    val bodySerie = "{\"results\":[{\"id\":\"12347\",\"name\":\"Serie de prueba\"},{\"id\":\"12348\",\"name\":\"Serie de prueba 2\"}]}"

    private fun prepareServer(): HttpUrl {
        val server = MockWebServer()

        val dispatcher: Dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse = when (request.path) {
                "/3/movie/popular?api_key=f5e9bf27655f6d95196bba24f231bd3a&language=es-ES" -> {
                    MockResponse().setResponseCode(200)
                            .setBody(bodyMovie)
                }
                "/3/search/movie?api_key=f5e9bf27655f6d95196bba24f231bd3a&query=prueba&language=es-ES" -> {
                    MockResponse().setResponseCode(200)
                            .setBody(bodyMovie)
                }
                "/3/search/tv?api_key=f5e9bf27655f6d95196bba24f231bd3a&query=prueba&language=es-ES" -> {
                    MockResponse().setResponseCode(200)
                            .setBody(bodySerie)
                }
                else -> MockResponse().setResponseCode(404)
            }
        }

        server.setDispatcher(dispatcher)

        return server.url("/")
    }

}