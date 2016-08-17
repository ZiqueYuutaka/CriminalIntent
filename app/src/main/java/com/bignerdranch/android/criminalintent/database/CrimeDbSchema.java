package com.bignerdranch.android.criminalintent.database;

/**
 * Created by Zique Yuutaka on 8/14/2016.
 */
public class CrimeDbSchema {
    public static final class CrimeTable{
        public static final String NAME = "crimes";

        //Define columns
        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String SUSPECT = "suspect";
        }
    }
}
