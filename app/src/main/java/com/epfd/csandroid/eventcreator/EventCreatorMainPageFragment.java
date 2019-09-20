package com.epfd.csandroid.eventcreator;


import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.Fragment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.epfd.csandroid.R;
import com.epfd.csandroid.administrator.newsedition.FileNewsPhotoBackEndActivity;
import com.epfd.csandroid.api.EventHelper;
import com.epfd.csandroid.models.Event;
import com.epfd.csandroid.utils.BitmapStorage;
import com.epfd.csandroid.utils.FireBaseStorageUtils;
import com.epfd.csandroid.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import org.joda.time.DateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;


public class EventCreatorMainPageFragment extends Fragment {

    private static final String BUNDLE_EVENT_CREATOR_PANEL_EVENT_OBJECT = "BUNDLE_EVENT_CREATOR_PANEL_EVENT_OBJECT";
    private static final String BUNDLE_EVENT_OUTSTATE = "BUNDLE_EVENT_OUTSTATE";
    private static final String BUNDLE_PHOTO_IMPORTED = "BUNDLE_PHOTO_IMPORTED";
    private static final String BUNDLE_LABEL_IMPORTED = "BUNDLE_LABEL_IMPORTED";
    private static final String BUNDLE_URI_STRING_PHOTO = "BUNDLE_URI_STRING_PHOTO";
    private static final String BUNDLE_URI_STRING_LABEL = "BUNDLE_URI_STRING_LABEL";

    @BindView(R.id.event_creator_title) EditText mTitle;
    @BindView(R.id.event_creator_panel_photo) ImageView mPhoto;
    @BindView(R.id.event_creator_label) ImageView mLabel;
    @BindView(R.id.event_creator_guide_navigation_bottom) Guideline mNavGuideline;
    @BindView(R.id.event_creator_date_picker) TextView mDateEventBtn;
    @BindView(R.id.event_creator_description) EditText mDescription;
    @BindView(R.id.event_creator_bottomNavigationView) BottomNavigationView mEventBottomNavigationView;
    @BindView(R.id.event_creator_save_btn) Button mSaveBtn;

    private View mView;
    private Event mEvent;
    private boolean mPhotoImported = false; // définit si une photo a été importé ou non
    private String mNavChoice;
    private boolean mLabelImported;

    static EventCreatorMainPageFragment newInstance(Event event){
        EventCreatorMainPageFragment eventCreatorMainPageFragment = new EventCreatorMainPageFragment();
        Bundle bundle = new Bundle(1);
        bundle.putParcelable(BUNDLE_EVENT_CREATOR_PANEL_EVENT_OBJECT, event);
        eventCreatorMainPageFragment.setArguments(bundle);
        return eventCreatorMainPageFragment;
    }

