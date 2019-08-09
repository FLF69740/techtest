package com.epfd.csandroid.notifications;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.epfd.csandroid.R;
import com.epfd.csandroid.utils.Utils;

public class NotificationWindowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_notification_window);

        TextView title = findViewById(R.id.notification_window_title);
        TextView message = findViewById(R.id.notification_window_body);

        title.setText(getIntent().getStringExtra(Utils.CONSOLE_NOTIF_TITLE));
        message.setText(getIntent().getStringExtra(Utils.CONSOLE_NOTIF_BODY));
    }
}
