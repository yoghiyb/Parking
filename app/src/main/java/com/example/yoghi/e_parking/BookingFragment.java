package com.example.yoghi.e_parking;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.yoghi.e_parking.R.layout.*;

public class BookingFragment extends Fragment {

    public TextView userEmail;
    public EditText nama_user, no_pol;
    Spinner spkampus, splantai;
    Button btn_pesan, btn_batal;
    private DatabaseReference dbParkir;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(fragment_booking, container, false);
        final Resources res = getResources();
        nama_user = v.findViewById(R.id.nama_user);
        no_pol = v.findViewById(R.id.no_pol);
        userEmail = v.findViewById(R.id.viewEmail);
        dbParkir = FirebaseDatabase.getInstance().getReference("Parkings");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            userEmail.setText(email);
        }
        splantai = v.findViewById(R.id.splantai);
        spkampus = v.findViewById(R.id.spkampus);
        spkampus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (spkampus.getSelectedItem().equals("Kampus 1")) {
                    ArrayAdapter lt = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, res.getStringArray(R.array.lantai_k1));
                    lt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    splantai.setAdapter(lt);
                } else if (spkampus.getSelectedItem().equals("Kampus 2")) {
                    ArrayAdapter lt = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, res.getStringArray(R.array.lantai_k2));
                    lt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    splantai.setAdapter(lt);
                } else if (spkampus.getSelectedItem().equals("Kampus 3")) {
                    ArrayAdapter lt = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, res.getStringArray(R.array.lantai_k3));
                    lt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    splantai.setAdapter(lt);
                } else if (spkampus.getSelectedItem().equals("Kampus 4")) {
                    ArrayAdapter lt = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, res.getStringArray(R.array.lantai_k4));
                    lt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    splantai.setAdapter(lt);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "Pilih Kampus dan lantai ", Toast.LENGTH_SHORT).show();
            }
        });

        btn_pesan = v.findViewById(R.id.btn_pesan);
        btn_pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //string untuk input database
                String email = userEmail.getText().toString();
                String nama = nama_user.getText().toString();
                String nopol = no_pol.getText().toString();
                String kampus = spkampus.getSelectedItem().toString();
                String lantai = splantai.getSelectedItem().toString();


                if (!TextUtils.isEmpty(nama) && !TextUtils.isEmpty(nopol)) {
                    String id = dbParkir.push().getKey();
                    Park parkir = new Park(id, email, nama, nopol, kampus, lantai);
                    dbParkir.child(nopol).setValue(parkir);
                    Toast.makeText(getActivity(), "Pemesanan Berhasil", Toast.LENGTH_SHORT).show();

                    SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    Date todayDate = new Date();
                    String thisDate = currentDate.format(todayDate);

                    SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault());
                    Date time = new Date();
                    String thisTime = currentTime.format(time);

                    TiketFragment tiketFragment = new TiketFragment();
                    FragmentManager manager = getFragmentManager();
                    Bundle bundle = new Bundle();
                    bundle.putString("kampus", kampus);
                    bundle.putString("email", email);
                    bundle.putString("nopol", nopol);
                    bundle.putString("tgl", thisDate);
                    bundle.putString("waktu", thisTime);
                    tiketFragment.setArguments(bundle);

                    manager.beginTransaction().replace(R.id.fragment_container, tiketFragment).commit();

//                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                            new TiketFragment()).commit();

                } else {
                    Toast.makeText(getActivity(), "Tolong isi Nama dan No.pol", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_batal = v.findViewById(R.id.btn_batal);
        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nama = nama_user.getText().toString();
                String nopol = no_pol.getText().toString();

                if (!TextUtils.isEmpty(nama) || !TextUtils.isEmpty(nopol)) {
                    nama_user.getText().clear();
                    no_pol.getText().clear();
                } else {
                    ProfileFragment profileFragment = new ProfileFragment();
                    FragmentManager manager = getFragmentManager();
                    manager.beginTransaction().replace(R.id.fragment_container, profileFragment).commit();
                }

            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
