package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Zique Yuutaka on 8/7/2016.
 * Responsible for displaying a list of crimes
 * Hosted by CrimeListActivity
 */
public class CrimeListFragment extends Fragment {
    //Changed by Zique Yuutaka for search time constant O(1) in CrimePagerActivity
    public static final String CRIME_POSITION = "com.bignerdranch.android.criminalintent.crime_position";
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private int crimeListPosition;
    private List<Crime> mCrimes;

    private class CrimeHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;
        private Crime mCrime;

        //Create the ViewHolder
        public CrimeHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_titleText);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_dateText);
            mSolvedCheckBox = (CheckBox)itemView.findViewById(R.id.list_item_crime_solved);
        }

        //Called in the Adapter.onBindViewHolder
        public void bindCrime(Crime crime){
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }

        //Overriden from View.OnClickListener
        @Override
        public void onClick(View v){
            //Toast.makeText(getActivity(), mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();
            //Starting new Activity
            //Intent intent = new Intent(getActivity(), CrimeActivity.class); //Implemented in Crime Activity
            //Decommissioned and replaced by CrimePagerActivity
            //Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            crimeListPosition = mCrimes.indexOf(mCrime);
            //Changed by Zique Yuutaka for debugging
            //Toast.makeText(getActivity(), "Clicking item at position " + crimeListPosition, Toast.LENGTH_SHORT).show();
            intent.putExtra(CRIME_POSITION, crimeListPosition);
            startActivity(intent);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{
        //moved to CrimeListFragment scope
        //private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes){
            mCrimes = crimes;
        }

        //Must override the following three methods
        @Override
        //Called when a new View is needed to display an item
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position){
            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);
        }

        @Override
        public int getItemCount(){
            return mCrimes.size();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    //Overriding onResume() to reload list with updated information
    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    //Method connects the RecyclerView with an Adapter
    private void updateUI(){
        //Create a database of crimes based on activity
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        //Create an adapter and give it the list of crimes
        if(mAdapter == null){
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }else{//Notify mAdapter that a Crime has changed internally
            //Changed by Zique Yuutaka for efficiency.  Rather than looking at the entire
            //list, look at just the Crime being changed.
            //mAdapter.notifyDataSetChanged();
            mAdapter.notifyItemChanged(crimeListPosition);
        }
    }
}
