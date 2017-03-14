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
		assertEquals("16266940Ki", nodeDesc.getMemoryCapacity());
		assertEquals(40, nodeDesc.getPodsCapacity());
		assertEquals("2500m", nodeDesc.getAllocatedCpuRequests());
		assertEquals("400m", nodeDesc.getAllocatedCpuLimits());
		assertEquals("13300Mi", nodeDesc.getAllocatedMemoryRequests());
		assertEquals("9900Mi", nodeDesc.getAllocatedMemoryLimits());
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
