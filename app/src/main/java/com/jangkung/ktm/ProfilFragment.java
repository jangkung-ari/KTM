package com.jangkung.ktm;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;
import org.apache.http.HttpResponse;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;
import android.widget.Toast;
import com.android.volley.RequestQueue;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jangkung.ktm.app.AppController;

import android.net.ConnectivityManager;

import net.steamcrafted.lineartimepicker.dialog.LinearDatePickerDialog;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextInputEditText pickDate;
    private TextInputEditText pickGender;
    private LinearDatePickerDialog dialogdate;
    private MaterialDialog dialoggender;
    private TextView save;
    private HttpResponse httpResponse;
    private JSONObject jsonObject = null ;
    private String StringHolder = "" ;
    private TextView prodi,nrp;
    private TextInputEditText fullname,kota,tanggal,gender,hp,mail;
    ConnectivityManager conMgr;
    JSONObject json = null;
    String IdHolder, UserNameHolder, UserPhoneHolder, UserMailHolder, UserKotaHolder, UserTanggalHolder, UserGenderHolder;
    HashMap<String,String> hashMap = new HashMap<>();
    Boolean CheckEditText ;
//    HttpParse httpParse = new HttpParse();

    public final static String TAG_USERNAME = "username";
    public final static String TAG_ID = "id";
    SharedPreferences sharedpreferences;
    Boolean session = false;
    private String id, username;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    // Adding HTTP Server URL to string variable.
    String HttpURL = "http://192.168.43.28/ktmlogin/JSonTextView.php";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ProfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilFragment newInstance(String param1, String param2) {
        ProfilFragment fragment = new ProfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences = getActivity().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        id = sharedpreferences.getString(TAG_ID, null);
        username = sharedpreferences.getString(TAG_USERNAME, null);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            getActivity().setTitle("Profil");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmen_profil, null);
        conMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        prodi= view.findViewById(R.id.tvprodi);
        nrp= view.findViewById(R.id.nrp);
        fullname=view.findViewById(R.id.etNama);
        kota=view.findViewById(R.id.etCity);
        tanggal=view.findViewById(R.id.etDate);
        gender=view.findViewById(R.id.etGender);
        hp=view.findViewById(R.id.etPhone);
        mail=view.findViewById(R.id.etMail);


        new GetDataFromServerIntoTextView(getContext()).execute();


        dialogdate = LinearDatePickerDialog.Builder.with(getContext())
                    .setYear(2018)
                    .setMaxYear(2018)
                    .setMinYear(1979)
                    .setDialogBackgroundColor(Color.rgb(33,33,33))
                    .setPickerBackgroundColor(Color.rgb(23,23,23))
                    .setTextColor(Color.rgb(255,255,100))
                    .setLineColor(Color.rgb(81,81,81))
                    .setButtonColor(Color.WHITE)
                    .setButtonCallback(new LinearDatePickerDialog.ButtonCallback() {
                        @Override
                        public void onPositive(DialogInterface dialog, int year, int month, int day) {
                            Toast.makeText(getContext(), "" + year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();
                            pickDate.setText("" + year + "-" + month + "-" + day);
                        }

                        @Override
                        public void onNegative(DialogInterface dialog) {

                        }
                    })
                    .build();

        pickDate = (TextInputEditText) view.findViewById(R.id.etDate);
        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialogdate.show();
            }
        });


        pickGender = (TextInputEditText) view.findViewById(R.id.etGender);
        pickGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialoggender = new MaterialDialog.Builder(getContext())
                        .title(R.string.gender)
                        .titleGravity(GravityEnum.CENTER)
                        .theme(Theme.DARK)
                        .items(R.array.gender)
                        .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                               pickGender.setText(text);
                                return true;
                            }
                        })
                        .positiveText(R.string.choose)
                        .show();
            }


        });

        save = (TextView) view.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEditTextIsEmptyOrNot();

                updateUser();

                Fragment fragment = new ProfilFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_main, fragment);
                ft.commit();

            }
        });
        return view;
    }

    public class GetDataFromServerIntoTextView extends AsyncTask<Void, Void, Void>
        {
        // Declaring CONTEXT.
        public Context context;


        public GetDataFromServerIntoTextView(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {

            HttpClient httpClient = new DefaultHttpClient();

            // Adding HttpURL to my HttpPost oject.
            HttpPost httpPost = new HttpPost(HttpURL+"?username="+username);
//            HttpPost httpPost = new HttpPost(HttpURL+"?username="+username+"&nama="+username+"&");

            try {
                httpResponse = httpClient.execute(httpPost);

                StringHolder = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try{
                // Passing string holder variable to JSONArray.
                JSONArray jsonArray = new JSONArray(StringHolder);
                jsonObject = jsonArray.getJSONObject(0);


            } catch ( JSONException e) {
                e.printStackTrace();
            }

            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Void result)
        {
            try {

                // Adding JSOn string to textview after done loading.
                prodi.setText(jsonObject.getString("prodi"));
                nrp.setText(jsonObject.getString("username"));
                fullname.setText(jsonObject.getString("nama"));
                kota.setText(jsonObject.getString("tempat_lahir"));
                tanggal.setText(jsonObject.getString("tanggal_lahir"));
                gender.setText(jsonObject.getString("gender"));
                hp.setText(jsonObject.getString("hp"));
                mail.setText(jsonObject.getString("email"));

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //Hiding progress bar after done loading TextView.
//            progressBar.setVisibility(View.GONE);

        }
    }

    public void CheckEditTextIsEmptyOrNot(){

        UserNameHolder = fullname.getText().toString();
        UserKotaHolder = kota.getText().toString();
        UserTanggalHolder = tanggal.getText().toString();
        UserGenderHolder = gender.getText().toString();
        UserPhoneHolder = hp.getText().toString();
        UserMailHolder = mail.getText().toString();

        if(TextUtils.isEmpty(UserNameHolder)
                || TextUtils.isEmpty(UserPhoneHolder) || TextUtils.isEmpty(UserGenderHolder)
                || TextUtils.isEmpty(UserKotaHolder) || TextUtils.isEmpty(UserTanggalHolder)
                || TextUtils.isEmpty(UserMailHolder))
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }

    private void updateUser() {
        final String nama = fullname.getText().toString().trim();
        final String city = kota.getText().toString().trim();
        final String tgllahir = tanggal.getText().toString().trim();
        final String kelamin = gender.getText().toString().trim();
        final String hape = hp.getText().toString().trim();
        final String email = mail.getText().toString().trim();

        class UpdateUser extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(),"Updating...","Wait",false,false);
            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
            }

            @Override
            protected String doInBackground(Void... params){
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_EMP_USERNAME,username);
                hashMap.put(Config.KEY_EMP_NAME,nama);
                hashMap.put(Config.KEY_EMP_KOTA,city);
                hashMap.put(Config.KEY_EMP_TANGGAL,tgllahir);
                hashMap.put(Config.KEY_EMP_GENDER,kelamin);
                hashMap.put(Config.KEY_EMP_HAPE,hape);
                hashMap.put(Config.KEY_EMP_EMAIL,email);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.URL_UPDATE_EMP,hashMap);
                return  s;
            }
        }

        UpdateUser uu = new UpdateUser();
        uu.execute();
    }

}
