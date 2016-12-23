package info.lsafarne.bson;

import info.lsafarne.messages.Opcode;

public enum BSONElementType {
	
	DOUBLE(1),
	STRING(2),
	DOCUMENT(3),
	ARRAY_DOCUMENT(4),
	BINARY(5),
	UNDEFINED(6),
	OBJECT_ID(7),
	BOOLEAN(8),
	UTC_DATETIME(9),
	NULL(10),
	REGULAR_EXPRESSION(11),
	DB_POINTER(12),
	JS_CODE(13),
	SYMBOL(14),
	JS_CODE_SCOPE(15),
	INT32(16),
	TIMESTAMP(17),
	INT64(18),
	DECIMAL128(19),
	MIN_KEY(20),
	MAX_KEY(21),
	INVALID(0);
	
	int value;

	BSONElementType(int value){
		this.value=value;
	}
	
	
	public static BSONElementType parse(int num){
		BSONElementType result=null;
		switch(num)
		{
		case 1:
			result=BSONElementType.DOUBLE;
			break;
		case 2:
			result=BSONElementType.STRING;
			break;
		case 3:
			result=BSONElementType.DOCUMENT;
			break;
		case 4:
			result=BSONElementType.ARRAY_DOCUMENT;
			break;
		case 5:
			result=BSONElementType.BINARY;
			break;
		case 6:
			result=BSONElementType.UNDEFINED;
			break;
		case 7:
			result=BSONElementType.OBJECT_ID;
			break;
		case 8:
			result=BSONElementType.BOOLEAN;
			break;
		case 9:
			result=BSONElementType.UTC_DATETIME;
			break;
		case 10:
			result=BSONElementType.NULL;
			break;
		case 11:
			result=BSONElementType.REGULAR_EXPRESSION;
			break;
		case 12:
			result=BSONElementType.DB_POINTER;
			break;
		case 13:
			result=BSONElementType.JS_CODE;
			break;
		case 14:
			result=BSONElementType.SYMBOL;
			break;
		case 15:
			result=BSONElementType.JS_CODE_SCOPE;
			break;
		case 16:
			result=BSONElementType.INT32;
			break;
		case 17:
			result=BSONElementType.TIMESTAMP;
			break;
		case 18:
			result=BSONElementType.INT64;
			break;
		case 19:
			result=BSONElementType.DECIMAL128;
			break;
		case 20:
			result=BSONElementType.MIN_KEY;
			break;
		case 21:
			result=BSONElementType.MAX_KEY;
			break;
			
		default:
			result=BSONElementType.INVALID;
			
				
		}
		
		return result;
	}
	
	
	

}
