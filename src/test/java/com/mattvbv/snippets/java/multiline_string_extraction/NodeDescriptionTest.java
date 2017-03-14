package com.mattvbv.snippets.java.multiline_string_extraction;

import static org.junit.Assert.assertEquals;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import com.mattvbv.snippets.java.multiline_string_extraction.NodeDescription;

public class NodeDescriptionTest {

	@Test
	public void testConstructor() throws Exception {
		String descriptionAsString = loadDescriptionFromFile();
		NodeDescription nodeDesc = new NodeDescription(descriptionAsString);

		assertEquals(4, nodeDesc.getCpuCapacity());

		assertEquals("16266940Ki", nodeDesc.getMemoryCapacityAsString());
		assertEquals(16266940, nodeDesc.getMemoryCapacity().getValue().intValue());
		assertEquals("Ki", nodeDesc.getMemoryCapacity().getUnit());
		
		assertEquals(40, nodeDesc.getPodsCapacity());

		assertEquals("2500m", nodeDesc.getAllocatedCpuRequestsAsString());
		assertEquals(2500, nodeDesc.getAllocatedCpuRequests().getValue().intValue());
		assertEquals("m", nodeDesc.getAllocatedCpuRequests().getUnit());
		
		assertEquals("400m", nodeDesc.getAllocatedCpuLimitsAsString());
		assertEquals(400, nodeDesc.getAllocatedCpuLimits().getValue().intValue());
		assertEquals("m", nodeDesc.getAllocatedCpuLimits().getUnit());
		
		
		assertEquals("13300Mi", nodeDesc.getAllocatedMemoryRequestsAsString());
		assertEquals(13300, nodeDesc.getAllocatedMemoryRequests().getValue().intValue());
		assertEquals("Mi", nodeDesc.getAllocatedMemoryRequests().getUnit());
		
		
		assertEquals("9900Mi", nodeDesc.getAllocatedMemoryLimitsAsString());
		assertEquals(9900, nodeDesc.getAllocatedMemoryLimits().getValue().intValue());
		assertEquals("Mi", nodeDesc.getAllocatedMemoryLimits().getUnit());
		
	}
	
	@Test
	public void extractValueFromNameValueLine() {
		String nameValueLine = "Name:			jenkins.example.com";
		assertEquals("jenkins.example.com", NodeDescription.extractValueFromNameValueLine(nameValueLine, ":"));
	}
	
	private String loadDescriptionFromFile() throws Exception {
		byte[] fileAsBytes = Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("multiline_string_extraction/node_description.txt").toURI()));
		return new String(fileAsBytes, StandardCharsets.UTF_8);
	}

}
