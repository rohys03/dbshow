package com.st11.dbshow.repository;

import lombok.Data;

import java.sql.Date;

@Data
public class SqlNameListVO {
    int dbId;
    String sqlNameNo;
    String sqlName;
    String repSqlId;
    String parsingSchemaName;
    String module;
    Date LastActiveTime;
    Date createDt;
    Date updateDt;
}
