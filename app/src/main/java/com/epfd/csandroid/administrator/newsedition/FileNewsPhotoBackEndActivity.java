package com.epfd.csandroid.administrator.newsedition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.epfd.csandroid.R;
import com.epfd.csandroid.administrator.newsedition.recyclerview.PhotBackendAdapter;
import com.epfd.csandroid.base.BaseActivity;
import com.epfd.csandroid.utils.RecyclerViewClickSupport;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FileNewsPhotoBackEndActivity extends BaseActivity {

    @BindView(R.id.file_news_photo_backend_recycler) RecyclerView mRecyclerView;
    private List<String> mUriStringList = new ArrayList<>();
    private List<String> mPhotoNameList = new ArrayList<>();
    private PhotBackendAdapter mAdapter;

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_file_news_photo_back_end;
    }

    @Override
    public Boolean isAChildActivity() {
        return true;
    }

    @Override
    public void start(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new PhotBackendAdapter(mUriStringList);
        mRecyclerView.setAdapter(mAdapter);

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.listAll().addOnSuccessListener(listResult -> {
            Toast.makeText(getApplicationContext(), "GETTING LIST", Toast.LENGTH_SHORT).show();
            for (StorageReference prefix : listResult.getItems()){
                prefix.getDownloadUrl().addOnSuccessListener(uri -> {
                    mUriStringList.add(uri.toString());
                    mPhotoNameList.add(prefix.getName());
                    mAdapter.notifyDataSetChanged();
                });
            }
        }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "STORAGE FAIL", Toast.LENGTH_SHORT).show());

        RecyclerViewClickSupport.addTo(mRecyclerView, R.layout.photo_backend_item)
                .setOnItemClickListener(new RecyclerViewClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Toast.makeText(getApplicationContext(), "PHOTO : " + mPhotoNameList.get(position), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
