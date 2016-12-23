package info.lsafarne.bson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Arrays;

import info.lsafarne.Utils;
import sun.security.util.Length;

public class BSONDocument {

	private ArrayList<BSONElement> elements = new ArrayList<>();
	private HashMap<String,BSONElement> elementMap = new HashMap<>();

	public static BSONDocument parse(byte[] buffer) throws Exception {

		BSONDocument result = null;
		int docLength = 0;
		int index = 0;

		if (buffer.length >= 5) {

			docLength = Utils.convertToInt(buffer, index);// red
			index += 4;

			if (docLength == buffer.length && buffer[docLength - 1] == 0) {
				result = new BSONDocument();
				while (index < docLength - 1) {
					BSONElement element = new BSONElement();
					int codeIndex = index;
					element.code = BSONElementType.parse(buffer[codeIndex]);
					index += 1;

					element.name = Utils.convertToString(buffer, index);
					index = index + element.name.length() + 1;

					if (element.code == BSONElementType.DOUBLE) {
						element.value =Utils.convertToDouble( Arrays.copyOfRange(buffer, index, index + 8));
						index += 8;
					} else if (element.code == BSONElementType.STRING) {
						int len = Utils.convertToInt(buffer, index);
						index = index + 4;
						element.value = new String( Arrays.copyOfRange(buffer, index, index + len - 1),"UTF-8");
						index += len;
					} else if (element.code == BSONElementType.DOCUMENT
							|| element.code == BSONElementType.ARRAY_DOCUMENT) {
						int len = Utils.convertToInt(buffer, index);
						element.value = BSONDocument.parse(Arrays.copyOfRange(buffer, index, index + len ));
						index += len;

					}

					else if (element.code == BSONElementType.OBJECT_ID) {
						element.value = Arrays.copyOfRange(buffer, index, index + 12);
						index += 12;
					} else if (element.code == BSONElementType.INT32) {
						element.value = Utils.convertToInt(buffer, index);
						index += 4;
					} 
					else if (element.code == BSONElementType.INT64) {
						element.value = Utils.convertToLong(buffer, index);
						index += 8;
					}
					else if (element.code == BSONElementType.UTC_DATETIME) {
						element.value = Utils.convertToLong(buffer, index);
						index += 8;
					}
					else if (element.code == BSONElementType.TIMESTAMP) {
						element.value = Utils.convertToLong(buffer, index);
						index += 8;
					}else if (element.code == BSONElementType.NULL) {
						int i=0;
						i=4;
					} else if (element.code == BSONElementType.BINARY) {
						int binaryLen = Utils.convertToInt(buffer, index);
						index = index + 4;
						element.value = Arrays.copyOfRange(buffer, index, index + 1 + binaryLen);
						index = index + 1 + binaryLen;
					}
					else if (element.code == BSONElementType.BOOLEAN) {
						element.value = (buffer[index] == 0)? false:true;
						index = index + 1 ;
					}
					else if(element.code == BSONElementType.INVALID)
					{
						throw new Exception(buffer[codeIndex] + " is not a valid element code");
					}


					result.elements.add(element);
					result.elementMap.put(element.name, element);
				}

			}

		}

		return result;

	}

	public byte[] convertToBinary() {
		
		byte[] result = null;
		ArrayList<byte[]> elementArr = new ArrayList<>();
		int totalLength=5;
		byte[] tmp = null;
		for(int i=0;i<elements.size();i++){
			tmp=elements.get(i).convertToBinary();
			if(tmp!=null){
				totalLength+=tmp.length;
				elementArr.add(tmp);
			}
			
		}
		
/*		Iterator<Map.Entry<String, BSONElement>> iterator = elements.entrySet().iterator();
		while(iterator.hasNext()) {
			tmp = iterator.next().getValue().convertToBinary();
			if(tmp != null)
			{
				totalLength += tmp.length;
				elementArr.add(tmp);
			}
		}
*/		
		
		
		if(totalLength >0)
		{
			result = new byte[totalLength];
			int index = 0;
			byte[] len = Utils.convertToBinary(totalLength);
			System.arraycopy(len, 0, result, index, 4);
			index+=4;
			for (int i = 0; i < elementArr.size(); i++) {
				System.arraycopy(elementArr.get(i),0, result, index, elementArr.get(i).length);
				index += elementArr.get(i).length;
			}
			result[result.length -1]=0;
		}
		

		return result;
	}

	@Override
	public String toString() {
		String result="{";
		
		for(int i=0;i<elements.size();i++){
			result+=elements.get(i).toString()+",";
		}
		
		/*
		Iterator<Map.Entry<String, BSONElement>> iterator = elements.entrySet().iterator();
		while(iterator.hasNext()){
			result+=iterator.next().getValue().toString()+",";
		}
		*/
		if(!elements.isEmpty()){
			result=result.substring(0, result.length()-1);
		}

		result+="}";
		return result;
	}

	public int getSize(){
		int result=5; //document ::= int32 e_list "\x00"
		for(int i=0;i<elements.size();i++){
			result+=elements.get(i).getSize();
		}
/*		
		Iterator<Map.Entry<String, BSONElement>> iterator = elements.entrySet().iterator();
		BSONElement tmp;
		while(iterator.hasNext()){
			tmp=iterator.next().getValue();
			result+=tmp.getSize();
		}
*/		
		return result;
	}
	public void insertElement(BSONElement element){
		elementMap.put(element.name, element);
		elements.add(element);
		
	}
	public HashMap<String,BSONElement> getElementMap(){
		return elementMap;
	}
	public ArrayList<BSONElement> getElements(){
		return elements;
	}
}
