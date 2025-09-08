package edu.uph.m23si2.pertamaapp.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class KRSDetail extends RealmObject {
    @PrimaryKey
    private int detailID;
    private String Status;
    private KRS krs;
    private KelasMatakuliah kelasMatakuliah;

    public KRSDetail(){}

    public int getDetailID() {
        return detailID;
    }

    public void setDetailID(int detailID) {
        this.detailID = detailID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public KRS getKrs() {
        return krs;
    }

    public void setKrs(KRS krs) {
        this.krs = krs;
    }

    public KelasMatakuliah getKelasMatakuliah() {
        return kelasMatakuliah;
    }

    public void setKelasMatakuliah(KelasMatakuliah kelasMatakuliah) {
        this.kelasMatakuliah = kelasMatakuliah;
    }
}