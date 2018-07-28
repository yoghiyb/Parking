package com.example.yoghi.e_parking;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.yoghi.e_parking.R.layout.fragment_profile;

public class ProfileFragment extends Fragment {

    TextView welcome, email_welcome;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(fragment_profile, container, false);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        welcome = v.findViewById(R.id.welcome);
        email_welcome = v.findViewById(R.id.welcome_email);
        if (user != null){
            String email = user.getEmail();
            email_welcome.setText(email);
        }

        return v;
    }
}
