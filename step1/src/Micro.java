import org.antlr.runtime.*;
import java.io.*;

public class Main {
		public static void main(String[] args) throws Exception {

				String source = "hahahha,hahaha";
				Micro scanner = new Micro(new ANTLRStringStream(source));
				CommonTokenStream tokens = new CommonTokenStream(scanner);

				for(Object o: tokens.getTokens()){
					CommonToken token = (CommonToken)o;
					System.out.println("Token Type: "+ token.getType());
					System.out.println("Value: "+ token.getText());
				}
		}
}