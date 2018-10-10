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
import android.widget.EditText;
import android.widget.Toast;

import com.plivotest.R;
import com.plivotest.callhandler.CallHandlerService;
import com.plivotest.ui.activities.PhoneCallActivity;
import com.plivotest.util.Utils;

public class DialerFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialer_fragment, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View view) {

        final EditText dialEditText = view.findViewById(R.id.dial_edittext);

        Button callButton = view.findViewById(R.id.call_button);

        callButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String numberText = dialEditText.getText().toString();

                // add more validations if as per requirement
                if (numberText != null) {

                    Intent intent = new Intent(DialerFragment.this.getContext(), CallHandlerService.class);
                    intent.setAction(Utils.DIAL_ACTION);
                    intent.putExtra(Utils.CALL_NUMBER, numberText);
                    DialerFragment.this.getContext().startService(intent);

                    ((PhoneCallActivity) getActivity()).loadFragment(numberText, Utils.ScreenMode.CALL_PROGRESS);

                } else {

                    Toast.makeText(getContext(), "Please Enter Valid Number", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
