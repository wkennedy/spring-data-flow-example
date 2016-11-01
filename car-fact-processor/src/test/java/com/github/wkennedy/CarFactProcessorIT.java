package com.github.wkennedy;

import com.github.wkennedy.dto.Car;
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
                "   \"engineCode\":\"SR20\",\n" +
                "   \"make\":\"Nissan\"\n" +
                "}");

        Map<String, Object> headers = new HashMap<>();
        Car car = new Car();
        car.setEngine("SR20");
        car.setMake("Nissan");
        headers.put("content-type", "application/json");
        Message<Car> message = new GenericMessage<>(car, headers);
        carFactProcessor.input().send(message);
        Message<String> received = (Message<String>) messageCollector.forChannel(carFactProcessor.output()).poll();
        String payload = received.getPayload();
		assertNotNull(payload);
        Tuple tupleResult = jsonStringToTupleConverter.convert(payload);
        assertTrue(tupleResult.getValue("engine", Integer.class) != null);
        assertTrue(tupleResult.getValue("make", Integer.class) != null);
    }
}
