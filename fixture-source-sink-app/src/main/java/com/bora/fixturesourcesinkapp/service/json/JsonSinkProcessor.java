package com.bora.fixturesourcesinkapp.service.json;

import com.bora.fixturesourcesinkapp.service.processors.FixtureSinkProcessor;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.bora.fixturesourcesinkapp.service.helpers.HelperService.checkFailStatus;

@Component
public class JsonSinkProcessor implements FixtureSinkProcessor {

    @Autowired
    private Environment env;

    @Autowired
    private RestTemplate restTemplate;

    private String uri;

    @Override
    public void sinkFixture(String id, String kind) {
        String url = env.getProperty("sink.a.uri");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        JSONObject json = new JSONObject();
        json.put("kind", kind);
        json.put("id", id);

        HttpEntity<String> httpEntity = new HttpEntity <String> (json.toString(), headers);

        String ret = "";

        do { // try reposting in case of fail
            String result = restTemplate.postForObject(url, httpEntity, String.class);
            ret = JsonPath.read(result, "$.status");
        } while(checkFailStatus(ret));
    }
}
