package com.dev.userapi;

import java.util.Date;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

/* GorosVi'16
 * 
 * Класс UserApi предоставляет набор функций для работы с пользователями и их правами
 * 
 * int grantRight(String right, Key grantingUserKey, Key gettingUserKey)
 * 
 * int revokeRight(String right, Key revokingUserKey, Key gettingUserKey)
 * 
 * int checkRight(String right, Key userKey)
 * 
 * int checkRightString(String right, String rights)
 * 
 * Entity getUserByID(String userID)
 *  
 * Entity createUser(String userID, String userName, String userEmail)
 * 
 * */

public class UserApi {
	private static DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	private static final Logger log = Logger.getLogger(UserWebApi.class.getName());

	public static int grantRight(String right, Key grantingUserKey, Key gettingUserKey){
		//          -2 if null in input values
		//          -1 if errors in input
		// returns:  0 if denied
		//           1 if present
		//           2 if granted
		if (right== null)
			return -1;
		if (checkRight("grant", grantingUserKey) > 0){
			Entity usermeta = null;
			try {
				usermeta = ds.get(gettingUserKey);
			} catch (EntityNotFoundException e) {
				log.severe("User not found in rights grant: datastore key = " + gettingUserKey);
				return -1;
			}
			catch (NullPointerException e) {
				log.severe("Null KEY in rights grant: datastore key = " + gettingUserKey);
				return -2;
			}
		
			if (usermeta != null){
				String rights = (String)usermeta.getProperty("rights");
				String jlog = (String)usermeta.getProperty("log");
				if (rights == null){
					rights = "{\"rights\":[]}";
				}
				if (jlog == null){
					jlog = "{\"rights\":[]}";
				}
				try {
					JSONObject jsrights = new JSONObject(rights);
					//log.info(jsrights.getString("rights"));
					JSONArray jsarr = new JSONArray(jsrights.getString("rights"));
					//log.info(jsarr.toString());
					for (int i=0; i<=jsarr.length()-1; i++){
						if (right.equals(jsarr.getString(i))){
							log.info("Right "+right+" was not granted to user "+gettingUserKey+" by user " + grantingUserKey + " because it was already granted");
							return 1;
						}
					}
					JSONObject jslog = new JSONObject(jlog);
					//log.info(jslog.getString("rights"));
					JSONArray jslogarr = new JSONArray(jslog.getString("rights"));
					//log.info(jslogarr.toString());
					JSONObject jslogEntity = new JSONObject();
					jslogEntity.put("right", right);
					jslogEntity.put("action", "granted");
					jslogEntity.put("date", Long.toString(new Date().getTime()));
					jslogEntity.put("user", grantingUserKey.getId());
					jslogarr.put(jslogEntity);
					
					jsarr.put(right);
					jsrights.put("rights", jsarr);
					jslog.put("rights", jslogarr);
					
					usermeta.setProperty("rights", jsrights.toString());
					usermeta.setProperty("log", jslog.toString());
					ds.put(usermeta);
					log.info("Right "+right+" granted to user "+gettingUserKey+" by user " + grantingUserKey);
					return 2;
					
				} catch (JSONException e) {
					log.severe("Broken record at rights grant: datastore key = " + gettingUserKey);
					return -1;
				}
			} else {
				log.severe("NULL in datastore result at rights grant: datastore key = " + gettingUserKey);
				return -1;
			}
		} else
			log.warning("User not able grant rights: datastore key = " + grantingUserKey);
		return 0;
	}
		
	public static int revokeRight(String right, Key revokingUserKey, Key gettingUserKey){
		//          -2 if null in input values
		//          -1 if errors in input
		// returns:  0 if denied
		//           1 if absent
		//           2 if revoked
		if (right== null)
			return -1;
		if (checkRight("grant", revokingUserKey) > 0){
			Entity usermeta = null;
			try {
				usermeta = ds.get(gettingUserKey);
			} catch (EntityNotFoundException e) {
				log.severe("User not found in rights revoking: datastore key = " + gettingUserKey);
				return -1;
			}
			catch (NullPointerException e) {
				log.severe("Null KEY in rights revoking: datastore key = " + gettingUserKey);
				return -2;
			}
		
			if (usermeta != null){
				String rights = (String)usermeta.getProperty("rights");
				String jlog = (String)usermeta.getProperty("log");
				if (rights == null){
					rights = "{\"rights\":[]}";
				}
				if (jlog == null){
					jlog = "{\"rights\":[]}";
				}
				try {
					JSONObject jsrights = new JSONObject(rights);
					//log.info(jsrights.getString("rights"));
					JSONArray jsarr = new JSONArray(jsrights.getString("rights"));
					//log.info(jsarr.toString());
					
					JSONObject jslog = new JSONObject(jlog);
					//log.info(jslog.getString("rights"));
					JSONArray jslogarr = new JSONArray(jslog.getString("rights"));
					//log.info(jslogarr.toString());
					
					JSONArray jsnewarr = new JSONArray();
					int removedCount = 0;
					
					for (int i=0; i<=jsarr.length()-1; i++){
						if (right.equals(jsarr.getString(i))){
							JSONObject jslogEntity = new JSONObject();
							jslogEntity.put("right", right);
							jslogEntity.put("action", "revoked");
							jslogEntity.put("date", Long.toString(new Date().getTime()));
							jslogEntity.put("user", revokingUserKey.getId());
							jslogarr.put(jslogEntity);
							log.info("Right "+right+" revoked from user "+gettingUserKey+" by user "+revokingUserKey);
							removedCount++;
						} else {
							jsnewarr.put(jsarr.getString(i));
						}	
					}
					
					if (removedCount > 0){
						jsrights.put("rights", jsnewarr);
						jslog.put("rights", jslogarr);
						
						usermeta.setProperty("rights", jsrights.toString());
						usermeta.setProperty("log", jslog.toString());
						ds.put(usermeta);
						return 2;
					}
					log.info("Right "+right+" was not revoked from user "+gettingUserKey+" by user " + revokingUserKey + " because it absent in rights array");					
					return 1;
					
				} catch (JSONException e) {
					log.severe("Broken record at rights revoking: datastore key = " + gettingUserKey);
					return -1;
				}
			} else {
				log.severe("NULL in datastore result at rights revoking: datastore key = " + gettingUserKey);
				return -1;
			}
		} else
			log.warning("User not able revoke rights: datastore key = " + revokingUserKey);
		return 0;
	}

