package com.jangkung.ktm;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.github.gcacace.signaturepad.views.SignaturePad;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PengajuanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PengajuanFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final int RESULT_OK = 1;
    private SignaturePad tandaTangan;
    private TextView btnSubmit;
    private Button btnClear;
    private RadioButton baru,hilang;
    private LinearLayout tengah,upldfoto,upldsurat;
    private HttpResponse httpResponse;
    private JSONObject jsonObject = null;
    private String StringHolder = "";
    private SegmentedGroup category;
    private ScrollView mScrollView;
    private ImageView poto,surat;
    private DialogProperties surathilang;
    private int PICK_IMAGE_REQUEST = 1;
    private FilePickerDialog dialog;
    private String kategori,validasi,active;
    private String cek =null;

    private TextView nrppengajuan;
    private TextInputEditText fullname,kota,tanggal,gender,prodi;
    ConnectivityManager conMgr;
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
    String HttpgetPengajuan ="http://192.168.43.28/ktmlogin/getPengajuan.php";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ViewPager viewPager;


    public PengajuanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PengajuanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PengajuanFragment newInstance(String param1, String param2) {
        PengajuanFragment fragment = new PengajuanFragment();
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

            getActivity().setTitle("Pengajuan");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pengajuan,container, false);

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

        prodi= view.findViewById(R.id.etProdi);
        nrppengajuan= view.findViewById(R.id.nrppengajuan);
        fullname=view.findViewById(R.id.etNama);
        kota=view.findViewById(R.id.etCity);
        tanggal=view.findViewById(R.id.etDate);
        gender=view.findViewById(R.id.etGender);
        baru = (RadioButton) view.findViewById(R.id.baru);
        hilang = (RadioButton) view.findViewById(R.id.hilang);
        tengah = (LinearLayout) view.findViewById(R.id.tengah);
        upldfoto= (LinearLayout) view.findViewById(R.id.uploadfoto);
        upldsurat= (LinearLayout) view.findViewById(R.id.uploadsurat);
        poto=(ImageView) view.findViewById(R.id.poto);
        surat=(ImageView)view.findViewById(R.id.surat);
//        tandaTangan = (SignaturePad) view.findViewById(R.id.signature_pad);
//        btnClear = (Button) view.findViewById(R.id.btn_clear);
        btnSubmit = (TextView) view.findViewById(R.id.btn_submit);
        SegmentedGroup segmented2 = (SegmentedGroup) view.findViewById(R.id.segmented2);
        segmented2.setTintColor(Color.DKGRAY);


        new PengajuanFragment.GetDataTextView(getContext()).execute();
//        new PengajuanFragment.GetDataPengajuan(getContext()).execute();




//        getPengajuan();


        upldfoto.setVisibility(View.GONE);
        upldsurat.setVisibility(View.GONE);
        tengah.setVisibility(View.GONE);


        surathilang = new DialogProperties();
        surathilang.selection_mode = DialogConfigs.SINGLE_MODE;
        surathilang.selection_type = DialogConfigs.FILE_SELECT;
        surathilang.root = new File(DialogConfigs.DEFAULT_DIR);
        surathilang.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        surathilang.offset = new File(DialogConfigs.DEFAULT_DIR);
        surathilang.extensions = null;

        dialog = new FilePickerDialog(getContext(),surathilang);
        dialog.setTitle("Select a File");

        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                //files is the array of the paths of files selected by the Application User.
            }
        });


        btnSubmit.setEnabled(false);

        baru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kategori = "Baru";
                validasi  = "Belum Divalidasi";
                active = "Active";
//                upldfoto.setVisibility(View.VISIBLE);
//                upldsurat.setVisibility(View.GONE);
//                tengah.setVisibility(View.GONE);
                btnSubmit.setEnabled(true);
//                validasi.setText("Belum Validasi");
            }
        });
        hilang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kategori = "Hilang";
                validasi  = "Belum Divalidasi";
                active = "Active";
//                upldfoto.setVisibility(View.VISIBLE);
//                upldsurat.setVisibility(View.VISIBLE);
//                tengah.setVisibility(View.VISIBLE);
                btnSubmit.setEnabled(true);
//                validasi.setText("Belum Validasi");
            }
        });

        poto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickPhoto.setType("image/*");
                startActivityForResult(pickPhoto , 1);//one can be replaced with any action code


            }
        });

        surat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });






