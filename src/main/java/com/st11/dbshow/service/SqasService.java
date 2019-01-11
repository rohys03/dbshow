package com.st11.dbshow.service;

import com.st11.dbshow.repository.SqlAreaVO;

import java.util.HashMap;
import java.util.List;

public interface SqasService {
    List<SqlAreaVO> getSqlAreaListAll();
    List<SqlAreaVO> getSqlAreaList(HashMap<String, Object> inParam);
}
