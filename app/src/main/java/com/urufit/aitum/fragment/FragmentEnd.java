package com.urufit.aitum.fragment;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import com.urufit.aitum.R;
import com.urufit.aitum.activity.SurveyActivity;
import com.urufit.aitum.model.SurveyProperties;
import com.urufit.aitum.ui.Answers;


public class FragmentEnd extends Fragment {

    private FragmentActivity mContext;
    private TextView textView_end;
    ImageView done;
    AnimatedVectorDrawableCompat avd;
    AnimatedVectorDrawable avd2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_end, container, false);

        done=rootView.findViewById(R.id.done);

        Button button_finish = (Button) rootView.findViewById(R.id.button_finish);
        textView_end = (TextView) rootView.findViewById(R.id.textView_end);

        button_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable=done.getDrawable();
                if(drawable instanceof  AnimatedVectorDrawableCompat){
                    avd=(AnimatedVectorDrawableCompat)drawable;
                    avd.start();
                }else  if(drawable instanceof AnimatedVectorDrawable){
                    avd2=(AnimatedVectorDrawable)drawable;
                    avd2.start();;
                }
                ((SurveyActivity) mContext).event_survey_completed(Answers.getInstance());

            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mContext = getActivity();
        SurveyProperties survery_properties = (SurveyProperties) getArguments().getSerializable("survery_properties");

        assert survery_properties != null;
        textView_end.setText(Html.fromHtml(survery_properties.getEndMessage()));

    }
}