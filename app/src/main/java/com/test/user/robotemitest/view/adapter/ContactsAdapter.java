package com.test.user.robotemitest.view.adapter;

import android.content.Context;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.user.robotemitest.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {
    SortedList<ContactItem> mContactList;
    private  ClickListener mClickListener;
    private Context mContext;

    public ContactsAdapter(Context context) {

        /**
         * Define the sorted list
         */
        mContactList = new SortedList<ContactItem>(ContactItem.class, new SortedListAdapterCallback<ContactItem>(this) {
            @Override
            public int compare(ContactItem o1, ContactItem o2) {
                return o2.getLastTime().compareTo(o1.getLastTime());
            }

            @Override
            public boolean areContentsTheSame(ContactItem oldItem, ContactItem newItem) {
                return oldItem.getName().equals(newItem.getName());
            }

            @Override
            public boolean areItemsTheSame(ContactItem item1, ContactItem item2) {
                return item1.getIdContact() == item2.getIdContact();
            }
        });

        mContext= context;
    }


    public void addAll(List<ContactItem> contactItems) {
        mContactList.beginBatchedUpdates();
        for (int i = 0; i < contactItems.size(); i++) {
            mContactList.add(contactItems.get(i));
        }
        mContactList.endBatchedUpdates();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView lastMessage;
        private  TextView time;
        CircleImageView profileImage;
        LinearLayout itemLayout;

        public MyViewHolder(View view) {
            super(view);
            profileImage = view.findViewById(R.id.profile_image);
            name = view.findViewById(R.id.name);
            itemLayout = view.findViewById(R.id.itemLayout);
            lastMessage = view.findViewById(R.id.lastMessage);
            time = view.findViewById(R.id.time);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.contacts_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ContactItem contactItem = mContactList.get(position);
        holder.name.setText(contactItem.getName());
        holder.lastMessage.setText(contactItem.getMessage());
        holder.time.setText(contactItem.getTime());
        Picasso.with(mContext.getApplicationContext()).load(contactItem.getImage())
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(holder.profileImage);


        holder.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            mClickListener.onImageClick(position,contactItem.getIdContact());
            }
        });

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(position,contactItem.getIdContact());
            }
        });
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }

    public interface ClickListener {
        void onImageClick(int position,int idContact);
        void onItemClick(int position, int id);
    }
}
