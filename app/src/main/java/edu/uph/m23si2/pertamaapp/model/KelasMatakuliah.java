package edu.uph.m23si2.pertamaapp.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class KelasMatakuliah extends RealmObject {
    @PrimaryKey
    private int kelasID;
    private String RuangKelas, Dosen;
    private Matakuliah matakuliah;

    public KelasMatakuliah(){}

    public int getKelasID() {
        return kelasID;
    }

    public void setKelasID(int kelasID) {
        this.kelasID = kelasID;
    }

    public String getRuangKelas() {
        return RuangKelas;
    }

    public void setRuangKelas(String ruangKelas) {
        RuangKelas = ruangKelas;
    }

    public String getDosen() {
        return Dosen;
    }

    public void setDosen(String dosen) {
        Dosen = dosen;
    }

    public Matakuliah getMatakuliah() {
        return matakuliah;
    }

    public void setMatakuliah(Matakuliah matakuliah) {
        this.matakuliah = matakuliah;
    }
}