package org.acme;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.PartFilename;
import org.jboss.resteasy.reactive.PartType;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.util.UUID;

@Path("")
@RegisterRestClient(baseUri="http://my.endpoint.com/api/v1", configKey="multipart_requests_yml")
@ApplicationScoped
public interface UserProfileDataApi {

    @POST
    @Path("/user-profile-data")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    void postUserProfileData(@BeanParam PostUserProfileDataMultipartForm multipartForm);

    class PostUserProfileDataMultipartForm {
        @FormParam("id")
        @PartType(MediaType.TEXT_PLAIN)
        public UUID id;

        @FormParam("address")
        @PartType(MediaType.APPLICATION_JSON)
        public Address address;

        @FormParam("profileImage")
        @PartFilename("profileImageFile")
        @PartType(MediaType.APPLICATION_OCTET_STREAM)
        public File profileImage;
    }
}
