package Model;

import java.util.HashMap;

public class Fan {
	private HashMap<String,Integer> information = new HashMap<String,Integer>();
	public void setInfo(String s, Integer n){
		information.put(s,n);
	}
	public HashMap<String,Integer> getInfo(){
		return information;
	}
	public void upadeInfo(String infoKey,Integer infoValue){
		information.put(infoKey, infoValue);
	}
	public int getInfo(String infoKey){
		return information.get(infoKey);
	}
}
