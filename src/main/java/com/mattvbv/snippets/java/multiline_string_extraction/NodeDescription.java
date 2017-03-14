package com.mattvbv.snippets.java.multiline_string_extraction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class NodeDescription implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int cpuCapacity;
	private String memoryCapacity;
	private int podsCapacity;
	
	private String allocatedCpuRequests;
	private String allocatedCpuLimits;
	private String allocatedMemoryRequests;
	private String allocatedMemoryLimits;
	
	private static final String CAPACITY = "Capacity:";
	private static final String ALLOCATED_RESOURCES = "Allocated resources:";
	
	
	private static final String NAME_VALUE_SEPARATOR_CHAR = ":";
	
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
				this.memoryCapacity = extractValueFromNameValueLine(line, NAME_VALUE_SEPARATOR_CHAR);
			} else if (line.startsWith("pods:")) {
				this.podsCapacity = Integer.valueOf(extractValueFromNameValueLine(line, NAME_VALUE_SEPARATOR_CHAR));
			} 
		}
	}
	private void processAllocatedResourcesLines(List<String> lines) {
		for (String line : lines) {
			if (Character.isDigit(line.charAt(0))) {
				// split by white space or tabs
				String[] values = line.split("[ \t]");
				this.allocatedCpuRequests = values[0];
				this.allocatedCpuLimits = values[2];
				this.allocatedMemoryRequests = values[4];
				this.allocatedMemoryLimits = values[6];
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

	public int getCpuCapacity() {
		return cpuCapacity;
	}

	public String getMemoryCapacity() {
		return memoryCapacity;
	}

	public int getPodsCapacity() {
		return podsCapacity;
	}

	public String getAllocatedCpuRequests() {
		return allocatedCpuRequests;
	}

	public String getAllocatedCpuLimits() {
		return allocatedCpuLimits;
	}

	public String getAllocatedMemoryRequests() {
		return allocatedMemoryRequests;
	}

	public String getAllocatedMemoryLimits() {
		return allocatedMemoryLimits;
	}

}
