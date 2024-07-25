package com.manager.wallet.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Mapper {

    public static <T> T map(Object instanceOrigin, Class<T> destinationClass) {
        var mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.getConfiguration().setAmbiguityIgnored(true);
        return mapper.map(instanceOrigin, destinationClass);
    }
}
