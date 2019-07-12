package com.epfd.csandroid.formulary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.epfd.csandroid.R;
import com.epfd.csandroid.api.UserHelper;
import com.epfd.csandroid.base.BaseActivity;
import com.epfd.csandroid.firstpage.FirstPageActivity;
import com.epfd.csandroid.formulary.recyclerview.FormularyAdapter;
import com.epfd.csandroid.models.Kid;
import com.epfd.csandroid.utils.Utils;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.OnClick;

public class FormularyActivity extends BaseActivity implements FormularyAdapter.Listener {

    @BindView(R.id.formulary_information) TextView mTextViewInformation;
    @BindView(R.id.formulary_recyclerview) RecyclerView mRecyclerView;
    @BindView(R.id.formulary_coordinatorLayout) CoordinatorLayout mCoordinatorLayout;

    private FormularyAdapter mAdapter;
    private ArrayList<Kid> mKidList;

    private static final String BUNDLE_KEY_KID_LIST = "BUNDLE_KEY_KID_LIST";


    @Override
    public int getFragmentLayout() {
        return R.layout.activity_formulary;
    }

    @Override
    public void start(@Nullable Bundle savedInstanceState) {
        if (this.getCurrentUser() != null){

            if (savedInstanceState != null){
                mKidList = savedInstanceState.getParcelableArrayList(BUNDLE_KEY_KID_LIST);
            }else {
                mKidList = new ArrayList<>();
                mKidList.add(new Kid("", "", "", Utils.EMPTY));
            }

            mAdapter = new FormularyAdapter(mKidList, this);
            this.mRecyclerView.setAdapter(mAdapter);
            this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));




        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(BUNDLE_KEY_KID_LIST, mKidList);
    }

    @Override
    public Boolean isAChildActivity() {
        return null;
    }

    /**
     *  BUTTON ACTION
     */

    @OnClick(R.id.formulary_add_kid_btn) public void formularyAddKid(){
        mKidList.add(new Kid("", "", "", Utils.EMPTY));
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.formulary_deconnexion) public void onClickSignOutButton() { this.signOutUserFromFirebase(); }

    @OnClick(R.id.formulary_valider) public void onClickValiderButton(){
        boolean kidListVerification = true;
        for (Kid kid : mKidList){
            if (kid.getPrenom().equals("") || kid.getNom().equals("") || kid.getClasse().equals("") || kid.getGenre().equals(Utils.EMPTY)){
                kidListVerification = false;
            }
        }
        if (kidListVerification){
            if (this.getCurrentUser() != null) {
                String urlPicture = (this.getCurrentUser().getPhotoUrl() != null) ? this.getCurrentUser().getPhotoUrl().toString() : Utils.EMPTY;
                UserHelper.createUser(this.getCurrentUser().getUid(), this.getCurrentUser().getDisplayName(), urlPicture,
                        BusinessFormulary.getKidNameList(mKidList), BusinessFormulary.getKidClassroomList(mKidList), BusinessFormulary.getKidGenderList(mKidList));
                startActivity(new Intent(this, FirstPageActivity.class));
                finish();
            }
        }else {
            Snackbar snackbar = Snackbar.make(mCoordinatorLayout, getString(R.string.formulary_snackbar_incomplet), Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();
            TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setMaxLines(6);
            snackbar.show();
        }
    }

    /**
     *  RECYCLER CALLBACK
     */

    @Override
    public void onClickDeleteButton(int position) {
        mKidList.remove(position);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateName(String name, int position) {
        mKidList.get(position).setNom(name);
    }

    @Override
    public void updateForname(String forname, int position) {
        mKidList.get(position).setPrenom(forname);
    }

    @Override
    public void updateClassroom(String classroom, int position) {
        mKidList.get(position).setClasse(classroom);
    }

    @Override
    public void updateGender(String gender, int position) {
        mKidList.get(position).setGenre(gender);
    }
}
