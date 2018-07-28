package com.example.yoghi.e_parking;

public class Park {

    String parkirId;
    String parkirEmail;
    String parkirNama;
    String parkirNopol;
    String ParkirKampus;
    String ParkirLantai;

    public Park() {

    }

    public Park(String parkirId, String parkirEmail, String parkirNama, String parkirNopol, String parkirKampus, String parkirLantai) {
        this.parkirId = parkirId;
        this.parkirEmail = parkirEmail;
        this.parkirNama = parkirNama;
        this.parkirNopol = parkirNopol;
        ParkirKampus = parkirKampus;
        ParkirLantai = parkirLantai;
    }

    public String getParkirId() {
        return parkirId;
    }

    public String getParkirEmail() {
        return parkirEmail;
    }

    public String getParkirNama() {
        return parkirNama;
    }

    public String getParkirNopol() {
        return parkirNopol;
    }

    public String getParkirKampus() {
        return ParkirKampus;
    }

    public String getParkirLantai() {
        return ParkirLantai;
    }
}
