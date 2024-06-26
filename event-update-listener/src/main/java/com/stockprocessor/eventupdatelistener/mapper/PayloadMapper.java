package com.stockprocessor.eventupdatelistener.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PayloadMapper {

    private final ObjectMapper objectMapper;

    public <T> T readPayload(String payload, Class<T> objectType) throws JsonProcessingException {
        T t = objectMapper.readValue(payload,objectType);
        return t;
    }
}
