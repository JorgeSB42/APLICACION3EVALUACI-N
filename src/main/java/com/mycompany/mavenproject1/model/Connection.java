/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.model;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CONNECTION")
@XmlAccessorType(XmlAccessType.FIELD)
public class Connection {

    private String host;
    private String db;
    private String user;
    private String password;

    public Connection(String host, String db, String user, String password) {
        this.host = host;
        this.db = db;
        this.user = user;
        this.password = password;
    }

    public Connection() {
        this("", "", "", "");
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Connection{" + "host=" + host + ", db=" + db + ", user=" + user + ", password=" + password + '}';
    }

    public void loadDataXML() {
        String file = "config.xml";
        File f = new File(file);
        if (f.canRead()) {
            JAXBContext context;
            try {
                context = JAXBContext.newInstance(Connection.class);

                Unmarshaller um = context.createUnmarshaller();
                Connection miconection = (Connection) um.unmarshal(f);
                this.host = miconection.host;
                this.db = miconection.db;
                this.user = miconection.user;
                this.password = miconection.password;
            } catch (JAXBException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("Archivo no valido");
        }
    }

}
