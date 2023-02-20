This project is a reproducer for 

## How to reproduce

Run `mvn clean verify` and `UserProfileDataApiTest.testUploadMultipartFormdata` will fail.

```bash
[ERROR]   UserProfileDataApiTest.testUploadMultipartFormdata:48 » IllegalState Form element 'org.acme.UserProfileDataApi$PostUserProfileDataMultipartForm.id' could not be converted to 'String' for REST Client interface 'org.acme.UserProfileDataApi'. A proper implementation of 'javax.ws.rs.ext.ParamConverter' needs to be returned by a 'javax.ws.rs.ext.ParamConverterProvider' that is registered with the client via the @RegisterProvider annotation on the REST Client interface.
```