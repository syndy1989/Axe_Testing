
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class Parsing{

	private static final String IndexName1 = null;
	ArrayList<JSONArray> json = new ArrayList<JSONArray>();
	JSONArray json1 = new JSONArray();
	String help = null;
	String RunId = "1";
	String description = null;
	String id = null;
	String helpUrl = null;
	String html = null;
	String impact = null;
	JSONArray nodes2 = null;
	String message = null;
	String TransportClient=null;
	String project_name = null;
	String run_id = null;
	
	JSONArray obj5;
	JSONArray obj6;
	List<String> obj7;

	JSONArray newarray;
	JSONObject object1;

	// trying

	JSONObject counterobject1 = new JSONObject();
	JSONArray counterarray1 = new JSONArray();
	JSONObject mainObject = new JSONObject();
	JSONObject runID = new JSONObject();
	JSONArray counterarray = new JSONArray();
	

	 
	
	
	public ArrayList<org.json.JSONArray> parsingfile(List<String> filelist, ArrayList<String> pagename)
			throws FileNotFoundException, IOException, ParseException  {
		

		for (int m = 1; m <= filelist.size(); m++) {

			object1 = new JSONObject();
			String filename = filelist.get(m - 1);
			String pagenames = pagename.get(m - 1);
			System.out.println("filelist.size():"+filename);
			newarray = new JSONArray();
			
			

			JSONObject countobject = new JSONObject();
			JSONParser parser = new JSONParser();
			JSONArray obj = (JSONArray) parser.parse(new FileReader(filename)); // the
																				// location
																				// of
																				// the
																				// file
			
			//System.out.println("json result :" + obj);
			JSONArray array1 = (JSONArray) obj;
			// System.out.println("Helpval :"+array1.size());
			int counter = 0;
			int counter1 = 0;
			int counter2 = 0;
			int counter3 = 0;
			for (int i = 0; i < array1.size(); i++) {

				JSONObject obj1 = (JSONObject) array1.get(i);
				help = (String) obj1.get("help");
				description = (String) obj1.get("description");
				id = (String) obj1.get("id");
				helpUrl = (String) obj1.get("helpUrl");

				JSONArray nodes = (JSONArray) obj1.get("nodes");
				// System.out.println("nodes :"+nodes.size());
				
				obj5 = new JSONArray();
				obj6 = new JSONArray();
				obj7 = new ArrayList<String>();

				for (int j = 1; j <= nodes.size(); j++) {
					JSONObject obj2 = (JSONObject) nodes.get(j - 1);
					// System.out.println("nodes :"+nodes);
					html = (String) obj2.get("html");
					obj6.add(html);
					impact = (String) obj2.get("impact");

					nodes2 = (JSONArray) obj2.get("target");
					String obj223 = (String) nodes2.get(0);
					// System.out.println("target :"+nodes2);
					obj5.add(obj223);

					JSONArray nodesnone = (JSONArray) obj2.get("none");
					for (int l = 1; l <= nodesnone.size(); l++) {
						// System.out.println("nodesize 1:"+nodes.size());
						JSONObject obj22 = (JSONObject) nodesnone.get(l - 1);
						message = (String) obj22.get("message");
						// System.out.println("any1 :"+message);
						if (message != null) {
							obj7.add(message);

						}
					}

					JSONArray nodes11 = (JSONArray) obj2.get("any");

					// System.out.println("nodesize :"+nodes.size());
					for (int k = 1; k <= nodes11.size(); k++) {
						// System.out.println("nodesize 1:"+nodes.size());
						JSONObject obj22 = (JSONObject) nodes11.get(k - 1);
						message = (String) obj22.get("message");
						// System.out.println("any1 :"+message);
						if (message != null) {
							obj7.add(message);
						}

						// System.out.println("messagecoming :"+obj4);
					}
					// JSONArray target = (JSONArray)obj1.get("target");
				}

				// System.out.println("countervalue :"+counter);
				JSONArray tags = (JSONArray) obj1.get("tags");
				// System.out.println("Guidelines :"+tags);

				if (impact.equalsIgnoreCase("critical")) {
					counter++;

				} else if (impact.equalsIgnoreCase("serious")) {
					counter1++;
				} else if (impact.equalsIgnoreCase("moderate")) {
					counter2++;
				} else if (impact.equalsIgnoreCase("minor")) {
					counter3++;
				}
				// System.out.println("Formatted JSON counter:" +counter);
				// System.out.println("Formatted JSON counter1:" +counter1);
				// System.out.println("Formatted JSON counter2:" +counter2);

				JSONObject object = new JSONObject();

				object.put("Help", help);
				object.put("description", description);
				object.put("id", id);
				object.put("helpUrl", helpUrl);
				object.put("message", obj7);
				object.put("html", obj6);
				object.put("impact", impact);
				object.put("Guidelines", tags);
				object.put("Target", obj5);
				object1.put("pagename", pagenames);
				newarray.add(object);
				//newarray.add(object1);
				
				
			}
			countobject.put("pagename", pagenames);
			countobject.put("critical", counter);
			countobject.put("serious", counter1);
			countobject.put("moderate", counter2);
			countobject.put("minor", counter3);
			counterarray.add(countobject);

			newarray.add(object1);
			
			json.add(newarray);
			//json1.add(counterarray);

		}
                
		
		System.out.println("Violation Result:" + json);
            
		String violationsfile = ".\\..\\violations.json";
		File violations = new File(violationsfile);

		if (violations.exists()) {
			System.out.println("Violations.json File is created");
		}
		FileWriter writerviolationsfile = new FileWriter(violations);
		writerviolationsfile.write(json.toString());
		writerviolationsfile.flush();
		writerviolationsfile.close();
            
		    mainObject.put("rundetails", counterarray);
			mainObject.put("runid",1);
			
			json1.add(mainObject);
		
			System.out.println("Severity Count Result:" + json1);
			//return json1;
			return counterarray;
	

	}
}
