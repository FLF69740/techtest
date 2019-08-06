package com.epfd.csandroid.administrator.classroomsedition;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.res.Configuration;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.ImageView;
import com.epfd.csandroid.R;
import com.epfd.csandroid.administrator.classroomsedition.recyclerview.ClassroomAdapter;
import com.epfd.csandroid.api.ClassroomsHelper;
import com.epfd.csandroid.base.BaseActivity;
import com.epfd.csandroid.models.Classroom;
import com.epfd.csandroid.utils.RecyclerViewClickSupport;
import com.epfd.csandroid.utils.Utils;
import com.google.common.base.Joiner;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import butterknife.BindView;

public class ClassroomsEditionActivity extends BaseActivity {

    @BindView(R.id.classrooms_edition_recycler) RecyclerView mRecyclerView;
    @BindView(R.id.classromms_edition_edit_classroom) EditText mEditTextClassroom;

    private int mSpanCountRecycler;
    private List<String> mListClassrooms;
    private String mClassrooms = "";

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_classrooms_edition;
    }

    @Override
    public void start(@Nullable Bundle savedInstanceState) {
        this.configureOnClickRecyclerView();
        mListClassrooms = getStringListToClassrooms(mClassrooms);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mSpanCountRecycler = 3;
        }else {
            mSpanCountRecycler = 5;
        }
        ImageView createClassroomBtn = findViewById(R.id.classrooms_edition_create);
        createClassroomBtn.setBackgroundResource(R.drawable.avd_anim_vote);
        createClassroomBtn.setOnClickListener(v -> this.createClassroom(createClassroomBtn));
        ClassroomsHelper.getClassrooms().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.getString(Utils.NAME_DATA_CLASSROOMS) != null){
                mClassrooms = documentSnapshot.getString(Utils.NAME_DATA_CLASSROOMS);
                if (mClassrooms != null) {
                    mListClassrooms = getStringListToClassrooms(mClassrooms);
                    configureRecyclerView();
                }
            }else {
                ClassroomsHelper.createClassroom("");
                mClassrooms = "";
                recreate();
            }
        });
    }

    //configure recyclerview
    private void configureRecyclerView(){
        ClassroomAdapter adapter = new ClassroomAdapter(mListClassrooms);
        this.mRecyclerView.setAdapter(adapter);
        this.mRecyclerView.setLayoutManager(new GridLayoutManager(this, mSpanCountRecycler));
    }

    //configure item click on RecyclerView
    private void configureOnClickRecyclerView(){
        RecyclerViewClickSupport.addTo(mRecyclerView, R.layout.classroom_recycler_item).setOnItemClickListener((recyclerView, position, v) -> {
            mListClassrooms.remove(position);
            mClassrooms = Joiner.on(",").join(mListClassrooms);
            Classroom classroom = new Classroom(mClassrooms);
            ClassroomsHelper.updateClassrooms(classroom.getName());
            configureRecyclerView();
        }
        );
    }

    @Override
    public Boolean isAChildActivity() {
        return true;
    }

    //creation classrooms logo animation and action
    private void createClassroom(ImageView imageView){
        if (!mEditTextClassroom.getText().toString().isEmpty()) {
            AnimatedVectorDrawable animationDrawable = (AnimatedVectorDrawable) imageView.getBackground();
            animationDrawable.start();

            new LogoAnimationHandler(this).setClassroomsUpdate();
        }

    }

    private void updateClassroomsInformation(){
        if (mClassrooms.equals("")){
            mClassrooms += mEditTextClassroom.getText().toString();
        }else {
            mClassrooms += "," + mEditTextClassroom.getText().toString();
        }
        Classroom classroom = new Classroom(mClassrooms);
        ClassroomsHelper.updateClassrooms(classroom.getName());
        mListClassrooms = getStringListToClassrooms(mClassrooms);
        configureRecyclerView();
        mEditTextClassroom.getText().clear();
    }

    //Transform String with ',' sperator to List of String
    public List<String> getStringListToClassrooms(String myList){
        return new ArrayList<>(Arrays.asList(myList.split(",")));
    }

    static class LogoAnimationHandler extends Handler{

        private WeakReference<ClassroomsEditionActivity> mWeakReference;

        public LogoAnimationHandler(ClassroomsEditionActivity classroomsEditionActivity){
            mWeakReference = new WeakReference<>(classroomsEditionActivity);
        }

        void setClassroomsUpdate(){
            postDelayed(()->{
                if (mWeakReference.get() != null){
                    mWeakReference.get().updateClassroomsInformation();
                }
            }, 2000);
        }
    }

}


