package org.woridge.ms_internal_users_management.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;


public enum LogType {
    DEV("Dev"),
    AUDIT("Audit");


    private String value;
    LogType(String x){
        this.value = x;
    }

    public String getValue(){
            return this.value;
    }
}
