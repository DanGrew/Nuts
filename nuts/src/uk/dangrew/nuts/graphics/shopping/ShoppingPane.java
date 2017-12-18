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
import uk.dangrew.nuts.graphics.table.ConceptTableWithControls;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;

/**
 * {@link ShoppingPane} provides a pane containing the {@link ShoppingNeedsTable} and {@link ShoppingListTable}.
 */
public class ShoppingPane extends GridPane {

   /**
    * Constructs a new {@link ShoppingPane}.
    * @param database the {@link Database}.
    * @param shoppingList the shopping list to show.
    */
   public ShoppingPane( Database database, Meal shoppingList ) {
      new JavaFxStyle().configureConstraintsForColumnPercentages( this, 60, 40 );
      new JavaFxStyle().configureConstraintsForEvenRows( this, 1 );
      
      add( new ShoppingListTable( shoppingList ), 0, 0 );
      add( new ConceptTableWithControls<>( 
               "Plans to shop for", 
               new ShoppingNeedsTable( database, shoppingList )
      ), 1, 0 );
   }//End Constructor
   
}//End Class
