/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.table.meal;

import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.table.food.FoodControls;

/**
 * {@link MealTableWithControls} provides a {@link MealTable} with {@link FoodControls}.
 */
public class MealTableWithControls extends TitledPane {

   private final MealTable mealTable;
   
   /**
    * Constructs a new {@link MealTableWithControls}.
    * @param title the title of the {@link TitledPane}.
    * @param database the {@link Database}.
    */
   public MealTableWithControls( String title, Database database ) {
      super( title, new BorderPane() );
      setCollapsible( false );
      BorderPane content = ( BorderPane ) getContent();
      content.setCenter( mealTable = new MealTable( database ) );
      content.setRight( new FoodControls( mealTable.controller() ) );
      
      JavaFxStyle styling = new JavaFxStyle();
      mealTable.setPlaceholder( styling.createWrappedTextLabel( 
               "No Meals to display. Use the '+' and '-' to add and remove. Double click on the meal name to change it." 
      ) );
   }//End Constructor

   /**
    * Access to the {@link MealTable}.
    * @return the {@link MealTable}.
    */
   public MealTable mealTable() {
      return mealTable;
   }//End Method

}//End Class
