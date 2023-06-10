package com.example.dismobileproject.data.api

import com.example.dismobileproject.data.repositories.*
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


interface AppContainer{
    val productRepository: ProductRepository
    val productParameterRepository: ProductParameterRepository
    val userRepository: UserRepository
}

class DefaultAppContainer : AppContainer{
    private val unsafeOkHttpClient: OkHttpClient
        get() {
            try {
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<java.security.cert.X509Certificate>,                        authType: String
                    ) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<java.security.cert.X509Certificate>,                        authType: String
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                        return arrayOf()
                    }
                })
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())
                val sslSocketFactory = sslContext.socketFactory
                val builder = OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder.hostnameVerifier { hostname, session -> true }
                return builder.build()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

    private val BASE_URL = "https://192.168.0.164:7031/api/mobile/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(unsafeOkHttpClient)
        .build()

    private val productService: ProductService by lazy {
        retrofit.create(ProductService::class.java)
    }

    override val productRepository: ProductRepository by lazy {
        NetworkProductRepository(productService)
    }


    private val productParameterService: ProductParameterService by lazy {
        retrofit.create(ProductParameterService::class.java)
    }

    override val productParameterRepository: ProductParameterRepository by lazy {
        NetworkProductParameterRepository(productParameterService)
    }


    private val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }

    override val userRepository: UserRepository by lazy {
        UserNetworkRepository(userService)
    }
}