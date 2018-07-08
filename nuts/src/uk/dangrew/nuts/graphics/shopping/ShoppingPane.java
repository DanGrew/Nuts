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
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.meal.MealTableControllerImpl;
import uk.dangrew.nuts.graphics.table.ConceptControls;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;

/**
 * {@link ShoppingPane} provides a pane containing the {@link ShoppingNeedsTable} and {@link ShoppingListTable}.
 */
@Deprecated //Not in use and food selection not quite right for it: 20/06/18
public class ShoppingPane extends GridPane {

   private final MealTableControllerImpl shoppingController;
   
   /**
    * Constructs a new {@link ShoppingPane}.
    * @param database the {@link Database}.
    * @param shoppingList the shopping list to show.
    */
   public ShoppingPane( Database database, Meal shoppingList ) {
      new JavaFxStyle().configureConstraintsForColumnPercentages( this, 60, 40 );
      new JavaFxStyle().configureConstraintsForEvenRows( this, 1 );
      
      add( new ShoppingListTable( shoppingList ), 0, 0 );
      add( new TableComponents< FoodPortion >()
               .withDatabase( database )
               .applyColumns( ShoppingNeedsTableColumns::new )
               .withController( shoppingController = new MealTableControllerImpl() )
               .withControls( new ConceptControls( shoppingController ) )
               .buildTableWithControls( "Plans to shop for" ), 
      1, 0 );
      
      shoppingController.showMeal( shoppingList );
   }//End Constructor
   
}//End Class
