package com.bignerdranch.android.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Zique Yuutaka on 8/7/2016.
 * A class used for short-term memory storage while application is open
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;

    private List<Crime> mCrimes;

    //If there is no CrimeLab create one with a context
    public static CrimeLab get(Context context){
        if(sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    //Create a CrimeLab and add to list
    private CrimeLab(Context context){
        mCrimes = new ArrayList<>();
        //For debugging
        /*for(int i = 0; i < 100; i++){
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i% 2 == 0); //Make every other crime solved
            mCrimes.add(crime);
        }*/
    }

    public void addCrime(Crime c){mCrimes.add(c);}

    public List<Crime> getCrimes(){
        return mCrimes;
    }

    //Retrieve a crime by its UUID
    public Crime getCrime(UUID id){
        for(Crime crime : mCrimes){
            if(crime.getId().equals(id)){
                return crime;
            }
        }

        return null;
    }
}
