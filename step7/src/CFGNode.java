import java.util.*;
public class CFGNode{
	private ArrayList<IRNode> predecessor;
	private ArrayList<IRNdoe> successor;
	private IRNode irNode;
	
	
	public CFGNode(IRNode node){
		this.predecessor = new ArrayList<IRNode>();
		this.successor = new ArrayList<IRNode>();
		this.irNode = node;
	}
	
	public void setLead(boolean var){
		this.getIRNode().setLead(var);
	}
	
	public ArrayList<IRNode> getPredecessor(){
		return this.predecessor;
	}
	
	public void addPredecessor(IRNode node){
		this.predecessor.add(node);
	}
	
	public ArrayList<IRNode> getSuccessor(){
		return this.successor;
	}
	
	public void addSuccessor(IRNode node){
		this.successor.add(node);
	}
	
	public IRNode getIRNode(){
		return this.irNode;
	}
	
	public HashSet<String> getLiveInSet(){
		return this.getIRNode().getLiveInSet();
	}
	
	public void addLiveIn(String var){
		this.getIRNode().addLiveIn(var);
	}
	
	public HashSet<String> getLiveOutSet(){
		return this.getIRNode().getLiveOutSet();
	}
	
	public void addLiveOut(String var){
		this.getIRNode().addLiveOut(var);
	}
	
	public HashSet<String> getGenSet(){
		return this.getIRNode().getGenSet();
	}
	
	public void addGenSet(String var){
		this.getIRNode().addGenSet(var);
	}
	
	public HashSet<String> getKillSet(){
		return this.getIRNode().getkillSet();
	}
	
	public void addKillSet(String var){
		this.getIRNode().addKillSet(var);
	}
	
}