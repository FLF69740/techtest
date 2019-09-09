package com.epfd.csandroid.administrator.newsedition;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import com.epfd.csandroid.R;
import com.epfd.csandroid.administrator.newsedition.recyclerview.PhotBackendAdapter;
import com.epfd.csandroid.base.BaseActivity;
import com.epfd.csandroid.utils.BitmapStorage;
import com.epfd.csandroid.utils.RecyclerViewClickSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FileNewsPhotoBackEndActivity extends BaseActivity {

    @BindView(R.id.file_news_photo_backend_recycler) RecyclerView mRecyclerView;
    private List<String> mPhotoNameList = new ArrayList<>();

    public static final String BUNDLE_EXTRA_PHOTO_BACKEND = "BUNDLE_EXTRA_PHOTO_BACKEND";

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

        String photoCode = BitmapStorage.getPhotoMemoryCode(this);
        List<String> photoNameString = Arrays.asList(photoCode.split(BitmapStorage.PHOTO_SEPARATOR));

        List<Bitmap> bitmapStorageList = new ArrayList<>();
        for (String photoName : photoNameString){
            if (BitmapStorage.isFileExist(this, photoName)) {
                mPhotoNameList.add(photoName);
                Bitmap bp = BitmapStorage.loadImage(this, photoName);
                bitmapStorageList.add(bp);
            }
        }

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        PhotBackendAdapter adapter = new PhotBackendAdapter(bitmapStorageList);
        mRecyclerView.setAdapter(adapter);
/*
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.listAll().addOnSuccessListener(listResult -> {
            for (StorageReference prefix : listResult.getItems()){
                prefix.getDownloadUrl().addOnSuccessListener(uri -> {
                    mUriStringList.add(uri.toString());
                    mPhotoNameList.add(prefix.getName());
                    mAdapter.notifyDataSetChanged();
                });
            }
        }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "STORAGE FAIL", Toast.LENGTH_SHORT).show());
*/



        RecyclerViewClickSupport.addTo(mRecyclerView, R.layout.photo_backend_item)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Intent intent = new Intent();
                    intent.putExtra(BUNDLE_EXTRA_PHOTO_BACKEND, mPhotoNameList.get(position));
                    setResult(RESULT_OK, intent);
                    finish();
                });
    }


}
