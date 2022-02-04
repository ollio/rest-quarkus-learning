package org.acme.rest;

import org.acme.rest.model.User;
import org.acme.rest.repository.UserRepository;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Path(UserResource.RESOURCE_URI)
public class UserResource {

    public static final String RESOURCE_URI = "/api/users";

    @Inject
    UserRepository repository;

    ModelMapper mapper = new ModelMapper();

    @POST
    public Response createUser() {
        Integer id = repository.create().getId();
        return Response.created(URI.create(RESOURCE_URI + "/" + id)).build();
    }

    @PUT
    @Path("/{userId}")
    public Response putUser(@PathParam("userId") Integer userId, User updatedUser) {
        final User existingUser = repository.getById(userId);
        mapper.map(updatedUser, existingUser);
        repository.save(existingUser);
        return Response.ok().entity(existingUser).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findUsers(@QueryParam("name") Optional<String> name,
                              @QueryParam("surname") Optional<String> surname,
                              @QueryParam("email") Optional<String> email) {

        List<User> result = new ArrayList<>();
        name.ifPresent( n -> result.addAll(repository.findAllByName(n)));
        surname.ifPresent( s -> result.addAll(repository.findAllBySurname(s)));
        email.ifPresent( e -> result.addAll(repository.findAllByEmail(e)));

        if(result.isEmpty()) {
            result.addAll(repository.getAll());
        }

        return Response.ok().entity(result).build();
    }

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("userId") Integer userId) {
        return Response.ok().entity(repository.getById(userId)).build();
    }

    @DELETE
    @Path("/{userId}")
    public Response deleteUser(@PathParam("userId") Integer userId) {
        repository.delete(userId);
        return Response.noContent().build();
    }
}