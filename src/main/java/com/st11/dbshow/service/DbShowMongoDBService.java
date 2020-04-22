package com.st11.dbshow.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface DbShowMongoDBService {
    void mongoLogInsert(HttpServletRequest request, HttpServletResponse response) ;
}
