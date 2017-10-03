package model;

import server.Data;

public class FixedDataQue {
		private final Data[] dataQue;
	public FixedDataQue(int length){
		dataQue = new Data[length]; 
		
		
	}
	public void addFirst(Data d){
		for(int i = dataQue.length; i > 0  ; i--){
			dataQue[i] = dataQue[i-1];
		}
		dataQue[0] = d;
	}
	public Data getFirst(){
		return dataQue[0];
	}
	public Data get(int index){
		return dataQue[index];
	}
}
