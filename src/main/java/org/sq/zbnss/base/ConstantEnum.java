package org.sq.zbnss.base;

import lombok.Getter;

@Getter
public enum ConstantEnum {
    AUTHORIZATION("Authorization");

    private String value;

    ConstantEnum(String value) {
        this.value = value;
    }
}
