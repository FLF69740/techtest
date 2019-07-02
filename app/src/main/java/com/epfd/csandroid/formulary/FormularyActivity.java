package com.epfd.csandroid.formulary;

import android.content.DialogInterface;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.epfd.csandroid.R;
import com.epfd.csandroid.base.BaseActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;

import butterknife.BindView;
import butterknife.OnClick;

public class FormularyActivity extends BaseActivity {

    @BindView(R.id.formulary_information) TextView mTextViewInformation;

    private static final int SIGN_OUT_TASK = 10;
    private static final int DELETE_USER_TASK = 20;

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_formulary;
    }

    @Override
    public void start() {
        if (this.getCurrentUser() != null){
            String info = "name : " + getCurrentUser().getDisplayName() + "\nmail : " + getCurrentUser().getEmail();
            mTextViewInformation.setText(info);
        }
    }

    @Override
    public Boolean isAChildActivity() {
        return null;
    }







    // 4 - Adding requests to button listeners

    @OnClick(R.id.formulary_deconnexion) public void onClickSignOutButton() { this.signOutUserFromFirebase(); }

    @OnClick(R.id.formulary_delete) public void onClickDeleteButton() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.popup_message_confirmation_delete_account)
                .setPositiveButton(R.string.popup_message_choice_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteUserFromFirebase();
                    }
                })
                .setNegativeButton(R.string.popup_message_choice_no, null)
                .show();
    }

    //create http request Sign Out
    private void signOutUserFromFirebase(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnSuccessListener(this, this.updateUIAfterRESTRequestsCompleted(SIGN_OUT_TASK));
    }

    //create http request Delete
    private void deleteUserFromFirebase(){
        if (this.getCurrentUser() != null) {
            AuthUI.getInstance()
                    .delete(this)
                    .addOnSuccessListener(this, this.updateUIAfterRESTRequestsCompleted(DELETE_USER_TASK));
        }
    }

    //Create OnCompleteListener called after tasks ended
    private OnSuccessListener<Void> updateUIAfterRESTRequestsCompleted(final int origin){
        return new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                switch (origin){
                    case SIGN_OUT_TASK:
                        finish();
                        break;
                    case DELETE_USER_TASK:
                        finish();
                        break;
                    default:
                        break;
                }
            }
        };
    }

}
