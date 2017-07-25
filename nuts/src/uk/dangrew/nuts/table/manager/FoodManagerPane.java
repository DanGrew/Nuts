/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.table.manager;

import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.table.food.FoodTableWithControls;
import uk.dangrew.nuts.table.meal.MealTableWithControls;

/**
 * {@link FoodManagerPane} provides a method of manager the {@link Database} {@link uk.dangrew.nuts.food.Food}s
 * graphically.
 */
public class FoodManagerPane extends GridPane {
   
   static final double FOOD_ITEMS_HEIGHT_PROPORTION = 40.0;
   static final double MEALS_HEIGHT_PROPORTION = 30.0;
   static final double MEAL_VIEW_HEIGHT_PROPORTION = 30.0;
   
   private final FoodTableWithControls< FoodItem > foodItemsTable;
   private final FoodTableWithControls< Meal > mealsTable;
   private final MealTableWithControls mealView;
   
   /**
    * Constructs a new {@link FoodManagerPane}.
    * @param database the {@link Database}.
    */
   public FoodManagerPane( Database database ) {
      this( new JavaFxStyle(), database );
   }//End Constructor
   
   /**
    * Constructs a new {@link FoodManagerPane}.
    * @param styling the {@link JavaFxStyle}.
    * @param database the {@link Database}.
    */
   FoodManagerPane( JavaFxStyle styling, Database database ) {
      styling.configureConstraintsForRowPercentages( 
               this, 
               FOOD_ITEMS_HEIGHT_PROPORTION, 
               MEALS_HEIGHT_PROPORTION, 
               MEAL_VIEW_HEIGHT_PROPORTION 
      );
      styling.configureConstraintsForEvenColumns( this, 1 );
      
      add( foodItemsTable = new FoodTableWithControls<>( database.foodItems() ), 0, 0 );
      add( mealsTable = new FoodTableWithControls<>( database.meals() ), 0, 1 );
      add( mealView = new MealTableWithControls( database ), 0, 2 );
      
      mealsTable.table().getSelectionModel().selectedItemProperty().addListener( ( s, o, n ) -> {
         mealView.mealTable().controller().showMeal( n.food() );
      } );
   }//End Constructor
   
   FoodTableWithControls< FoodItem > foodItemsTable(){
      return foodItemsTable;
   }//End Method
   
   FoodTableWithControls< Meal > mealsTable(){
      return mealsTable;
   }//End Method
   
   MealTableWithControls mealView(){
      return mealView;
   }//End Method

}//End Class
