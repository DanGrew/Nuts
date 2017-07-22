/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.fooditems;

import uk.dangrew.jupa.file.protocol.JarJsonPersistingProtocol;
import uk.dangrew.jupa.json.marshall.ModelMarshaller;
import uk.dangrew.jupa.json.session.SessionManager;
import uk.dangrew.nuts.main.Nuts;
import uk.dangrew.nuts.store.Database;

/**
 * The {@link FoodSessions} provides the {@link SessionManager} for saving and loading {@link uk.dangrew.nuts.food.FoodItem}s.
 */
public class FoodSessions {

   static final String FOLDER_NAME = "uk.dangrew.nuts";
   static final String FOOD_ITEM_FILE_NAME = "food-items.json";
   
   private final Database database;
   private final FoodItemChangeMonitor monitor;
   private final SessionManager sessions;
   private final JarJsonPersistingProtocol fileLocation;
   
   /**
    * Constructs a new {@link FoodSessions}.
    * @param database the {@link Database}.
    */
   public FoodSessions( 
            Database database 
   ) {
      this( 
               database, 
               new JarJsonPersistingProtocol( 
                        FOLDER_NAME, FOOD_ITEM_FILE_NAME, Nuts.class 
               )
      );
   }//End Constructor
   
   /**
    * Constructs a new {@link BuildWallConfigurationSessions}.
    * @param database the {@link Database}.
    * @param protocol the {@link JarJsonPersistingProtocol}.
    */
   FoodSessions( 
            Database database, 
            JarJsonPersistingProtocol protocol 
   ) {
      this.database = database;
      this.fileLocation = protocol;
      this.monitor = new FoodItemChangeMonitor();
      
      ModelMarshaller marshaller = constructMarshaller();
      marshaller.read();
      this.sessions = new SessionManager( marshaller );
      this.sessions.triggerWriteOnChange( monitor.trigger() );
   }//End Constructor
   
   /**
    * Method to construct the {@link ModelMarshaller}.
    * @param database the {@link Database}.
    * @param locationProtocol the {@link JarJsonPersistingProtocol}.
    * @return the {@link ModelMarshaller} constructed.
    */
   private ModelMarshaller constructMarshaller(){
      FoodItemPersistence persistence = new FoodItemPersistence( database );
      return new ModelMarshaller( 
               persistence.structure(), 
               persistence.readHandles(), 
               persistence.writeHandles(), 
               fileLocation 
      );
   }//End Method
   
   /**
    * Method to shutdown the {@link SessionManager}s associated.
    */
   public void shutdownSessions(){
      sessions.stop();
   }//End Method
}//End Class
