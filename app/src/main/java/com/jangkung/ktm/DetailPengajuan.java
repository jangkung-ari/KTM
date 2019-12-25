package com.jangkung.ktm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class DetailPengajuan extends AppCompatActivity {
    private String id,spin,validasi;
    private int index;
    private TextInputEditText enama,enrp,eprodi,estatus,ekota,etanggal,egender;
    private TextView btnSubmit;
    private HttpResponse httpResponse;
    private String StringHolder = "";
    private JSONObject jsonObject = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pengajuan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("Montserrat-SemiBold.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        Intent intent = getIntent();

        id = intent.getStringExtra(Config.EMP_ID);
        System.out.println(id);

        enama = (TextInputEditText) findViewById(R.id.etNamapengajuan);
        enrp = (TextInputEditText) findViewById(R.id.etNRPpengajuan);
        eprodi = (TextInputEditText) findViewById(R.id.etProdipengajuan);
        estatus = (TextInputEditText) findViewById(R.id.etStatuspengajuan);
        ekota = (TextInputEditText) findViewById(R.id.etCitypengajuan);
        etanggal = (TextInputEditText) findViewById(R.id.etDatepengajuan);
        egender = (TextInputEditText) findViewById(R.id.etGenderpengajuan);

        btnSubmit = (TextView) findViewById(R.id.btn_submitpengajuan);


        getDetailPengajuan();
        System.out.println(index);



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                updateDetail();
                Toast.makeText(DetailPengajuan.this, spin,
                        Toast.LENGTH_LONG).show();
                finish();

            }
        });
    }

    private void getDetailPengajuan(){
        class GetDetailPengajuan extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(DetailPengajuan.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                showPengajuan(s);
            }

            @Override
            protected String doInBackground(Void... params){
                RequestHandler rh = new RequestHandler();
                String s= rh.sendGetRequestParam(Config.URL_GET_ID,id);
                return s;
            }
        }GetDetailPengajuan ge = new GetDetailPengajuan();
        ge.execute();
    }

    private void showPengajuan(String json){
        try {

                JSONObject jsonObject = new JSONObject(json);
                JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
                JSONObject c = result.getJSONObject(0);
                String nama = c.getString(Config.TAG_NAME);
                String nrp = c.getString(Config.TAG_NRP);
                String prodi = c.getString(Config.TAG_PRODI);
                String kota = c.getString(Config.TAG_KOTA);
                String tanggal = c.getString(Config.TAG_TANGGAL);
                String gender = c.getString(Config.TAG_GENDER);
                String status = c.getString(Config.TAG_STATUS);
                validasi = c.getString(Config.TAG_VALIDASI);
            if(validasi.equals("Belum Divalidasi")){
                index=0;
            }else if(validasi.equals("Validasi Ditolak")){
                index=1;
            }else if(validasi.equals("Validasi")){
                index=2;
            }else if(validasi.equals("Validasi Dicetak")){
                index=3;
            }
            MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);
            spinner.setItems("Belum Divalidasi", "Validasi Ditolak", "Validasi", "Validasi Dicetak");
            spinner.setSelectedIndex(index);
            spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                    Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                    spin=item;
                }
            });

                enama.setText(nama);
                enrp.setText(nrp);
                eprodi.setText(prodi);
                ekota.setText(kota);
                etanggal.setText(tanggal);
                egender.setText(gender);
                estatus.setText(status);

        } catch (JSONException e){
            e.printStackTrace();
        }
    }
    private void updateDetail() {

//        final String valid = enama.getText().toString().trim();
        final String valid = spin.toString().trim();

        class UpdateUser extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(DetailPengajuan.this,"Updating...","Wait",false,false);
            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
            }

            @Override
            protected String doInBackground(Void... params){
                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put(Config.KEY_EMP_VALIDASI,valid);
                hashMap.put(Config.KEY_EMP_ID,id);
                System.out.println(valid);
                System.out.println(id);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.URL_UPDATE_VALIDASI,hashMap);
                return  s;
            }
        }

        UpdateUser uu = new UpdateUser();
        uu.execute();
    }


    @Override
    public boolean onSupportNavigateUp(){
        super.onBackPressed();
        return true;
    }

}
