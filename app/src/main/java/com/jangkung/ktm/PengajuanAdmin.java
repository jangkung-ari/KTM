package com.jangkung.ktm;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ListView;
import android.widget.ListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class PengajuanAdmin extends Fragment implements ListView.OnItemClickListener {
    private Button status;
    private ListView listView;
    private String JSON_STRING;

    public PengajuanAdmin() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pengajuan_admin,container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        getJSON();
        listView.setOnItemClickListener(this);


        return view;
    }

    private void showPengajuan(){
        System.out.println(JSON_STRING);
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try{
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i =0; i<result.length();i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Config.TAG_ID);
                String nama = jo.getString(Config.TAG_NAME);
                String status = jo.getString(Config.TAG_STATUS);
                String prodi = jo.getString(Config.TAG_PRODI);
                String kota = jo.getString(Config.TAG_KOTA);
                String tanggal = jo.getString(Config.TAG_TANGGAL);
                String nrp = jo.getString(Config.TAG_NRP);
                String gender = jo.getString(Config.TAG_GENDER);
                String validasi = jo.getString(Config.TAG_VALIDASI);

                HashMap<String,String> pengajuan = new HashMap<>();
                pengajuan.put(Config.TAG_NAME,nama);
                pengajuan.put(Config.TAG_ID,id);
                pengajuan.put(Config.TAG_STATUS,status);
                pengajuan.put(Config.TAG_PRODI,prodi);
                pengajuan.put(Config.TAG_KOTA,kota);
                pengajuan.put(Config.TAG_TANGGAL,tanggal);
                pengajuan.put(Config.TAG_NRP,nrp);
                pengajuan.put(Config.TAG_GENDER,gender);
                pengajuan.put(Config.TAG_VALIDASI,validasi);
                list.add(pengajuan);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(
                getContext(), list, R.layout.layout_row_item,
                new String[]{Config.TAG_NAME,Config.TAG_STATUS},
                new int[]{R.id.judul, R.id.subjudul});
        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(),"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showPengajuan();
            }

            @Override
            protected String doInBackground(Void... params){
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_ALL);
                return s;

            }
        }GetJSON gj = new GetJSON();
        gj.execute();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), DetailPengajuan.class);
        HashMap<String,String> map = (HashMap)parent.getItemAtPosition(position);
        String pengajuanId = map.get(Config.TAG_ID).toString();
        intent.putExtra(Config.EMP_ID,pengajuanId);
        startActivity(intent);
    }
}
