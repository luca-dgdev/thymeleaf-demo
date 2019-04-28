package com.example.thymeleafdemo.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.logging.Logger;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Work;

public class GaeDatastoreUserRegistry implements UserRegistry {
	
	//@Autowired 
	//private ObjectifyFactory objectifyFactory;

	private static final Logger log = Logger.getLogger(GaeDatastoreUserRegistry.class.getName());
	
	public GaeDatastoreUserRegistry() {
		super();
		ObjectifyService.init();
	}
	
	private static final String USER_TYPE = "GaeUser";
	private static final String USER_FORENAME = "forename";
	private static final String USER_SURNAME = "surname";
	private static final String USER_NICKNAME = "nickname";
	private static final String USER_EMAIL = "email";
	private static final String USER_ENABLED = "enabled";
	private static final String USER_AUTHORITIES = "authorities";

	public GaeUser findUser(String userId) {
		GaeUser gaeUser = ObjectifyService.run(new Work<GaeUser>() {
            @Override
            public GaeUser run() {
            	GaeUser gaeUser = ofy().load().type(GaeUser.class).id(userId).now();
				return gaeUser;
            }
        });
		return gaeUser;
		
		//return objectifyFactory.ofy().load().type(GaeUser.class).id(userId).now();
		
//		Key key = KeyFactory.createKey(USER_TYPE, userId);
//		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//
//		try {
//			Entity user = datastore.get(key);
//
//			long binaryAuthorities = (Long) user.getProperty(USER_AUTHORITIES);
//			Set<AppRole> roles = Utils.getAuthoritiesFromBinary(binaryAuthorities);
//
//			GaeUser gaeUser = new GaeUser(user.getKey().getName(), (String) user.getProperty(USER_NICKNAME),
//					(String) user.getProperty(USER_EMAIL), (String) user.getProperty(USER_FORENAME),
//					(String) user.getProperty(USER_SURNAME), roles, (Boolean) user.getProperty(USER_ENABLED));
//
//			return gaeUser;
//
//		} catch (EntityNotFoundException e) {
//			log.severe(userId + " not found in datastore");
//			return null;
//		}
	}

	public void registerUser(GaeUser newUser) {
		ObjectifyService.run(new Work<Key>() {
            @Override
            public Key run() {
            	Key<GaeUser> now = ofy().save().entity(newUser).now();
				return now;
            }
        });
		log.severe("saving GaeUser on Datastore");
		//objectifyFactory.ofy().save().entity(newUser).now();
		
//		Key key = KeyFactory.createKey(USER_TYPE, newUser.getUserId());
//		Entity user = new Entity(key);
//		user.setProperty(USER_EMAIL, newUser.getEmail());
//		user.setProperty(USER_NICKNAME, newUser.getNickname());
//		user.setProperty(USER_FORENAME, newUser.getForename());
//		user.setProperty(USER_SURNAME, newUser.getSurname());
//		user.setUnindexedProperty(USER_ENABLED, newUser.isEnabled());
//
//		Collection<? extends GrantedAuthority> roles = newUser.getAuthorities();
//
//		long binaryAuthorities = Utils.calculateBinaryAuthorities((Set<AppRole>) roles);
//
//		user.setUnindexedProperty(USER_AUTHORITIES, binaryAuthorities);
//
//		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//		datastore.put(user);
	}

	public void removeUser(String userId) {
		ObjectifyService.run(new Work<GaeUser>() {
            @Override
            public GaeUser run() {
            	GaeUser gaeUser = ofy().load().type(GaeUser.class).id(userId).now();
            	ofy().delete().entity(gaeUser);
				return gaeUser;
            }
        });
		//Objectify ofy = objectifyFactory.ofy();
		//GaeUser gaeUser = ofy.load().type(GaeUser.class).id(userId).now();
		//ofy.delete().entity(gaeUser);
//		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//		Key key = KeyFactory.createKey(USER_TYPE, userId);
//
//		datastore.delete(key);
	}

	public void findAll() {

	}
}