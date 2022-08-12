package io.yousefessa.applicationupdatersample;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationUpdaterSample {
    private static final String DEVELOPMENT_HELLO_WORLD_FORMAT = "Hello World from %s version!";
    private static final String HELLO_WORLD_FORMAT = "Hello World from v%s!";
    private static final String VERSION_PROPERTY = "version";
    private static final String VERSION_PROPERTY_DEFAULT = "development";
    private static final String APP_PROPERTIES_DESTINATION = "version.properties";
    public static final String VERSION_TOKEN_PROPERTY = "${version}";

    public static void main(String[] args) {
        InputStream inputStream =
                ApplicationUpdaterSample.class.getResourceAsStream(APP_PROPERTIES_DESTINATION);
        try {
            if (inputStream == null) {
                inputStream = new FileInputStream("./src/main/resources/version.properties");
            }

            final Properties properties = new Properties();
            properties.load(inputStream);

            boolean development = false;
            String version = properties.getProperty(VERSION_PROPERTY, VERSION_PROPERTY_DEFAULT);
            if (version.equals(VERSION_TOKEN_PROPERTY)) {
                development = true;
                version = VERSION_PROPERTY_DEFAULT;
            }

            final String message;
            if (development) {
                message = String.format(DEVELOPMENT_HELLO_WORLD_FORMAT, version);
            } else {
                message = String.format(HELLO_WORLD_FORMAT, version);
            }

            System.out.println(message);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } finally {
            if (inputStream == null) {
                System.out.println("Nothing to print out!");
                return;
            }

            try {
                inputStream.close();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