    public EventCreatorMainPageFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_event_creator_main_page, container, false);
        ButterKnife.bind(this, mView);
        mEventBottomNavigationView.setOnNavigationItemSelectedListener(item -> bottomNavigationViewAction(item.getItemId()));

        if (savedInstanceState != null){
            mEvent = savedInstanceState.getParcelable(BUNDLE_EVENT_OUTSTATE);
            mPhotoImported = savedInstanceState.getBoolean(BUNDLE_PHOTO_IMPORTED);
            mUriPhotoString = savedInstanceState.getString(BUNDLE_URI_STRING_PHOTO);
            mLabelImported = savedInstanceState.getBoolean(BUNDLE_LABEL_IMPORTED);
            mUriLabelString = savedInstanceState.getString(BUNDLE_URI_STRING_LABEL);
            if (!mUriPhotoString.equals(Utils.EMPTY)){
                this.configureImageViewWithURI(mUriPhotoString, RC_CHOOSE_PHOTO);
            }else if (!mEvent.getPhoto().equals(Utils.EMPTY)){
                this.configureImageViewWithBitmap(mEvent.getPhoto(), RC_PHOTO_BACKEND);
            }
            if (!mUriLabelString.equals(Utils.EMPTY)){
                this.configureImageViewWithURI(mUriLabelString, RC_CHOOSE_LABEL);
            } else if (!mEvent.getLabel().equals(Utils.EMPTY)){
                this.configureImageViewWithBitmap(mEvent.getLabel(), RC_LABEL_BACKEND);
            }
        }else {
            assert getArguments() != null; mEvent = getArguments().getParcelable(BUNDLE_EVENT_CREATOR_PANEL_EVENT_OBJECT);
            assert mEvent != null; if (!mEvent.getName().equals("")){
                mSaveBtn.setText(getString(R.string.event_creator_maj));
            }
        }

        mTitle.setText(mEvent.getName());
        mDateEventBtn.setText(mEvent.getDate());
        mDescription.setText(mEvent.getDescription());

        if (!mEvent.getPhoto().equals(Utils.EMPTY)){
            mPhoto.setImageBitmap(BitmapStorage.loadImage(getContext(), mEvent.getPhoto()));
        }

        if (!mEvent.getLabel().equals(Utils.EMPTY)){
            mLabel.setImageBitmap(BitmapStorage.loadImage(getContext(), mEvent.getLabel()));
        }

        mDescription.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { mEvent.setDescription(s.toString()); }
            @Override public void afterTextChanged(Editable s) {}
        });

        mTitle.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { mEvent.setName(s.toString()); }
            @Override public void afterTextChanged(Editable s) {}
        });

    //    BitmapStorage.deleteImage(getContext(), "26041840"); BitmapStorage.deleteImage(getContext(), "26041839");

        return mView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_EVENT_OUTSTATE, mEvent);
        outState.putBoolean(BUNDLE_PHOTO_IMPORTED, mPhotoImported);
        outState.putString(BUNDLE_URI_STRING_PHOTO, mUriPhotoString);
        outState.putBoolean(BUNDLE_LABEL_IMPORTED, mLabelImported);
        outState.putString(BUNDLE_URI_STRING_LABEL, mUriLabelString);
    }

    @OnClick(R.id.event_creator_panel_photo)
    void eventCreatorLoadPhoto(){
        mNavChoice = BUNDLE_PHOTO_IMPORTED;
        playAnimation(true);
    }

    @OnClick(R.id.event_creator_label)
    void eventCreatorLoadLogo(){
        mNavChoice = BUNDLE_LABEL_IMPORTED;
        playAnimation(true);
    }

    private void configureImageViewWithURI(String uri, int requestCode){
        if (requestCode == RC_CHOOSE_PHOTO) {
            mUriPhotoString = uri;
            Glide.with(Objects.requireNonNull(getContext())).load(uri).apply(RequestOptions.fitCenterTransform()).into(mPhoto);
        }else if (requestCode == RC_CHOOSE_LABEL) {
            mUriLabelString = uri;
            Glide.with(Objects.requireNonNull(getContext())).load(uri).apply(RequestOptions.fitCenterTransform()).into(mLabel);
        }
    }

    private void configureImageViewWithBitmap(String bitmap, int requestCode){
        if (requestCode == RC_PHOTO_BACKEND) mPhoto.setImageBitmap(BitmapStorage.loadImage(getContext(), bitmap));
        else mLabel.setImageBitmap(BitmapStorage.loadImage(getContext(), bitmap));
    }

    /**
     *  DATE PICKER
     */

    private DateTime mCalendarDateEvent = new DateTime();

    @OnClick(R.id.event_creator_date_picker)
    void clickOnDateEventBtn(){
        new DatePickerDialog(Objects.requireNonNull(getContext()), dateEventInsertion, mCalendarDateEvent.getYear(), mCalendarDateEvent.getMonthOfYear()-1, mCalendarDateEvent.getDayOfMonth()).show();
    }

    private DatePickerDialog.OnDateSetListener dateEventInsertion = (view, year, month, dayOfMonth) -> {
        DateTime calendar = new DateTime();
        calendar = calendar.year().setCopy(year);
        calendar = calendar.monthOfYear().setCopy(month+1);
        calendar = calendar.dayOfMonth().setCopy(dayOfMonth);
        mCalendarDateEvent = calendar;
        mDateEventBtn.setText(calendar.toString("dd/MM/yyyy"));
        mEvent.setDate(mDateEventBtn.getText().toString());
    };

    /**
     *  BOTTOM NAVIGATION
     */

    private Boolean bottomNavigationViewAction(Integer integer){
        switch (integer){
            case R.id.event_creator_disk_validation :
                this.hideBottomNav();
                if (mNavChoice.equals(BUNDLE_PHOTO_IMPORTED)) importEventPhotoExternalStorage(RC_CHOOSE_PHOTO);
                else importEventPhotoExternalStorage(RC_CHOOSE_LABEL);
                break;
            case R.id.event_creator_back :
                playAnimation(false);
                break;
            case R.id.event_creator_backend_validation :
                this.hideBottomNav();
                if (mNavChoice.equals(BUNDLE_PHOTO_IMPORTED)) importEventPhotoBackEnd(RC_PHOTO_BACKEND);
                else importEventPhotoBackEnd(RC_LABEL_BACKEND);
                break;
        }
        return true;
    }

    /**
     *  IMPORT PHOTO EXTERNAL STORAGE
     */

    private String mUriPhotoString = Utils.EMPTY;
    private String mUriLabelString = Utils.EMPTY;
    private static final String PERMS = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final int RC_IMAGE_PERMS = 100;
    private static final int RC_CHOOSE_PHOTO = 200;
    private static final int RC_CHOOSE_LABEL = 210;
    private static final int RC_PHOTO_BACKEND = 300;
    private static final int RC_LABEL_BACKEND = 310;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_IMAGE_PERMS)
    private void importEventPhotoExternalStorage(int requestCode){
        if (!EasyPermissions.hasPermissions(Objects.requireNonNull(getContext()), PERMS)){
            EasyPermissions.requestPermissions(this, "Accepter l'import photo", RC_IMAGE_PERMS, PERMS);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, requestCode);
    }

    /**
     *  IMPORT PHOTO BACKEND
     */

    private void importEventPhotoBackEnd(int requestCode){
        startActivityForResult(new Intent(getContext(), FileNewsPhotoBackEndActivity.class), requestCode);
    }

    /**
     *  ACTIVITY RESULT
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_CHOOSE_PHOTO && resultCode == RESULT_OK){
            this.mPhotoImported = true;
            this.mEvent.setPhoto(Utils.EMPTY);
            assert data != null; this.mUriPhotoString = Objects.requireNonNull(data.getData()).toString();
            configureImageViewWithURI(mUriPhotoString, requestCode);
        } else if (requestCode == RC_CHOOSE_LABEL && resultCode == RESULT_OK){
            this.mLabelImported = true;
            this.mEvent.setLabel(Utils.EMPTY);
            assert data != null; this.mUriLabelString = Objects.requireNonNull(data.getData()).toString();
            configureImageViewWithURI(mUriLabelString, requestCode);
        } else if (requestCode == RC_PHOTO_BACKEND && resultCode == RESULT_OK){
            this.mPhotoImported = false;
            this.mUriPhotoString = Utils.EMPTY;
            assert data != null; this.mEvent.setPhoto(data.getStringExtra(FileNewsPhotoBackEndActivity.BUNDLE_EXTRA_PHOTO_BACKEND));
            configureImageViewWithBitmap(mEvent.getPhoto(), requestCode);
        } else if (requestCode == RC_LABEL_BACKEND && resultCode == RESULT_OK){
            this.mLabelImported = false;
            this.mUriLabelString = Utils.EMPTY;
            assert data != null; this.mEvent.setLabel(data.getStringExtra(FileNewsPhotoBackEndActivity.BUNDLE_EXTRA_PHOTO_BACKEND));
            configureImageViewWithBitmap(mEvent.getLabel(), requestCode);
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
     *  REGISTRATION EVENT
     */

    @OnClick(R.id.event_creator_save_btn)
    void registerEvent(){
        boolean isEventComplete = true;
        if (mEvent.getName().equals("") | mEvent.getDescription().equals("")){
            isEventComplete = false;
        }
        if (mEvent.getPhoto().equals(Utils.EMPTY) && mUriPhotoString.equals(Utils.EMPTY)){
            isEventComplete = false;
        }
        if (mEvent.getLabel().equals(Utils.EMPTY) && mUriLabelString.equals(Utils.EMPTY)){
            isEventComplete = false;
        }

        if (isEventComplete){
            DateTime dateTime = new DateTime();
            String photoReference = ""+dateTime.getDayOfYear()+dateTime.getSecondOfDay();
            int secondOfDayPlus = dateTime.getSecondOfDay() + 1;
            String labelReference = ""+dateTime.getDayOfYear()+secondOfDayPlus;
            if (mPhotoImported){
                mEvent.setPhoto(photoReference);
                BitmapStorage.saveImageInternalStorage(getContext(), photoReference ,Uri.parse(mUriPhotoString));
                StorageReference storageReference = FirebaseStorage.getInstance().getReference(photoReference);
                storageReference.putFile(Uri.parse(mUriPhotoString)).addOnSuccessListener(taskSnapshot -> {
                    FireBaseStorageUtils fireBaseStorageUtils = new FireBaseStorageUtils();
                    fireBaseStorageUtils.createStorageSerial();
                    mPhotoImported = false;
                    mUriPhotoString = Utils.EMPTY;
                });

            }
            if (mLabelImported){
                mEvent.setLabel(labelReference);
                BitmapStorage.saveImageInternalStorage(getContext(), labelReference ,Uri.parse(mUriLabelString));
                StorageReference storageReference = FirebaseStorage.getInstance().getReference(labelReference);
                storageReference.putFile(Uri.parse(mUriLabelString)).addOnSuccessListener(taskSnapshot -> {
                    FireBaseStorageUtils fireBaseStorageUtils = new FireBaseStorageUtils();
                    fireBaseStorageUtils.createStorageSerial();
                    mLabelImported = false;
                    mUriLabelString = Utils.EMPTY;
                });
            }

            if (mEvent.getUid().equals(Utils.EMPTY)){
                mEvent.setUid(EventHelper.ROOT_UID + mEvent.getDate().replace("/",""));
            }

            EventHelper.createEvent(mEvent.getUid(), mEvent).addOnSuccessListener(aVoid -> {
                Snackbar.make(mView, R.string.event_creator_save, Snackbar.LENGTH_SHORT).show();
                mSaveBtn.setText(getString(R.string.event_creator_maj));
                mCallback.eventFirstPageValidate();
            });
        }else {
            Snackbar.make(mView, R.string.event_creator_incomplet, Snackbar.LENGTH_SHORT).show();
        }

    }


    /**
     *  Callback
     */

    // interface for button clicked
    public interface EventSaveClickedListener{
        void eventFirstPageValidate();
    }

    //callback for button clicked
    private EventSaveClickedListener mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (EventSaveClickedListener) getActivity();
        } catch (ClassCastException e){
            throw new ClassCastException(e.toString() + " must implement ItemClickedListener");
        }
    }

























}
