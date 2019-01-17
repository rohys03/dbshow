package com.st11.dbshow.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.st11.dbshow.config.ApiServerConfig;
import com.st11.dbshow.repository.SqlAreaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;

@Service
public class ApiServiceImpl implements ApiService  {

    @Autowired
    ApiServerConfig apiServerConfig;

    @Override
    public Collection getApiModels(String apiUrl, TypeReference type, String ... apiParams) throws IOException {
        Collection<Class> modelCollection;
        ObjectMapper mapper = new ObjectMapper();

        String urlString = apiServerConfig.getBaseUrl() + "/" + apiUrl;

        for(String arg : apiParams) {
            urlString = urlString + "/" + arg;
        }

        modelCollection = mapper.readValue(new URL(urlString), type);

        System.out.println("[ApiService.getApiModels] : " + urlString);

        return modelCollection;
    }
}
