package com.wozpi.core.service

import okhttp3.Interceptor
import okhttp3.Response

class WozErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        /**
         * Wait to finish call res
         * */
        val response = chain.proceed(request)
        /**
         * Finish call res and show error
         * */

        return response
    }
}