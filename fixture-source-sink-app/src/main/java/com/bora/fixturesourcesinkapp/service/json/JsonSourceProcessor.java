package com.bora.fixturesourcesinkapp.service.json;

import com.bora.fixturesourcesinkapp.service.FixtureService;
import com.bora.fixturesourcesinkapp.service.processors.FixtureSourceProcessor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.bora.fixturesourcesinkapp.service.helpers.HelperService.checkDoneStatus;
import static com.bora.fixturesourcesinkapp.service.helpers.HelperService.checkOkStatus;

@Component
public class JsonSourceProcessor implements FixtureSourceProcessor {

    @Autowired
    private Environment env;

    @Autowired
    private RestTemplate restTemplate;

    private String uri;

    @Override
    public void getFixture() {
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
                    FixtureService.fixtureMap.put(id, id);
                }
            }
        } while (!checkDoneStatus(status));
    }
}
