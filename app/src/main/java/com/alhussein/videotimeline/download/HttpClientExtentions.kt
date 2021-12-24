package com.alhussein.videotimeline.download

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import java.io.OutputStream
import kotlin.math.roundToInt

@ExperimentalCoroutinesApi
suspend fun HttpClient.downloadFile(file: OutputStream, url: String): Flow<DownloadResult> {
    return callbackFlow {
        try {
            val client = HttpClient(Android)
            client.get<HttpStatement>(url).execute { httpResponse ->
                val channel: ByteReadChannel = httpResponse.receive()
                var totalByte:Long = 0
                while (!channel.isClosedForRead) {
                    val packet = channel.readRemaining(DEFAULT_BUFFER_SIZE.toLong())
                    while (!packet.isEmpty) {
                        val bytes = packet.readBytes()
                        file.run {
                            write(bytes)
                            totalByte += bytes.size
                        }
                        val percent: Int =
                            (totalByte * 100 / httpResponse.contentLength()!!).toInt()
                        trySend(DownloadResult.Progress(percent))
                        println("Received ${totalByte} bytes from ${httpResponse.contentLength()}")
                    }
                }
                if (httpResponse.status.isSuccess()) {
                    file.close()
                    trySend(DownloadResult.Success)
                } else {
                    trySend(DownloadResult.Error("File not downloaded"))
                }
//                println("A file saved to ${file.path}")
            }
        } catch (e: TimeoutCancellationException) {
            trySend(DownloadResult.Error("Connection timed out"))
        } catch (t: Throwable) {
            trySend(DownloadResult.Error("Failed to connect"))
        }
        awaitClose()
    }
}
