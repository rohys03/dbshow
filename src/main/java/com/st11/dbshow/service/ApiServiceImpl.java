package com.st11.dbshow.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.st11.dbshow.config.ApiServerConfig;
import com.st11.dbshow.repository.DaStatMngVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Logger;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.web.client.RestTemplate;


@Service
public class ApiServiceImpl implements ApiService  {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    ApiServerConfig apiServerConfig;

    @Override
    public <T> T getApiModel(String apiUrl, Class<T> type, HashMap<String, String> apiParams) throws IOException, URISyntaxException {

        ObjectMapper mapper = new ObjectMapper();

        String urlString = apiServerConfig.getBaseUrl() + "/" + apiUrl;

        URIBuilder uriBuilder = new URIBuilder(urlString);

        for(String key : apiParams.keySet()) {
            uriBuilder.addParameter(key,apiParams.get(key));
        }

        logger.info("[ApiService.getApiModel] : " + uriBuilder.toString());
        try {
            return type.cast(mapper.readValue(new URL(uriBuilder.toString()), type));
        } catch (MismatchedInputException e) {
            logger.info("[ApiService.getApiModel] MismatchedInputException: " + uriBuilder.toString());
            return null;
        }

    }

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

    @Override
    public Collection getApiModels(String apiUrl, TypeReference type, HashMap<String, String> apiParams) throws IOException, URISyntaxException {
        Collection<Class> modelCollection;
        ObjectMapper mapper = new ObjectMapper();

        String urlString = apiServerConfig.getBaseUrl() + "/" + apiUrl;

        URIBuilder uriBuilder = new URIBuilder(urlString);

        for(String key : apiParams.keySet()) {
            uriBuilder.addParameter(key,apiParams.get(key));
        }

        logger.info("[ApiService.getApiModels].map : " + uriBuilder.toString());

        modelCollection = mapper.readValue(new URL(uriBuilder.toString()), type);

        return modelCollection;
    }


    @Override
    public String getApiString(String apiUrl, HashMap<String, String> apiParams) throws URISyntaxException {

        String urlString = apiServerConfig.getBaseUrl() + "/" + apiUrl;

        URIBuilder uriBuilder = new URIBuilder(urlString);

        for(String key : apiParams.keySet()) {
            uriBuilder.addParameter(key,apiParams.get(key));
        }

        logger.info("[ApiService.getApiModels].map : " + uriBuilder.toString());

        URI uri = new URI(uriBuilder.toString());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> entity = restTemplate.getForEntity(uri, String.class);

        if (entity.getStatusCode() == HttpStatus.OK) {
            return entity.getBody();
        } else
            return null;
    }

    public Collection<DaStatMngVO> getLastDaStatMng(String dbName, String statName) throws IOException, URISyntaxException {
        Collection<DaStatMngVO> daStatMngVOCollection;
        ObjectMapper mapper = new ObjectMapper();

        String urlString = apiServerConfig.getBaseUrl() + "/dbshow/getLastDaStatMng";

        URIBuilder uriBuilder = new URIBuilder(urlString);
        uriBuilder.addParameter("dbName",dbName);
        uriBuilder.addParameter("statName",statName);

        logger.info("[ApiService.getLastDaStatMng].DaStatMngVO : " + uriBuilder.toString());

        daStatMngVOCollection = mapper.readValue(new URL(uriBuilder.toString()), new TypeReference<Collection<DaStatMngVO>>() {});
        logger.info("[ApiService.getLastDaStatMng].daStatMngVOResult : " + daStatMngVOCollection.toString());

        return daStatMngVOCollection;
    }
}
