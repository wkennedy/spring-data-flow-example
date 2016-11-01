package com.github.wkennedy;

import com.github.wkennedy.dto.Car;
import com.github.wkennedy.entities.EngineDimEntity;
import com.github.wkennedy.entities.MakeDimEntity;
import com.github.wkennedy.repo.EngineDimRepository;
import com.github.wkennedy.repo.MakeDimRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.tuple.JsonStringToTupleConverter;
import org.springframework.tuple.Tuple;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CarFactProcessorTest {

    @InjectMocks
    CarFactProcessor carFactProcessor = new CarFactProcessor();

    private JsonStringToTupleConverter jsonStringToTupleConverter = new JsonStringToTupleConverter();

    @Mock
    private MakeDimRepository makeDimRepository;

    @Mock
    private EngineDimRepository engineDimRepository;

    private static final int ENGINE_ID = 123;
    private static final int MAKE_ID = 324;

    @Before
    public void setup() {
        EngineDimEntity engineDimEntity = new EngineDimEntity();
        engineDimEntity.setCode("SR20");
        engineDimEntity.setDisplacement("2.0 litre");
        engineDimEntity.setFuel("gasoline");
        engineDimEntity.setId(ENGINE_ID);
        given(engineDimRepository.findByCode("SR20")).willReturn(engineDimEntity);

        MakeDimEntity makeDimEntity = new MakeDimEntity();
        makeDimEntity.setId(MAKE_ID);
        makeDimEntity.setName("Nissan");
        given(makeDimRepository.findByName("Nissan")).willReturn(makeDimEntity);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testTransform() throws ParseException {
        Tuple tupleMessage = jsonStringToTupleConverter.convert("{  \n" +
                "   \"engineCode\":\"SR20\",\n" +
                "   \"make\":\"Nissan\"\n" +
                "}");

//        GenericMessage<String> response = (GenericMessage<String>) carFactProcessor.transform(tupleMessage);
        Car car = new Car();
        car.setMake("Nissan");
        car.setEngine("SR20");
        GenericMessage<String> response = (GenericMessage<String>) carFactProcessor.transform(car);
        assertNotNull("Response from CarFactProcessor transform should not be null", response);
        Tuple tupleResponse = jsonStringToTupleConverter.convert(response.getPayload());
        assertNotNull(tupleResponse);
        assertEquals(MAKE_ID, tupleResponse.getInt("make"));
        assertEquals(ENGINE_ID, tupleResponse.getInt("engine"));
    }

}
