package com.st11.dbshow.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.st11.dbshow.repository.DaDbVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Service
public class DbShowServiceImpl implements  DbShowService {
    @Autowired
    ApiService apiService;

    @Override
    public Collection<DaDbVO> getDaDbList(String dbshowUseYn) throws IOException, URISyntaxException {

        HashMap<String, String> inParam = new HashMap<>();
        String apiMethod = "jpa/daDbList";
        inParam.put("dbshowUseYn", dbshowUseYn);

//        System.out.println("[DbShowServiceImpl.inParam]: " + inParam.toString());
        Collection<DaDbVO> modelCollection = null;

        if (!inParam.isEmpty()) {
            modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<DaDbVO>>() {
                    }
                    , inParam);
        }

//        System.out.println("[DbShowServiceImpl.MODEL]: " + modelCollection.toString());
        return modelCollection;
    }

}
