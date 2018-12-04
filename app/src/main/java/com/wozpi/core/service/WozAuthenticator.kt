package com.wozpi.core.service

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class WozAuthenticator(private val mNameHeader:String,private val mAuthentication:String ?) : Authenticator {

    override fun authenticate(route: Route, response: Response): Request? {
        return if(mAuthentication == null){
            null
        }else{
            response.request().newBuilder()
                .header(mNameHeader,mAuthentication)
                .build()

        }
    }
}