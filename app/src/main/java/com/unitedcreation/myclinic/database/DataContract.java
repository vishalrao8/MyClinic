package com.unitedcreation.myclinic.database;

import android.provider.BaseColumns;

public class DataContract {
    public class DataTable implements BaseColumns {
        public static final String TABLE_NAME="MasterData";
        public static final String P_NAME ="p_name",P_ID="p_id",
        P_AGE="p_age",P_STATE="p_state",P_ADDRESS="p_address",P_ISSUE="p_issue",P_QUALIFICATION="p_qualification",P_LICENCE="p_licence";
    }

}
