package kr.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class HandlerMapping {
	private Map<String, Controller> mappings;
	
	public HandlerMapping(String proploLocation) {
		mappings = new HashMap<>();
		
		Properties prop = new Properties();
		try {
			InputStream fis = new FileInputStream(proploLocation);
			prop.load(fis);
			
			 Set<Object> keys =prop.keySet();
			 for(Object key : keys) {
				 String class_name = prop.getProperty(key.toString());
				 mappings.put(key.toString(), (Controller)Class.forName(class_name).newInstance());
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		mappings.put("/board/list_post.do", new ListPost_Controller());
//		mappings.put("/board/add_post.do", new AddPost_Controller());
//		mappings.put("/board/add_post_form.do", new AddPostForm_Controller());
	}
	
	public Controller get_Controller(String uri) {
		return mappings.get(uri);		
	}

}
