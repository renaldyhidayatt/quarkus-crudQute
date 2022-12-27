package org.sanedge;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateException;
import io.quarkus.qute.TemplateInstance;

@Path("/")
public class UserEndPoint {
    @Inject
    Template home;

    @Inject
    Template createupdate;

    @Inject
    UserResource userResource;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getAllUserView() throws TemplateException {
        List<User> users = userResource.getUsers();

        return home.data(Map.of("users", users));
    }

    @GET
    @Path("/create")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance createUserView()
            throws TemplateException, IOException {
        User user = new User();
        Map<String, Object> obj = new HashMap<>();
        obj.put("user", user);
        obj.put("isUpdate", false);
        return createupdate.data(obj);
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/create")
    public TemplateInstance createUser(@FormParam("firstName") String firstName,
            @FormParam("lastName") String lastName,
            @FormParam("email") String email)
            throws TemplateException {
        User usr = new User();
        usr.setEmail(email);
        usr.setFirstName(firstName);
        usr.setLastName(lastName);
        userResource.addUser(usr);
        return getAllUserView();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/update/{id}")
    public TemplateInstance updateUser(@PathParam("id") Long id)
            throws TemplateException {
        User user = userResource.getUser(id);
        Map<String, Object> obj = new HashMap<>();
        obj.put("user", user);
        obj.put("isUpdate", true);
        return createupdate.data(obj);
    }

    @POST
    @Path("/update/{id}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance createUser(@FormParam("firstName") String firstName,
            @FormParam("lastName") String lastName,
            @FormParam("email") String email,
            @PathParam("id") Long id)
            throws TemplateException {
        User usr = new User();
        usr.setEmail(email);
        usr.setFirstName(firstName);
        usr.setLastName(lastName);
        usr.setId(id);
        userResource.updateUser(usr);
        return getAllUserView();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/delete/{id}")
    public TemplateInstance deleteUser(@PathParam("id") Long id)
            throws TemplateException, IOException {
        userResource.deleteUser(id);
        return getAllUserView();
    }
}
