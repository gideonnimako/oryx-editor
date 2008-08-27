package de.hpi.bpmn2pn.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hpi.bpmn.Container;
import de.hpi.petrinet.Transition;

public class HighConversionContext extends ConversionContext {
	/* Maintain list of ancestor tasks which should perform exception handling/ 
	 * token cleansing
	 */
	public Map<Container, List<Transition>> excpTransitionMap = new HashMap();
	public List<Transition> getExcpTransitions() {
		// "flatten" all transitions from list
		List<Transition> trans = new ArrayList<Transition>();
		for(List<Transition> t : excpTransitionMap.values())
			trans.addAll(t);
		return trans;
	}
	public List<Transition> getExcpTransitions(Container c) {
		return excpTransitionMap.get(c);
	}
	
	public void addAncestorExcpTransition(Container c, Transition t){
		this.ancestorHasExcpH = true;
		if(!excpTransitionMap.containsKey(c))
			excpTransitionMap.put(c, new ArrayList<Transition>());
		excpTransitionMap.get(c).add(t);
	}
	
	/* Removes all transitions for a container*/
	public void removeAncestorExcpTransition(Container c){
		excpTransitionMap.remove(c);
		if(excpTransitionMap.size() == 0)
			this.ancestorHasExcpH = false;
	}
	
	/* Removes one transition for a container*/
	public void removeAncestorExcpTransition(Container c, Transition t){
		excpTransitionMap.get(c).remove(t);
		if(excpTransitionMap.get(c).size() == 0)
			removeAncestorExcpTransition(c);
	}
}
