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
				
					
			}	
					
	}	

  