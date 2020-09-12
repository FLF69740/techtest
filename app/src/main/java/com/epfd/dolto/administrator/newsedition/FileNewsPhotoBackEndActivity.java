package com.epfd.dolto.administrator.newsedition;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import com.epfd.dolto.R;
import com.epfd.dolto.administrator.newsedition.recyclerview.PhotBackendAdapter;
import com.epfd.dolto.base.BaseActivity;
import com.epfd.dolto.utils.BitmapStorage;
import com.epfd.dolto.utils.RecyclerViewClickSupport;
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

        for (String photoName : photoNameString){
            if (BitmapStorage.isFileExist(this, photoName)) {
                mPhotoNameList.add(photoName);
            }
        }

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        PhotBackendAdapter adapter = new PhotBackendAdapter(mPhotoNameList);
        mRecyclerView.setAdapter(adapter);

        RecyclerViewClickSupport.addTo(mRecyclerView, R.layout.photo_backend_item)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Intent intent = new Intent();
                    intent.putExtra(BUNDLE_EXTRA_PHOTO_BACKEND, mPhotoNameList.get(position));
                    setResult(RESULT_OK, intent);
                    finish();
                });
    }


}
