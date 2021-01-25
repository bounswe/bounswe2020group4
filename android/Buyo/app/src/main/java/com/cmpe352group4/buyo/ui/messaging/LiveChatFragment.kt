package com.cmpe352group4.buyo.ui.messaging

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.api.Status
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.viewmodel.MessagesViewModel
import com.cmpe352group4.buyo.vo.*
import com.cmpe352group4.buyo.util.extensions.visible
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.fragment_live_chat.*
import javax.inject.Inject


class LiveChatFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var sharedPref: SharedPref

    lateinit var mSocket: Socket

    lateinit var userId: String
    lateinit var userType: String
    lateinit var withId: String
    lateinit var withType: String
    lateinit var withName: String

    val gson: Gson = Gson()

    val socketUrl = "http://3.138.113.101:5003"

    var messages = mutableListOf(LiveChatMessage(user = MessageUserInfo(
        id = "5fff449804ba0e00144ec689", name = "Koray", userType = "vendor"),
        id = "5fff4768773c3e001488eb75",
        date = "2021-01-13T19:18:00.345Z",
        message = "Last test message"),
        LiveChatMessage(user = MessageUserInfo(
            id = "600dd2fcd51fb300124636e2", name = "Olcay", userType = "customer"),
            id = "5fff4748773c3e001488eb74",
            date = "2021-01-13T19:17:28.002Z",
            message = "Second test message"),
        LiveChatMessage(user = MessageUserInfo(
            id = "600dd2fcd51fb300124636e2", name = "Olcay", userType = "customer"),
            id = "5fff470fd3e781001467add6",
            date = "2021-01-13T19:16:31.660Z",
            message = "First test message"),
        LiveChatMessage(user = MessageUserInfo(
            id = "5fff449804ba0e00144ec689", name = "Koray", userType = "vendor"),
            id = "5fff4768773c3e001488eb75",
            date = "2021-01-13T19:18:00.345Z",
            message = "Third test message"),
        LiveChatMessage(user = MessageUserInfo(
            id = "600dd2fcd51fb300124636e2", name = "Olcay", userType = "customer"),
            id = "5fff4748773c3e001488eb74",
            date = "2021-01-13T19:17:28.002Z",
            message = "Second test message"),
        LiveChatMessage(user = MessageUserInfo(
            id = "600dd2fcd51fb300124636e2", name = "Olcay", userType = "customer"),
            id = "5fff470fd3e781001467add6",
            date = "2021-01-13T19:16:31.660Z",
            message = "First test message"),
        LiveChatMessage(user = MessageUserInfo(
            id = "5fff449804ba0e00144ec689", name = "Koray", userType = "vendor"),
            id = "5fff4768773c3e001488eb75",
            date = "2021-01-13T19:18:00.345Z",
            message = "Third test message"),
        LiveChatMessage(user = MessageUserInfo(
            id = "600dd2fcd51fb300124636e2", name = "Olcay", userType = "customer"),
            id = "5fff4748773c3e001488eb74",
            date = "2021-01-13T19:17:28.002Z",
            message = "Second test message"),
        LiveChatMessage(user = MessageUserInfo(
            id = "600dd2fcd51fb300124636e2", name = "Olcay", userType = "customer"),
            id = "5fff470fd3e781001467add6",
            date = "2021-01-13T19:16:31.660Z",
            message = "First test message")
        )

    private val messageViewModel: MessagesViewModel by viewModels {
        viewModelFactory
    }

    private val liveChatAdapter by lazy {
        LiveChatAdapter(mutableListOf(), sharedPref.getUserId() ?: "")
    }


    companion object {
        private const val WITHID = "with_id"
        private const val WITHTYPE = "with_type"
        private const val WITHNAME = "with_name"
        fun newInstance(withId: String, withType: String, withName: String) = LiveChatFragment().apply {
            arguments = Bundle().apply {
                putString(WITHID, withId)
                putString(WITHTYPE, withType)
                putString(WITHNAME, withName)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_live_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sendButtonListener()

        withId = arguments?.getString(WITHID) ?: ""
        withType = arguments?.getString(WITHTYPE) ?: ""
        withName = arguments?.getString(WITHNAME) ?: ""
        val withInfo = "$withName($withType)"

        tv_live_chat_with_info.text = withInfo

        if(!sharedPref.getUserId().isNullOrEmpty() && !sharedPref.getUserType().isNullOrEmpty()){
            userId = sharedPref.getUserId() ?: ""
            userType = sharedPref.getUserType() ?: ""

            connectToSocket()

            val reverse: MutableList<LiveChatMessage> = messages.reversed().toMutableList();
            liveChatAdapter.submitList(reverse)


            messageViewModel.onFetchLiveChatMessages(LiveChatMessagesRequest(
                                                                            id = userId,
                                                                            userType = userType,
                                                                            withId = withId,
                                                                            withType = withType
                                                                            ))

            messageViewModel.liveChatMessages.observe(viewLifecycleOwner, Observer {
                if (it.status == Status.SUCCESS && it.data != null) {


                    if(it.data.messages.isNullOrEmpty()){
                        tv_live_chat_empty.visible = true
                    }else{
                        tv_live_chat_empty.visible = false
                        liveChatAdapter.submitList(it.data.messages?.reversed() as MutableList<LiveChatMessage>)
                    }

                    dispatchLoading()
                } else if (it.status == Status.ERROR) {
                    dispatchLoading()
                } else if (it.status == Status.LOADING) {
                    showLoading()
                }
            })

            rv_live_chat.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            rv_live_chat.adapter = liveChatAdapter
            rv_live_chat.post(Runnable { rv_live_chat.smoothScrollToPosition(liveChatAdapter.getItemCount() - 1) })
        }

        live_chat_back_button.setOnClickListener {
            activity?.onBackPressed()
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        mSocket.disconnect()
    }

    private fun sendButtonListener(){
        btnSend.setOnClickListener {
            if(txtMessage.text.isNotEmpty()) {
                mSocket.on("message", sentMessage)
            } else {
                Toast.makeText(context,"Message should not be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun connectToSocket(){

        try {
            mSocket = IO.socket(socketUrl)
            Log.d("success", mSocket.id())

        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("fail", "Failed to connect")
        }

        mSocket.connect()
        mSocket.on("discover", onDiscover)
        mSocket.on("message", receiveMessage)

    }

    private var onDiscover = Emitter.Listener {

        val data = DiscoverUserInfo(userType, userId)
        val jsonData = gson.toJson(data) // Gson changes data object to Json type.
        mSocket.emit("discover", jsonData)
    }

    private var receiveMessage = Emitter.Listener {

        val message: SendMessageSocket = gson.fromJson(it[0].toString(), SendMessageSocket::class.java)
        if(withId == message.withId){
//            val currentTime = LocalDateTime.now().toString()
            val liveChatMessage = LiveChatMessage(id = "", date = "", message = message.message,
                user = MessageUserInfo(id = withId, name = withName, userType = withType))
            addItemToRecyclerView(liveChatMessage)
        }
    }

    private var sentMessage = Emitter.Listener {
        val message = txtMessage.text.toString()
        val data = SendMessageSocket(id = userId, userType = userType, withId = withId, withType = withType, message = message)
        val jsonData = gson.toJson(data) // Gson changes data object to Json type.
        mSocket.emit("message", jsonData)

//        val currentTime = LocalDateTime.now().toString()
        val liveChatMessage = LiveChatMessage(id = "", date = "", message = message,
            user = MessageUserInfo(id = userId, name = "", userType = userType))

        addItemToRecyclerView(liveChatMessage)

    }

    private fun addItemToRecyclerView(message: LiveChatMessage) {
        activity?.runOnUiThread {
            liveChatAdapter.addMessage(message)
            rv_live_chat.smoothScrollToPosition(liveChatAdapter.getItemCount() - 1)
        }
    }

}