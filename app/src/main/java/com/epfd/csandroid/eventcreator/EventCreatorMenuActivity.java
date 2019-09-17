package com.epfd.csandroid.eventcreator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.epfd.csandroid.R;
import com.epfd.csandroid.base.BaseActivity;
import com.epfd.csandroid.models.Event;
import com.epfd.csandroid.utils.Utils;

import org.joda.time.DateTime;

import butterknife.OnClick;

public class EventCreatorMenuActivity extends BaseActivity {

    public static final String INTENT_EVENT_CREATOR_MENU = "INTENT_EVENT_CREATOR_MENU";

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_event_creator_menu;
    }

    @Override
    public Boolean isAChildActivity() {
        return true;
    }

    @Override
    public void start(@Nullable Bundle savedInstanceState) {

    }

    @OnClick(R.id.event_creator_add_event)
    public void addAnEvent(){
        DateTime dateTime = new DateTime();
        Event event = new Event("", dateTime.toString("dd/MM/yyyy"),"", Utils.EMPTY, Utils.EMPTY);
        Intent intent = new Intent(this, EventPanelActivity.class);
        intent.putExtra(INTENT_EVENT_CREATOR_MENU, event);
        startActivity(intent);
    }


}
