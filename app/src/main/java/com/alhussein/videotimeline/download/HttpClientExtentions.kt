package com.alhussein.videotimeline.download

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.OutputStream
import kotlin.math.roundToInt

suspend fun HttpClient.downloadFile(file: OutputStream, url: String): Flow<DownloadResult> {
    return flow {
        try {
            val response = this@downloadFile.request<HttpResponse> {
                url(url)
                method = HttpMethod.Get
            }.response

            val data = ByteArray(response.contentLength()!!.toInt())
            var offset = 0

            do {
                val currentRead = response.content.readAvailable(data, offset, data.size)
                offset += currentRead
                val progress = (offset * 100f / data.size).roundToInt()
                emit(DownloadResult.Progress(progress))
            } while (currentRead > 0)

            response.close()

            if (response.status.isSuccess()) {
                withContext(Dispatchers.IO) {
                    file.run { write(data) }
                }
                emit(DownloadResult.Success)
            } else {
                emit(DownloadResult.Error("File not downloaded"))
            }
        } catch (e: TimeoutCancellationException) {
            emit(DownloadResult.Error("Connection timed out", e))
        } catch (t: Throwable) {
            emit(DownloadResult.Error("Failed to connect"))
        }
    }
}
