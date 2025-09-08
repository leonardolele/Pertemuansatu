package edu.uph.m23si2.pertamaapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import edu.uph.m23si2.pertamaapp.api.ApiResponse;
import edu.uph.m23si2.pertamaapp.api.ApiResponseKabupaten;
import edu.uph.m23si2.pertamaapp.api.ApiService;
import edu.uph.m23si2.pertamaapp.model.KRS;
import edu.uph.m23si2.pertamaapp.model.KRSDetail;
import edu.uph.m23si2.pertamaapp.model.Kabupaten;
import edu.uph.m23si2.pertamaapp.model.KelasMatakuliah;
import edu.uph.m23si2.pertamaapp.model.Mahasiswa;
import edu.uph.m23si2.pertamaapp.model.Matakuliah;
import edu.uph.m23si2.pertamaapp.model.Prodi;
import edu.uph.m23si2.pertamaapp.model.Provinsi;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText edtNama, edtPassword;
    Spinner sprProvinsi, sprKabupaten;
    List<Provinsi> provinsiList =  new ArrayList<>();
    List<String> namaProvinsi = new ArrayList<>();
    List<Kabupaten> kabupatenList = new ArrayList<>();
    List<String> namaKabupaten = new ArrayList<>();
    ArrayAdapter<String> adapter, adapterKabupaten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("default.realm")
                .schemaVersion(1)
                .allowWritesOnUiThread(true) // sementara aktifkan untuk demo
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        //initData();
        sprProvinsi = findViewById(R.id.sprProvinsi);
        adapter =new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,namaProvinsi);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sprProvinsi.setAdapter(adapter);

        sprKabupaten = findViewById(R.id.sprKabupaten);
        adapterKabupaten = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, namaKabupaten);
        adapterKabupaten.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sprKabupaten.setAdapter(adapterKabupaten);

        //init retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wilayah.id")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        //panggil API
        apiService.getProvinsi().enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.isSuccessful() && response.body()!=null){
                    provinsiList = response.body().getData();
                    namaProvinsi.clear();
                    for(Provinsi p: provinsiList){
                        if(p.getName()!=null){
                            Log.d("Provinsi", p.getName());
                            namaProvinsi.add(p.getName());
                        }
                    }

                    adapter.notifyDataSetChanged();

                    sprProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Provinsi selectedProvinsi = provinsiList.get(position);
                            Log.d("Provinsi", selectedProvinsi.getCode() + " - " + selectedProvinsi.getName());

                            // Panggil API kabupaten sesuai kode provinsi
                            apiService.getKabupaten(selectedProvinsi.getCode()).enqueue(new Callback<ApiResponseKabupaten>() {
                                @Override
                                public void onResponse(Call<ApiResponseKabupaten> call, Response<ApiResponseKabupaten> response) {
                                    if(response.isSuccessful() && response.body()!=null){
                                        kabupatenList = response.body().getData();
                                        namaKabupaten.clear();
                                        for(Kabupaten k : kabupatenList){
                                            if(k.getName()!=null){
                                                Log.d("Kabupaten", k.getName());
                                                namaKabupaten.add(k.getName());
                                            }
                                        }
                                        adapterKabupaten.notifyDataSetChanged();

                                        sprKabupaten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                Kabupaten selectedKabupaten = kabupatenList.get(position);
                                                Log.d("Kabupaten", selectedKabupaten.getCode() + " - " + selectedKabupaten.getName());
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> parent) { }
                                        });
                                    }
                                }

                                @Override
                                public void onFailure(Call<ApiResponseKabupaten> call, Throwable t) {
                                    Toast.makeText(LoginActivity.this,"Gagal : "+t.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Gagal :"+t.getMessage(),Toast.LENGTH_LONG);
            }
        });


        btnLogin = findViewById(R.id.btnLogin);
        edtNama = findViewById(R.id.edtNama);
        edtPassword = findViewById(R.id.edtPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDashboard();
            }
        });



    }

    public void initData(){ // menambahkan data prodi dan matakuliah
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(r -> {
            Number maxId = r.where(Mahasiswa.class).max("studentID");
            Prodi prodiSI = r.createObject(Prodi.class,0);
            prodiSI.setFakultas("Fakultas Teknologi Informasi");
            prodiSI.setNama("Sistem Informasi");

            Matakuliah matMobile = r.createObject(Matakuliah.class,0);
            matMobile.setNama("Pemrograman Mobile Lanjut");
            matMobile.setSks(3);
            matMobile.setProdi(prodiSI);

            Matakuliah matPBO = r.createObject(Matakuliah.class,1);
            matPBO.setNama("Pemrograman Berorientasi Objek");
            matPBO.setSks(3);
            matPBO.setProdi(prodiSI);

            KelasMatakuliah kelasMobile = r.createObject(KelasMatakuliah.class,0);
            kelasMobile.setRuangKelas("AD101");
            kelasMobile.setDosen("Sir Ade");

            KelasMatakuliah kelasPBO = r.createObject(KelasMatakuliah.class,1);
            kelasPBO.setRuangKelas("AD102");
            kelasPBO.setDosen("Sir Des");

            KRS krs1 = r.createObject(KRS.class,0);
            krs1.setSemester(6);
            krs1.setTahunAjaran("2024/2025");

            KRSDetail detail1 = r.createObject(KRSDetail.class,0);
            detail1.setKrs(krs1);
            detail1.setKelasMatakuliah(kelasMobile);
            detail1.setStatus("Diambil");

            KRSDetail detail2 = r.createObject(KRSDetail.class,1);
            detail2.setKrs(krs1);
            detail2.setKelasMatakuliah(kelasPBO);
            detail2.setStatus("Diambil");

        });
        Toast.makeText(this, "Data tersimpan", Toast.LENGTH_SHORT).show();

    }
    public void toProfil(){
        Intent intent = new Intent(this,ProfilActivity.class);
        intent.putExtra("nama",edtNama.getText().toString());
        startActivity(intent);
    }
    public void toDashboard(){
        Intent intent = new Intent(this,DashboardActivity.class);
        startActivity(intent);
    }
}