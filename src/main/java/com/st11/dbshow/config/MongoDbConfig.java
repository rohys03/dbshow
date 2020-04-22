package com.st11.dbshow.config;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import java.util.ArrayList;
import java.util.List;


@Data
//@Configuration
//@ConfigurationProperties(prefix="spring.data.mongodb")
public class MongoDbConfig extends AbstractMongoConfiguration {

    private String uri;
    private String database;
    private String username;
    private String password;

    protected String getDatabaseName() {
        return this.database;
    }

    public MongoClient mongoClient() {
        MongoClientURI mongoClientURI = new MongoClientURI(this.uri);
        MongoCredential mongoCredential =
                MongoCredential.createCredential(this.username, this.database, this.password.toCharArray());

        List<ServerAddress> serverAddress = getHosts(mongoClientURI.getHosts());

//        ServerAddress serverAddress = new ServerAddress(mongoClientURI.getHosts().get(0));

        MongoClientOptions options = MongoClientOptions.builder().build();
        MongoClient mongoClient = new MongoClient(serverAddress, mongoCredential, options);

        System.out.println("[URI]" + this.uri + " [address] : " + mongoClientURI.getHosts().toString() + " [addressList] : " + serverAddress.toString());
        System.out.println("[mongoClient]: " + mongoClient.toString() + " [mongoClient address]: " + mongoClient.getAllAddress().toString());

        return mongoClient;
    }

    public MongoDatabase getMongoDatabase() {
        MongoClient mongoClient = this.mongoClient();

        return mongoClient.getDatabase(this.database);
    }

    // Convert List<String> to List<ServerAddress> .... :(
    private static List<ServerAddress> getHosts(final List<String> hostsList) {
        List<ServerAddress> serverAddressList = new ArrayList<>(hostsList.size());
        for (String host : hostsList) {
            serverAddressList.add(new ServerAddress(host));
        }
        return serverAddressList;
    }
}
