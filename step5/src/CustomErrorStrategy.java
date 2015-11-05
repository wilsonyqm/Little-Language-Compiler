import org.antlr.v4.runtime.*;
import java.io.*;
public class CustomErrorStrategy extends DefaultErrorStrategy{
					@Override
					public void recover(Parser recognizer, RecognitionException e){
							throw e;
					}
					
					@Override
		            public Token recoverInline(Parser recognizer){
			        	//return null;
			        	RecognitionException e = null;
			        	throw e;
			       	
		            }
		            
					@Override
					public void reportError(Parser recognizer, RecognitionException e){
						throw e;
					}	
					
}