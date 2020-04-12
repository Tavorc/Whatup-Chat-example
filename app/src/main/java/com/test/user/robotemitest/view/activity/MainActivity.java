package com.test.user.robotemitest.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.test.user.robotemitest.R;
import com.test.user.robotemitest.networking.pubnub.PubNubBuilder;
import com.test.user.robotemitest.view.adapter.ContactItem;
import com.test.user.robotemitest.view.adapter.ContactsAdapter;
import com.test.user.robotemitest.model.Contacts;
import com.test.user.robotemitest.presenter.ContactsPresenter;
import com.test.user.robotemitest.view.dialog.ProfileDialog;
import com.test.user.robotemitest.view.view.BaseHeader;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends BaseActivity implements ContactsPresenter.ContactsView {
    private ContactsPresenter mContactsPresenter;
    private RecyclerView mRecyclerView;
    private ContactsAdapter mAdapter;
    private final  static  String ID_CONTACT="id_contact";
    private BaseHeader mHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContactsPresenter=new ContactsPresenter(this);
        initView();
    }

    public void initView(){
        mHeader=findViewById(R.id.header);
        mHeader.setVisibilityBackButton(View.INVISIBLE);
        mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new ContactsAdapter(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        toggleLoader(true);
        mContactsPresenter.getContactsData();

        mAdapter.setOnItemClickListener(new ContactsAdapter.ClickListener() {
            @Override
            public void onImageClick(int position, int idContact) {
                Contacts contactById = mContactsPresenter.getContactById(idContact);
                ProfileDialog.newInstance(contactById.getAvatar(),contactById.getFirstName()+" "+contactById.getLastName(),contactById.getPhone(),contactById.getEmail()).show(getSupportFragmentManager(),"");
            }

            @Override
            public void onItemClick(int position, int id) {
                Intent intent = new Intent(getBaseContext(), ContactChatActivity.class);
                intent.putExtra(ID_CONTACT, id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void setData(List<Contacts> contacts) {
        toggleLoader(false);
        convertContactsToItemList(contacts);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Convert contacts list to the items of the adapter
     * @param contacts
     */
    private void convertContactsToItemList(List<Contacts> contacts){
        List<ContactItem> arrayListContact=new ArrayList<>();
        for(Contacts item: contacts){
            ContactItem contactItem=new ContactItem();
            contactItem.setIdContact(item.getId());
            contactItem.setImage(item.getAvatar());
            contactItem.setName(item.getFirstName() + " " + item.getLastName());

            if(item.getLastMessage()!=null){
                contactItem.setMessage(item.getLastMessage());
            }
            else{
                contactItem.setMessage("");
            }

            if(item.getLastTimeFormatted()!=null)
            {
                long rest = 0;
                @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                try {
                    Date now=new Date();
                    Date parse = dateFormat.parse(item.getLastTimeFormatted());
                    /**
                     * Calculate the difference between now and the time of the las message
                     */
                    rest =  now.getTime()- parse.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(rest>60000){
                    long minutes=rest/60000;
                    contactItem.setTime(minutes+" min ago");
                }
                if(rest<60000){
                        contactItem.setTime("today");
                 }
             }
             else{
               contactItem.setTime("");
             }

            if(item.getLastTime()!=0){
                contactItem.setLastTime(String.valueOf(item.getLastTime()));
            }
            else{
                contactItem.setLastTime("0");
            }

            arrayListContact.add(contactItem);
        }
        mAdapter.addAll(arrayListContact);
    }

    @Override
    public void onError(String message) {
        toggleLoader(false);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContactsPresenter.unbind();
        PubNubBuilder.getInstance().unsubscribe();
    }
}
