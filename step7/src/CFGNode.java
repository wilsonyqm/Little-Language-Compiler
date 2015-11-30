import java.util.*;
public class CFGNode{
	 ArrayList<CFGNode> predecessor;
	 ArrayList<CFGNode> successor;
	 private IRNode irNode;
	 Set<String> liveInSet;
	 Set<String> liveOutSet;
	 Set<String> genSet;
	 Set<String> killSet;
	 private boolean isLead;
	
	public CFGNode(IRNode node){
		this.predecessor = new ArrayList<CFGNode>();
		this.successor = new ArrayList<CFGNode>();
		this.irNode = node;
		this.liveInSet = new HashSet<>();
		this.liveOutSet = new HashSet<>();
		this.genSet = new HashSet<>();
		this.killSet = new HashSet<>();
		this.isLead = false;
		
	}
	
	public void setLead(boolean var){
		this.isLead = var;
	}
	
	public Set<String> getLiveInSet(){
		return this.liveInSet;
	}
	
	public void setLiveInSet(Set<String> liveInSet){
		this.liveInSet = new HashSet<String>(liveInSet);
	}
	
	public void addLiveIn(String var){
		this.liveInSet.add(var);
	}
	
	public void addAllLiveIn(Set<String> vars){
		this.liveInSet.addAll(vars);
	}
	
	public void removeAllLiveIn(Set<String> vars){
		this.liveInSet.removeAll(vars);
	}
	
	public Set<String> getLiveOutSet(){
		return this.liveOutSet;
	}
	
	public void addLiveOut(String var){
		this.liveOutSet.add(var);
	}
	
	public void addAllLiveOut(Set<String> vars){
		this.liveOutSet.addAll(vars);
	}
	
	public Set<String> getGenSet(){
		return this.genSet;
	}
	
	public void addGenSet(String var){
		this.genSet.add(var);
	}
	
	public Set<String> getKillSet(){
		return this.killSet;
	}
	
	public void addKillSet(String var){
		this.killSet.add(var);
	}	
	
	public ArrayList<CFGNode> getPredecessor(){
		return this.predecessor;
	}
	
	public void addPredecessor(CFGNode node){
		this.predecessor.add(node);
	}
	
	public ArrayList<CFGNode> getSuccessor(){
		return this.successor;
	}
	
	public void addSuccessor(CFGNode node){
		this.successor.add(node);
	}
	
	public IRNode getIRNode(){
		return this.irNode;
	}
	
}