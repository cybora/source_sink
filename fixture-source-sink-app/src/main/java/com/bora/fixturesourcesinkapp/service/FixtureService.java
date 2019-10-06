package com.bora.fixturesourcesinkapp.service;
import com.bora.fixturesourcesinkapp.service.json.JsonSinkProcessor;
import com.bora.fixturesourcesinkapp.service.json.JsonSourceProcessor;
import com.bora.fixturesourcesinkapp.service.xml.XmlSourceProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class FixtureService {

    @Autowired
    private JsonSourceProcessor jsonSourceProcessor;

    @Autowired
    private XmlSourceProcessor xmlSourceProcessor;

    @Autowired
    private JsonSinkProcessor jsonSinkProcessor;

    public static Map<String, String> fixtureMap = new HashMap<>();

    public Map<String, String> getFixtureMap() {
        return fixtureMap;
    }

    public void start() {
        jsonSourceProcessor.getFixture();
        xmlSourceProcessor.getFixture();
    }

}
