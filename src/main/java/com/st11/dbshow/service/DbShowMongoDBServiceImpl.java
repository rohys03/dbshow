package com.st11.dbshow.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.st11.dbshow.config.MongoDbConfig;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;

import static com.st11.dbshow.common.DbShow.getCurrentTime;
import static com.st11.dbshow.common.DbShow.getParameterMap;

@Service
public class DbShowMongoDBServiceImpl implements DbShowMongoDBService {

//    @Autowired
    MongoDbConfig mongoDbConfig;

    public void mongoLogInsert(HttpServletRequest request, HttpServletResponse response) {

        MongoDatabase mongoDatabase = mongoDbConfig.getMongoDatabase();
        MongoCollection<Document> collection = mongoDatabase.getCollection("dbshowLog");
        Document doc = new Document("logTime", getCurrentTime())
                .append("newDate", new Date())
                .append("ip", request.getHeader("X-FORWARDED-FOR"))
                .append("RequestURL", request.getRequestURL().toString())
                .append("ParameterMap", getParameterMap(request).toString())
                ;

        collection.insertOne(doc);

    }
}
