package com.bora.fixturesourcesinkapp.service;

import com.bora.fixturesourcesinkapp.model.Fixture;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.bora.fixturesourcesinkapp.service.HelperService.*;

@Service
public class FixtureService {

    @Autowired
    private Environment env;

    private Map<String, String> fixtureMap;

    private RestTemplate restTemplate;

    private String uri;

    public FixtureService() {
        fixtureMap = new HashMap<>();
        restTemplate = new RestTemplate();
    }

    public void start() {
        getFixtureFromSourceA();
        getFixtureFromSourceB();
    }

    public void getFixtureFromSourceA()
    {
        uri = env.getProperty("source.a.uri");

        String status = "";
        do {
            String result = restTemplate.getForObject(uri, String.class);
            JSONObject fixtureJsonObject = null;
            try {
                fixtureJsonObject = new JSONObject(result);
            } catch(JSONException e) {
                continue;
            }
            if (fixtureJsonObject != null) {
                status = fixtureJsonObject.getString("status");
                if (checkOkStatus(status)) {
                    String id = fixtureJsonObject.getString("id");
                    fixtureMap.put(id, id);
                }
            }
        } while (!checkDoneStatus(status));
    }

    public void getFixtureFromSourceB()
    {
        uri = env.getProperty("source.b.uri");

        String status = "";
        String kind = "";
        do {
            Fixture result = null;
            try {
                result = restTemplate.getForObject(uri, Fixture.class);
            } catch (Exception e) {
               continue;
            }
            if (result != null) {
                status = result.getDone();
                if (status == null) {
                    if (result.getId() != null) {
                        String id = result.getId().getValue();
                        kind = getFixtureType(id, fixtureMap);
                        sinkFixtureToSourceC(id, kind);
                    }
                }
                }
            } while(status == null);
    }

    public void sinkFixtureToSourceC(String id, String kind) {
        String url = env.getProperty("sink.a.uri");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        JSONObject json = new JSONObject();
        json.put("kind", kind);
        json.put("id", id);

        HttpEntity <String> httpEntity = new HttpEntity <String> (json.toString(), headers);

        String ret = "";

        do { // try reposting in case of fail
            String result = restTemplate.postForObject(url, httpEntity, String.class);
            ret = JsonPath.read(result, "$.status");
        } while(checkFailStatus(ret));


    }
}
