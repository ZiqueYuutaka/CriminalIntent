package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by Zique Yuutaka on 8/11/2016.
 * Creates and manages ViewPager used to swipe through the Crimes list
 * and replaces CrimeActivity
 */
public class CrimePagerActivity extends AppCompatActivity
            implements CrimeFragment.Callbacks{
    private static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id";

    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    public static Intent newIntent(Context packageContext, UUID crimeId){
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    public void onCrimeUpdated(Crime crime){}

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        //Get extras
        int crimePosition = (int) getIntent().getSerializableExtra(CrimeListFragment.CRIME_POSITION);
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_viewpager);

        //Get a list of crimes from the current Context's CrimeLab
        mCrimes = CrimeLab.get(this).getCrimes();
        //Create a fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();
        //Set the adapater for ViewPager to get a Crime
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        //This could be better since searching at worst will take linear time N
        //Try to make search time constant O(1)
        /*for(int i = 0; i < mCrimes.size(); i++){
            if(mCrimes.get(i).getId().equals(crimeId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }*/
        //Changed by Zique Yuutaka for constant search time
        mViewPager.setCurrentItem(crimePosition);
    }//End onCreate
}
