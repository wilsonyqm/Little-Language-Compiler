import org.antlr.v4.runtime.*;
import java.io.*;

public class Micro {
		public static void main(String[] args) throws Exception {

				File file = new File(args[0]);
				Microlexer scanner = new Microlexer((CharStream)(new ANTLRFileStream(file.getAbsolutePath())));
		
				CommonTokenStream tokens = new CommonTokenStream((TokenSource)scanner);
				tokens.fill();
				for(Object o: tokens.getTokens()){
					CommonToken token = (CommonToken)o;

					switch(token.getType()){
						case 1:
						System.out.println("Token Type: "+ "KEYWORD");
						System.out.println("Value: "+ token.getText());
						break;
						case 2:
						System.out.println("Token Type: "+ "IDENTIFIER");
						System.out.println("Value: "+ token.getText());
						break;
						case 3:
						System.out.println("Token Type: "+ "INTLITERAL");
						System.out.println("Value: "+ token.getText());
						break;
						case 4:
						System.out.println("Token Type: "+ "FLOATLITERAL");
						System.out.println("Value: "+ token.getText());
						break;
						case 5:
						System.out.println("Token Type: "+ "STRINGLITERAL");
						System.out.println("Value: "+ token.getText());
						break;
						case 6:
						System.out.println("Token Type: "+ "COMMENT");
						System.out.println("Value: "+ token.getText());
						break;
						case 7:
						System.out.println("Token Type: "+ "OPERATOR");
						System.out.println("Value: "+ token.getText());
						break;
						default:
						break;

					}
					
					
				}
				
		
	}	
}