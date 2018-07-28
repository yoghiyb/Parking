package com.example.yoghi.e_parking;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import static com.example.yoghi.e_parking.R.layout.fragment_tiket;

public class TiketFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(fragment_tiket, container, false);

        TextView viewKampus = v.findViewById(R.id.viewKampus);
        TextView viewEmailBooking = v.findViewById(R.id.viewEmailBooking);
        TextView viewTanggal = v.findViewById(R.id.viewTanggal);
        TextView viewWaktu = v.findViewById(R.id.viewWaktu);
        ImageView imageQrcode = v.findViewById(R.id.qrcodeImage);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String kampus = bundle.getString("kampus");
            String email = bundle.getString("email");
            String text2Qr = bundle.getString("nopol");
            String tgl = bundle.getString("tgl");
            String waktu = bundle.getString("waktu");

            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE, 200, 200);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                imageQrcode.setImageBitmap(bitmap);

            } catch (WriterException e) {
                e.printStackTrace();
            }

            viewEmailBooking.setText(email);
            viewKampus.setText(kampus);
            viewTanggal.setText(tgl);
            viewWaktu.setText(waktu);
        }


        return v;
    }
}
