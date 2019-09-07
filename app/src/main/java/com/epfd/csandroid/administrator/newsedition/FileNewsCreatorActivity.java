package com.epfd.csandroid.administrator.newsedition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.epfd.csandroid.R;
import com.epfd.csandroid.api.NewsHelper;
import com.epfd.csandroid.base.BaseActivity;
import com.epfd.csandroid.models.News;
import com.epfd.csandroid.utils.Utils;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class FileNewsCreatorActivity extends BaseActivity {

    @BindView(R.id.news_file_date_btn) TextView mDateNewsBtn;
    @BindView(R.id.news_file_publication_btn) TextView mPublicationNewsBtn;
    @BindView(R.id.news_file_title_edittext) EditText mTitle;
    @BindView(R.id.news_file_title_title) TextView mTitleTextView;
    @BindView(R.id.news_file_body_edittext) EditText mBody;
    @BindView(R.id.news_file_title_body) TextView mBodyTextView;
    @BindView(R.id.news_file_photo) ImageView mPhoto;
    @BindView(R.id.news_file_delete_btn) Button mDeleteBtn;
    @BindView(R.id.news_file_maj_btn) Button mMajBtn;
    @BindView(R.id.news_file_add_btn) Button mAddBtn;

    private static final String BUNDLE_PHOTO_NEWS = "BUNDLE_PHOTO_NEWS";
    private static final String BUNDLE_PHOTO_IMPORTED = "BUNDLE_PHOTO_IMPORTED";
    private static final String BUNDLE_NEWS = "BUNDLE_NEWS";

    private News mNews = new News();

    private static final String PERMS = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final int RC_IMAGE_PERMS = 100;
    private static final int RC_CHOOSE_PHOTO = 200;
    private Boolean mPhotoImported = false; // définit si une photo a été importé ou non (après rotation d'écran)

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_file_news_creator;
    }

    @Override
    public Boolean isAChildActivity() {
        return true;
    }

    @Override
    public void start(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        DateTime calendar = new DateTime();
        mDateNewsBtn.setText(calendar.toString("dd/MM/yyyy"));
        mPublicationNewsBtn.setText(calendar.toString("dd/MM/yyyy"));
        if (savedInstanceState != null){
            mNews = savedInstanceState.getParcelable(BUNDLE_NEWS);
            if (mNews.getDate() != null && mNews.getPublication() != null) {
                if (mNews.getDate().contains("/") && mNews.getPublication().contains("/")) {
                    mDateNewsBtn.setText(mNews.getDate());
                    mPublicationNewsBtn.setText(mNews.getPublication());
                }
            }

            mPhotoImported = savedInstanceState.getBoolean(BUNDLE_PHOTO_IMPORTED, false);
            mUriString = savedInstanceState.getString(BUNDLE_PHOTO_NEWS, Utils.EMPTY);
            if (!mUriString.equals(Utils.EMPTY)){
                mUriImageSelected = Uri.parse(mUriString);
                Glide.with(this)
                        .load(this.mUriImageSelected)
                        .apply(RequestOptions.centerCropTransform())
                        .into(this.mPhoto);
            }

        } else {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null){
                mNews = bundle.getParcelable(NewsCreatorActivity.INTENT_CREATOR_NEWS);
                if (mNews.getPhoto() != null) {
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                    storageReference.child(Objects.requireNonNull(mNews.getPhoto())).getDownloadUrl().addOnSuccessListener(uri -> {
                        mUriString = uri.toString();
                        Glide.with(getApplicationContext())
                                .load(uri)
                                .apply(RequestOptions.fitCenterTransform())
                                .into(mPhoto);
                    });
                }

                mTitle.setText(mNews.getTitle());
                mDateNewsBtn.setText(mNews.getDate());
                mPublicationNewsBtn.setText(mNews.getPublication());
                mBody.setText(mNews.getBody());
            }
        }
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
        mCalendarDateNews = fmt.parseDateTime(mDateNewsBtn.getText().toString());
        mCalendarPublicationNews = fmt.parseDateTime(mPublicationNewsBtn.getText().toString());
        if (mNews.getTitle() == null){
            mDeleteBtn.setVisibility(View.INVISIBLE);
            mAddBtn.setVisibility(View.VISIBLE);
            mMajBtn.setVisibility(View.INVISIBLE);
        }else {
            mDeleteBtn.setVisibility(View.VISIBLE);
            mAddBtn.setVisibility(View.INVISIBLE);
            mMajBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_PHOTO_NEWS, mUriString);
 /*       outState.putBoolean(BUNDLE_PHOTO_IMPORTED, mPhotoImported);
        */
        outState.putParcelable(BUNDLE_NEWS, mNews);
    }

    private void goBack(){
        startActivity(new Intent(getApplicationContext(), NewsCreatorActivity.class));
        finish();
    }

    /**
     *  DATE PICKER
     */

    private DateTime mCalendarDateNews = new DateTime();
    private DateTime mCalendarPublicationNews = new DateTime();

    @OnClick(R.id.news_file_date_btn)
    public void clickOnDateFileNewsBtn(){
        new DatePickerDialog(this, dateNewsInsertion, mCalendarDateNews.getYear(), mCalendarDateNews.getMonthOfYear()-1, mCalendarDateNews.getDayOfMonth()).show();
    }

    @OnClick(R.id.news_file_publication_btn)
    public void clickOnPublicationFileNewsBtn(){
        new DatePickerDialog(this, publicationNewsInsertion, mCalendarPublicationNews.getYear(), mCalendarPublicationNews.getMonthOfYear()-1, mCalendarPublicationNews.getDayOfMonth()).show();
    }

    DatePickerDialog.OnDateSetListener dateNewsInsertion = (view, year, month, dayOfMonth) -> {
        DateTime calendar = new DateTime();
        calendar = calendar.year().setCopy(year);
        calendar = calendar.monthOfYear().setCopy(month+1);
        calendar = calendar.dayOfMonth().setCopy(dayOfMonth);
        mCalendarDateNews = calendar;
        mDateNewsBtn.setText(calendar.toString("dd/MM/yyyy"));
    };

    DatePickerDialog.OnDateSetListener publicationNewsInsertion = (view, year, month, dayOfMonth) -> {
        DateTime calendar = new DateTime();
        calendar = calendar.year().setCopy(year);
        calendar = calendar.monthOfYear().setCopy(month+1);
        calendar = calendar.dayOfMonth().setCopy(dayOfMonth);
        mCalendarPublicationNews = calendar;
        mPublicationNewsBtn.setText(calendar.toString("dd/MM/yyyy"));
    };

    /**
     *  IMPORT PHOTO EXTERNAL STORAGE
     */

    private Uri mUriImageSelected;
    private String mUriString = Utils.EMPTY;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @OnClick(R.id.news_file_tel_btn)
    @AfterPermissionGranted(RC_IMAGE_PERMS)
    public void importNewsFilePhotoExternalStorage(){
        if (!EasyPermissions.hasPermissions(this, PERMS)){
            EasyPermissions.requestPermissions(this, "Accepter l'import photo", RC_IMAGE_PERMS, PERMS);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RC_CHOOSE_PHOTO);
    }

    /**
     *  ACTIVITY RESULT
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_CHOOSE_PHOTO){
            if (resultCode == RESULT_OK){
                this.mUriImageSelected = data.getData();
                this.mUriString = data.getData().toString();
                this.mPhotoImported = true;
                Glide.with(this)
                        .load(this.mUriImageSelected)
                        .apply(RequestOptions.centerCropTransform())
                        .into(this.mPhoto);
            }
        }
    }

    /**
     *  REGISTRATION NEWS
     */

    private void registerNews(String tagString, String dateBloc){
        boolean isNewsComplete = true;
        mTitleTextView.setTextColor(Color.parseColor("#000000"));
        mBodyTextView.setTextColor(Color.parseColor("#000000"));
        mDateNewsBtn.setTextColor(Color.parseColor("#000000"));
        mPublicationNewsBtn.setTextColor(Color.parseColor("#000000"));
        if (mTitle.getText().toString().equals("")) {
            isNewsComplete = false;
            mTitleTextView.setTextColor(Color.parseColor("#FF0000"));
        }
        if (mBody.getText().toString().equals("")){
            isNewsComplete = false;
            mBodyTextView.setTextColor(Color.parseColor("#FF0000"));
        }

        if (isNewsComplete){
            if (mPhotoImported){
                StorageReference storageReference = FirebaseStorage.getInstance().getReference(tagString);
                storageReference.putFile(this.mUriImageSelected)
                        .addOnSuccessListener(this, taskSnapshot -> {
                            String nameImageSavedInFirebase = taskSnapshot.getMetadata().getReference().getName();
                            NewsHelper.createNews(mTitle.getText().toString(), mDateNewsBtn.getText().toString(), false, mPublicationNewsBtn.getText().toString(),
                                    nameImageSavedInFirebase, mBody.getText().toString(), "ALL", Integer.valueOf(tagString), "NEWS", dateBloc);
                        });
            }else {
                if (mNews.getPhoto() == null) mNews.setPhoto("ic_logo_pos2");
                NewsHelper.createNews(mTitle.getText().toString(), mDateNewsBtn.getText().toString(), false, mPublicationNewsBtn.getText().toString(),
                        mNews.getPhoto(), mBody.getText().toString(), "ALL", Integer.valueOf(tagString), "NEWS", dateBloc);
            }
            this.goBack();
        }
    }

    /**
     *  action BTN : ADD - MAJ - DELETE
     */

    @OnClick(R.id.news_file_add_btn)
    public void createANews(){
        DateTime dateTime = new DateTime();
        String dateBloc = mDateNewsBtn.getText().toString().replace("/", "");
        String referenceStorageCode = ""+ dateTime.getDayOfYear() + dateTime.getSecondOfDay();
        this.registerNews(referenceStorageCode, dateBloc);
    }

    @OnClick(R.id.news_file_maj_btn)
    public void majANews(){
        if (!mNews.getDate().equals(mDateNewsBtn.getText().toString())){
            NewsHelper.deleteNews("NEWS" + mNews.getDate().replace("/","") + mNews.getTag());
            this.createANews();
        }else {
            this.registerNews(String.valueOf(mNews.getTag()), mNews.getDate().replace("/", ""));
        }
    }

    @OnClick(R.id.news_file_delete_btn)
    public void deleteANews(){
        String uid = "NEWS" + mDateNewsBtn.getText().toString().replace("/", "") + mNews.getTag();
        NewsHelper.deleteNews(uid);
        goBack();
    }


}
