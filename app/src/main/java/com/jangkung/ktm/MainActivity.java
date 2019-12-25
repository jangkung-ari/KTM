package com.jangkung.ktm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;


import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {
    private FloatingActionButton fab, fab1, fab2;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    boolean isOpen = false;
//    private TextInputEditText pickDate;
//    private LinearDatePickerDialog dialogdate;
    private View positiveAction;
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ScrollView  mScrollView;
    private NavigationView navigationView;
    private HttpResponse httpResponse;
    private JSONObject jsonObject = null;
    private String StringHolder = "";
    private String cek,level;

    //utility methods
    private Toast mToast;
    private Thread mThread;
    private Handler mHandler;
    private int chooserDialog;
    private SliderLayout mDemoSlider;


    Button btn_logout;
    TextView txt_id, txt_username;
    String id, username;
    SharedPreferences sharedpreferences;

    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";

    String HttpgetPengajuan ="http://192.168.43.28/ktmlogin/getPengajuan.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getSharedPreferences(LoginDialog.my_shared_preferences, Context.MODE_PRIVATE);


        id = getIntent().getStringExtra(TAG_ID);
        username = getIntent().getStringExtra(TAG_USERNAME);
        if (username ==  null){
            hideItem();
        }else{
            showItem();
        }



//        Fragmentation.builder()
//                .stackViewMode(Fragmentation.BUBBLE )
//                .debug(BuildConfig)
//
//                .install();
//        if (findFragment(DashboarFragment.class) == null){
//            loadRootFragment(R.id.content_main, DashboarFragment.newInstance());
//        }


        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("KTM","https://s26.postimg.org/t6aihw6jd/1-01.jpg");
        url_maps.put("Easy to Use","https://s26.postimg.org/sgrq5jdpl/2-01.jpg");
        url_maps.put("Kartu Mahasiswa","https://s26.postimg.org/3ni64vf9l/3-01.jpg");

        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
                //    .setOnSliderClickListener(this);

            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
       // mDemoSlider.addOnPageChangeListener(this);




        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("Montserrat-SemiBold.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
                );

        ButterKnife.bind(this);

        mHandler = new Handler();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");
        toolbar.setLogo(R.drawable.toolbar);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);

        fabOpen = AnimationUtils.loadAnimation(this,R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this,R.anim.fab_close);

        rotateForward=AnimationUtils.loadAnimation(this,R.anim.rotate_forward);
        rotateBackward=AnimationUtils.loadAnimation(this,R.anim.rotate_backward);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFab();
                getPengajuan();
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FabClose();

                if (username == null){
                    Snackbar.make(view, "Anda belum login!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }else if(username.equals("Admin")){
                    displaySelectedItem(R.id.nav_pengajuanadmin);

                }else if(username!=null && cek.equals("Belum Divalidasi")) {
                    displaySelectedItem(R.id.nav_dikirim);
                    navigationView.setCheckedItem(R.id.nav_pengajuan);
                    System.out.println("kunam");
                }else if(username!=null && cek.equals("Validasi Ditolak")) {
                    displaySelectedItem(R.id.nav_ditolak);
                    navigationView.setCheckedItem(R.id.nav_pengajuan);
                    System.out.println("kunam");
                }else if(username!=null && cek.equals("Validasi")) {
                    displaySelectedItem(R.id.nav_divalidasi);
                    navigationView.setCheckedItem(R.id.nav_pengajuan);
                    System.out.println("kunam");
                }else if(username!=null && cek.equals("Validasi Dicetak")) {
                    displaySelectedItem(R.id.nav_dicetak);
                    navigationView.setCheckedItem(R.id.nav_pengajuan);
                    System.out.println("kunam");
                }else{
                    Toast.makeText(MainActivity.this,"Pengajuan", Toast.LENGTH_SHORT).show();
                    displaySelectedItem(R.id.nav_pengajuan);
                    navigationView.setCheckedItem(R.id.nav_pengajuan);
                    System.out.println("yes");
                }
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FabClose();
                if (username == null){
                    Snackbar.make(view, "Anda belum login!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else {


                    Toast.makeText(MainActivity.this,"Profil", Toast.LENGTH_SHORT).show();
                    displaySelectedItem(R.id.nav_profil);
                    navigationView.setCheckedItem(R.id.nav_profil);
                }


            }
        });

        displaySelectedItem(R.id.nav_dashboard);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displaySelectedItem(R.id.nav_dashboard);
        navigationView.setCheckedItem(R.id.nav_dashboard);
    }



