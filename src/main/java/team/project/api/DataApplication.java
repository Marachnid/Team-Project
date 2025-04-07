package team.project.api;

import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//Defines the base URI for all resource URIs.
@ApplicationPath("/services")

//The java class declares root resource and provider classes
@OpenAPIDefinition(info = @Info(
        title = "Fitness Tracker API",
        version = "1.0",
        description = "With the growing use of health trackers and health research, it can be difficult to keep up with the many calculations and methods" +
                "       used, and even more difficult to find everything in one place. This service aims to centralize those calculations into one location with flexible usage." +
       "   Generic users will be included in the service to allow displays of health metrics across various physical characteristics as well as having the capability" +
       " to set or reuse common physical qualities to more easily return reused health metrics."
     ))
public class DataApplication extends Application {

    //The method returns a non-empty collection with classes, that must be included in the published JAX-RS application
    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> h = new HashSet<Class<?>>();
        h.add(CalculationServices.class);
        h.add(ProfileServices.class);
        h.add(OpenApiResource.class);
        return h;
    }

    //TODO the internet tells me this SHOULD remove the annoying wadl endpoints that don't mean anything in our api spec but it's not working yet! halp
    //or we can figure out how to just filter endpoints from the spec on the api side?
    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put("jersey.config.server.wadl.disableWadl", true);
        return props;
    }

}