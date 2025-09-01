package edu.uph.m23si2.pertamaapp.model;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class KRS extends RealmObject {
    @PrimaryKey
    private int idKrs;
    private Mahasiswa mahasiswa;
    private Integer semester;

    public KRS(Integer idKrs, Mahasiswa mahasiswa, Integer semester) {
        this.idKrs = idKrs;
        this.mahasiswa = mahasiswa;
        this.semester = semester;
    }

    // Getter & Setter
    public Integer getIdKrs() {
        return idKrs;
    }

    public void setIdKrs(Integer idKrs) {
        this.idKrs = idKrs;
    }

    public Mahasiswa getMahasiswa() {
        return mahasiswa;
    }

    public void setMahasiswa(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    @Override
    public String toString() {
        return "KRS{" +
                "idKrs='" + idKrs + '\'' +
                ", mahasiswa=" + mahasiswa.getNama() +
                ", semester='" + semester + '\'' +
                '}';
    }
}