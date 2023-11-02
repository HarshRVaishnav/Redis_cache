package com.example.serde;



import java.io.IOException;

import com.example.enums.OrderStatusVo;
import com.example.exception.OrderNotFoundException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;


@Component
public class OrderStatusSerdeProvider extends SimpleModule {

    private static final long serialVersionUID = 1L;

    public OrderStatusSerdeProvider() {
        addSerializer(OrderStatusVo.class, new OrderStatusVoSerializer());
        addDeserializer(OrderStatusVo.class, new OrderStatusVoDeserializer());
    }

    private static class OrderStatusVoSerializer extends JsonSerializer<OrderStatusVo> {
        @Override
        public void serialize(OrderStatusVo orderStatusVo, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) {
            try {
                jsonGenerator.writeString(orderStatusVo.getValue().toString());
            }catch (Exception exception){
                throw new OrderNotFoundException();
            }
        }
    }

    private static class OrderStatusVoDeserializer extends JsonDeserializer<OrderStatusVo> {
        @Override
        public OrderStatusVo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext){
            try {
                String value = jsonParser.getValueAsString();
                return new OrderStatusVo(value);
            }catch (Exception exception){
                throw new OrderNotFoundException();
            }
        }
    }
}
