package com.xti.spring.cloud.heroku.discovery.metadata;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MetadataController.class})
@WebMvcTest
public class MetadataControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testLocalMetadata() throws Exception {
        LocallyMutableMetadataProvider provider = LocallyMutableMetadataProvider.getInstance();
        provider.getMetadata().put("entry", "value");

        mvc.perform(get("/spring-cloud-heroku-metadata"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.entry", is("value")));
    }
}