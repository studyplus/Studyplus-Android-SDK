package jp.studyplus.android.sdk_example_java;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import jp.studyplus.android.sdk.Studyplus;
import jp.studyplus.android.sdk.record.StudyRecord;
import jp.studyplus.android.sdk.record.StudyRecordBuilder;

public class MainActivity extends AppCompatActivity {

    private final static int REQUEST_CODE_AUTH = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Studyplus.getInstance().setup(
                getString(R.string.consumer_key),
                getString(R.string.consumer_secret));

        Button startAuthButton = findViewById(R.id.start_auth);
        startAuthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Studyplus.getInstance().startAuth(MainActivity.this, REQUEST_CODE_AUTH);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Need for Studyplus 2.14.0+", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button postRecordButton = findViewById(R.id.post_study_record);
        postRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudyRecord record = new StudyRecordBuilder()
                        .setComment("勉強した！！！")
                        .setAmountTotal(30)
                        .setDurationSeconds(2 * 60)
                        .build();
                Studyplus.getInstance().postRecord(MainActivity.this, record,
                        new Studyplus.Companion.OnPostRecordListener() {
                            @Override
                            public void onResult(boolean success, Long recordId, Throwable throwable) {
                                if (success) {
                                    Toast.makeText(MainActivity.this,
                                            String.format("Post Study Record!! (%d)", recordId), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "error!!", Toast.LENGTH_LONG).show();
                                    if (throwable != null) {
                                        throwable.printStackTrace();
                                    }
                                }
                            }
                        });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        TextView authStatusText = findViewById(R.id.auth_status);
        authStatusText.setText(Studyplus.getInstance().isAuthenticated(this)
                ? getString(R.string.status_authenticated)
                : getString(R.string.status_unauthenticated));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_AUTH:
                if (resultCode == Activity.RESULT_OK) {
                    Studyplus.getInstance().setAuthResult(this, data);
                    Toast.makeText(this, "Success!!", Toast.LENGTH_LONG).show();
                }
        }
    }
}
