/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.setup;

import uk.dangrew.nuts.store.Database;

/**
 * {@link DataSetup} is responsible for doing a little processing as data is read to ensure
 * the various structures are set up correctly and present where expected.
 */
public class DataSetup {

   private final Database database;
   
   /**
    * Constructs a new {@link DataSetup}.
    * @param database the {@link Database}.
    */
   public DataSetup( Database database ) {
      this.database = database;
   }//End Constructor
   
   /**
    * Ensures there is a default {@link uk.dangrew.nuts.goal.Goal} configured on
    * {@link Database#templates()} from the {@link uk.dangrew.nuts.goal.GoalStore}.
    */
   public void configureDefaultGoal(){
      if ( database.templates().defaultGoal() == null ) {
         if ( database.goals().objectList().isEmpty() ) {
            database.goals().createFood( "Default Goal" );
         }
         database.templates().setDefaultGoal( database.goals().objectList().get( 0 ) );
      }
   }//End Method

   /**
    * Ensures there is a default ShoppingList provided.
    */
   public void configureDefaultShoppingList() {
      if ( database.shoppingLists().objectList().isEmpty() ) {
         database.shoppingLists().createFood( "Shopping" );
      }
   }//End Method
   
}//End Class
