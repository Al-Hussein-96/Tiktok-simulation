package com.alhussein.videotimeline.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class KtorModule {

    @Singleton
    @Provides
    fun getKtorHttpClient(): HttpClient = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        defaultRequest {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
        engine {
            // this: AndroidEngineConfig
            connectTimeout = 20_000
            socketTimeout = 20_000
        }
    }

}