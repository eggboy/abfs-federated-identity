# abfs-federated-identity

Sample provider to support Workload identity federation with Haddop ABFS with CustomTokenProvider. 

```
<property>
  <name>fs.azure.account.auth.type</name>
  <value>Custom</value>
  <description>
  Custom Authentication
  </description>
</property>
<property>
  <name>fs.azure.account.oauth.provider.type</name>
  <value>io.jaylee.oauth2.WorkloadIdentityTokenProvider</value>
  <description>
  classname of Custom Authentication Provider
  </description>
</property>
<property>
  <name>fs.azure.account.oauth2.sa.mapping.file</name>
  <value></value>
  <description>
  location of storage account and tenant mapping file
  </description>
</property>
```

Example of mapping file

```
storageAccountName:clientId:tenantId
storageAccountName2:clientId:tenantId2
```

