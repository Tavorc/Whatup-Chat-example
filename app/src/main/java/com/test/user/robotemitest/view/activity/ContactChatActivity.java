package com.test.user.robotemitest.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.test.user.robotemitest.R;
import com.test.user.robotemitest.model.Contacts;
import com.test.user.robotemitest.model.Message;
import com.test.user.robotemitest.presenter.ChatPresenter;
import com.test.user.robotemitest.view.adapter.ChatAdapter;
import com.test.user.robotemitest.view.adapter.ChatMessage;
import com.test.user.robotemitest.view.view.BaseHeader;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

public class ContactChatActivity extends AppCompatActivity implements BaseHeader.BackClickListener {

    private BaseHeader mHeader;
    private final  static  String ID_CONTACT="id_contact";
    private int mCurrentId;
    private RecyclerView mRecyclerView;
    private ChatAdapter mAdapter;
    private List<ChatMessage> mMessageList = new ArrayList<>();
    private ChatPresenter mPresenter;
    private Contacts mContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_chat);

        //Get id contact from main activity
        mCurrentId = getIntent().getIntExtra(ID_CONTACT,0);
        mPresenter =new  ChatPresenter();
        mContact = mPresenter.getContactById(mCurrentId);
        initView();
    }

    public void initView(){
        mHeader=findViewById(R.id.headerChat);
        mHeader.setListner(this);
        mHeader.setVisibilityProfileImage(View.VISIBLE);
        mHeader.setProfileImage(mContact.getAvatar());
        mHeader.setTitle(mContact.getFirstName()+" "+ mContact.getLastName());
        mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new ChatAdapter(mMessageList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        initList();
    }

    /**
     * Initialize the list of messages
     */
    private void initList() {
        RealmResults<Message> messages = mPresenter.getMessages(String.valueOf(mCurrentId));
        for(Message item: messages){
            ChatMessage chatMessage=new ChatMessage();
            chatMessage.setMessage(item.getMessage());
            chatMessage.setSender(item.getIdUserSender());
            chatMessage.setTimestamp(item.getTime());
            mMessageList.add(chatMessage);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickBackButton() {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }
}
