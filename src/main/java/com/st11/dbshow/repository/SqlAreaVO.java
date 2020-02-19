package com.st11.dbshow.repository;

import lombok.Data;

@Data
public class SqlAreaVO {
    private int instId;
    private String sqlId;
    private String planHashValue;
    private String parsingSchemaName;
    private String module;
    private String programId;
    private long executions;
    private long bufferGets;
    private long rowsProcessed;
    private long cpuTime;
    private long elapsedTime;
    private long blockPerExec;
    private String lastActiveTime;
    private String firstLoadTime;
    private String commandType;
    private String sqlName;
    private String sqlNameNo;
    private String sqlText;
    private String sqlFullText;
}