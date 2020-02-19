package com.st11.dbshow.repository;
import lombok.Data;

@Data
public class SqlAreaDiffVO extends SqlAreaVO {

    private int rank;
    private int rank2;
    private long executions2;
    private long bufferGets2;
    private long rowsProcessed2;
    private long cpuTime2;
    private long elapsedTime2;
    private float statRatio;
    private String objectNm;

}
