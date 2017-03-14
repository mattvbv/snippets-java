package com.mattvbv.snippets.java.multiline_string_extraction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class NodeDescription implements Serializable {

	private int cpuCapacity;
	private String memoryCapacityAsString;
	private ValueAndUnit<Integer> memoryCapacity;
	private int podsCapacity;
	
	private String allocatedCpuRequestsAsString;
	private ValueAndUnit<Integer> allocatedCpuRequests;
	
	private String allocatedCpuLimitsAsString;
	private ValueAndUnit<Integer> allocatedCpuLimits;
	
	private String allocatedMemoryRequestsAsString;
	private ValueAndUnit<Integer> allocatedMemoryRequests;
	
	private String allocatedMemoryLimitsAsString;
	private ValueAndUnit<Integer> allocatedMemoryLimits;
	
	private static final String CAPACITY = "Capacity:";
	private static final String ALLOCATED_RESOURCES = "Allocated resources:";
	
	private static final String NAME_VALUE_SEPARATOR_CHAR = ":";

	private static final long serialVersionUID = 1L;
	
	
	public NodeDescription(String descriptionAsString) {
		map(descriptionAsString);
	}
	
	private void map(String descriptionAsString) {
		BufferedReader reader = new BufferedReader(new StringReader(descriptionAsString));
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				processLine(line, reader);
			}
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		} finally {
			IOUtils.closeQuietly(reader);	
		}
	}
	
	private void processLine(String line, BufferedReader reader) throws IOException {
		if (line.startsWith(CAPACITY)) {
			// a better approach would be to take lines between to "headers" (ex : between "Capacity" and "System Info"
			// instead of line counting
			processCapacityLines(readNextLines(reader, 3));
		} else if (line.startsWith(ALLOCATED_RESOURCES)) {
			processAllocatedResourcesLines(readNextLines(reader, 4));
		}
	}
	
	private void processCapacityLines(List<String> lines) {
		for (String line : lines) {
			if (line.startsWith("cpu:")) {
				this.cpuCapacity = Integer.valueOf(extractValueFromNameValueLine(line, NAME_VALUE_SEPARATOR_CHAR));
			} else if (line.startsWith("memory:")) {
				setMemoryCapacityAsString(extractValueFromNameValueLine(line, NAME_VALUE_SEPARATOR_CHAR));
			} else if (line.startsWith("pods:")) {
				this.podsCapacity = Integer.valueOf(extractValueFromNameValueLine(line, NAME_VALUE_SEPARATOR_CHAR));
			} 
		}
	}
	private void processAllocatedResourcesLines(List<String> lines) {
		for (String line : lines) {
			if (Character.isDigit(line.charAt(0))) {
				// split by white space or tab
				String[] values = line.split("[ \t]");
				setAllocatedCpuRequestsAsString(values[0]);
				setAllocatedCpuLimitsAsString(values[2]);
				setAllocatedMemoryRequestsAsString(values[4]);
				setAllocatedMemoryLimitsAsString(values[6]);
			}  
		}		
	}
	
	private List<String> readNextLines(BufferedReader reader, int numberOfLines) throws IOException {
		List<String> lines = new ArrayList<String>();
		for (int i=0; i<numberOfLines; i++) {
			lines.add(reader.readLine().trim());
		}
		return lines;
	}
	
	public static String extractValueFromNameValueLine(String nameValueLine, String separatorChar) {
		return nameValueLine.split(separatorChar)[1].trim();
	}
	
	public static String[] separateValueFromUnit(String valueAndUnit) {
		return valueAndUnit.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
	}

	public int getCpuCapacity() {
		return cpuCapacity;
	}
	
	public ValueAndUnit<Integer> getMemoryCapacity() {
		return memoryCapacity;
	}

	public String getMemoryCapacityAsString() {
		return memoryCapacityAsString;
	}
	
	private void setMemoryCapacityAsString(String memoryCapacityAsString) {
		this.memoryCapacityAsString = memoryCapacityAsString;
		String[] valueAndUnit = separateValueFromUnit(this.memoryCapacityAsString);
		this.memoryCapacity = new ValueAndUnit<Integer>(Integer.valueOf(valueAndUnit[0]), valueAndUnit[1]);
	}

	public int getPodsCapacity() {
		return podsCapacity;
	}
	
	public ValueAndUnit<Integer> getAllocatedCpuRequests() {
		return allocatedCpuRequests;
	}

	public String getAllocatedCpuRequestsAsString() {
		return allocatedCpuRequestsAsString;
	}

	private void setAllocatedCpuRequestsAsString(String allocatedCpuRequestsAsString) {
		this.allocatedCpuRequestsAsString = allocatedCpuRequestsAsString;
		String[] valueAndUnit = separateValueFromUnit(this.allocatedCpuRequestsAsString);
		this.allocatedCpuRequests = new ValueAndUnit<Integer>(Integer.valueOf(valueAndUnit[0]), valueAndUnit[1]);
	}
	
	public ValueAndUnit<Integer> getAllocatedCpuLimits() {
		return allocatedCpuLimits;
	}

	public String getAllocatedCpuLimitsAsString() {
		return allocatedCpuLimitsAsString;
	}

	private void setAllocatedCpuLimitsAsString(String allocatedCpuLimitsAsString) {
		this.allocatedCpuLimitsAsString = allocatedCpuLimitsAsString;
		String[] valueAndUnit = separateValueFromUnit(this.allocatedCpuLimitsAsString);
		this.allocatedCpuLimits = new ValueAndUnit<Integer>(Integer.valueOf(valueAndUnit[0]), valueAndUnit[1]);
	}
	
	public ValueAndUnit<Integer> getAllocatedMemoryRequests() {
		return allocatedMemoryRequests;
	}

	public String getAllocatedMemoryRequestsAsString() {
		return allocatedMemoryRequestsAsString;
	}

	private void setAllocatedMemoryRequestsAsString(String allocatedMemoryRequestsAsString) {
		this.allocatedMemoryRequestsAsString = allocatedMemoryRequestsAsString;
		String[] valueAndUnit = separateValueFromUnit(this.allocatedMemoryRequestsAsString);
		this.allocatedMemoryRequests = new ValueAndUnit<Integer>(Integer.valueOf(valueAndUnit[0]), valueAndUnit[1]);
	}
	
	public ValueAndUnit<Integer> getAllocatedMemoryLimits() {
		return allocatedMemoryLimits;
	}

	public String getAllocatedMemoryLimitsAsString() {
		return allocatedMemoryLimitsAsString;
	}

	private void setAllocatedMemoryLimitsAsString(String allocatedMemoryLimitsAsString) {
		this.allocatedMemoryLimitsAsString = allocatedMemoryLimitsAsString;
		String[] valueAndUnit = separateValueFromUnit(this.allocatedMemoryLimitsAsString);
		this.allocatedMemoryLimits = new ValueAndUnit<Integer>(Integer.valueOf(valueAndUnit[0]), valueAndUnit[1]);
	}

}
