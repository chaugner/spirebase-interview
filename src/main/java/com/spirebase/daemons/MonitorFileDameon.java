package com.spirebase.daemons;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spirebase.repositories.FileRepository;
import com.spirebase.resources.FileArchiveDto;

@Component
public class MonitorFileDameon implements Runnable {
	
	private final static Logger LOG = LoggerFactory.getLogger(MonitorFileDameon.class);
	
	@Value("${app.environment}")
	private String environment;
	
	@Value("${app.dataFolder}")
	private String dataFolder;
	
	@Autowired
	private FileRepository fileRepo;
	
	@Override
	public void run() {
			
		Boolean end = false;
		while(!end) {
	
			try {
				process();
			} catch (Throwable t) {
				t.printStackTrace();
			}
				
			try {
				Thread.sleep(15 * 1000);
			} catch (InterruptedException e) {}
			
		}	
		
	}
	
	private static ObjectMapper MAPPER = new ObjectMapper();
	
	@Transactional	
	private void process() throws Throwable {
		
	
		File dir = new File(dataFolder);

		System.out.println("Getting all files in " + dir.getCanonicalPath() + " including those in subdirectories");
		
		List<File> files = (List<File>) FileUtils.listFiles(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		for (File file : files) {
		
			
			com.spirebase.model.File dbFile = fileRepo.findByName(file.getCanonicalPath());
			if (dbFile == null) {
			
				dbFile = new com.spirebase.model.File();
				dbFile.setName(file.getCanonicalPath());
				
				fileRepo.save(dbFile);
				
			
				String content = IOUtils.toString(new FileInputStream(file), "UTF-8");
				FileArchiveDto asJson = MAPPER.readValue(content, FileArchiveDto.class);
				
				asJson.setArchived(new Date().toString());
				
				String updated = MAPPER.writeValueAsString(asJson);
				
				BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
				writer.write(updated);
				writer.flush();
			}
		}
	}
}
