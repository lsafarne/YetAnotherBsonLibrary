package net.safarnejad;

import net.safarnejad.bson.BSONDocument;
import net.safarnejad.bson.BSONElement;
import net.safarnejad.bson.BSONElementType;

public class Program {
	public static void main(String[] args) {
		try {

			//create a new bson document
			BSONDocument doc = new BSONDocument();
			System.out.println(doc.toString()); // empty document

			// add a new element to the document
			BSONElement elem = new BSONElement();
			elem.name = "Name";
			elem.code = BSONElementType.STRING;
			elem.value = "John";
			doc.insertElement(elem);
			
			// add another element to the document
			elem = new BSONElement();
			elem.name = "Age";
			elem.code = BSONElementType.INT32;
			elem.value = 12;
			doc.insertElement(elem);

			System.out.println(doc.toString());

			// serialization (to BSON format)
			byte[] bson = doc.convertToBinary(); 

			// deserialization (BSON data to BSONDocument object)
			BSONDocument another = BSONDocument.parse(bson); 
			
			System.out.println(another.toString());

			
		} catch (Exception exp) {
			System.out.println(exp.getMessage());
		}

	}
}
