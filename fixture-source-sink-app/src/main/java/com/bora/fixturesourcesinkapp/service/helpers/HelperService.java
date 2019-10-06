package com.bora.fixturesourcesinkapp.service.helpers;

import com.bora.fixturesourcesinkapp.service.constants.FixtureConstants;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class HelperService {

    public static boolean checkOkStatus(String status) {
        if ("ok".equals(status)) {
            return true;
        }

        return false;
    }

    public static boolean checkDoneStatus(String status) {
        if ("done".equals(status)) {
            return true;
        }

        return false;
    }

    public static boolean checkFailStatus(String status) {
        if ("fail".equals(status)) {
            return true;
        }

        return false;
    }

    public static String getFixtureType(String id, Map<String, String> fixtureMap) {
        String kind = "";

        if (fixtureMap.containsKey(id)) {
            kind = FixtureConstants.JOINED;
        } else {
            kind = FixtureConstants.ORPHANED;
        }
        return kind;
    }
}
