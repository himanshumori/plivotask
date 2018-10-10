package com.plivotest.callhandler.listeners

interface CallEventListener : NotificationClickListener {

    fun onCallFailed()

    fun onShowMessage(msg:String)

    fun onShowDialer()
}