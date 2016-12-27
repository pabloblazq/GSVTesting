package com.blame.gsv;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.blame.gsv.jaxbgenerated.Panorama;

public class GSVPanorama {
	private static final Logger logger = Logger.getLogger(Main.class);
	
	protected CloseableHttpClient chc;

	public GSVPanorama() {
		super();
		chc = HttpClients.createDefault();
	}

	public Panorama getPanoramaMetadataFromURL(String panoramaId) throws Exception {
		HttpGet hg = new HttpGet("http://maps.google.com/cbk?output=xml&panoid=" + panoramaId + "&cb_client=api&pm=0&ph=0&v=2");
		CloseableHttpResponse chr = null;
		try {
		    logger.info("Sending HTTP request to server");
			chr = chc.execute(hg);
		    logger.info("HTTP response received: " + chr.getStatusLine());
		    HttpEntity he = chr.getEntity();
		    String responseXml = EntityUtils.toString(he);
		    	        
	        Unmarshaller unmarshaller = JAXBContext.newInstance(Panorama.class).createUnmarshaller();
	        Panorama panorama = (Panorama) unmarshaller.unmarshal(new StringReader(responseXml));
	        EntityUtils.consume(he);
	        return panorama;
		}
		catch(Exception e) {
			logger.error("Unable to get panorama " + panoramaId + " metadata from URL", e);
			throw e;
		}
		finally {
			try {
				if(chr != null)
					chr.close();
			} catch (IOException e) {/*nothing to do*/}
		}
	}
	
	public BufferedImage getPanoramaImageFromURL(String panoramaId, int width, int height, float fieldOfView, float heading, float pitch, String key) throws Exception {
		String url = "https://maps.googleapis.com/maps/api/streetview?" + 
				"size=" + width + "x" + height + "&pano=" + panoramaId + "&fov=" + fieldOfView + "&heading=" + heading + "&pitch=" + pitch + "&key=" + key;
		
	    BufferedImage bi = ImageIO.read(new URL(url));
	    logger.info("image read");
	    
	    return bi;
	}
	
}
