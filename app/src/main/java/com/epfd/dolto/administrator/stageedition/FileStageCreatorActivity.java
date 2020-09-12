package com.epfd.dolto.administrator.stageedition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.epfd.dolto.R;
import com.epfd.dolto.administrator.newsedition.FileNewsPhotoBackEndActivity;
import com.epfd.dolto.administrator.stageedition.recyclerview.FileStageScheduleAdapter;
import com.epfd.dolto.api.StageCreatorHelper;
import com.epfd.dolto.base.BaseActivity;
import com.epfd.dolto.models.Stage;
import com.epfd.dolto.utils.BitmapStorage;
import com.epfd.dolto.utils.FireBaseStorageUtils;
import com.epfd.dolto.utils.RecyclerViewClickSupport;
import com.epfd.dolto.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class FileStageCreatorActivity extends BaseActivity {

    @BindView(R.id.stage_file_title_edittext) EditText mTitle;
    @BindView(R.id.stage_file_people_edittext) EditText mPeopleNumber;
    @BindView(R.id.stage_file_schedules_answer) TextView mSchedules;
    @BindView(R.id.stage_file_register_schedule) ImageView mRegisterSchedule;
    @BindView(R.id.stage_file_guide_navigation_bottom) Guideline mNavGuideline;
    @BindView(R.id.stage_file_photo) ImageView mPhoto;
    @BindView(R.id.stage_file_bottomNavigationView) BottomNavigationView mStageBottomNavigationView;
    @BindView(R.id.stage_file_schedule_recycler) RecyclerView mRecyclerView;

    private static final String BUNDLE_URI_STRING_PHOTO = "BUNDLE_URI_STRING_PHOTO";
    private static final String BUNDLE_STAGE_OUTSTATE = "BUNDLE_STAGE_OUTSTATE";
    private static final String BUNDLE_PHOTO_IMPORTED = "BUNDLE_PHOTO_IMPORTED";
    private static final String BUNDLE_INSTANCE_DATE_START = "BUNDLE_INSTANCE_DATE_START";
    private static final String BUNDLE_INSTANCE_DATE_END = "BUNDLE_INSTANCE_DATE_END";
    private static final String BUNDLE_INSTANCE_SCHEDULES_TEMP = "BUNDLE_INSTANCE_SCHEDULES_TEMP";
    private static final String BUNDLE_LIST_SCHEDULES_STRING = "BUNDLE_LIST_SCHEDULES_STRING";

    private Stage mStage;
    private boolean mPhotoImported = false; // définit si une photo a été importé ou non
    private FileStageScheduleAdapter mAdapter;
    private ArrayList<String> mStringListSchedules;

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_file_stage_creator;
    }

    @Override
    public Boolean isAChildActivity() {
        return true;
    }

    @Override
    public void start(@Nullable Bundle savedInstanceState) {
        mCalendarDateEventStart = new DateTime();
        mCalendarDateEventEnd = new DateTime();
        mRegisterSchedule.setBackgroundResource(R.drawable.avd_anim_vote);
        mRegisterSchedule.setOnClickListener(v -> validateSchedules(mRegisterSchedule));
        mStageBottomNavigationView.setOnNavigationItemSelectedListener(item -> bottomNavigationViewAction(item.getItemId()));

        if (savedInstanceState != null){
            mStage = savedInstanceState.getParcelable(BUNDLE_STAGE_OUTSTATE);
            mStringListSchedules = savedInstanceState.getStringArrayList(BUNDLE_LIST_SCHEDULES_STRING);
            DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");
            if (savedInstanceState.getString(BUNDLE_INSTANCE_DATE_START) != null) mCalendarDateEventStart = formatter.parseDateTime(savedInstanceState.getString(BUNDLE_INSTANCE_DATE_START));
            if (savedInstanceState.getString(BUNDLE_INSTANCE_DATE_END) != null) mCalendarDateEventEnd = formatter.parseDateTime(savedInstanceState.getString(BUNDLE_INSTANCE_DATE_END));
            mPhotoImported = savedInstanceState.getBoolean(BUNDLE_PHOTO_IMPORTED);
            if (!mUriPhotoString.equals(Utils.EMPTY)){
                Glide.with(this).load(mUriPhotoString).apply(RequestOptions.fitCenterTransform()).into(mPhoto);
            }else if (!mStage.getPhoto().equals(Utils.EMPTY)){
                this.mPhoto.setImageBitmap(BitmapStorage.loadImage(this, mStage.getPhoto()));
            }
            mSchedules.setText(savedInstanceState.getString(BUNDLE_INSTANCE_SCHEDULES_TEMP));
        }else {
            mStage = getIntent().getParcelableExtra(StageCreatorActivity.INTENT_STAGE_CREATOR_MENU);
            if (!mStage.getSchedule().equals(Utils.EMPTY)) {
                mStringListSchedules = new ArrayList<>(Arrays.asList(mStage.getSchedule().split(",")));
            }else {
                mStringListSchedules = new ArrayList<>();
            }
        }

        mTitle.setText(mStage.getTitle());
        mPeopleNumber.setText(String.valueOf(mStage.getPeople()));
        mAdapter = new FileStageScheduleAdapter(mStringListSchedules);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        RecyclerViewClickSupport.addTo(mRecyclerView, R.layout.file_stage_creator_schedule_item)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    mStringListSchedules.remove(position);
                    mAdapter.notifyDataSetChanged();
                });

        if (!mStage.getPhoto().equals(Utils.EMPTY)){
            mPhoto.setImageBitmap(BitmapStorage.loadImage(this, mStage.getPhoto()));
        }

        mTitle.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { mStage.setTitle(s.toString()); }
            @Override public void afterTextChanged(Editable s) {}
        });

        mPeopleNumber.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")){
                    mStage.setPeople(0);
                }else {
                    mStage.setPeople(Integer.valueOf(s.toString()));
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_INSTANCE_DATE_START, mCalendarDateEventStart.toString("HH:mm"));
        outState.putString(BUNDLE_INSTANCE_DATE_END, mCalendarDateEventEnd.toString("HH:mm"));
        outState.putString(BUNDLE_INSTANCE_SCHEDULES_TEMP, mSchedules.getText().toString());
        outState.putParcelable(BUNDLE_STAGE_OUTSTATE, mStage);
        outState.putBoolean(BUNDLE_PHOTO_IMPORTED, mPhotoImported);
        outState.putString(BUNDLE_URI_STRING_PHOTO, mUriPhotoString);
        outState.putStringArrayList(BUNDLE_LIST_SCHEDULES_STRING, mStringListSchedules);
    }

    @OnClick(R.id.stage_file_photo) void stageFileLoadPhoto(){playAnimation(true);}

    /**
     *  CLOCK PICKER
     */

    private DateTime mCalendarDateEventStart;
    private DateTime mCalendarDateEventEnd;

    @OnClick(R.id.stage_file_add_schedule_start) void clickOnScheduleStageStartBtn(){
        new TimePickerDialog(this, timeEventInsertionStart, mCalendarDateEventStart.getHourOfDay(), mCalendarDateEventStart.getMinuteOfHour(), true).show();
    }

    @OnClick(R.id.stage_file_add_schedule_end) void clickOnScheduleStageEndBtn(){
        new TimePickerDialog(this, timeEventInsertionEnd, mCalendarDateEventEnd.getHourOfDay(), mCalendarDateEventEnd.getMinuteOfHour(), true).show();
    }

    private TimePickerDialog.OnTimeSetListener timeEventInsertionStart = (view, hourOfDay, minute) -> {
        DateTime dateTime = new DateTime();
        dateTime = dateTime.hourOfDay().setCopy(hourOfDay);
        dateTime = dateTime.minuteOfHour().setCopy(minute);
        mCalendarDateEventStart = dateTime;
        String answer = mCalendarDateEventStart.toString("HH:mm") + " / " + mCalendarDateEventEnd.toString("HH:mm");
        mSchedules.setText(answer);
    };

    private TimePickerDialog.OnTimeSetListener timeEventInsertionEnd = (view, hourOfDay, minute) -> {
        DateTime dateTime = new DateTime();
        dateTime = dateTime.hourOfDay().setCopy(hourOfDay);
        dateTime = dateTime.minuteOfHour().setCopy(minute);
        mCalendarDateEventEnd = dateTime;
        String answer = mCalendarDateEventStart.toString("HH:mm") + " / " + mCalendarDateEventEnd.toString("HH:mm");
        mSchedules.setText(answer);
    };

    /**
     *  ANIMATION LOGO REGISTRATION SCHEDULES
     */

    //creation classrooms logo animation and action
    private void validateSchedules(ImageView imageView){
        if (mCalendarDateEventStart.getMinuteOfDay() < mCalendarDateEventEnd.getMinuteOfDay()) {
            AnimatedVectorDrawable animationDrawable = (AnimatedVectorDrawable) imageView.getBackground();
            animationDrawable.start();
            new LogoAnimationHandler(this).setClassroomsUpdate();
        }

    }

    // create weak reference for animation
    static class LogoAnimationHandler extends Handler {

        private WeakReference<FileStageCreatorActivity> mWeakReference;

        LogoAnimationHandler(FileStageCreatorActivity classroomsEditionActivity){
            mWeakReference = new WeakReference<>(classroomsEditionActivity);
        }

        void setClassroomsUpdate(){
            postDelayed(()->{
                if (mWeakReference.get() != null){
                    mWeakReference.get().updateSchdulesInformation();
                }
            }, 2000);
        }
    }

    private void updateSchdulesInformation() {
        boolean addNewEntry = true;
        for (String scheduleUnit : mStringListSchedules){
            if (scheduleUnit.equals(mSchedules.getText().toString())) addNewEntry = false;
        }
        if (addNewEntry) {
            mStringListSchedules.add(mSchedules.getText().toString());
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     *  BOTTOM NAVIGATION ANIMATION
     */

    private PropertyValuesHolder mOpenValues = PropertyValuesHolder.ofInt(DIMENSION, 100, 90);
    private PropertyValuesHolder mHideValues = PropertyValuesHolder.ofInt(DIMENSION, 90, 100);
    private static final String DIMENSION = "DIMENSION";

    // launch animation at the start of MainActivity
    private void playAnimation(boolean open) {
        AnimatorSet animatorSet = new AnimatorSet();
        List<Animator> animators = new ArrayList<>();
        animators.add(showBottomNavigation(open));
        animatorSet.playSequentially(animators);
        animatorSet.start();
    }

    // reduce / open bottom navigation view
    private Animator showBottomNavigation(boolean open){
        ValueAnimator animator = new ValueAnimator();
        if (open){
            animator.setValues(mOpenValues);
        }else {
            animator.setValues(mHideValues);
        }
        animator.setDuration(300);
        animator.addUpdateListener(animation -> {
            int bottomValue = (int) animation.getAnimatedValue(DIMENSION);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mNavGuideline.getLayoutParams();
            params.guidePercent = (float)bottomValue /100;
            mNavGuideline.setLayoutParams(params);
        });
        return animator;
    }

    // reduce bottom navigation view without animation
    private void hideBottomNav(){
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mNavGuideline.getLayoutParams();
        params.guidePercent = (float)1;
        mNavGuideline.setLayoutParams(params);
    }

    /**
     *  BOTTOM NAVIGATION
     */

    private Boolean bottomNavigationViewAction(Integer integer){
        switch (integer){
            case R.id.event_creator_disk_validation :
                this.hideBottomNav();
                importEventPhotoExternalStorage();
                break;
            case R.id.event_creator_back :
                playAnimation(false);
                break;
            case R.id.event_creator_backend_validation :
                this.hideBottomNav();
                importEventPhotoBackEnd();
                break;
        }
        return true;
    }

    /**
     *  IMPORT PHOTO EXTERNAL STORAGE
     */

    private String mUriPhotoString = Utils.EMPTY;
    private static final String PERMS = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final int RC_IMAGE_PERMS = 100;
    private static final int RC_CHOOSE_PHOTO = 2000;
    private static final int RC_PHOTO_BACKEND = 3000;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_IMAGE_PERMS)
    private void importEventPhotoExternalStorage(){
        if (!EasyPermissions.hasPermissions(this, PERMS)){
            EasyPermissions.requestPermissions(this, "Accepter l'import photo", RC_IMAGE_PERMS, PERMS);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RC_CHOOSE_PHOTO);
    }

    /**
     *  IMPORT PHOTO BACKEND
     */

    private void importEventPhotoBackEnd(){
        startActivityForResult(new Intent(this, FileNewsPhotoBackEndActivity.class), RC_PHOTO_BACKEND);
    }

    /**
     *  ACTIVITY RESULT
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_CHOOSE_PHOTO && resultCode == RESULT_OK){
            this.mPhotoImported = true;
            this.mStage.setPhoto(Utils.EMPTY);
            assert data != null; this.mUriPhotoString = Objects.requireNonNull(data.getData()).toString();
            Glide.with(this).load(mUriPhotoString).apply(RequestOptions.fitCenterTransform()).into(mPhoto);
        } else if (requestCode == RC_PHOTO_BACKEND && resultCode == RESULT_OK){
            this.mPhotoImported = false;
            this.mUriPhotoString = Utils.EMPTY;
            assert data != null; this.mStage.setPhoto(data.getStringExtra(FileNewsPhotoBackEndActivity.BUNDLE_EXTRA_PHOTO_BACKEND));
            this.mPhoto.setImageBitmap(BitmapStorage.loadImage(this, mStage.getPhoto()));
        }
    }

    /**
     *      REGISTRATION STAGE
     */

    @OnClick(R.id.stage_file_save) void registerStage(){
        boolean isStageComplete = true;
        if (mStringListSchedules.size() != 0){
            StringBuilder builder = new StringBuilder();
            for (String schedule : mStringListSchedules) builder.append(schedule).append(",");
            mStage.setSchedule(builder.toString());
        }
        if (mStage.getTitle().equals("") | mStage.getPeople() == 0 | mStage.getSchedule().equals(Utils.EMPTY)){
            isStageComplete = false;
        }
        if (mStage.getPhoto().equals(Utils.EMPTY) && mUriPhotoString.equals(Utils.EMPTY)){
            isStageComplete = false;
        }

        if (isStageComplete){
            DateTime dateTime = new DateTime();
            String photoReference = ""+dateTime.getDayOfYear()+dateTime.getSecondOfDay();
            if (mPhotoImported){
                mStage.setPhoto(photoReference);
                BitmapStorage.saveImageInternalStorage(this, photoReference , Uri.parse(mUriPhotoString));
                StorageReference storageReference = FirebaseStorage.getInstance().getReference(photoReference);
                storageReference.putFile(Uri.parse(mUriPhotoString)).addOnSuccessListener(taskSnapshot -> {
                    FireBaseStorageUtils fireBaseStorageUtils = new FireBaseStorageUtils();
                    fireBaseStorageUtils.createStorageSerial();
                    mPhotoImported = false;
                    mUriPhotoString = Utils.EMPTY;
                });
            }

            if (mStage.getUid().equals(Utils.EMPTY)) {
                mStage.setUid(StageCreatorHelper.ROOT_UID + photoReference);
                StageCreatorHelper.createStage(StageCreatorHelper.ROOT_UID + photoReference, mStage);
            }else {
                StageCreatorHelper.createStage(mStage.getUid(), mStage);
            }

            Toast.makeText(this, R.string.stage_creator_stage_enregistre, Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this, R.string.stage_creator_stage_incomplet, Toast.LENGTH_SHORT).show();
        }


    }










}
