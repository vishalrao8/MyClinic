package com.unitedcreation.myclinic.database;

import android.provider.BaseColumns;

public class DataContract {

    public class DataTable implements BaseColumns {

        static final String TABLE_NAME = "MasterData";
        public static final String P_NAME = "p_name";
        public static final String P_ID = "p_id";
        public static final String P_AGE = "p_age";
        public static final String P_STATE = "p_state";
        public static final String P_ADDRESS = "p_address";
        public static final String P_ISSUE = "p_issue";
        public static final String P_QUALIFICATION = "p_qualification";
        public static final String P_LICENCE = "p_licence";

    }
}
