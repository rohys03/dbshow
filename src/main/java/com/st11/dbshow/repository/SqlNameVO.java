package com.st11.dbshow.repository;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;

/*
SqlNameVO is Dauser.da_Sqlname_list join Dauser.da_sqlname_stats
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SqlNameVO extends SqlNameStatsVO {
    int dbId;
    long sqlNameNo;
    String sqlName;
    String repSqlId;
    String parsingSchemaName;
    String module;
    Date LastActiveTime;
    Date createDt;
    Date updateDt;
}
