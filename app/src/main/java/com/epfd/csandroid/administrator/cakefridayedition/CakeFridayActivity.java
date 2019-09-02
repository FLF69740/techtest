package com.epfd.csandroid.administrator.cakefridayedition;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.widget.Toast;

import com.epfd.csandroid.R;
import com.epfd.csandroid.api.CakeHelper;
import com.epfd.csandroid.api.ClassroomsHelper;
import com.epfd.csandroid.api.NewsHelper;
import com.epfd.csandroid.base.BaseActivity;
import com.epfd.csandroid.utils.Utils;
import com.google.android.material.tabs.TabLayout;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CakeFridayActivity extends BaseActivity implements CakeClassroomFragment.dataUpdate{

    private String mClassroomListSaved = "";
    private String mDateListSaved = "";

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_cake_friday;
    }

    @Override
    public Boolean isAChildActivity() {
        return true;
    }

    @Override
    public void start(@Nullable Bundle savedInstanceState) {
        ClassroomsHelper.getClassrooms().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.getString(Utils.NAME_DATA_CLASSROOMS) != null) {
                List<String> myList = new ArrayList<>(Arrays.asList(documentSnapshot.getString(Utils.NAME_DATA_CLASSROOMS).split(",")));
                myList.add(0, Utils.ALL);
                configureCakeDate(myList);
            }

        });
    }

    //get list of cake friday date
    private void configureCakeDate(List<String> classroomList){


        CakeHelper.getCakeEvent().addOnSuccessListener(documentSnapshot -> {
            List<CakeClassroom> cakeClassrooms = new ArrayList<>();
            for (String classroomString : classroomList){
                CakeClassroom cakeClassroom = new CakeClassroom();
                cakeClassroom.setClassroomCake(classroomString);
                cakeClassrooms.add(cakeClassroom);
            }

            if (documentSnapshot.getString(Utils.NAME_DATA_CAKE_CLASSROOMS) != null && documentSnapshot.getString(Utils.NAME_DATA_CAKE_DATE) != null) {
                mClassroomListSaved = documentSnapshot.getString(Utils.NAME_DATA_CAKE_CLASSROOMS);
                mDateListSaved = documentSnapshot.getString(Utils.NAME_DATA_CAKE_DATE);
                List<String> classroomListString = Arrays.asList(documentSnapshot.getString(Utils.NAME_DATA_CAKE_CLASSROOMS).split(","));
                List<String> dateListString = Arrays.asList(documentSnapshot.getString(Utils.NAME_DATA_CAKE_DATE).split(","));

                for (int i = 0; i < classroomListString.size(); i++) {
                    BusinessCakeFriday.getListCakeDateFromFirebase(cakeClassrooms, classroomListString.get(i), dateListString.get(i));
                }

                configureViewPager(cakeClassrooms);
            }else {
                CakeHelper.createCakeEvent("","");
                configureViewPager(cakeClassrooms);
            }
        });
    }

    //configure viewPager
    private void configureViewPager(List<CakeClassroom> cakeClassrooms){
        ViewPager viewPager = findViewById(R.id.cake_friday_viewpager);
        viewPager.setAdapter(new CakeFridayAdapter(getSupportFragmentManager(), 2, cakeClassrooms));
        TabLayout tabLayout = findViewById(R.id.cake_friday_tablayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public void cakeDataUp(String date, String classroom) {
        if (mClassroomListSaved.equals("")){
            mClassroomListSaved += classroom;
            mDateListSaved += date;
        }else {
            mClassroomListSaved += "," + classroom;
            mDateListSaved += "," + date;
        }
        CakeHelper.updateCakeEventClassrooms(mClassroomListSaved);
        CakeHelper.updateCakeEventDate(mDateListSaved);
        String dateChange = date.replace("/", "");
        String body;
        int tag = BusinessCakeFriday.getCakeTag(new DateTime());
        if (!classroom.equals(Utils.ALL)){
            body = getString(R.string.cake_edition_notification_body_part_intorduction) + " " + date +
                    getString(R.string.cake_edition_notification_body_part_one) + " " + classroom + " " + getString(R.string.cake_edition_notification_body_part_two);
        }else {
            body = getString(R.string.cake_edition_notification_body_part_intorduction) + " " + date +
                    getString(R.string.cake_edition_notification_body_part_one_bis) + " " + getString(R.string.cake_edition_notification_body_part_two);
        }
        NewsHelper.createNews(CakeHelper.getEventName(), date, true, Utils.getDayLessThree(null, date), getResources().getResourceEntryName(R.drawable.ic_cake_friday_photo),
                body, classroom, tag, CakeHelper.getCollectionName(), dateChange);
    }

    @Override
    public void cakeDataDown(String date, String classroom) {
        String classroomTemp = mClassroomListSaved;
        String dateTemp = mDateListSaved;
        mClassroomListSaved = BusinessCakeFriday.deleteCakeNameForFirebase(classroomTemp, BusinessCakeFriday.getCakePositionToDelete(classroomTemp, dateTemp, classroom, date));
        mDateListSaved = BusinessCakeFriday.deleteCakeDateForFirebase(dateTemp, BusinessCakeFriday.getCakePositionToDelete(classroomTemp, dateTemp, classroom, date));
        CakeHelper.updateCakeEventClassrooms(mClassroomListSaved);
        CakeHelper.updateCakeEventDate(mDateListSaved);
        date = date.replace("/","");
        NewsHelper.deleteNews(CakeHelper.getCollectionName() + date);
    }
}
