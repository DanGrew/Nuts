/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.shopping;

import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.graphics.table.FoodTableWithControls;
import uk.dangrew.nuts.store.Database;

/**
 * {@link ShoppingPane} provides a pane containing the {@link ShoppingNeedsTable} and {@link ShoppingListTable}.
 */
public class ShoppingPane extends GridPane {

   /**
    * Constructs a new {@link ShoppingPane}.
    * @param database the {@link Database}.
    */
   public ShoppingPane( Database database ) {
      new JavaFxStyle().configureConstraintsForColumnPercentages( this, 60, 40 );
      new JavaFxStyle().configureConstraintsForEvenRows( this, 1 );
      
      add( new ShoppingListTable( database.shoppingList() ), 0, 0 );
      add( new FoodTableWithControls<>( 
               "Plans to shop for", 
               new ShoppingNeedsTable( database )
      ), 1, 0 );
   }//End Constructor
   
}//End Class
