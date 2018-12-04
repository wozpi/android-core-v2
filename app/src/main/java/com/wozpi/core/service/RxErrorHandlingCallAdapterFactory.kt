package com.wozpi.core.service

import com.wozpi.core.R
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import rx.Observable
import java.lang.reflect.Type

class RxErrorHandlingCallAdapterFactory : CallAdapter.Factory() {

    private val origin: RxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create()

    companion object {
        fun create(): CallAdapter.Factory{
            return RxErrorHandlingCallAdapterFactory()
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        val wrapper = origin.get(returnType,annotations,retrofit) as CallAdapter<R,Observable<*>>
        return RxCallAdapterWrapper(retrofit, wrapper)
    }


    private class RxCallAdapterWrapper(retrofit: Retrofit, callAdapter: CallAdapter<R,Observable<*>>) : CallAdapter<R,Observable<*>> {

        private val mRetrofit = retrofit
        private val mCallAdapter = callAdapter

        /**
         * Returns the value type that this adapter uses when converting the HTTP response body to a Java object.
         * */

        override fun adapt(call: Call<R>): Observable<*> {
            val callObservable = mCallAdapter.adapt(call) as Observable
            return callObservable.onErrorResumeNext {
                t -> Observable.error(asRetrofitException(t))
            }
        }

        /**
         * Returns the value type that this adapter uses when converting the HTTP response body to a Java object.
         * */

        override fun responseType(): Type {
            return mCallAdapter.responseType()
        }


        /**
         * Handle retrofit
         * */
        private fun asRetrofitException(throwable: Throwable) : RetrofitException{


             /** We had non-200 http error*/
            if (throwable is HttpException){
                val response = throwable.response()
            /** On out api 422's get metadata in the response.*/
                return if(throwable.code() == 422){
                    RetrofitException.httpErrorWithObject(response.raw().request().url().toString(),response,mRetrofit)
                }else{
                    RetrofitException.httpError(response.raw().request().url().toString(),response,mRetrofit)
                }

            }

//          TODO  check is have internet
//          TODO  check have internet but not to internet

//            TODO("when server not fond")


            return RetrofitException.unexpectedError(throwable)
        }
    }
}