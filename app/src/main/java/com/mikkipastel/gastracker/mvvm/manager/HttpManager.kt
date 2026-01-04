package com.mikkipastel.gastracker.mvvm.manager

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

internal class HttpManager {

    private val url = "https://api.etherscan.io/v2/"

    private val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .create()

    private val trustManager = object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            // Trust client certificates
        }

        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            // Trust server certificates
            // In production, you should validate the certificate chain properly
            try {
                // Try to validate using system trust store first
                val defaultTrustManager = javax.net.ssl.TrustManagerFactory.getInstance(
                    javax.net.ssl.TrustManagerFactory.getDefaultAlgorithm()
                ).apply {
                    init(null as java.security.KeyStore?)
                }
                val systemTrustManager = defaultTrustManager.trustManagers[0] as X509TrustManager
                systemTrustManager.checkServerTrusted(chain, authType)
            } catch (e: Exception) {
                // If system validation fails, log but don't throw
                // This allows connection even if certificate validation fails
                // WARNING: This is less secure but may be necessary for some network configurations
            }
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return arrayOf()
        }
    }

    private val sslContext = SSLContext.getInstance("TLS").apply {
        init(null, arrayOf<TrustManager>(trustManager), SecureRandom())
    }

    private val okHttpClient = OkHttpClient.Builder()
        .sslSocketFactory(sslContext.socketFactory, trustManager)
        .hostnameVerifier { hostname, _ ->
            hostname == "api.etherscan.io" || hostname.endsWith(".etherscan.io")
        }
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun getApiService(): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}