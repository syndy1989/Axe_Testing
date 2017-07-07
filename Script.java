

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.json.JSONArray;
import org.json.JSONException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import com.nft.parsing_docker.Parsing;
import com.nft.testing.Testing;



public class Docker_Accessibility{
	
	
	static Testing t=new Testing();
	static List<String> filelist =new ArrayList();
	static ArrayList<JSONArray> final10=new ArrayList<JSONArray>();
	static String result1=null;
	static JSONArray jsonresults=null;
	static ArrayList<String> pagename = new ArrayList<String>();
	//Parsing value= new Parsing();
	
	static final URL scriptUrl = Docker_Accessibility.class.getResource("axe.min.js");

	
	public static void main(String[] args) throws FileNotFoundException, IOException, JSONException, ParseException, InvalidRemoteException, TransportException, GitAPIException, org.json.simple.parser.ParseException{
		
		 File file = new File("C:\\docker_accessibility\\datafile.properties");
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
			
			
		
		System.setProperty("phantomjs.binary.path", "C:\\docker_accessibility\\phantomjs.exe");        

		WebDriver driver = new PhantomJSDriver();
		
		driver.get(prop.getProperty("URL"));
		
		
		//calling method axe
		jsonresults=t.run_axe(driver, scriptUrl);
		
		
		//calling substring for unique file name
		result1=t.Sub_String_url(driver.getCurrentUrl(), 1);
		
		
		
		//creating file with unique name
	String fileresult=	t.dynamic_filecreation(result1, jsonresults);
		filelist.add(fileresult);
		pagename.add("home");
		
		Parsing value=new Parsing();
		
		final10=value.parsingfile(filelist, pagename);
		
	
		
	}
	
		
	
}

