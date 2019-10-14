package com.thlee.work.http;

import java.util.ArrayList;
import java.util.List;

import com.thlee.work.model.HttpRequest;
import com.thlee.work.model.ServerSetting;

import lombok.Builder;
import lombok.Getter;

public class ValidationFilter {

    private static List<ValidatorInfo> validatorList;

    static {
        validatorList = new ArrayList<>();
        validatorList.add(ValidatorInfo.builder()
            .errorCode(403)
            .validator(new Validator403UpperDirectoryImpl())
            .build());
        validatorList.add(ValidatorInfo.builder()
            .errorCode(403)
            .validator(new Validator403ExeImpl())
            .build());
        validatorList.add(ValidatorInfo.builder()
            .errorCode(404)
            .validator(new Validator404Impl())
            .build());
    }

    public static int validate(HttpRequest httpRequest, ServerSetting serverSetting) {

        for (ValidatorInfo validatorInfo : validatorList) {
            if (!validatorInfo.getValidator().validate(httpRequest, serverSetting)) {
                return validatorInfo.getErrorCode();
            }
        }

        return 200;
    }

    @Builder
    @Getter
    private static class ValidatorInfo {

        private int errorCode;
        private Validator validator;
    }
}
