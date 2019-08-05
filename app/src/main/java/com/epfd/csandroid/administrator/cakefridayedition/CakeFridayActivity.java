package com.epfd.csandroid.administrator.cakefridayedition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.epfd.csandroid.R;
import com.epfd.csandroid.api.CakeHelper;
import com.epfd.csandroid.api.ClassroomsHelper;
import com.epfd.csandroid.base.BaseActivity;
import com.epfd.csandroid.models.CakeFriday;
import com.epfd.csandroid.utils.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CakeFridayActivity extends BaseActivity {

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
        ClassroomsHelper.getClassrooms().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.getString(Utils.NAME_DATA_CLASSROOMS) != null) {
                    List<String> myList = new ArrayList<>(Arrays.asList(documentSnapshot.getString(Utils.NAME_DATA_CLASSROOMS).split(",")));
                    myList.add(0, "ALL");
                    configureCakeDate(myList);
                }

            }
        });
    }

    //get list of cake friday date
    private void configureCakeDate(List<String> classroomList){


        CakeHelper.getCakeEvent().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                List<CakeClassroom> cakeClassrooms = new ArrayList<>();
                for (String classroomString : classroomList){
                    CakeClassroom cakeClassroom = new CakeClassroom();
                    cakeClassroom.setClassroomCake(classroomString);
                    cakeClassrooms.add(cakeClassroom);
                }


                if (documentSnapshot.getString(Utils.NAME_DATA_CAKE_CLASSROOMS) != null && documentSnapshot.getString(Utils.NAME_DATA_CAKE_DATE) != null) {
            //        List<String> classroomListString = Arrays.asList(documentSnapshot.getString(Utils.NAME_DATA_CAKE_CLASSROOMS).split(","));
            //        List<String> dateListString = Arrays.asList(documentSnapshot.getString(Utils.NAME_DATA_CAKE_DATE).split(","));

                    List<String> classroomListString = new ArrayList<>();
                    List<String> dateListString = new ArrayList<>();
                    classroomListString.add("ALL"); classroomListString.add("CP"); classroomListString.add("PS/MS");
                    dateListString.add("01/01/2001");dateListString.add("01/01/2002");dateListString.add("01/01/2010");
                    classroomListString.add("ALL"); classroomListString.add("CP"); classroomListString.add("PS/MS");
                    dateListString.add("01/01/2011");dateListString.add("01/01/2012");dateListString.add("01/01/2020");

                    for (int i = 0; i < classroomListString.size(); i++) {
                        BusinessCakeFriday.getListWithInsertedCakeDate(cakeClassrooms, classroomListString.get(i), dateListString.get(i));
                    }

                    configureViewPager(cakeClassrooms);
                }else {
                    CakeHelper.createCakeEvent("","");
                    configureViewPager(cakeClassrooms);
                }



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


}
