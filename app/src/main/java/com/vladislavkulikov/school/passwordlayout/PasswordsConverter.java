package com.vladislavkulikov.school.passwordlayout;

import android.widget.ImageView;

public class PasswordsConverter {

    private final String [] mRussians;
    private final String [] mLatins;


    public PasswordsConverter(String[] mRussians, String[] mLatins) {
        if (mRussians.length != mLatins.length){
            throw new IllegalArgumentException();
        }

        this.mRussians = mRussians;
        this.mLatins = mLatins;
    }

    public  String convert(CharSequence source){
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            String key = String.valueOf(Character.toLowerCase(c));

            for (int dict = 0; dict < mRussians.length; dict++) {
                if (key.equals(mRussians[dict])){
                    result.append(Character.isUpperCase(c) ?
                            mLatins[dict].toUpperCase() : mLatins[dict]);
                }
            }
            if (result.length() <= i){
                result.append(c);
            }
        }

        return result.toString();

    }

    public int getQuality(CharSequence password){

        return Math.min(password.length(), 10);
    }




}