//        tandaTangan.setOnSignedListener(new SignaturePad.OnSignedListener() {
//            @Override
//            public void onStartSigning() {
//                Toast.makeText(getContext(), "OnStartSigning", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onSigned() {
//                btnSubmit.setEnabled(true);
//                btnClear.setEnabled(true);
//            }
//
//            @Override
//            public void onClear() {
//                btnClear.setEnabled(true);
//                btnSubmit.setEnabled(true);
//            }
//        });
//
//        btnClear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tandaTangan.clear();
//            }
//        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                createSignature();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPengajuan();
                Toast.makeText(getContext(), "Pengajuan Telah Dikirim", Toast.LENGTH_SHORT).show();
                Fragment fragment = new DashboarFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_main, fragment);
                ft.commit();
            }
        });

        return view;
    }


    private void createSignature(){
        Bitmap signatureBitmap = tandaTangan.getTransparentSignatureBitmap();
        if(addPngSignatureToGallery(signatureBitmap)){
            Toast.makeText(getContext(), "Tanda tangan disimpan di Gallery", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "Gagal menyimpan tanda tangan", Toast.LENGTH_SHORT).show();
        }

        if(saveSvgSignatureToGallery(tandaTangan.getSignatureSvg())){
            Toast.makeText(getContext(), "SVG Format saved into Gallery", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "Failed to save SVG format to gallery", Toast.LENGTH_SHORT).show();
        }
    }

    public File getAlbumStorageDir(String albumName){
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);

        if(!file.mkdirs()){
            Log.d("TAG", "Directory not created");
        }

        return file;
    }

    private void saveBitmapToPng(Bitmap bitmap, File photo){
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0,0, null);
        try {
            OutputStream stream = new FileOutputStream(photo);
            newBitmap.compress(Bitmap.CompressFormat.PNG,80,stream);
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addPngSignatureToGallery(Bitmap signature) {
        boolean result = false;
        File photo = new File(getAlbumStorageDir("KTM tandatangan"), String.format("Signature_%d.png", System.currentTimeMillis()));
        saveBitmapToPng(signature, photo);
        scanMediaFile(photo);
        result = true;
        return result;
    }

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        getContext().sendBroadcast(mediaScanIntent);
    }
    private boolean saveSvgSignatureToGallery(String signatureSvg){
        boolean result = false;

        File svgFile = new File(getAlbumStorageDir("KTM tandatangan"), String.format("Signature_%d.svg", System.currentTimeMillis()));
        try {
            OutputStream stream = new FileOutputStream(svgFile);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            writer.write(signatureSvg);
            writer.close();
            stream.flush();
            stream.close();
            scanMediaFile(svgFile);
            result = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


//    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
//        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
//        switch(requestCode) {
//            case 0:
//                if(resultCode == RESULT_OK){
//                    Uri selectedImage = imageReturnedIntent.getData();
////                    imageview.setImageURI(selectedImage);
//                    Toast.makeText(getContext(), "khutnul 1", Toast.LENGTH_SHORT).show();
//                }
//
//                break;
//            case 1:
//                if(resultCode == RESULT_OK){
//                    Uri selectedImage = imageReturnedIntent.getData();
////                    imageview.setImageURI(selectedImage);
//                    Toast.makeText(getContext(), "khutnul 2", Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }
//    }

    //add Pengajuan Baru
    private void addPengajuan(){
//        final String nrp = nrppengajuan.getText().toString().trim();
        final String nama = fullname.getText().toString().trim();
        final String programstudi = prodi.getText().toString().trim();
        final String kotalahir = kota.getText().toString().trim();
        final String tgllahir = tanggal.getText().toString().trim();
        final String kelamin = gender.getText().toString().trim();
        final String stats = kategori;
        final String val = validasi;
        final String act = active;

        class AddPengajuan extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(),"Menambahkan...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
//                Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v){
                HashMap <String,String> params = new HashMap<>();
                params.put(Config.KEY_EMP_USERNAME,username);
                params.put(Config.KEY_EMP_NAME,nama);
                params.put(Config.KEY_EMP_PRODI,programstudi);
                params.put(Config.KEY_EMP_KOTA,kotalahir);
                params.put(Config.KEY_EMP_TANGGAL,tgllahir);
                params.put(Config.KEY_EMP_GENDER,kelamin);
                params.put(Config.KEY_EMP_STATUS,stats);
                params.put(Config.KEY_EMP_VALIDASI,val);
                params.put(Config.KEY_EMP_ACTIVE,act);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD, params);
                return res;
            }
        }
        AddPengajuan ap = new AddPengajuan();
        ap.execute();
    }

//    private void getPengajuan(){
//        class GetPengajuan extends AsyncTask<Void,Void,String>{
//            @Override
//            protected void onPreExecute(){
//                super.onPreExecute();
//            }
//
//            @Override
//            protected void onPostExecute(String s){
//                super.onPostExecute(s);
//                showPengajuan(s);
//            }
//
//            @Override
//            protected String doInBackground(Void...params){
//                RequestHandler rh = new RequestHandler();
//                String s = rh.sendGetRequestParam(Config.URL_GET_PENGAJUAN,username);
//                return s;
//            }
//        }
//        GetPengajuan gp = new GetPengajuan();
//        gp.execute();
//    }
//
//    private void showPengajuan(String json){
//        try {
//
//                JSONObject jsonObject = new JSONObject(json);
//                JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
//                JSONObject c = result.getJSONObject(0);
//                String status = c.getString(Config.TAG_STATUS);
//
//            }
//        catch (JSONException e){
//            e.printStackTrace();
//        }
//    }


    public class GetDataTextView extends AsyncTask<Void, Void, Void>
    {
        // Declaring CONTEXT.
        public Context context;


        public GetDataTextView(Context context)
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
                nrppengajuan.setText(jsonObject.getString("username"));
                fullname.setText(jsonObject.getString("nama"));
                prodi.setText(jsonObject.getString("prodi"));
                kota.setText(jsonObject.getString("tempat_lahir"));
                tanggal.setText(jsonObject.getString("tanggal_lahir"));
                gender.setText(jsonObject.getString("gender"));

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //Hiding progress bar after done loading TextView.
//            progressBar.setVisibility(View.GONE);

        }
    }

    public class GetDataPengajuan extends AsyncTask<Void, Void, Void>
    {
        // Declaring CONTEXT.
        public Context context;


        public GetDataPengajuan(Context context)
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
            HttpPost httpPost = new HttpPost(HttpgetPengajuan+"?username="+username);
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

              cek=(jsonObject.getString("status"));

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //Hiding progress bar after done loading TextView.
//            progressBar.setVisibility(View.GONE);

        }
    }


}
