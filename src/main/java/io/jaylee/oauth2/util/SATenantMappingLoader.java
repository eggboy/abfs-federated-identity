package io.jaylee.oauth2.util;

import io.jaylee.oauth2.domain.StorageAccount;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class SATenantMappingLoader {

	private SATenantMappingLoader() {
	}

	private static ConcurrentHashMap<String, StorageAccount> saTenantMapping = new ConcurrentHashMap<>();

	public static void populateMapping(String file) throws FileNotFoundException {
		try (Scanner scanner = new Scanner(new File(file))) {
			scanner.useDelimiter(":");

			while (scanner.hasNextLine()) {
				String storageAccountName = scanner.next();
				String clientId = scanner.next();
				String tenantId = scanner.next();

				StorageAccount storageAccount = new StorageAccount.Builder(storageAccountName).clientId(clientId)
						.tenantId(tenantId).build();
				saTenantMapping.putIfAbsent(storageAccountName, storageAccount);
			}
		}

	}

	public static StorageAccount getStorageAccount(String storageAccountName) {
		return saTenantMapping.get(storageAccountName);
	}

}
