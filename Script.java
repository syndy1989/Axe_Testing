package docker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.WebDriver;

import com.nft.testing.Testing;

public class script{
	
	
	static Testing t=new Testing();
	static List<String> filelist =new ArrayList();
	static ArrayList<JSONArray> final10=new ArrayList<JSONArray>();
	static String result1=null;
	static JSONArray jsonresults=null;
	static ArrayList<String> pagename = new ArrayList<String>();
	//Parsing value= new Parsing();
	
	static final URL scriptUrl = script.class.getResource("axe.min.js");

	
	public static void main(String[] args) throws FileNotFoundException, IOException, JSONException, ParseException, InvalidRemoteException, TransportException, GitAPIException{
		
		 File file = new File("C:\\Users\\Sinduja_Docker\\docker_accessibility\\datafile.properties");
			FileInputStream fileInput = null;
			try {
				fileInput = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			Properties prop = new Properties();
			
			//load properties file
			try {
				prop.load(fileInput);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		
		System.setProperty("phantomjs.binary.path", "C:\\Users\\Sinduja_Docker\\docker_accessibility\\phantomjs.exe");        

		WebDriver driver = new PhantomJSDriver();
		
		driver.get(prop.getProperty("URL"));
		
		
		//calling method axe
		jsonresults=t.run_axe(driver, scriptUrl);
		
		//System.out.println("Result"+jsonresults);
		
		//calling substring for unique file name
		result1=t.Sub_String_url(driver.getCurrentUrl(), 1);
		//System.out.println("Result"+result1);
		
		
		//creating file with unique name
	String fileresult=	t.dynamic_filecreation(result1, jsonresults);
		filelist.add(fileresult);
		pagename.add("home");
		
		Parsing value=new Parsing();
		
		final10=value.parsingfile(filelist, pagename);
		
		//System.out.println("Resultfinal10"+final10);
		
	}
	
		
	
}

