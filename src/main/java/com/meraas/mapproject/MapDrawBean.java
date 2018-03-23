package com.meraas.mapproject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.json.JSONObject;
import org.primefaces.context.RequestContext;

import com.opencsv.CSVReader;


/**
 * @author Rohit
 *
 */

@ManagedBean(name = "mapDrawBean", eager = true)
@RequestScoped
public class MapDrawBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String CSV_FILE_PATH=  "C:\\Users\\Administrator\\Desktop\\plots.csv";  //CSV file Path
	private static final String OUTPUT_JSON_FILE_PATH="C://Tomcat7//webapps//plotmap//resources//json//output.json";
	private static final String SVG_JS_FILE_PATH="C://Tomcat7//webapps//plotmap//resources//js//jquery-jvectormap-svg-path.js";
	private String pathId;
	private String btnStatus;

	// no arg public constructor
	public MapDrawBean() {
		onWindowLoad();
	}
	
    
	
	public String getPathId() {
		return pathId;
	}



	public void setPathId(String pathId) {
		this.pathId = pathId;
	}

    

	public String getBtnStatus() {
		return btnStatus;
	}



	public void setBtnStatus(String btnStatus) {
		this.btnStatus = btnStatus;
	}


	public void showStatusMessage(){
		RequestContext.getCurrentInstance().update("growl");
   	    FacesContext context = FacesContext.getCurrentInstance(); 
	    context.addMessage(null, new FacesMessage("This Plot has been "+btnStatus,""));
	}
	
	
	/**
	 * Converts JSONObject into a string
	 * @return String
	 */
	public static String getJSONObjectStr(){
		InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/json/output.json"); 
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			br = new BufferedReader(new InputStreamReader(stream));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return sb.toString();
	}
	
	public void onWindowLoad(){
		
		JSONObject jsonObject = new JSONObject(getJSONObjectStr());
        CSVReader reader = null;
		
		try {
            reader = new CSVReader(new FileReader(CSV_FILE_PATH));
            String[] line;
            boolean flag=true;
            while ((line = reader.readNext()) != null) {
            	if(flag==true){
            		flag=false;
            		continue;
            	}
            	String json_key=line[0];
            	JSONObject obj= (JSONObject) jsonObject.get(json_key);
                String out="Id: "+line[1]+"\n"+"Status: "+line[2]+"\n"+"Address: "+line[3]+"\n"+"Info: "+line[4];
                obj.put("name",out); 
            }
            
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
        	
        	try {
				writeJSONToFile(jsonObject.toString(),OUTPUT_JSON_FILE_PATH);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } 
		
	}
	

	public void onBuyPlot(ActionEvent ae) throws MalformedURLException {
		
		RequestContext.getCurrentInstance().update("growl");
   	    FacesContext context = FacesContext.getCurrentInstance(); 
	    context.addMessage(null, new FacesMessage("Congrats, Transaction Completed!",""));
		
		/*InputStream stream = FacesContext.getCurrentInstance().getExternalContext()
				.getResourceAsStream("/resources/json/output.json");

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {

			br = new BufferedReader(new InputStreamReader(stream));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		JSONObject obj = new JSONObject(sb.toString());
		JSONObject pathObj = obj.getJSONObject(pathId);
		
		String result = pathObj.getString("name");
		String[] val = result.split("\n");
		
		if(btnStatus.equals("buy") || btnStatus =="buy"){
			val[1]="Status: SOLD";
		}else{
			val[1]="Status: ON HOLD";
		}
		
		String out="";
		for(int j=0;j<val.length;j++){
			if(j==val.length-1){
				out=out+val[j];
				continue;
			}
			out=out+val[j]+"\n";
		}
		
		
		//update to SOLD
		pathObj.put("name",out);
		writeJSONToFile(obj.toString());*/
	    
	    
	    if(btnStatus.equals("buy") || btnStatus =="buy"){
	    	updatePlotStatusInCSV("SOLD",pathId);
		}else{
			updatePlotStatusInCSV("ON HOLD",pathId);
		}
	    
		return;
	}
	
	
	public static void updatePlotStatusInCSV(String status,String key){
		StringBuffer str=new StringBuffer();
        CSVReader reader = null;
		
		try {
            reader = new CSVReader(new FileReader(CSV_FILE_PATH));
            String[] line;
            boolean flag=true;
            while ((line = reader.readNext()) != null) {
				if(flag){
					str.append(line[0] + "," + line[1] + "," + line[2] + "," + line[3]+ "," + line[4]+ "\n");
					flag=false;
					continue;
				}
				String json_key = line[0];
				if (json_key.equals(key) || json_key==key) {
					str.append(line[0] + "," + line[1] + "," + status + "," + "\"" + line[3] + "\"" + "," + line[4]+ "\n");
				} else {
					str.append(line[0] + "," + line[1] + "," + line[2] + "," + "\"" + line[3] + "\"" + "," + line[4]+ "\n");
				}
			}
            
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
        	try {
				writeCSVToFile(str.toString(),CSV_FILE_PATH);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		
	}
	
	
	public static void writeJSONToFile(String res,final String file) throws MalformedURLException {
	
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			bw.write(res);
			System.out.println("JSON created!");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
				
				updatePlotSatusInJS(res);
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} 
	}
	
	public static void writeCSVToFile(String res,final String file) throws MalformedURLException {
		
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			bw.write(res);
			System.out.println("CSV created!");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
				
				//onWindowLoad();
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} 
	}
	
	public static void updatePlotSatusInJS(String res){
		
        String jvector_start="jQuery.fn.vectorMap('addMap','map_drawing', {\"insets\": [{\"width\": 900.0,\"top\": 0,\"height\": 800.0,\"left\": 0}],\"paths\":";
		String jvector_end =",\"height\": 800,\"width\": 900})";
		
		String output=jvector_start+res+jvector_end;
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter(SVG_JS_FILE_PATH);
			bw = new BufferedWriter(fw);
			bw.write(output);
			System.out.println("JS created!");
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
		}
		
	}

	
}
