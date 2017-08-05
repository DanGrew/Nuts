/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.shopping;

import uk.dangrew.nuts.plan.Plan;

/**
 * {@link ShoppingList} provides a more specific {@link Plan}.
 */
public class ShoppingList extends Plan {

   /**
    * Constructs a new {@link ShoppingList}.
    * @param name the name of the list.
    */
   public ShoppingList( String name ) {
      super( name );
   }//End Constructor

}//End Class
