package io.jaylee.oauth2;

import com.microsoft.aad.msal4j.*;
import org.apache.hadoop.fs.azurebfs.oauth2.AzureADToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class WorkloadIdentityAuthenticator {

	private WorkloadIdentityAuthenticator() {
		throw new IllegalStateException("Adapter class");
	}

	private static final String SCOPE = "https://storage.azure.com/.default";

	private static final Logger LOG = LoggerFactory.getLogger(WorkloadIdentityAuthenticator.class);

	public static AzureADToken getToken(String azureFederatedTokenFile, String azureAuthorityHost, String azureClientId,
			String accountName, String tenantId) throws IOException {
		AzureADToken token = new AzureADToken();

		String clientAssertion = null;
		try {
			clientAssertion = Files.readString(Paths.get(azureFederatedTokenFile));
		}
		catch (IOException e) {
			LOG.error("Error getting AZURE_FEDERATED_TOKEN_FILE", e);
			throw new IOException();
		}

		IClientCredential credential = ClientCredentialFactory.createFromClientAssertion(clientAssertion);

		StringBuilder authority = new StringBuilder();
		authority.append(azureAuthorityHost);
		authority.append(tenantId);

		ConfidentialClientApplication app = null;
		try {
			app = ConfidentialClientApplication.builder(azureClientId, credential).authority(authority.toString())
					.build();
		}
		catch (MalformedURLException e) {
			LOG.error("Authority building failed", e);
			throw new IOException();
		}

		Set<String> scopes = new HashSet<>(Arrays.asList(SCOPE));

		ClientCredentialParameters parameters = ClientCredentialParameters.builder(scopes).build();
		IAuthenticationResult result = app.acquireToken(parameters).join();
		token.setAccessToken(result.accessToken());
		token.setExpiry(result.expiresOnDate());

		return token;

	}

}
