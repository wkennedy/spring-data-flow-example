package com.github.wkennedy;

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
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.tuple.Tuple;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@EnableBinding(Processor.class)
public class CarFactProcessor {

    private static Logger logger = LoggerFactory.getLogger(CarFactProcessor.class);

    private String fromPattern = "yyyy-MM-dd HH:mm:ss";
    private SimpleDateFormat fromDateFormat = new SimpleDateFormat(fromPattern);

    private String idPattern = "yyyyMMdd";
    private SimpleDateFormat idDateFormat = new SimpleDateFormat(idPattern);

    @Autowired
    private EngineDimRepository engineDimRepository;

    @Autowired
    private MakeDimRepository makeDimRepository;

    @Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    public Object transform(Object payload) throws ParseException {
        logger.debug("Transforming payload: " + payload.toString());
        Tuple tuple = (Tuple) payload;

        String engineCode = (String) tuple.getValue("engineCode");
        String make = (String) tuple.getValue("make");

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