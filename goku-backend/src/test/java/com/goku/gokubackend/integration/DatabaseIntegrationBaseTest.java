package com.goku.gokubackend.integration;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestPersistenceConfig.class}, loader = AnnotationConfigContextLoader.class)
@Sql({"/schema.sql", "/test-data.sql"})
@Sql(value = {"/drop-tables-after-tests.sql"}, executionPhase = AFTER_TEST_METHOD)
public class DatabaseIntegrationBaseTest {
}
