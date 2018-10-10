package com.plivotest.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.plivotest.R;
import com.plivotest.callhandler.CallHandlerService;
import com.plivotest.ui.activities.PhoneCallActivity;
import com.plivotest.util.Utils;

public class CallProgressFragment extends Fragment {

    private String numberText;

    public static CallProgressFragment getInstance(String numberTxt) {

        CallProgressFragment callProgressFragment = new CallProgressFragment();

        // also can be done via set arguments
        callProgressFragment.setNumberText(numberTxt);

        return callProgressFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.call_progress_fragment, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View view) {

        TextView statusTv = view.findViewById(R.id.status_tv);

        TextView numberTv = view.findViewById(R.id.number_tv);

        numberTv.setText(numberText);

        Button statusButton = view.findViewById(R.id.status_button);

        statusButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CallProgressFragment.this.getContext(), CallHandlerService.class);
                intent.putExtra(Utils.CALL_NUMBER, numberText);
                intent.setAction(Utils.END_ACTION);
                CallProgressFragment.this.getContext().startService(intent);

                ((PhoneCallActivity) getActivity()).loadFragment("", Utils.ScreenMode.DIALER);

            }
        });
    }

    public void setNumberText(String numberTet) {

        this.numberText = numberTet;
    }
}
