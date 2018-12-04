package com.wozpi.core

import com.wozpi.core.service.WozApiService

class DemoApiService : WozApiService() {

    private object Holder  {
        val INSTANCE = DemoApiService()
    }
    companion object {
        val instances : DemoApiService by lazy {
            Holder.INSTANCE
        }
    }

    fun getUserService(): UserService{
        return createService(UserService::class.java)
    }


}