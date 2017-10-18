package server;
/**
 * 
 * @author nicla
 *	contains a log of the 10 last data objects sent to the server to save memmory and provide a small backlog of data
 */
public class FixedDataQueue {
		private final Data[] dataQue;
	public FixedDataQueue(int length){
		dataQue = new Data[length]; 
		
		
	}
	/**
	 * adds a data objects to the first index and removing the last by moving all objects +1 
	 * @param d Data objects to add
	 */
	public void addFirst(Data d){
		for(int i = dataQue.length-1; i > 0  ; i--){
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
