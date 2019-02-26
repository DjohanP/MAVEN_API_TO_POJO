package interfaces;

import java.net.URL;
import java.util.List;

import org.json.simple.JSONObject;


public interface Interface_api {
	List<Interface_model> search(String query);
	List<Interface_model> convertToModel(JSONObject results);
	String requestGet(URL url);
}