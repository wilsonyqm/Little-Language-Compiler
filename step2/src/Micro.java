import org.antlr.v4.runtime.*;
import java.io.*;

public class Micro {
		public static void main(String[] args) throws Exception {

				File file = new File(args[0]);
				MicroLexer scanner = new MicroLexer((CharStream)(new ANTLRFileStream(file.getAbsolutePath())));
		
				CommonTokenStream tokens = new CommonTokenStream((TokenSource)scanner);
				
				MicroParser parser = new MicroParser(tokens);
			
				ANTLRErrorStrategy es = new CustomErrorStrategy();
				parser.setErrorHandler(es);
				
				try{	
					parser.program();
					System.out.println("Accepted");
				}catch(Exception e){
					System.out.println("Not accepted");
				}
				
			}	
			
				
		
	}	

   class CustomErrorStrategy extends DefaultErrorStrategy{
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