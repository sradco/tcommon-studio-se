package routines.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.XPath;
import org.dom4j.tree.AbstractNode;

public class DocumentToFlat {
	
	private org.dom4j.Document doc;
	
	private String currentLoop;
	
	private String originalLoop; 
	private String[] currentRelativePathMappings;
	private String[] absolutePathMappings;
	
	private Map<String,String> xmlNameSpaceMap;
	
	private boolean top = false;
	
	//check whether namespace define exist in UI
	private boolean defineNS = true;
	private NameSpaceTool namespaceTool;
	
	private List<AbstractNode> nodes;

	//result show
	private List<Map<String,String>> resultSet = new ArrayList<Map<String,String>>();
	
	public DocumentToFlat() {
		
	}

	/**
	 * Document to Flat
	 */
	public void flat() {
		XPath loopXpath = null;
		if(!defineNS) {
			loopXpath = doc.createXPath(namespaceTool.addDefaultNSPrefix(currentLoop, currentLoop));
		} else {
			loopXpath = doc.createXPath(currentLoop);
		}
		loopXpath.setNamespaceURIs(xmlNameSpaceMap);
		nodes = loopXpath.selectNodes(doc);
		if(nodes.size() == 0 && !top) {
			setParentAsLoop();
			flat();
		} else {
			//reset relative paths
			if(currentLoop != originalLoop) {//not point to the same string
				for(int i=0;i<currentRelativePathMappings.length;i++) {
					currentRelativePathMappings[i] = resetRelativeXPath(currentRelativePathMappings[i]);
				}
			}
			
			for(AbstractNode node : nodes) {
				//init row
				Map<String,String> row = new HashMap<String,String>();
				resultSet.add(row);
				//init columns for one row
				for(int i=0;i<currentRelativePathMappings.length;i++) {
					String relativePath = currentRelativePathMappings[i];
					XPath xpath = null;
					if(!defineNS) {
						xpath = node.createXPath(namespaceTool.addDefaultNSPrefix(relativePath, currentLoop));
					} else {
						xpath = node.createXPath(relativePath);
					}
					xpath.setNamespaceURIs(xmlNameSpaceMap);
					Object obj = xpath.evaluate(node);
					if(obj instanceof String || obj instanceof Number){
						row.put(absolutePathMappings[i], String.valueOf(obj));
			    	}else{
				    	row.put(absolutePathMappings[i], xpath.selectSingleNode(node)!=null ? xpath.valueOf(node) : null);
				    }
				}
			}
			doc = null;
			nodes = null;
		}
	}
	
	private String resetRelativeXPath(String relativePath) {
    	//get absolute path by original loop path
		String absolutePath = originalLoop;
    	for(String step : relativePath.split("/")) {
			if("..".equals(step)) {
				absolutePath = absolutePath.substring(0,absolutePath.lastIndexOf("/"));
			} else if(".".equals(step)){
				//do nothing
			} else if(!"".equals(step)){
				absolutePath += "/" + step;
			}
		}
    	//get relative path by new loop path
		if(absolutePath.equals(currentLoop)) {
			return ".";
		} else if(absolutePath.startsWith(currentLoop)){
			return absolutePath.substring(currentLoop.length() + 1);
		} else {
			StringBuilder relativeXPath = new StringBuilder();
			String tmp = currentLoop;
			
			while(!absolutePath.startsWith(tmp)){
				int index = tmp.lastIndexOf("/");
				if(index<0){ break; }
				tmp = tmp.substring(0,index);
				relativeXPath.append("../");
			}
			
			relativeXPath.append(absolutePath.substring(tmp.length() + 1));
			return relativeXPath.toString();
		}
    }
	
	private void setParentAsLoop() {
		int end = currentLoop.length();
		int idx = currentLoop.lastIndexOf('/');
		if(idx > 0) {
			end = idx;
		} else if(idx == 0) {//currentLoop is root
			top = true;
		}
		currentLoop =  currentLoop.substring(0, end);
	}

	public List<Map<String, String>> getResultSet() {
		return resultSet;
	}

	public void setDoc(org.dom4j.Document doc) {
		this.doc = doc;
	}

	public void setOriginalLoop(String originalLoop) {
		this.originalLoop = originalLoop;
		this.currentLoop = originalLoop;
	}

	public void setCurrentRelativePathMappings(String[] currentRelativePathMappings) {
		this.currentRelativePathMappings = currentRelativePathMappings;
	}

	public void setAbsolutePathMappings(String[] absolutePathMappings) {
		this.absolutePathMappings = absolutePathMappings;
	}

	public void setXmlNameSpaceMap(Map<String, String> xmlNameSpaceMap) {
		this.xmlNameSpaceMap = xmlNameSpaceMap;
	}

	public void setDefineNS(boolean defineNS) {
		this.defineNS = defineNS;
	}

	public void setNamespaceTool(NameSpaceTool namespaceTool) {
		this.namespaceTool = namespaceTool;
	}

	private Map<String, Object> lookupInfo;
	private Map<String, String> xpathOfResults;
	private Map<String, String> xpathToTypeMap;
	private Map<String, String> xpathToPatternMap;
	 
	public DocumentToFlat(Map<String, Object> lookupInfo,
            Map<String, String> xpathOfResults,
            Map<String, String> xpathToTypeMap,
            Map<String, String> xpathToPatternMap) {
		this.lookupInfo = lookupInfo;
		this.xpathOfResults = xpathOfResults;
		this.xpathToTypeMap = xpathToTypeMap;
		this.xpathToPatternMap = xpathToPatternMap;
	}
	
	public void flatForLookup() {
		XPath loopXpath = doc.createXPath(currentLoop);
		loopXpath.setNamespaceURIs(xmlNameSpaceMap);
		nodes = loopXpath.selectNodes(doc);
		if(nodes.size() == 0 && !top) {
			setParentAsLoop();
			flatForLookup();
		} else {
			if(currentLoop != originalLoop) {//not point to the same string
				reset();
			}
		}
	}
	
	private void reset() {
		resetMapRelativeXpathKey(lookupInfo);
    	resetMapRelativeXpathKey(xpathToTypeMap);
    	resetMapRelativeXpathKey(xpathToPatternMap);
    	resetMapRelativeXpathValue(xpathOfResults);
	}
	
	private void resetMapRelativeXpathKey(Map<String, ? extends Object> source) {
    	Map content = new HashMap();
    	for(String key : source.keySet()) {
    		String newKey = resetRelativeXPath(key);
    		content.put(newKey, source.get(key));
    	}
    	source.clear();
    	source.putAll(content);
    }
    
    private void resetMapRelativeXpathValue(Map<String,String> source) {
    	Map content = new HashMap();
    	for(String key : source.keySet()) {
    		String value = source.get(key);
    		String newValue = resetRelativeXPath(value);
    		content.put(key, newValue);
    	}
    	source.clear();
    	source.putAll(content);
    }
	
	public List<AbstractNode> getNodes() {
		return nodes;
	}
	
}
