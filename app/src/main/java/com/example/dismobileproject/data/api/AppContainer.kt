package com.example.dismobileproject.data.api

import com.example.dismobileproject.data.repositories.NetworkProductParameterRepository
import com.example.dismobileproject.data.repositories.NetworkProductRepository
import com.example.dismobileproject.data.repositories.ProductParameterRepository
import com.example.dismobileproject.data.repositories.ProductRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


interface AppContainer{
//    val testRepository: TestRepository
    val productRepository: ProductRepository
    val productParameterRepository: ProductParameterRepository

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
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder.hostnameVerifier { hostname, session -> true }
                return builder.build()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

    private val BASE_URL = "https://192.168.0.164:7031/api/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(unsafeOkHttpClient)
        .build()

    /*private val retrofitService: TestService by lazy {
        retrofit.create(TestService::class.java)
    }*/
    /*override val testRepository: TestRepository by lazy {
        NetworkTestRepository(retrofitService)
    }*/
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
}