package com.demo.demo.resource;

import org.springframework.core.io.FileSystemResource;

import java.io.*;

public class ResourceDemo {
	public static void main(String[] args) throws IOException {
		FileSystemResource fileSystemResource = new FileSystemResource(
				"/Users/zhangchao/code/spring-source-code/spring-framework-5.2.0.RELEASE/springdemo/src/main/java/com/demo/demo/resource/test.txt"
		);
		File file = fileSystemResource.getFile();
		System.out.println(file.length());
		OutputStream outputStream =  fileSystemResource.getOutputStream();
		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
		bufferedWriter.write("Hello World");
		bufferedWriter.flush();
		outputStream.close();
		bufferedWriter.close();
	}
}
