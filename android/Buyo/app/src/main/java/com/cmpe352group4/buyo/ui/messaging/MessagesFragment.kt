package com.cmpe352group4.buyo.ui.messaging

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.api.Status
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.util.extensions.visible
import com.cmpe352group4.buyo.viewmodel.MessagesViewModel
import com.cmpe352group4.buyo.vo.LastMessageRequest
import com.cmpe352group4.buyo.vo.LastMessages
import com.cmpe352group4.buyo.vo.MessageUserInfo
import kotlinx.android.synthetic.main.fragment_messages.*
import javax.inject.Inject

class MessagesFragment : BaseFragment() {



    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var sharedPref: SharedPref

    var lastMessageList = mutableListOf(LastMessages(user = MessageUserInfo(
        id = "5fff449804ba0e00144ec689", name = "Koray", userType = "customer"),
        date = "2021-01-13T19:18:00.345Z",
        lastMessage = "Test message"))

    private val messageViewModel: MessagesViewModel by viewModels {
        viewModelFactory
    }

    private val lastMessageAdapter by lazy {
        LastMessagesAdapter(mutableListOf()
        ) { UserInfo ->
            navigationManager?.onReplace(
                LiveChatFragment.newInstance(withId = UserInfo.id, withType = UserInfo.userType, withName = UserInfo.name),
                TransactionType.Replace, true
            )
        }
    }


    companion object {
        fun newInstance() = MessagesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!sharedPref.getUserId().isNullOrEmpty() && !sharedPref.getUserType().isNullOrEmpty()){
            val userId = sharedPref.getUserId()
            val userType = sharedPref.getUserType()
            messageViewModel.onFetchLastMessages(LastMessageRequest(id = userId ?: "", userType = userType ?: ""))

            messageViewModel.lastMessages.observe(viewLifecycleOwner, Observer {
                if (it.status == Status.SUCCESS && it.data != null) {

                    lastMessageAdapter.submitList(lastMessageList)

//                    if(it.data.lastMessages.isNullOrEmpty()){
//                        tv_message_empty.visible = true
//                    }else{
//                        tv_message_empty.visible = false
//                        lastMessageAdapter.submitList(it.data.lastMessages as MutableList<LastMessages>)
//                    }

                    dispatchLoading()
                } else if (it.status == Status.ERROR) {
                    dispatchLoading()
                } else if (it.status == Status.LOADING) {
                    showLoading()
                }
            })

            val decorator = DividerItemDecoration(rv_messages.context, LinearLayoutManager.VERTICAL)
            decorator.setDrawable(ContextCompat.getDrawable(rv_messages.context, R.drawable.divider_drawable)!!)
            rv_messages.addItemDecoration(decorator)
            rv_messages.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            rv_messages.adapter = lastMessageAdapter
        }

        messages_back_button.setOnClickListener {
            activity?.onBackPressed()
        }

    }

}