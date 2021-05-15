package org.CarLounge.fis.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProviderServiceTest {
    @BeforeAll
    static void beforeAll() {

    }

    @AfterAll
    static void afterAll() {

    }

    @BeforeEach
    void setUp() throws Exception{
        FileSystemService.APPLICATION_FOLDER = ".testingCarLoungeDatabases";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    @DisplayName ("Provider database is initialized and there are no providers.")
    void testProviderInitDB() {
        ProviderService.initDatabase();
        assertThat(ProviderService.getAllProviders()).isNotNull();
        assertThat(ProviderService.getAllProviders()).isEmpty();
    }
}