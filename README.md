# KeyValueStoreService

### Requirements

1. Implement a Key-Value Store Service which should persist its state across restarts.
2. A Key-value Store is a service that enables a user to store a value linked to a key and it should also provide ways to retrieve that value using the key and also delete the value.
3. A Persistent store is something that retains its state across restarts.
4. So, here you will have to create a service that exposes REST Api’s to create, read, update and delete a key-value pair.
5. This service should be fast(Any key-value store should be fast.) and should retain back the state when restarted.

### Expectations

1. Implement the REST endpoint for the service.
2. Key can be any STRING and value can be arbitrary JSON.
3. Design of the storage.
4. Modularity, extensibility of the code.
