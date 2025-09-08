package edu.uph.m23si2.pertamaapp.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class KRS extends RealmObject {
    @PrimaryKey
    private int krsID;
    private int Semester;
    private String TahunAjaran;
    private Mahasiswa mahasiswa;

    public KRS(){}

    public int getKrsID() {
        return krsID;
    }

    public void setKrsID(int krsID) {
        this.krsID = krsID;
    }

    public int getSemester() {
        return Semester;
    }

    public void setSemester(int semester) {
        Semester = semester;
    }

    public String getTahunAjaran() {
        return TahunAjaran;
    }

    public void setTahunAjaran(String tahunAjaran) {
        TahunAjaran = tahunAjaran;
    }

    public Mahasiswa getMahasiswa() {
        return mahasiswa;
    }

    public void setMahasiswa(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
    }
}