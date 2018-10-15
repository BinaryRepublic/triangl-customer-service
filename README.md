# Triangl-Customer-Service
**Url**: https://api.triangl.io/customer-service/

## Routes

- Get all Customers with GET /customers/all
- Get a Customer by ID with GET /customers/{id}
- Create a Customer with  POST /customers
- Update a Customer with PATCH /customers/{id}
- Delete a Customer with DELETE /customers/{id}

## What does it do
This Service is an Endpoint to Get, Create, Update or Delete a Customer. It is connected to a Google Cloud Datastore database
and notifies the [Pipeline](https://github.com/codeuniversity/triangl-processing-pipeline) over Google Pub/Sub about every change happening in the Datastore. 
The [Pipeline](https://github.com/codeuniversity/triangl-processing-pipeline) can then apply the changes to the Serving SQL Database.

## UpdateCustomer function
The Update Customer function works in the following way:

It expects a Customer object where only the values are set that needs to be updated. Every value that shouldn't be updadet is set null in
the Customer instance. The UpdateCustomer function then fetches the current Customer object from the datastore and calls the ```Customer.Merge(customer: Customer): Customer``` function on it.
This function iterates over every class property of the ValuesToUpdate Customer Object and checks if it not null, because not null
means it should be updated and then updates it on the customer object from the db. But this means it currently can't detect correctly
if the maps is updated because ```[map Object] != [map Object]``` always returns true.

## Buggy/Todo
### Can't check if Map is updated
Currently the UpdateCustomer function does not check if the received map is different from the old. Therefore the function will
return ```updated:true``` even if the send map is identical to the old.

## Tools used
- Objectify
  https://github.com/objectify/objectify
  Used to connect and write to Google Datastore. "Objectify is a Java data access API specifically designed for the Google Cloud Datastore"

## Environment Variables
The following Environment variables are need for this service:

```GOOGLE_APPLICATION_CREDENTIALS:{pathToGoogleKeyFile.json}```

## Run
- With Gradle

  ```GOOGLE_APPLICATION_CREDENTIALS=/path/to/google/key/file.json ./gradlew bootRun```

![Infrastructure](./docs/six-sense-infrastructure.svg)