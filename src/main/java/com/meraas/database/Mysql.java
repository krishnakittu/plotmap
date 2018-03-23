package com.meraas.database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;


import com.meraas.entity.Path;

public class Mysql {
	
	private static final String DATABASE = "plotmapdb";

	/*public static PersistenceManagerFactory getInstance() {
	    Properties p=new Properties();		
		p.setProperty("javax.jdo.PersistenceManagerFactoryClass", "org.datanucleus.api.jdo.JDOPersistenceManagerFactory");
		p.setProperty("javax.jdo.option.ConnectionURL", "jdbc:mysql://localhost:3306/"+DATABASE);
		p.setProperty("javax.jdo.option.ConnectionDriverName", "com.mysql.jdbc.Driver");
		p.setProperty("javax.jdo.option.ConnectionUserName", "root");
		p.setProperty("javax.jdo.option.ConnectionPassword", "mysql");
		p.setProperty("datanucleus.autoCreateSchema", "true");
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory(p);
		return pmf;
    }
	
	  *//**
	  *  this returns all Path from database
	  * @return
	  *//*
	 public static ArrayList<Path> selectAllQuery() {
		    PersistenceManagerFactory pmf = getInstance();
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			ArrayList<Path> pathList=new ArrayList<Path>(); 
			try
			{
			    tx.begin();
			    Query query = pm.newQuery(Path.class);// select * from
			    Collection result = (Collection) query.execute();
			    java.util.Iterator iter = result.iterator();
		        while (iter.hasNext())
		        {
		            pathList.add((Path) iter.next());
		        }
			    tx.commit();
			}catch(Throwable t) {
				t.printStackTrace();
			}
			finally
			{
			    if (tx.isActive())
			    {
			        tx.rollback();
			    }
			    pm.close();
			}			
		 return pathList;
	 }
	 
	 public static Path selectByIdQuery(String pathId) {
		    PersistenceManagerFactory pmf = getInstance();
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx=pm.currentTransaction();
			Path Path= new Path(); 
			try
			{
			    tx.begin();
			    Query query = pm.newQuery(Path.class, "pathId == " + pathId);
			    Collection result = (Collection) query.execute();
				if(result.size()!=0) {
					 Path = (Path) result.iterator().next();
					 tx.commit();
				}
			}catch(Throwable t) {
				t.printStackTrace();
			}
			finally
			{
			    if (tx.isActive())
			    {
			        tx.rollback();
			    }
			    pm.close();
			}			
		 return Path;
		 	
	 }
	 
	 
	 public static boolean deleteQuery(String pathId) {
		 PersistenceManagerFactory pmf = getInstance();
		 PersistenceManager pm = pmf.getPersistenceManager();
		 Transaction tx=pm.currentTransaction();
		 boolean isDeleted=false;
		 try {
			   tx.begin();
			   Query query = pm.newQuery(Path.class, "pathId == " + pathId);
			   Collection result = (Collection) query.execute();
			   if(result.size()!=0) {
				   Path toBeDeleted = (Path) result.iterator().next();
		           pm.deletePersistent(toBeDeleted); // delete from table
				   tx.commit();
				   isDeleted=true;
			   }
		 }catch(Throwable t) {
			 t.printStackTrace();
		 }finally {
			 if (tx.isActive())
			    {
			        tx.rollback();
			    }
			 pm.close();
		 }
		 
		 return isDeleted;
	 }*/
	 
	/* public static boolean updateQuery(Integer id,String name) {
		 PersistenceManagerFactory pmf = getInstance();
		 PersistenceManager pm = pmf.getPersistenceManager();
		 Transaction tx=pm.currentTransaction();
		 boolean isUpdated=false;
		 try {
			   tx.begin();
			   Query query = pm.newQuery(Path.class, "id == " + id);
			   Collection result = (Collection) query.execute();
			   if(result.size()!=0) {
				   Path  toBeEdited  = (Path) result.iterator().next();
				   //toBeEdited.setName(name);
				   tx.commit();
				   isUpdated=true;
			   }
		 }catch(Throwable t) {
			 t.printStackTrace();
		 }finally {
			 if (tx.isActive())
			    {
			        tx.rollback();
			    }
			 pm.close();
		 }
		 
		 return isUpdated;
	 }*/
	
}
