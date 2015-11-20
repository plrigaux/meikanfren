package com.plr;

import java.util.Map;

import groovy.util.slurpersupport.NodeChild;

public interface CleanerInt {
	void clean(NodeChild subNode, TransType type);
	
	String generateOutPut(NodeChild node, Map <String, Object> params);
}
