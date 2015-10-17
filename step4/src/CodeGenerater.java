import java.util.*;

public class CodeGenerater{
	ArrayList<IRNode> iRNodes;
    ArrayList<TinyNode> tinyNodes;
    
    public CodeGenerater(){
    	iRNodes = new ArrayList<IRNode>();
    	tinyNodes = new ArrayList<TinyNode>();
    }
    
    public void addIRNode(IRNode node){
    	iRNodes.add(node);
    }
    
    public void addTinyNode(TinyNode node){
    	tinyNodes.add(node);
    }
    
    public void printIRNodes(){
    	System.out.println(";IR code");
    	for(int i=0; i<iRNodes.size();i++){
    		System.out.println(";"+iRNodes.get(i).toString());
    	}	
    }
    public void printTinyNodes(){
		//convertIRtoTiny(iRNodes, tinyNodes);
    	System.out.println(";Tiny code");
    	for(int i=0; i<tinyNodes.size();i++){
    		System.out.println(tinyNodes.get(i).toString());
    	}	
    }
	
}