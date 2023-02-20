package org.acme;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.http.ContentTypeHeader;
import com.github.tomakehurst.wiremock.matching.MultipartValuePatternBuilder;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

@QuarkusTest
@QuarkusTestResource(WiremockMultipart.class)
class UserProfileDataApiTest {

    WireMockServer multipartServer;

    @RestClient
    @Inject
    UserProfileDataApi userProfileDataApi;

    @Test
    void testUploadMultipartFormdata(@TempDir Path tempDir) throws IOException {
        File testFile = File.createTempFile("test", "", tempDir.toFile());
        try (PrintWriter printWriter = new PrintWriter(testFile)) {
            printWriter.print("Content of the file");
        }

        UserProfileDataApi.PostUserProfileDataMultipartForm requestBody = new UserProfileDataApi.PostUserProfileDataMultipartForm();
        requestBody.address = new Address().street("Champs-Elysees").city("Paris");
        requestBody.id = UUID.fromString("00112233-4455-6677-8899-aabbccddeeff");
        requestBody.profileImage = testFile;

        userProfileDataApi.postUserProfileData(requestBody);

        multipartServer.verify(postRequestedFor(urlEqualTo("/user-profile-data"))
                .withRequestBodyPart(new MultipartValuePatternBuilder()
                        .withName("id")
                        // Primitive => text/plain
                        .withHeader(ContentTypeHeader.KEY, equalTo("text/plain; charset=UTF-8"))
                        .withBody(equalTo("00112233-4455-6677-8899-aabbccddeeff")).build()));

        multipartServer.verify(postRequestedFor(urlEqualTo("/user-profile-data"))
                .withRequestBodyPart(new MultipartValuePatternBuilder()
                        .withName("address")
                        // Complex value => application/json
                        .withHeader(ContentTypeHeader.KEY, equalTo(MediaType.APPLICATION_JSON))
                        .withBody(equalToJson("{\"street\":\"Champs-Elysees\", \"city\":\"Paris\"}")).build()));

        multipartServer.verify(postRequestedFor(urlEqualTo("/user-profile-data"))
                .withRequestBodyPart(new MultipartValuePatternBuilder()
                        .withName("profileImage")
                        .withHeader("Content-Disposition", containing("filename="))
                        // binary string => application/octet-stream
                        .withHeader(ContentTypeHeader.KEY, equalTo(MediaType.APPLICATION_OCTET_STREAM))
                        .withBody(equalTo("Content of the file")).build()));
    }
}