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
import com.epfd.csandroid.api.ClassroomsHelper;
import com.epfd.csandroid.api.UserHelper;
import com.epfd.csandroid.base.BaseActivity;
import com.epfd.csandroid.firstpage.FirstPageActivity;
import com.epfd.csandroid.formulary.recyclerview.FormularyAdapter;
import com.epfd.csandroid.models.Kid;
import com.epfd.csandroid.models.User;
import com.epfd.csandroid.utils.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

public class FormularyActivity extends BaseActivity implements FormularyAdapter.Listener {

    @BindView(R.id.formulary_information) TextView mTextViewInformation;
    @BindView(R.id.formulary_recyclerview) RecyclerView mRecyclerView;
    @BindView(R.id.formulary_coordinatorLayout) CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.formulary_bottomNavigationView) BottomNavigationView mBottomNavigationView;

    private FormularyAdapter mAdapter;
    private ArrayList<Kid> mKidList;
    private List<String> mClassroomsList;

    private static final String BUNDLE_KEY_KID_LIST = "BUNDLE_KEY_KID_LIST";


    @Override
    public int getFragmentLayout() {
        return R.layout.activity_formulary;
    }

    @Override
    public void start(@Nullable Bundle savedInstanceState) {
        if (this.getCurrentUser() != null){

            mBottomNavigationView.setOnNavigationItemSelectedListener(item -> bottomNavigationViewAction(item.getItemId()));

            if (savedInstanceState != null){
                mKidList = savedInstanceState.getParcelableArrayList(BUNDLE_KEY_KID_LIST);
             //   this.configureRecyclerView(mKidList);
                this.preconfigRecyclerView(mKidList);
            }else {
                UserHelper.getUser(this.getCurrentUser().getUid()).addOnSuccessListener(documentSnapshot1 -> {
                    if (documentSnapshot1.toObject(User.class) != null) {
                        User user = documentSnapshot1.toObject(User.class);
                        mKidList = new ArrayList<>();
                        if (user != null) {
                            for (int i = 0; i < BusinessFormulary.getStringListToKids(user.getStringKidNameList()).size(); i++) {
                                String completeName = BusinessFormulary.getStringListToKids(user.getStringKidNameList()).get(i);
                                String classroom = BusinessFormulary.getStringListToKids(user.getStringClasseNameList()).get(i);
                                String gender = BusinessFormulary.getStringListToKids(user.getStringGenderList()).get(i);
                                mKidList.add(new Kid(BusinessFormulary.getStringNameFromKid(completeName), BusinessFormulary.getStringFornameFromKid(completeName), classroom, gender));
                            }
                        }
                    //    this.configureRecyclerView(mKidList);
                        this.preconfigRecyclerView(mKidList);
                    } else {
                        mKidList = new ArrayList<>();
                        mKidList.add(new Kid("", "", "", Utils.EMPTY));
                    //    this.configureRecyclerView(mKidList);
                        this.preconfigRecyclerView(mKidList);
                    }
                });
            }
        }
    }

    private void preconfigRecyclerView(List<Kid> kidList){
        ClassroomsHelper.getClassrooms().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                mClassroomsList = Arrays.asList(documentSnapshot.getString(Utils.NAME_DATA_CLASSROOMS).split(","));
                configureRecyclerView(kidList, mClassroomsList);
            }
        });
    }

    //configure RecyclerView
    private void configureRecyclerView(List<Kid> kidList, List<String> myList){
        mAdapter = new FormularyAdapter(this, kidList, myList, this);
        this.mRecyclerView.setAdapter(mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    private Boolean bottomNavigationViewAction(Integer integer){
        switch (integer){
            case R.id.formulary_deconnexion :
                this.signOutUserFromFirebase();
                break;
            case R.id.formulary_back :
                onBackPressed();
                break;
            case R.id.formulary_validation :
                this.onClickValiderButton();
                break;
        }
        return true;
    }

    private void onClickValiderButton(){
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
