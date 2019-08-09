package com.epfd.csandroid.administrator.cakefridayedition;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.epfd.csandroid.R;
import com.epfd.csandroid.administrator.cakefridayedition.recyclerview.CakeClassroomAdapter;
import com.epfd.csandroid.utils.RecyclerViewClickSupport;
import org.joda.time.DateTime;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CakeClassroomFragment extends Fragment {

    @BindView(R.id.cake_classroom_fragment_classroom_recycler) RecyclerView mRecyclerView;

    private static final String ARG_PARAM1 = "param1";
    private CakeClassroom mCakeClassroom;
    private CakeClassroomAdapter mAdapter;
    private dataUpdate mCallback;

    public interface dataUpdate{
        void cakeDataUp(String date, String classroom);
        void cakeDataDown(String date, String classroom);
    }

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

        this.configureRecycler();
        this.configureOnClickRecyclerView();

        return view;
    }

    //configure recyclerview
    private void configureRecycler(){
        mAdapter = new CakeClassroomAdapter(mCakeClassroom.getDatesPlannification());
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mRecyclerView.setAdapter(mAdapter);
    }

    //configure item click on RecyclerView
    private void configureOnClickRecyclerView(){
        RecyclerViewClickSupport.addTo(mRecyclerView, R.layout.cake_recycler_item)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    mCallback.cakeDataDown(mCakeClassroom.getDatesPlannification().get(position), mCakeClassroom.getClassroomCake());
                    List<String> temp = new ArrayList<>(mCakeClassroom.getDatesPlannification());
                    temp.remove(position);
                    mCakeClassroom.setDatesPlannification(temp);
                    configureRecycler();
                });
    }

    @OnClick(R.id.cake_classroom_fragment_floating_btn)
    public void clickOnCakeFloatingBtn(){
        new DatePickerDialog(getContext(), dateInsertion, mCalendarDateCake.getYear(), mCalendarDateCake.getMonthOfYear()-1, mCalendarDateCake.getDayOfMonth()).show();
    }

    /**
     *  DATE PICKER
     */

    private DateTime mCalendarDateCake = new DateTime();

    DatePickerDialog.OnDateSetListener dateInsertion = (view, year, month, dayOfMonth) -> {
        DateTime calendar = new DateTime();
        calendar = calendar.year().setCopy(year);
        calendar = calendar.monthOfYear().setCopy(month+1);
        calendar = calendar.dayOfMonth().setCopy(dayOfMonth);
        configureDataFromRecycler(calendar.toString("dd/MM/yyyy"));
    };

    //configure classroom datas
    private void configureDataFromRecycler(String date){
        int classroomDateSize = mCakeClassroom.getDatesPlannification().size();
        BusinessCakeFriday.updateCakeDateListString(mCakeClassroom, date);
        mAdapter.notifyDataSetChanged();

        if (classroomDateSize < mCakeClassroom.getDatesPlannification().size()){
            mCallback.cakeDataUp(date, mCakeClassroom.getClassroomCake());
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try { mCallback = (dataUpdate) getActivity(); }
        catch (ClassCastException e) { throw new ClassCastException(e.toString()+ " must implement OnButtonClickedListener"); }
    }


}