	public static int checkRight(String right, Key userKey){
		//          -2 if key is null
		// returns: -1 if user not found
		//           0 if denied
		//           1 if granted
		if (right == null)
			return 0;
		Entity usermeta = null;
		try {
			usermeta = ds.get(userKey);
		} catch (EntityNotFoundException e) {
			log.severe("User not found in rights check: datastore key = " + userKey);
			return -1;
		}
		catch (NullPointerException e) {
			log.severe("Null KEY in rights check: datastore key = " + userKey);
			return -2;
		}
	
		if (usermeta != null){
			String rights = (String)usermeta.getProperty("rights");
			if (rights != null){
				try {
					JSONObject jrights = new JSONObject(rights);
					//log.info(jrights.getString("rights"));
					JSONArray jsarr = new JSONArray(jrights.getString("rights"));
					//log.info(jsarr.toString());
					for (int i=0; i<=jsarr.length()-1; i++){
						if (right.equals(jsarr.getString(i)))
							return 1;
					}
					return 0;
				} catch (JSONException e) {
					log.severe("Broken record at rights check: datastore key = " + userKey);
					return 0;
				}
			} else 
				return 0;
		} else {
			log.severe("NULL in datastore result at rights check: datastore key = " + userKey);
			return -1;
		}
	}
	
	public static int checkRightString(String right, String rights){
		// returns:  0 if denied
		//           1 if granted
		if (right == null)
			return 0;
		if (rights != null){
			try {
				JSONObject jrights = new JSONObject(rights);
				//log.info(jrights.getString("rights"));
				JSONArray jsarr = new JSONArray(jrights.getString("rights"));
				//log.info(jsarr.toString());
				for (int i=0; i<=jsarr.length()-1; i++){
					if (right.equals(jsarr.getString(i)))
						return 1;
				}
				return 0;
			} catch (JSONException e) {
				log.severe("Broken record at rights check: string = " + rights);
				return 0;
			}
		} else 
			return 0;
	}
	
	public static Entity createUser(String userID, String userName, String userEmail){
		//           null if input is null
		// returns:  null if userID already exists - DISABLED!!!
		//           Entity if created
		if (userID == null){
			return null;
		}
		//if (getUserByID(userID) != null){
		//	log.warning("Cannot create user with UID "+userID+" : this ID already used!");
		//	return null;
		//}
		// Create user record
		Date localdate = new Date();
		Entity result = new Entity ("User");
		result.setProperty("name", userName);
		result.setProperty("email", userEmail);
		result.setProperty("info", "{\"authres\":\"google\"}");
		result.setProperty("userid", userID);
		result.setProperty("rights", "{\"rights\":[]}");
		//result.setProperty("rights", "{\"rights\":[\"edit\",\"approve\",\"grant\"]}");
		result.setProperty("lastlogin", localdate);
		result.setProperty("log", "{\"rights\":[],\"createdate\":\""+localdate.getTime()+"\"}");
		ds.put(result);
		log.info("New user added: "+result.getProperty("email")+" with userid = "+result.getProperty("userid")+" and datastore key = "+result.getKey());
		return result;
	}
	
	public static Entity getUserByID(String userID){
		// returns:  Entity if found
		//           Null if not found
		if (userID == null){
			return null;
		}
		Query q = new Query("User")
				  .setFilter(new FilterPredicate("userid", FilterOperator.EQUAL, userID));
		// Check record existing
		Entity result = null;
		PreparedQuery pq = ds.prepare(q);
		try {
			result = pq.asSingleEntity();
		} catch (Exception e){
			// Duplicated user records found. Selecting first, deleting others.
			boolean first = true;
			for (Entity resultItem : pq.asIterable()) {
				if (first == true){
					first = false;
					result = resultItem;
					log.warning("User duplicated record selected: "+result.getProperty("email")+" with userid = "+result.getProperty("userid")+" and datastore key = "+result.getKey());
				} else {
					log.warning("User duplicated record DELETED!: "+resultItem.getProperty("email")+" with userid = "+resultItem.getProperty("userid")+" and datastore key = "+resultItem.getKey());
					ds.delete(resultItem.getKey());
				}
			}		
		}
		if (result != null)
			log.info("User record query: "+result.getProperty("email")+" with userid = "+result.getProperty("userid")+" and datastore key = "+result.getKey()+" using LastLogin "+result.getProperty("lastlogin"));
		else 
			log.info("User ID "+userID+" record query returns NULL");
		return result;
	}

}
