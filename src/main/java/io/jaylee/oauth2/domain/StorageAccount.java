package io.jaylee.oauth2.domain;

public class StorageAccount {

	private final String storageAccountName;

	private final String clientId;

	private final String tenantId;

	private StorageAccount(Builder builder) {
		this.storageAccountName = builder.storageAccountName;
		this.clientId = builder.clientId;
		this.tenantId = builder.tenantId;
	}

	public String getStorageAccountName() {
		return storageAccountName;
	}

	public String getClientId() {
		return clientId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public static class Builder {

		private final String storageAccountName;

		private String clientId;

		private String tenantId;

		public Builder(String storageAccountName) {
			this.storageAccountName = storageAccountName;
		}

		public StorageAccount build() {
			return new StorageAccount(this);
		}

		public Builder clientId(String clientId) {
			this.clientId = clientId;
			return this;
		}

		public Builder tenantId(String tenantId) {
			this.tenantId = tenantId;
			return this;
		}

	}

	@Override
	public String toString() {
		return "StorageAccount{" + "storageAccountName='" + storageAccountName + '\'' + ", clientId='" + clientId + '\''
				+ ", tenantId='" + tenantId + '\'' + '}';
	}

}
