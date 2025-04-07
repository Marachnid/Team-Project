package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public interface PropertiesLoader {

    /**
     * This default method will load a properties file into a Properties instance
     * and return it.
     * @param propertiesFilePath a path to a file on the java classpath list
     * @return a populated Properties instance or an empty Properties instance if
     * the file path was not found.
     */
    default Properties loadProperties(String propertiesFilePath){

        Properties properties = new Properties();

        try (InputStream inputStream =
                     this.getClass().getResourceAsStream(propertiesFilePath)) {

            //extra validation for incorrect/non-existent files returning as null instead of IO catching the error
            //InputStream inputStream = this.getClass().getResourceAsStream(propertiesFilePath);
            if (inputStream == null) {
                System.out.println("Error finding properties file: " + propertiesFilePath);

            } else {
                properties.load(this.getClass().getResourceAsStream(propertiesFilePath));
            }


        } catch (IOException ioException) {
            System.out.println("Database.loadProperties()...Cannot load the properties file");
            ioException.printStackTrace();

        } catch (Exception exception) {
            System.out.println("Database.loadProperties()..." + exception);
            exception.printStackTrace();
        }

        return properties;
    }
}