package com.tomcatwork.ex10.realm;

import com.tomcatwork.apache.catalina.Role;
import com.tomcatwork.apache.catalina.User;
import com.tomcatwork.apache.catalina.UserDatabase;
import com.tomcatwork.apache.catalina.realm.GenericPrincipal;
import com.tomcatwork.apache.catalina.realm.RealmBase;
import com.tomcatwork.apache.catalina.users.MemoryUserDatabase;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @program: testProject
 * @description:
 * @author: zzk
 * @create: 2020-09-03
 */
public class SimpleUserDatabaseRealm extends RealmBase {

    protected UserDatabase database = null;

    protected static final String name = "SimpleUserDatabaseRealm";

    protected String resourceName = "UserDatabase";

    public Principal authenticate(String username, String credentials) {
        User user = database.findUser(username);
        if(user == null){
            return null;
        }
        boolean validated = false;
        if(hasMessageDigest()){
            validated = (digest(credentials).equalsIgnoreCase(user.getPassword()));
        }else{
            validated = (digest(credentials).equals(user.getPassword()));
        }
        if(!validated){
            return null;
        }

        ArrayList combined = new ArrayList();
        Iterator roles = user.getRoles();
        while (roles.hasNext()){
            Role role = (Role) roles.next();
            String rolename = role.getRolename();
            if(!combined.contains(rolename)){
                combined.add(rolename);
            }
        }

        Iterator groups = user.getGroups();
        while(groups.hasNext()){
            Role role = (Role) roles.next();
            String rolename = role.getRolename();
            if(!combined.contains(rolename)){
                combined.add(rolename);
            }
        }
        return (new GenericPrincipal(this, user.getUsername(), user.getPassword(), combined));
    }

    @Override
    protected String getName() {
        return this.name;
    }

    @Override
    protected String getPassword(String username) {
        return null;
    }

    @Override
    protected Principal getPrincipal(String username) {
        return null;
    }

    public void createDatabase(String path){
        database = new MemoryUserDatabase(name);
        ((MemoryUserDatabase) database).setPathname(path);
        try{
            database.open();
        }catch (Exception e){

        }
    }
}
