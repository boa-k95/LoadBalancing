package com.example.secondService.services;

import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Service
@Data
public class generatedNumber {

    public Integer checkSize(@Nullable List<Integer> numbers) {
        IntStream ints = null;

        if (CollectionUtils.size(numbers) > 0 && CollectionUtils.size(numbers) > 1) {

            Random random = new Random();
            ints = random.ints(numbers.get(0), numbers.get(1));
}
        return Integer.valueOf(String.valueOf(ints));
    }
}