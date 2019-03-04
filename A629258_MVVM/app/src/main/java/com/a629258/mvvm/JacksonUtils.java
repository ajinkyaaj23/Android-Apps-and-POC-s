package com.a629258.mvvm;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author a186748
 * 
 */
public class JacksonUtils {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * @return
     */
    public static ObjectMapper getObjectMapperInstance() {
    	objectMapper.setSerializationInclusion(Include.NON_NULL);
    	objectMapper.setSerializationInclusion(Include.NON_NULL);
    	return objectMapper;
    }

    /**
     * Convert object to json string
     * 
     * @param object
     * @return
     * @throws JsonProcessingException
     *             the JsonProcessingException
     */
    public static String convertObjectToJSONString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    /**
     * Convert a JSON string to an object
     * 
     * @param json
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static Object fromJsonToJava(String json, @SuppressWarnings("rawtypes") Class type) throws JsonParseException, JsonMappingException,
            IOException {
        return objectMapper.readValue(json, type);
    }
    
    /**
     * Class used to format specific date string sent by BFS. 
     * E.g. JSON String - \"DOB\":\"\\\/Date(562444200000)\\\/\"
     * @author a551481
     */
/*    public class DeserializeBFSDateString extends JsonDeserializer<Long> {

    	public DeserializeBFSDateString() {
			// TODO Auto-generated constructor stub
		}
    	
		@Override
		public Long deserialize(JsonParser parser, DeserializationContext context)
				throws IOException, JsonProcessingException {
			
			String JSONDateToMilliseconds = "\\/(Date\\((.*?)(\\+.*)?\\))\\/";
	        Pattern pattern = Pattern.compile(JSONDateToMilliseconds);
	        Matcher matcher = pattern.matcher(parser.getText());
	        String result = matcher.replaceAll("$2"); 
	        return Long.valueOf(result);
		}

    	
    }*/

}