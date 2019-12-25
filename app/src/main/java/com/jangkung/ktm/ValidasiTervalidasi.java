package com.jangkung.ktm;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ValidasiTervalidasi extends Fragment {
    private TextView kembali;


    public ValidasiTervalidasi() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_validasi_tervalidasi,container, false);

        kembali = (TextView) view.findViewById(R.id.validasikembali);
        SpannableString content = new SpannableString("Kembali");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        kembali.setText(content);

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new DashboarFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_main, fragment);
                ft.commit();
            }
        });

        return view;
    }

}
