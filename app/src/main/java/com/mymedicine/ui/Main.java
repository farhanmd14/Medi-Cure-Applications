package com.mymedicine.ui;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.mymedicine.R;

import java.util.Timer;
import java.util.TimerTask;

public class Main extends Fragment implements View.OnClickListener {
    ViewPager vf;
    TextView tv, tv1;
    int[] images = {R.drawable.coronavv, R.drawable.respiratoryyyyy, R.drawable.tuberclosiss, R.drawable.diabetesss, R.drawable.bloodd};
    Runnable mRunnable;
    int position=0;

    public Main() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        vf = root.findViewById(R.id.image);
        tv = root.findViewById(R.id.tv_info);
        tv1 = root.findViewById(R.id.text_info);

        vf.setAdapter(new MyPager(getActivity()));

        final Handler m_Handler = new Handler();

        mRunnable = new Runnable() {
            @Override
            public void run() {
                vf.setCurrentItem((++position)%images.length,true);
                m_Handler.postDelayed(mRunnable, 3000);// move this inside the run method
            }
        };
        mRunnable.run();

        Toast.makeText(getContext(), "TO KNOW MORE \nCLICK ON IMAGE", Toast.LENGTH_SHORT).show();


        // Inflate the layout for this fragment
        return root;


    }


    @Override
    public void onClick(View v) {
        if (v.getTag().equals("iv0")) {
            tv.setText("Coronavirus (COVID-19)");
            tv1.setText(getResources().getString(R.string.corona_details));
            //vf.stopFlipping();
        } else if (v.getTag().equals("iv1")) {
            tv.setText("Respiratory");
            tv1.setText(getResources().getString(R.string.res_info));
        } else if (v.getTag().equals("iv2")) {
            tv.setText("Tuberculosis (TB)");
            tv1.setText(getResources().getString(R.string.tube_details));
        } else if (v.getTag().equals("iv3")) {
            tv.setText("Diabetes");
            tv1.setText(getResources().getString(R.string.diabetes));
        } else if (v.getTag().equals("iv4")) {
            tv.setText("Blood Pressure");
            tv1.setText(getResources().getString(R.string.blood));
        }

    }


    public class MyPager extends PagerAdapter {
        private Context context;
        public MyPager(Context context) {
            this.context = context;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.linear_layout, null);
            ImageView imageView = view.findViewById(R.id.image);
            imageView.setOnClickListener(Main.this);
            imageView.setTag("iv"+position);
            imageView.setImageDrawable(context.getResources().getDrawable(getImageAt(position)));
            container.addView(view);
            return view;

            /*ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(images[position]);
            imageView.setTag("iv" + position);
            imageView.setOnClickListener(Main.this);

            return imageView;*/
        }
        /*
        This callback is responsible for destroying a page. Since we are using view only as the
        object key we just directly remove the view from parent container
        */
        @Override
        public void destroyItem(ViewGroup container, int position, Object view) {
            container.removeView((View) view);
        }
        /*
        Returns the count of the total pages
        */
        @Override
        public int getCount() {
            return images.length;
        }
        /*
        Used to determine whether the page view is associated with object key returned by instantiateItem.
        Since here view only is the key we return view==object
        */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object == view;
        }

        private int getImageAt(int position) {
            return images[position];
        }

    }

}
