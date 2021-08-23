package com.github.prbpedro.javaspringbootreactiveapiexemple.services;

import com.github.prbpedro.javaspringbootreactiveapiexemple.dto.DumbEntityDTO;
import com.github.prbpedro.javaspringbootreactiveapiexemple.repositories.write.DumbEntityWriteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.util.Assert;
import reactor.test.StepVerifier;

@SpringBootTest
public class DumbEntityServiceIntegrationTests {

    @Autowired
    DumbEntityWriteRepository repository;

    @Autowired
    DumbEntityService service;

    @BeforeTestMethod
    public void beforeAll() {
        repository.deleteAll();
    }

    @Test
    public void saveTest() {
        StepVerifier
            .create(service.save(DumbEntityDTO.builder().value(1L).build()))
            .assertNext(dto -> {
                Assert.notNull(dto, "Returned entity should not be null");
                Assert.notNull(dto.getId(), "Returned entity should not be null");
            })
            .verifyComplete();
    }

    @Test
    public void getTest() {
        StepVerifier
            .create(
                service
                    .save(DumbEntityDTO.builder().value(1L).build())
                    .flatMap(dumbEntityDTO -> service.get(dumbEntityDTO.getId()))
            )
            .assertNext(dto -> {
                Assert.notNull(dto, "Returned entity should not be null");
                Assert.notNull(dto.getId(), "Returned entity should not be null");
            })
            .verifyComplete();
    }

    @Test
    public void deleteTest() {
        StepVerifier
            .create(
                service
                    .save(DumbEntityDTO.builder().value(1L).build())
                    .flatMap(dumbEntityDTO -> service.delete(dumbEntityDTO))
                    .flatMap(dumbEntityDto -> service.get(1L))
            )
            .expectNextCount(0)
            .verifyComplete();
    }

    @Test
    public void listAllTest() {
        StepVerifier
            .create(
                service
                    .save(DumbEntityDTO.builder().value(1L).build())
                    .map(dumbEntityDTO -> service.listAll())
            )
            .expectNextCount(1)
            .verifyComplete();
    }
}
