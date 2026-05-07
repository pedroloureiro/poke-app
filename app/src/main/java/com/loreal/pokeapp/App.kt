package com.loreal.pokeapp

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.network.ktor3.KtorNetworkFetcherFactory
import dagger.hilt.android.HiltAndroidApp
import io.ktor.client.HttpClient
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), SingletonImageLoader.Factory {
    @Inject lateinit var httpClient: HttpClient

    override fun newImageLoader(context: PlatformContext): ImageLoader =
        ImageLoader.Builder(context)
            .components { add(KtorNetworkFetcherFactory(httpClient)) }
            .build()
}
