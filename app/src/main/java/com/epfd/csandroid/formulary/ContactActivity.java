package com.epfd.csandroid.formulary;

import androidx.annotation.Nullable;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.epfd.csandroid.MainActivity;
import com.epfd.csandroid.R;
import com.epfd.csandroid.base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.contact_ecole_btn) TextView mContactSchoolBtn;
    @BindView(R.id.contact_apel_btn) TextView mContactApelBtn;
    @BindView(R.id.contact_dev_btn) TextView mContactDevBtn;
    @BindView(R.id.contact_title_mail_text) EditText mMailTitle;
    @BindView(R.id.contact_message_body) EditText mMessageBody;

    private static final String BUNDLE_KEY_TAG = "BUNDLE_KEY_TAG";
    private static final String APEL = "apel.dolto.sqf@gmail.com";
    private static final String SCHOOL = "ecole.f.dolto@free.fr";
    private static final String DEV = "slayer171.flf@gmail.com";

    private String mRecipientTag;

    private String[] mRecipient = {APEL};

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_contact;
    }

    @Override
    public void start(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        if (savedInstanceState != null){
            mRecipientTag = savedInstanceState.getString(BUNDLE_KEY_TAG, "10");
            configureDesign(mRecipientTag);
        } else {
            mRecipientTag = "10";
        }
        mContactSchoolBtn.setOnClickListener(this);
        mContactApelBtn.setOnClickListener(this);
        mContactDevBtn.setOnClickListener(this);
    }

    @Override
    public Boolean isAChildActivity() {
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_KEY_TAG, mRecipientTag);
    }

    @Override
    public void onClick(View v) {
        mRecipientTag = v.getTag().toString();
        mContactApelBtn.setBackground(getDrawable(R.drawable.pink_empty_btn));
        mContactDevBtn.setBackground(getDrawable(R.drawable.orange_empty_btn));
        mContactSchoolBtn.setBackground(getDrawable(R.drawable.green_empty_btn));
        this.configureDesign(mRecipientTag);
    }

    //configure design elements
    private void configureDesign(String tag){
        switch (tag){
            case "10":
                mContactApelBtn.setBackground(getDrawable(R.drawable.pink_full_btn));
                mMailTitle.setBackground(getDrawable(R.drawable.pink_empty_btn));
                mMessageBody.setBackground(getDrawable(R.drawable.pink_empty_btn));
                mRecipient[0] = APEL;
                break;
            case "20":
                mContactSchoolBtn.setBackground(getDrawable(R.drawable.green_full_btn));
                mMailTitle.setBackground(getDrawable(R.drawable.green_empty_btn));
                mMessageBody.setBackground(getDrawable(R.drawable.green_empty_btn));
                mRecipient[0] = SCHOOL;
                break;
            case "30":
                mContactDevBtn.setBackground(getDrawable(R.drawable.orange_full_btn));
                mMailTitle.setBackground(getDrawable(R.drawable.orange_empty_btn));
                mMessageBody.setBackground(getDrawable(R.drawable.orange_empty_btn));
                mRecipient[0] = DEV;
                break;
        }
    }

    @OnClick(R.id.contact_cancel_message_btn) public void contactCancelMail(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @OnClick(R.id.contact_send_message_btn) public void contactSendMail(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, mRecipient);
        intent.putExtra(Intent.EXTRA_SUBJECT, mMailTitle.getText().toString());
        intent.putExtra(Intent.EXTRA_TEXT, mMessageBody.getText().toString());
        intent.setType("message/rcf822");
        startActivity(Intent.createChooser(intent,"choose"));
    }
}
