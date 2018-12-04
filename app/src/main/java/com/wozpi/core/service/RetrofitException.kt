package com.wozpi.core.service

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import timber.log.Timber
import java.io.IOException
import java.lang.RuntimeException

class RetrofitException(message: String,private val mUrl: String?,
                        private val mResponse: Response<*>?,private val mKind: KIND,
                        private val mThrowable: Throwable?,private val mRetrofit: Retrofit?) : RuntimeException(message) {

    enum class KIND{
        /** An [IO Exception] occurred while communicating to the server.  */
        NETWORK,
        NETWORK_NOT_INTERNET,
        /** A non-200 HTTP status code was received from the server.  */
        HTTP,
        HTTP_422_WITH_DATA,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }

    /** The data returned from the server in the response body*/
    private var mServerError: ServerError? = null

    fun getErrorData(): ServerError?{
        return mServerError
    }

    companion object {
        fun httpError(url: String, response: Response<*>, retrofit: Retrofit):RetrofitException{
            val message = response.code().toString() + " " + response.message()
            return RetrofitException(message,url,response,KIND.HTTP,null,retrofit)
        }

        fun httpErrorWithObject(url: String, response: Response<*>, retrofit: Retrofit):RetrofitException{
            val message = response.code().toString() + " " + response.message()
            val errorException = RetrofitException(message,url,response,KIND.HTTP_422_WITH_DATA,null,retrofit)
            errorException.deserializeServerError()
            return errorException
        }

        fun networkError(throwable: Throwable): RetrofitException{
            return RetrofitException(throwable.localizedMessage,null,null,KIND.NETWORK,throwable,null)
        }

        fun networkErrorNotInternet(throwable: Throwable): RetrofitException{
            return RetrofitException(throwable.localizedMessage,null,null,KIND.NETWORK_NOT_INTERNET,throwable,null)
        }

        fun unexpectedError(throwable: Throwable): RetrofitException{
            return RetrofitException(throwable.localizedMessage,null,null,KIND.UNEXPECTED,throwable,null)
        }
    }


    private fun deserializeServerError(){
        if(mResponse?.errorBody() != null){
            try {
                mServerError = getBodyErrorAs(ServerError::class.java)
            }catch (e:IOException){
                Timber.e(e)
            }
        }
    }

    private fun <T> getBodyErrorAs(type: Class<T>): T?{
        return if(mResponse?.errorBody() == null || mRetrofit == null ){
            null
        }else{
            val converter: Converter<ResponseBody,T> = mRetrofit.responseBodyConverter(type, arrayOfNulls<Annotation>(0))
            converter.convert(mResponse.errorBody()!!)
        }
    }
}