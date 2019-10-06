package com.bora.fixturesourcesinkapp.service.xml;

import com.bora.fixturesourcesinkapp.model.Fixture;
import com.bora.fixturesourcesinkapp.service.FixtureService;
import com.bora.fixturesourcesinkapp.service.processors.FixtureSourceProcessor;
import com.bora.fixturesourcesinkapp.service.json.JsonSinkProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.bora.fixturesourcesinkapp.service.helpers.HelperService.getFixtureType;

@Component
public class XmlSourceProcessor implements FixtureSourceProcessor {

    @Autowired
    private Environment env;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JsonSinkProcessor jsonSinkProcessor;

    private String uri;

    @Override
    public void getFixture() {
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
                        kind = getFixtureType(id, FixtureService.fixtureMap);
                        jsonSinkProcessor.sinkFixture(id, kind);
                    }
                }
            }
        } while (status == null);
    }
}
