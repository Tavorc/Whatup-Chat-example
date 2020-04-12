package com.test.user.robotemitest.view.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.test.user.robotemitest.R;

public class ProfileDialog extends DialogFragment {

    private final static String URL="url";
    private final static String NAME="name";
    private final static String PHONE="phone";
    private final static String EMAIL="email";

    private ImageView mImageProfile;
    private TextView mUserName;
    private TextView mPhoneNumber;
    private TextView mEmail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_profile, container, false);
        mImageProfile=v.findViewById(R.id.imageProfile);
        mUserName=v.findViewById(R.id.userName);
         mPhoneNumber=v.findViewById(R.id.phoneNumber);
        mEmail=v.findViewById(R.id.email);

        Picasso.with(this.getActivity()).load(getArguments().getString(URL))
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(mImageProfile);
        mUserName.setText(getArguments().getString(NAME));
        mPhoneNumber.setText(getArguments().getString(PHONE));
        mEmail.setText(getArguments().getString(EMAIL));
        return v;
    }

    public static ProfileDialog newInstance(String imageUrl,String name,String phone,String email) {
        Bundle args = new Bundle();
        args.putString(URL,imageUrl);
        args.putString(NAME,name);
        args.putString(PHONE,phone);
        args.putString(EMAIL,email);
        ProfileDialog fragment = new ProfileDialog();
        fragment.setArguments(args);
        return fragment;
    }
}
