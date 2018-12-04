package com.wozpi.core

import retrofit2.http.GET
import rx.Observable

interface UserService {
    @GET("search?q=title:DNA")
    fun getProfile() : Observable<User>
}