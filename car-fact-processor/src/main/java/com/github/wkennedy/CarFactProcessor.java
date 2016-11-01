package com.github.wkennedy;

import com.github.wkennedy.dto.Car;
import com.github.wkennedy.entities.EngineDimEntity;
import com.github.wkennedy.entities.MakeDimEntity;
import com.github.wkennedy.repo.EngineDimRepository;
import com.github.wkennedy.repo.MakeDimRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.text.ParseException;

@EnableBinding(Processor.class)
public class CarFactProcessor {

    private static Logger logger = LoggerFactory.getLogger(CarFactProcessor.class);

    @Autowired
    private EngineDimRepository engineDimRepository;

    @Autowired
    private MakeDimRepository makeDimRepository;

    @Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    public Message<String> transform(Car payload) throws ParseException {
        logger.debug("Transforming payload: " + payload.toString());

        String engineCode = payload.getEngine();
        String make = payload.getMake();

        EngineDimEntity engineDimEntity = engineDimRepository.findByCode(engineCode);
        MakeDimEntity makeDimEntity = makeDimRepository.findByName(make);

        String message = "{" +
                "   \"engine\":\"" + engineDimEntity.getId() + "\"," +
                "   \"make\":" + makeDimEntity.getId() +
                "}";

        logger.debug("WinFactProcessor transformed result: " + message);
        return MessageBuilder.withPayload(message).build();
    }
}