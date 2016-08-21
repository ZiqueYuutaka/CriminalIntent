package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Created by Zique Yuutaka on 8/7/2016.
 */
public class CrimeListActivity extends SingleFragmentActivity
        implements CrimeListFragment.Callbacks, CrimeFragment.Callbacks{
    @Override
    protected Fragment createFragment(){
        return new CrimeListFragment();
    }

    @Override
    protected int getLayoutResId(){
        return R.layout.activity_masterdetail;
    }

    //method from CrimeListFragment.Callbacks
    //decides which fragment to start depending on the device
    //changed by Zique Yuutaka to take an int argument to find position of crime
    @Override
    public void onCrimeSelected(Crime crime, int position){
        if(findViewById(R.id.detail_fragment_container) == null){//If using a phone
            Intent intent = CrimePagerActivity.newIntent(this, crime.getId());
            intent.putExtra(CrimeListFragment.CRIME_POSITION, position);
            startActivity(intent);
        }else{ //If using a tablet
            Fragment newDetail = CrimeFragment.newInstance(crime.getId());

            getSupportFragmentManager().beginTransaction().
                    replace(R.id.detail_fragment_container, newDetail).commit();
        }
    }

    //method from CrimeFragment.Callbacks
    //used to updated the view of the list on tablets
    public void onCrimeUpdated(Crime crime){
        CrimeListFragment listFragment = (CrimeListFragment)getSupportFragmentManager().
                findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }
}
