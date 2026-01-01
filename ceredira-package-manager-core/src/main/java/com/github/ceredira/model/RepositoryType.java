package com.github.ceredira.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RepositoryType {
    LOCAL("local"),
    REMOTE("remote");

    private final String value;

    RepositoryType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static RepositoryType fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (RepositoryType type : RepositoryType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Неизвестный тип репозитория: '" + value + "'");
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
