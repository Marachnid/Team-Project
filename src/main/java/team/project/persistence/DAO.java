package team.project.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import team.project.api.Planet;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class DAO {

    /**
     * sends an API call to swapi to retrieve the first planet in response
     * @return planet object (not a list of objects)
     */
    Planet getPlanet() {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://swapi.info/api/planets/1");
        String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
        ObjectMapper mapper = new ObjectMapper();
        Planet planet = null;

        try {

            planet = mapper.readValue(response, Planet.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Test");
            throw new RuntimeException(e);
        }

        return planet;
    }
}
