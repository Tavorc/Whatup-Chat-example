package com.test.user.robotemitest.view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.test.user.robotemitest.R;
import de.hdodenhof.circleimageview.CircleImageView;


public class BaseHeader extends LinearLayout {

    private  Context mContext;
    private TextView mTitle;
    private ImageView mBackButton;
    private  BackClickListener mListner;
    private CircleImageView mProfileImage;

    public BaseHeader(Context context) {
        super(context);
        mContext=context;
        initView();
    }

    public BaseHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        initView();
    }

    public BaseHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        initView();
    }

    public void initView(){
        LayoutInflater.from(mContext).inflate(R.layout.base_header, this, true);
        mTitle = findViewById(R.id.title);
        mBackButton = findViewById(R.id.imageViewBack);
        mProfileImage = findViewById(R.id.profile_image);
        mBackButton.setOnClickListener(v -> mListner.onClickBackButton());
    }


    public void setListner(BackClickListener mListner) {
        this.mListner = mListner;
    }

    public void setVisibilityBackButton(int vis){
        mBackButton.setVisibility(vis);
    }

    public void setTitle(String title){
        mTitle.setText(title);
    }

    public void setVisibilityProfileImage(int vis){
        mProfileImage.setVisibility(vis);
    }

    public void setProfileImage(String url){
        Picasso.with(mContext.getApplicationContext()).load(url)
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(mProfileImage);
    }

    public interface BackClickListener{

        void onClickBackButton();
    }
}
