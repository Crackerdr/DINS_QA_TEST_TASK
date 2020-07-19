package converter;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.logging.Logger;

public class Converter {
    final static Logger LOGGER = Logger.getLogger(Converter.class.getName());

    public static String toJSON(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        LOGGER.info("json created!");
        String requestJSON = mapper.writeValueAsString(object);
        LOGGER.info(requestJSON);
        return requestJSON;
    }
}
