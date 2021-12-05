package com.alhussein.videotimeline.download

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import java.io.OutputStream
import kotlin.math.roundToInt

@ExperimentalCoroutinesApi
suspend fun HttpClient.downloadFile(file: OutputStream, url: String): Flow<DownloadResult> {
    return channelFlow {

        try {
            val client = HttpClient(Android)
            var offset: Long = 0

            val httpResponse: HttpResponse = client.get(url) {
                onDownload { bytesSentTotal, contentLength ->
                    println("Received $bytesSentTotal bytes from $contentLength")


                    offset += bytesSentTotal
                    val progress = (bytesSentTotal * 100f / contentLength).roundToInt()
                    println("progress: $progress")
                    send(DownloadResult.Progress(progress))

                    println("byteToSent $bytesSentTotal")
//                    response.close()


                }
            }
            if (httpResponse.status.isSuccess()) {
                withContext(Dispatchers.IO) {
                    val responseBody: ByteArray = httpResponse.receive()

                    file.run { write(responseBody) }
                }
                send(DownloadResult.Success)
            } else {
                send(DownloadResult.Error("File not downloaded"))
            }



        } catch (e: TimeoutCancellationException) {
            send(DownloadResult.Error("Connection timed out", e))
        } catch (t: Throwable) {
            send(DownloadResult.Error("Failed to connect"))
        }


    }
}
