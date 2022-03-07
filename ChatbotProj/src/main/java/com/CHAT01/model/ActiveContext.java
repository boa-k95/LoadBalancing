package com.CHAT01.model;

import lombok.Data;

import java.util.Map;
import java.util.Objects;
@Data
public class ActiveContext{
    private String name;
    private Map<String, ? super Object> parameters;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActiveContext)) return false;
        ActiveContext that = (ActiveContext) o;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getParameters().keySet(), that.getParameters().keySet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getParameters());
    }
}

