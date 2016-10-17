package com.github.wkennedy;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.BinderFactory;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.tuple.JsonStringToTupleConverter;
import org.springframework.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = {"spring.cloud.stream.bindings.output.contentType=application/json"})
@DirtiesContext
public class CarFactProcessorIT {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private Processor carFactProcessor;

    @Autowired
    private BinderFactory<MessageChannel> binderFactory;

    @Autowired
    private MessageCollector messageCollector;

    private JsonStringToTupleConverter jsonStringToTupleConverter = new JsonStringToTupleConverter();

    @BeforeClass
    public static void disableTestsOnCiServer() {
        String profilesFromConsole = System.getProperty("spring.profiles.active", "");
        assumeFalse(profilesFromConsole.contains("integration-tests"));
    }

    @Before
    public void setup() {

    }

    @After
    public void teardown() {

    }

    @Test
    @SuppressWarnings("unchecked")
    public void testWiring() {
        Tuple tupleMessage = jsonStringToTupleConverter.convert("{  \n" +
                "   \"engineCode\":\"SR20DE\",\n" +
                "   \"make\":\"Ford\"\n" +
                "}");

        Map<String, Object> headers = new HashMap<>();
        headers.put("content-type", "application/x-spring-tuple");
        Message<Tuple> message = new GenericMessage<>(tupleMessage, headers);
        carFactProcessor.input().send(message);
        Message<String> received = (Message<String>) messageCollector.forChannel(carFactProcessor.output()).poll();
        String payload = received.getPayload();
		assertNotNull(payload);
        Tuple tupleResult = jsonStringToTupleConverter.convert(payload);
        assertTrue(tupleResult.getValue("engine", Integer.class) != null);
        assertTrue(tupleResult.getValue("make", Integer.class) != null);
    }
}
