package info.lsafarne.bson;

public class BSONObjectId {
	byte [] objId;
	public BSONObjectId(byte[] objId){
		this.objId=objId;
	}
	@Override
	public String toString() {
	
		String result="";
		for (int i=0;i<objId.length;i++){

			result+=String.format("%x", objId[i]);
		}
		return result;
	}


}
