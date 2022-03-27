package io.jaylee.oauth2.domain;

public final class StorageAccount {

    private String storageAccountName;
    private String clientId;
    private String tenantId;

    private StorageAccount() {
    }

    public static class Builder {
        private StorageAccount storageAccount;

        public Builder() {
            storageAccount = new StorageAccount();
        }

        public StorageAccount build() {
            StorageAccount builtPerson = storageAccount;
            storageAccount = new StorageAccount();

            return builtPerson;
        }

        public Builder setStorageAccount(String storageAccountName) {
            this.storageAccount.storageAccountName = storageAccountName;
            return this;
        }

        public Builder setClientId(String clientId) {
            this.storageAccount.clientId = clientId;
            return this;
        }

        public Builder setTenantId(String tenantId) {
            this.storageAccount.tenantId = tenantId;
            return this;
        }
    }

    @Override
    public String toString() {
        return "StorageAccount{" +
                "storageAccountName='" + storageAccountName + '\'' +
                ", clientId='" + clientId + '\'' +
                ", tenantId='" + tenantId + '\'' +
                '}';
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
}
