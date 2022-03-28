package io.jaylee.oauth2.util;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SATenantMappingLoaderTest {

	@Test
    void testSATenantMapping() throws FileNotFoundException {
        Path resourceDirectory = Paths.get("src","test","resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        File mappingFile = new File(absolutePath + "/mapping.csv");

        SATenantMappingLoader.populateMapping(mappingFile);
        assertEquals("storageAccountName", SATenantMappingLoader.getStorageAccount("storageAccountName").getStorageAccountName());
        assertEquals("clientId", SATenantMappingLoader.getStorageAccount("storageAccountName").getClientId());
        assertEquals("tenantId", SATenantMappingLoader.getStorageAccount("storageAccountName").getTenantId());

    }

}
