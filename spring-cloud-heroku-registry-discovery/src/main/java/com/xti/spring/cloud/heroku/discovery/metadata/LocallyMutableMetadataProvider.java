package com.xti.spring.cloud.heroku.discovery.metadata;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocallyMutableMetadataProvider {

    private Map<String, String> metadata;
    private static LocallyMutableMetadataProvider instance;

    private LocallyMutableMetadataProvider(){
        metadata = new ConcurrentHashMap<>();
    }

    public static LocallyMutableMetadataProvider getInstance(){
        if(instance == null){
            instance = new LocallyMutableMetadataProvider();
        }
        return instance;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }
}
