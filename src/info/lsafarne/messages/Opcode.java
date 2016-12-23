package info.lsafarne.messages;

public enum Opcode {
	
	
	REPLY(1), 
	GENERIC_MSG(1000),
	UPDATE(2001),
	INSERT(2002),
	RESERVED(2003),
	QUERY(2004),
	GET_MORE(2005),
	DELETE(2006),
	KILL_CURSORS(2007),
	OP_COMMAND(2010),
	OP_COMMANDREPLY(2011),
	INVALID(0);
	
	int value;
	
	Opcode(int value){
		this.value=value;
	}
	
	public static Opcode parse(int num){
		Opcode result=null;
		switch(num)
		{
		case 1:
			result=Opcode.REPLY;
			break;
		case 1000:
			result=Opcode.GENERIC_MSG;
			break;
		case 2001:
			result=Opcode.UPDATE;
			break;
		case 2002:
			result=Opcode.INSERT;
			break;
		case 2003:
			result=Opcode.RESERVED;
			break;
		case 2004:
			result=Opcode.QUERY;
			break;
		case 2005:
			result=Opcode.GET_MORE;
			break;
		case 2006:
			result=Opcode.DELETE;
			break;
		case 2007:
			result=Opcode.KILL_CURSORS;
			break;
		case 2010:
			result=Opcode.OP_COMMAND;
			break;
		case 2011:
			result=Opcode.OP_COMMANDREPLY;
			break;
			
		default:
			result=Opcode.INVALID;
			
				
		}
		
		return result;
	}

}
