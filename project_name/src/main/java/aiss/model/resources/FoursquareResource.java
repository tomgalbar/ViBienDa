package aiss.model.resources;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import aiss.model.foursquare.FoursquareSearch;
import aiss.model.foursquare.FoursquareToken;

public class FoursquareResource {
	
	private static final String Foursquare_Client_Id = "HADL3PWUYW1AT3W0XHX4MQALAY22K1RMD2JIUBX5HXPTJYNC";
	private static final String Foursquare_Client_Secret = "JR3ENW0XR5FAURWCPLZS5INDLFPB4IYOWYDNUXL0AXRWEGVT";
	private static final String CALLBACK_URI = "http://localhost:8090/UserListsController";

	private static final Logger log = Logger.getLogger(FoursquareResource.class.getName());
	
	public FoursquareSearch getRecommendedVenues(Double lat, Double lon, Double radio) throws UnsupportedEncodingException {
		
		ClientResource cr=null;
		FoursquareSearch recommendedVenues=null;
		
		String uri = "https://api.foursquare.com/v2/venues/search?client_id=" + Foursquare_Client_Id + "&client_secret=" + Foursquare_Client_Secret + "&ll=" + lat + "," + lon + "&v=20200101&intent=browse&radius=" + radio;
		
		log.log(Level.FINE, "Foursquare getVenues URI: " + uri);
		
		try {
			cr = new ClientResource(uri);
			recommendedVenues = cr.get(FoursquareSearch.class);
		}
		catch (ResourceException re){
			System.err.println("Error when retrieving the recommendedVenues" + cr.getResponse().getStatus());
		}
		return recommendedVenues;
	}
	
	public FoursquareToken getFoursquareAccessToken(String code) throws UnsupportedEncodingException {
		//PARA OBTENER EL ACCESS TOKEN A PARTIR DEL CODIGO OBTENIDO TRAS ACEPTAR EL USUARIO LOS PERMISOS
		ClientResource cr = null;
		FoursquareToken accessToken = null;
		
		String uri = "https://foursquare.com/oauth2/access_token?client_id=" + Foursquare_Client_Id + "&client_secret=" + Foursquare_Client_Secret + "&grant_type=authorization_code&redirect_uri=" + CALLBACK_URI + "&code=" + code;
		log.log(Level.FINE, "FoursquareAccessToken URI: " + uri);
		
		try {
			cr = new ClientResource(uri);
			accessToken= cr.get(FoursquareToken.class);
		}
		catch (ResourceException re) {
			System.err.println("Error when retrieving the accessToken" + cr.getResponse().getStatus());
		}
		return accessToken;
	}
}