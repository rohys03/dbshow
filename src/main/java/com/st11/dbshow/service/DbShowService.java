package com.st11.dbshow.service;

import com.st11.dbshow.repository.DaDbVO;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

public interface DbShowService {
    Collection<DaDbVO> getDaDbList(String dbshowUseYn) throws IOException, URISyntaxException;
}
