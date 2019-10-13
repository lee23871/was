package com.thlee.work.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.thlee.work.model.HttpRequest;
import com.thlee.work.model.ServerSetting;

public class ValidationFilter {

    private static Map<Integer, Validator> validatorMap;

    static {
        validatorMap = new HashMap<>();
        validatorMap.put(403, new Validator403Impl());
        validatorMap.put(404, new Validator404Impl());
    }

    public static int validate(HttpRequest httpRequest, ServerSetting serverSetting) {

        for (Entry<Integer, Validator> validatorEntry : validatorMap.entrySet()) {
            if (!validatorEntry.getValue().validate(httpRequest, serverSetting)) {
                return validatorEntry.getKey();
            }
        }

        return 200;
    }
}
