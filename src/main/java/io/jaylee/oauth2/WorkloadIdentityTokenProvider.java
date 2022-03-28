package io.jaylee.oauth2;

import io.jaylee.oauth2.util.SATenantMappingLoader;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.azurebfs.extensions.CustomTokenProviderAdaptee;
import org.apache.hadoop.fs.azurebfs.oauth2.AzureADToken;
import org.apache.hadoop.util.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static io.jaylee.oauth2.WorkloadIdentityConstants.*;

public class WorkloadIdentityTokenProvider implements CustomTokenProviderAdaptee {

	private final String azureFederatedTokenFile = System.getenv(AZURE_FEDERATED_TOKEN_FILE);

	private final String azureAuthorityHost = System.getenv(AZURE_AUTHORITY_HOST);

	private final String azureClientId = System.getenv(AZURE_CLIENT_ID);

	private AzureADToken azureADToken = new AzureADToken();

	private String accountName;

	private static final Logger LOG = LoggerFactory.getLogger(WorkloadIdentityTokenProvider.class);

	@Override
	public void initialize(Configuration configuration, String accountName) throws IOException {
		LOG.debug("WorkloadIdentityTokenProvider: Created for {} Storage Account", accountName);

		Preconditions.checkNotNull(AZURE_FEDERATED_TOKEN_FILE, "AZURE_FEDERATED_TOKEN_FILE");
		Preconditions.checkNotNull(AZURE_AUTHORITY_HOST, "AZURE_AUTHORITY_HOST");
		Preconditions.checkNotNull(AZURE_CLIENT_ID, "AZURE_CLIENT_ID");
		Preconditions.checkNotNull(configuration.get(FS_AZURE_STORAGEACCOUNT_TENANT_ID_MAPPING_FILE),
				"FS_AZURE_STORAGEACCOUNT_TENANT_ID_MAPPING_FILE");

		this.accountName = accountName;
		SATenantMappingLoader.populateMapping(new File(configuration.get(FS_AZURE_STORAGEACCOUNT_TENANT_ID_MAPPING_FILE)));
	}

	@Override
	public String getAccessToken() throws IOException {
		LOG.debug("AADToken: Getting workload identity based token");

		azureADToken = WorkloadIdentityAuthenticator.getToken(azureFederatedTokenFile, azureAuthorityHost,
				azureClientId, SATenantMappingLoader.getStorageAccount(accountName).getTenantId());

		return azureADToken.getAccessToken();
	}

	@Override
	public Date getExpiryTime() {
		return azureADToken.getExpiry();
	}

}
