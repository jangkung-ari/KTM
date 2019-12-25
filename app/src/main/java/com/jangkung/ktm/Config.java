package com.jangkung.ktm;

/**
 * Created by Sankarea on 1/14/2018.
 */

public class Config {

    //Address of our scripts of the CRUD
    public static final String URL_ADD="http://192.168.43.28/ktmlogin/addPengajuan.php";
    public static final String URL_GET_ALL = "http://192.168.43.28/ktmlogin/getAllPengajuan.php";
    public static final String URL_GET_PENGAJUAN = "http://192.168.43.28/ktmlogin/getPengajuan.php?nrp=";
    public static final String URL_UPDATE_EMP = "http://192.168.43.28/ktmlogin/UserUpdate.php";
    public static final String URL_UPDATE_VALIDASI = "http://192.168.43.28/ktmlogin/UpdateValidasi.php";
    public static final String URL_UPDATE_PENGAJUAN = "http://192.168.43.28/ktmlogin/updateDitolak.php";
    public static final String URL_GET_ID = "http://192.168.43.28/ktmlogin/getIdPengajuan.php?id=";

    //Keys that will be used to send the request to php scripts
    public static final String KEY_EMP_ID = "id";
    public static final String KEY_EMP_PRODI = "programstudi";
    public static final String KEY_EMP_USERNAME = "username";
    public static final String KEY_EMP_NAME = "nama";
    public static final String KEY_EMP_KOTA = "city";
    public static final String KEY_EMP_TANGGAL = "tanggal";
    public static final String KEY_EMP_GENDER = "gender";
    public static final String KEY_EMP_HAPE = "hape";
    public static final String KEY_EMP_EMAIL = "email";
    public static final String KEY_EMP_STATUS = "status";
    public static final String KEY_EMP_VALIDASI = "validasi";
    public static final String KEY_EMP_ACTIVE = "active";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id";
    public static final String TAG_NRP = "nrp";
    public static final String TAG_PRODI = "prodi";
    public static final String TAG_ACTIVE = "active";
    public static final String TAG_VALIDASI = "validasi";
    public static final String TAG_STATUS = "status";
    public static final String TAG_NAME = "nama";
    public static final String TAG_KOTA  = "kota";
    public static final String TAG_TANGGAL = "tanggal";
    public static final String TAG_GENDER = "gender";
    public static final String TAG_hape  = "hape";
    public static final String TAG_email = "email";

    //employee id to pass with intent
    public static final String EMP_ID = "pengajuan_id";

}
