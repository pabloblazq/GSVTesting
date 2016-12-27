package com.blame.gsv;

import org.apache.log4j.Logger;

import com.blame.gsv.jaxbgenerated.Panorama;

public class Main {

	public static void main(String[] args) {
		Logger logger = Logger.getLogger(Main.class);
		
		logger.info("Starting application");

		try {
			GSVPanorama gp = new GSVPanorama();
			Panorama p = gp.getPanoramaMetadataFromURL("XYEpYsvzCXYck-Am0FypTQ");
			gp.getPanoramaImageFromURL("XYEpYsvzCXYck-Am0FypTQ", 640, 400, 120, 0, 0, "AIzaSyDxcLOH13FSJrUV2KfU1RfBR6TsuJ6DDRI");
		} 
		catch (Exception e) {
			logger.error("Error during application execution", e);
		}

		logger.info("Application finished");
	}

}
