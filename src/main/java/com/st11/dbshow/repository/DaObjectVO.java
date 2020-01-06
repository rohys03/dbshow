package com.st11.dbshow.repository;

import lombok.Data;

import java.sql.Date;


@Data
public class DaObjectVO {

    String dbId;
    String objectId;
    String owner;
    String objectName;
    String objectType;
    Date created;
    Date lastDdlTime;
    String status;
    String temporary;
}
