package com.google.codelabs.mdc.java.shrine;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.codelabs.mdc.java.shrine.models.HamburgerCard;
import com.google.codelabs.mdc.java.shrine.models.Profile;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

public class HamburgerMatch extends Fragment {

    private SwipePlaceHolderView mSwipeView;
    private Context mContext;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.matchear_hamburguesa, container, false);

        mSwipeView = (SwipePlaceHolderView) view.findViewById(R.id.swipeView);
        mContext = mContext.getApplicationContext();

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.hamburger_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.hamburger_swipe_out_msg_view));


        for (Profile profile : Utils.loadProfiles(this.mContext.getApplicationContext())) {
            mSwipeView.addView(new HamburgerCard(mContext, profile, mSwipeView));
        }

        view.findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(false);
            }
        });

        view.findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(true);
            }
        });

        return view;
    }
}

