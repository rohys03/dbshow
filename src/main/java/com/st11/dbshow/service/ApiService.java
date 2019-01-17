package com.st11.dbshow.service;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.Collection;

public interface ApiService {
    Collection getApiModels(String apiUrl, TypeReference type, String ... apiParams) throws IOException;
}
