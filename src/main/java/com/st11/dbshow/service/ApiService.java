package com.st11.dbshow.service;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;

public interface ApiService {
    Collection getApiModels(String apiUrl, TypeReference type, String ... apiParams) throws IOException;
    Collection getApiModels(String apiUrl, TypeReference type, HashMap<String, String> apiParams) throws IOException, URISyntaxException, ClientProtocolException;
}
