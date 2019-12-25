package com.jangkung.ktm;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class ValidasiDicetak extends Fragment {
    private TextView kembali;
    public final static String TAG_USERNAME = "username";
    public final static String TAG_ID = "id";
    SharedPreferences sharedpreferences;
    Boolean session = false;
    private String id, username;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";


    public ValidasiDicetak() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_validasi_dicetak,container, false);
        sharedpreferences = getActivity().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        id = sharedpreferences.getString(TAG_ID, null);
        username = sharedpreferences.getString(TAG_USERNAME, null);

        kembali = (TextView) view.findViewById(R.id.validasikembali);
        SpannableString content = new SpannableString("Lanjut");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        kembali.setText(content);

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updatePengajuan();
                Fragment fragment = new PengajuanFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_main, fragment);
                ft.commit();
            }
        });

        return view;
    }
    private void updatePengajuan() {


        class UpdatePengajuan extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute(){
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);

            }

            @Override
            protected String doInBackground(Void... params){
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_EMP_USERNAME,username);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.URL_UPDATE_PENGAJUAN,hashMap);
                return  s;
            }
        }

        UpdatePengajuan uu = new UpdatePengajuan();
        uu.execute();
    }
}
