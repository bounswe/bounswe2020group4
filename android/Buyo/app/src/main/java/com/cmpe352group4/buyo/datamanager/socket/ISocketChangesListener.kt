package com.cmpe352group4.buyo.datamanager.socket

import com.cmpe352group4.buyo.vo.SendMessageSocket

interface ISocketChangesListener {
    fun onConnected()
//    fun onConnectionError()
//    fun onSuccess()
//    fun onDisconnected()
    fun onMessageReceived(message: SendMessageSocket)
}