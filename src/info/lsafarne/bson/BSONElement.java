package info.lsafarne.bson;

import java.io.UnsupportedEncodingException;

import info.lsafarne.Utils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class BSONElement {

	public BSONElementType code;
	public String name;
	public Object value;

	public int getSize() {
		int result = -1;
		result = name.getBytes().length + 1 + 1;

		switch (code) {
		case DOUBLE:
			result += 8;
			break;
		case STRING:
			String temp = (String) value;
			result += temp.getBytes().length + 5;
			break;
		case DOCUMENT:
		case ARRAY_DOCUMENT:
			BSONDocument tmpDoc = (BSONDocument) value;
			result += tmpDoc.getSize();
			break;
		case OBJECT_ID:
			result += 12;
			break;
		case INT32:
			result += 4;
			break;
		case INT64:
		case TIMESTAMP:
		case UTC_DATETIME:
			result += 8;
			break;
		case NULL:
			result += 0;
			break;
		case BINARY:
			byte[] tmpBinary = (byte[]) value;
			result += tmpBinary.length + 4;
			break;
		case BOOLEAN:
			result += 1;
			break;

		}
		return result;
	}

	@Override
	public String toString() {

		String result = "";
		result = result + "\"" + name + "\"" + ":";
		switch (code) {
		case STRING:
			String temp = (String) value;
			result += "\"" + temp + "\"";
			break;
		case INT32:
			result += value.toString();
			break;
		case DOUBLE:
			result += String.format("%f", value);
			break;
		case BOOLEAN:
			result += value.toString();
			break;
		case DOCUMENT:
		case ARRAY_DOCUMENT:
			BSONDocument tempDoc = (BSONDocument) value;
			result += tempDoc.toString();
			break;

		case OBJECT_ID:
			result += "\"" + Utils.convertByteArrToHexStr((byte[]) value) + "\"";

			break;
		/*
		 * default: throw new NotImplementedException();
		 */
		}

		return result;

	}

	public byte[] convertToBinary() {
		byte[] result = new byte[getSize()];
		result[0] = (byte) code.value;
		int index = 1;
		byte[] nameByte = Utils.convertToBinary(name);
		int nameLen = nameByte.length;
		System.arraycopy(nameByte, 0, result, index, nameLen);
		index += nameLen;
		byte[] valueByte = getValueByte();
		System.arraycopy(valueByte, 0, result, index, valueByte.length);

		return result;
	}

	public byte[] getValueByte() {
		byte[] result = null;
		switch (code) {
		case DOUBLE:
			double tmpDouble = (double) value;
			result = Utils.convertToBinary(tmpDouble);
			break;
		case STRING:
			String tmpString = (String) value;
			byte[] literalValue = Utils.convertToBinary(tmpString);
			byte[] size = Utils.convertToBinary((int)literalValue.length);
			result = new byte[4+literalValue.length];
			System.arraycopy(size, 0, result, 0, size.length);
			System.arraycopy(literalValue, 0, result, 4, literalValue.length);
			break;
		case DOCUMENT:
		case ARRAY_DOCUMENT:
			BSONDocument tmpDoc = (BSONDocument) value;
			result = tmpDoc.convertToBinary();
			break;
		case OBJECT_ID:
			result = (byte[]) value;	
			break;
		case INT32:
			int tmpInt = (int) value;
			result = Utils.convertToBinary(tmpInt);
			break;
		case INT64:
		case TIMESTAMP:
		case UTC_DATETIME:
			result = Utils.convertToBinary((long) value);
			break;
		case NULL:
			result=null;
			break;
		case BINARY:
			int binSize = ((byte[]) value).length;
			byte[] binSizeArr = Utils.convertToBinary(binSize-1);
			result = new byte[binSize + 4];
			System.arraycopy(binSizeArr, 0, result, 0,  4);
			System.arraycopy((byte[]) value, 0, result, 4, binSize);
			break;
		case BOOLEAN:
			byte tmpBoolean=(byte) (((boolean) value)?1:0);
			result=new byte[1];
			result[0] = tmpBoolean;
			break;

		}

		return result;
	}

}
