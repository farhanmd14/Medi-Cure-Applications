package com.mymedicine.ui.medicine;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mymedicine.R;

public class Buy extends Fragment {
    TextView tv_1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.buy_medicine, container, false);

        tv_1 = root.findViewById(R.id.tv_1);
        tv_1.setMovementMethod(LinkMovementMethod.getInstance());

        return root;
    }
}