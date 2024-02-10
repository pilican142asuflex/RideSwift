package com.pilican.rideswift.Login;

import android.content.Intent;
import android.os.Bundle;

import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.google.android.gms.tasks.OnCompleteListener;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.pilican.rideswift.R;

import java.util.HashMap;
import java.util.Map;



public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mName;

    private SegmentedButtonGroup mRadioGroup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initializeObjects();
    }



    private void register() {
        if (mName.getText().length() == 0) {
            mName.setError("please fill this field");
            return;
        }


        final String name = mName.getText().toString();
        final String accountType;
        int selectId = mRadioGroup.getPosition();

        if (selectId == 1) {
            accountType = "Drivers";
        } else {
            accountType = "Customers";
        }

        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Map<String, Object> newUserMap = new HashMap<>();
        newUserMap.put("name", name);
        newUserMap.put("profileImageUrl", "default");
        if (accountType.equals("Drivers")) {
            newUserMap.put("service", "type_1");
            newUserMap.put("activated", true);
        }
        FirebaseDatabase.getInstance().getReference().child("Users").child(accountType).child(user_id).updateChildren(newUserMap).addOnCompleteListener((OnCompleteListener<Void>) task -> {
            Intent intent = new Intent(DetailsActivity.this, LauncherActivity.class);
            startActivity(intent);
            finish();
        });
    }



    private void initializeObjects() {
        mName = findViewById(R.id.name);
        Button mRegister = findViewById(R.id.register);
        mRadioGroup = findViewById(R.id.radioRealButtonGroup);

        mRadioGroup.setPosition(0, false);
        mRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.register) {
            register();
        }
    }
}