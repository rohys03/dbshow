package com.st11.dbshow.repository;

import lombok.Data;

import java.sql.Date;

@Data
public class DaDbVO {

    private Integer dbId;
    private String dbNm;
    private String hostNm;
    private Date createDt;
    private String dqmDbId;
    private String dqmDbNm;
    private String dbshowUseYn;

}
