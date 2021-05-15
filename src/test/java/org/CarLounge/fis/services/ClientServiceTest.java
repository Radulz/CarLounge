package org.CarLounge.fis.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

class ClientServiceTest {
    @BeforeAll
    static void beforeAll() {

    }
    @AfterAll
    static void afterAll() {

    }

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".testingCarLoungeDatabases";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
    }

    @AfterEach
    void tearDown() {

    }

   @Test
   @DisplayName ("Client database is initialized and there are no clients.")
    void testClientInitDB() {
        ClientService.initDatabase();
        assertThat(ClientService.getAllClients()).isNotNull();
        assertThat(ClientService.getAllClients()).isEmpty();
    }

}