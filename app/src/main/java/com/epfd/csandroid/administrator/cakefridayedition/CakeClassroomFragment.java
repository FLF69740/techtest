package com.epfd.csandroid.administrator.cakefridayedition;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.epfd.csandroid.R;
import com.epfd.csandroid.administrator.cakefridayedition.recyclerview.CakeClassroomAdapter;
import com.google.common.base.Joiner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CakeClassroomFragment extends Fragment {

    @BindView(R.id.cake_classroom_fragment_classroom_recycler) RecyclerView mRecyclerView;

    private static final String ARG_PARAM1 = "param1";
    private CakeClassroom mCakeClassroom;
    private CakeClassroomAdapter mAdapter;

    public CakeClassroomFragment() {}

    public static CakeClassroomFragment newInstance(CakeClassroom cakeClassroom) {
        CakeClassroomFragment fragment = new CakeClassroomFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, cakeClassroom);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCakeClassroom = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cake_classroom, container, false);
        ButterKnife.bind(this, view);

        if (mCakeClassroom.getDatesPlannification() == null) {
            mCakeClassroom.setDatesPlannification(new ArrayList<>());
        }

        mAdapter = new CakeClassroomAdapter(mCakeClassroom.getDatesPlannification());
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mRecyclerView.setAdapter(mAdapter);





        return view;
    }

}
