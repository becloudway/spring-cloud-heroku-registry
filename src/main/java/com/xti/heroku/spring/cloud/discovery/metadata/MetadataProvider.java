package com.xti.heroku.spring.cloud.discovery.metadata;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Map;

public interface MetadataProvider {
    Map<String, String> getMetadata();

    Map<String, String> getMetadata(@NotNull URI instanceURI);
}
