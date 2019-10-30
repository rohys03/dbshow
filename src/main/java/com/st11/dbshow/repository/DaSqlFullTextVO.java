package com.st11.dbshow.repository;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class DaSqlFullTextVO {

    private String dbNm;
    private int dbId;
    private String sqlId;
    private String sqlFullText;
    private int programId;

}
