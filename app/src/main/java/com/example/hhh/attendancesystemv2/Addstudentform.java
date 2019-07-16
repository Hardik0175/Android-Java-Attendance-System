package com.example.hhh.attendancesystemv2;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Addstudentform extends Fragment {

    TextInputEditText firstname;
    TextInputLayout firstnamelayout;
    public Addstudentform() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppThemeforform);

        // clone the inflater using the ContextThemeWrapper
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        View v=localInflater.inflate(R.layout.fragment_addstudentform, container, false);
        // inflate the layout using the cloned inflater, not default inflater

        // Inflate the layout for this fragment
        Typeface face= Typeface.createFromAsset(getActivity().getAssets(),"fonts/CabinSketch-Regular.ttf");

        firstname=(TextInputEditText) v.findViewById(R.id.textInputEditTextFirstName);
        firstname.setTypeface(face);

        return v;
    }

}