//    @Override
//    protected void onStop(){
//        mDemoSlider.stopAutoCycle();
//        super.onStop();
//    }


    private void hideItem()
    {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        header.setVisibility(View.GONE);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_pengajuan).setVisible(false);
        nav_Menu.findItem(R.id.nav_logout).setVisible(false);
        nav_Menu.findItem(R.id.nav_login).setVisible(true);
        nav_Menu.findItem(R.id.nav_profil).setVisible(false);
        nav_Menu.findItem(R.id.nav_dikirim).setVisible(false);
        nav_Menu.findItem(R.id.nav_ditolak).setVisible(false);
        nav_Menu.findItem(R.id.nav_divalidasi).setVisible(false);
        nav_Menu.findItem(R.id.nav_dicetak).setVisible(false);
        nav_Menu.findItem(R.id.nav_pengajuanadmin).setVisible(false);

    }

    public void showItem()
    {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        header.setVisibility(View.GONE);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_pengajuan).setVisible(false);
        nav_Menu.findItem(R.id.nav_logout).setVisible(true);
        nav_Menu.findItem(R.id.nav_login).setVisible(false);
        nav_Menu.findItem(R.id.nav_profil).setVisible(false);
        nav_Menu.findItem(R.id.nav_dikirim).setVisible(false);
        nav_Menu.findItem(R.id.nav_ditolak).setVisible(false);
        nav_Menu.findItem(R.id.nav_divalidasi).setVisible(false);
        nav_Menu.findItem(R.id.nav_dicetak).setVisible(false);
        nav_Menu.findItem(R.id.nav_pengajuanadmin).setVisible(false);
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    private void FabClose (){
        if (isOpen){
            fab.startAnimation(rotateForward);
            fab1.startAnimation(fabClose);
            fab2.startAnimation(fabClose);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isOpen=false;

        }else{}
    }
    private void animateFab(){
        if(isOpen){
            fab.startAnimation(rotateForward);
            fab1.startAnimation(fabClose);
            fab2.startAnimation(fabClose);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isOpen=false;
        }
        else{
            fab.startAnimation(rotateBackward);
            fab1.startAnimation(fabOpen);
            fab2.startAnimation(fabOpen);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isOpen=true;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

            displaySelectedItem(id);
            FabClose();

        return true;
    }


    private void displaySelectedItem(int id){
        Fragment fragment = null;

        switch (id){
            case R.id.nav_dashboard:
//                mDemoSlider.setVisibility(View.VISIBLE);
                fragment = new DashboarFragment();
                break;
            case R.id.nav_pengajuan:
//                mDemoSlider.setVisibility(View.GONE);
                fragment = new PengajuanFragment();
                break;
            case R.id.nav_about:
//                mDemoSlider.setVisibility(View.GONE);
                fragment = new about();
                break;
            case R.id.nav_login:
//                mDemoSlider.setVisibility(View.GONE);
                fragment = new LoginDialog();

                openDialog();
                break;
            case R.id.nav_help:
//                mDemoSlider.setVisibility(View.GONE);
                fragment = new help();


                break;
            case R.id.nav_pengajuanadmin:
//                mDemoSlider.setVisibility(View.GONE);
                fragment = new PengajuanAdmin();
                break;
            case R.id.nav_profil:
//                mDemoSlider.setVisibility(View.GONE);
                fragment = new ProfilFragment();
                break;
            case R.id.nav_dikirim:
//                mDemoSlider.setVisibility(View.GONE);
                fragment = new ValidasiDikirim();
                break;
            case R.id.nav_ditolak:
//                mDemoSlider.setVisibility(View.GONE);
                fragment = new ValidasiDitolak();
                break;
            case R.id.nav_divalidasi:
//                mDemoSlider.setVisibility(View.GONE);
                fragment = new ValidasiTervalidasi();
                break;
            case R.id.nav_dicetak:
//                mDemoSlider.setVisibility(View.GONE);
                fragment = new ValidasiDicetak();
                break;
            case R.id.nav_logout:
//                mDemoSlider.setVisibility(View.GONE)
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(LoginDialog.session_status, false);
                editor.putString(TAG_ID, null);
                editor.putString(TAG_USERNAME, null);
                editor.commit();
                hideItem();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                new MainActivity().finish();
                startActivity(intent);
                break;
        }
        if (fragment != null){
//            FragmentManager manager = getSupportFragmentManager();
//            manager.beginTransaction().replace(
//                    R.id.content_main,
//                    fragment,
//                    fragment.getTag()
//            ).commit();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main, fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private android.app.Fragment openDialog(){
        LoginDialog loginDialog = new LoginDialog();
        loginDialog.show(getSupportFragmentManager(),"");
        return null;
    }

    private void getPengajuan(){
        class GetPengajuan extends AsyncTask<Void,Void,String>{
            @Override
            protected void onPreExecute(){
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                showPengajuan(s);
            }

            @Override
            protected String doInBackground(Void...params){
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_PENGAJUAN,username);
                return s;
            }
        }
        GetPengajuan gp = new GetPengajuan();
        gp.execute();
    }

    private void showPengajuan(String json){
        System.out.println(json);
        try {

                JSONObject jsonObject = new JSONObject(json);
                JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
                JSONObject c = result.getJSONObject(0);
                String vald = c.getString(Config.TAG_VALIDASI);

            System.out.println(vald);
            cek = vald;


            }
        catch (JSONException e){
            e.printStackTrace();
        }
    }


}
