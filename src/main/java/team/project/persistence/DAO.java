package team.project.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import team.project.entity.Profile;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class DAO {

    /**
     * sends an API call to swapi to retrieve the first planet in response
     * @return planet object (not a list of objects)
     */
    Profile getProfile() {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/Team_Project_war/......???????");
        String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
        ObjectMapper mapper = new ObjectMapper();
        Profile profile = null;

        try {

            profile = mapper.readValue(response, Profile.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Test");
            throw new RuntimeException(e);
        }

        return profile;
    }
}
