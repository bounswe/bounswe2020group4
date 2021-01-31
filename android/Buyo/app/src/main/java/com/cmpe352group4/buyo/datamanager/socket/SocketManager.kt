package com.cmpe352group4.buyo.datamanager.socket

import android.content.Context
import com.cmpe352group4.buyo.dependencyinjection.ApplicationContext
import com.cmpe352group4.buyo.util.GeneralUtil
import com.cmpe352group4.buyo.vo.DiscoverUserInfo
import com.cmpe352group4.buyo.vo.SendMessageSocket
import com.google.gson.Gson
import com.google.gson.JsonParseException
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import javax.inject.Inject


private const val SOCKET_DISCOVER = "discover"
private const val SOCKET_RECEIVE_MESSAGE = "receive"
private const val SOCKET_SENT_MESSAGE = "sent"


val SOCKET_URL = "http://3.141.25.245:5003"

class SocketManager @Inject constructor(
    @ApplicationContext private val context: Context
)  {
//    private val options = IO.Options().apply {
//        transports = arrayOf(WebSocket.NAME)
//    }
    private val socket: Socket = IO.socket(SOCKET_URL)
    private var socketChangesListener: ISocketChangesListener? = null
    private val gson = Gson()

    private val onConnect = Emitter.Listener {
        socketChangesListener?.onConnected()

    }
//    private val onDisconnect = Emitter.Listener {
//        socketChangesListener?.onDisconnected()
//    }
//
//    private val onConnectError = Emitter.Listener {
//        socketChangesListener?.onConnectionError()
//    }

    private val onReceiveMessage = Emitter.Listener { socketResponse ->
        val responseFromSocket = gson.fromJson(socketResponse.first().toString(), SendMessageSocket::class.java)

//        val responseFromSocket = GeneralUtil.convertFromJson<SendMessageSocket>(socketResponse.first().toString(), jsonParser)
        socketChangesListener?.onMessageReceived(responseFromSocket)
    }

    fun setSocketChangesListener(socketChangesListener: ISocketChangesListener) {
        this.socketChangesListener = socketChangesListener
    }

    fun addAllSocketListener() {
        socket.on(Socket.EVENT_CONNECT, onConnect)
//        socket.on(Socket.EVENT_DISCONNECT, onDisconnect)
//        socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
        socket.on(SOCKET_RECEIVE_MESSAGE, onReceiveMessage)
    }

    fun clearAllSocketListener() {
        socket.off(Socket.EVENT_CONNECT, onConnect)
//        socket.off(Socket.EVENT_DISCONNECT, onDisconnect)
//        socket.off(Socket.EVENT_CONNECT_ERROR, onConnectError)
        socket.off(SOCKET_RECEIVE_MESSAGE, onReceiveMessage)
        onDisconnect()
    }


    fun onDiscover(userInfo: DiscoverUserInfo) {
        val jsonData = gson.toJson(userInfo) // Gson changes data object to Json type.
        socket.emit(SOCKET_DISCOVER,  jsonData)
    }

    fun onSentMessage(message: SendMessageSocket) {
        val jsonData = gson.toJson(message) // Gson changes data object to Json type.
        socket.emit(SOCKET_SENT_MESSAGE,  jsonData)

    }

    fun onDisconnect() {
        socket.disconnect()
    }

    fun onConnect() {
        socket.connect()
    }

}