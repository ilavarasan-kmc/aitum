package com.urufit.aitum.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.trafi.ratingseekbar.RatingSeekBar;
import com.urufit.aitum.R;
import com.urufit.aitum.model.Question;

public class Survey_Test extends Fragment implements RatingSeekBar.OnRatingSeekBarChangeListener {
    TextView ratingLabel;
    private Question q_data;
    private TextView textview_q_title;
    private Button button_continue;
    private FragmentActivity mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.activity_sur_test, container, false);
        textview_q_title = (TextView) rootView.findViewById(R.id.textview_q_title);
        ((RatingSeekBar) rootView.findViewById(R.id.rating_seek_bar_one)).setOnSeekBarChangeListener(this);
        ratingLabel = (TextView)rootView. findViewById(R.id.rating_label_one);
        button_continue = (Button) rootView.findViewById(R.id.button_continue);

        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SurveyActivity) mContext).go_to_next();
            }
        });


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        q_data = (Question) getArguments().getSerializable("data");

        textview_q_title.setText(q_data.getQuestionTitle());

    }

    @Override
    public void onProgressChanged(RatingSeekBar ratingSeekBar, int i) {
        ratingLabel.setText("Rating " + i + " / " + ratingSeekBar.getMax());
        if (q_data.getRequired()) {
            if (i>0) {
                button_continue.setVisibility(View.VISIBLE);
            } else {
                button_continue.setVisibility(View.GONE);
            }
        }
    }
}
