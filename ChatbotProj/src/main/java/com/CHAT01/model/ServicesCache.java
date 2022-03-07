package com.CHAT01.model;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

@Data // Serve per test
public class ServicesCache {
    private Map<String, ? super Object> cache;

    public ServicesCache() {
        cache = Maps.newHashMap();
    }
}
