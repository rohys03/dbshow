package com.st11.dbshow.repository;

import lombok.Data;

@Data
public class RefObjectVO {
    private int depth;
    private String objectType;
    private String owner;
    private String status;
    private String objectName;
    private String referencedPath;
}
