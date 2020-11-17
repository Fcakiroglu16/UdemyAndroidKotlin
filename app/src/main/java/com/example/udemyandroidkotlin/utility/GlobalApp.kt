package com.example.udemyandroidkotlin.utility

import android.app.Application
import android.content.Context

class GlobalApp:Application()
{


    companion object
    {
        private  lateinit var mContext:Context

        public  fun getAppContext():Context
        {
            return mContext;
        }


    }

    override fun onCreate() {
        super.onCreate()
        mContext=this;
    }

}