package insightjournal.database;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class DatabaseUtil {

    public static File getJarDir() throws URISyntaxException {
        // Get the location of the JAR file
        return new File(DatabaseUtil.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();
    }

    public static File copyDatabaseFromResources(String resourcePath, String destinationPath) throws IOException {
        InputStream inputStream = DatabaseUtil.class.getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new IOException("Database file not found in resources: " + resourcePath);
        }

        File destinationFile = new File(destinationPath);
        if (!destinationFile.exists()) {
            Files.copy(inputStream, destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        inputStream.close();

        return destinationFile;
    }
}
