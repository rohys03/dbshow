package com.st11.dbshow.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.st11.dbshow.config.ApiServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.logging.Logger;


@Service
public class ApiServiceImpl implements ApiService  {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

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

        logger.info("[ApiService.getApiModels] : " + urlString);


        modelCollection = mapper.readValue(new URL(urlString), type);

        return modelCollection;
    }
}
