/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.manager;

import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.graphics.food.GeneralFoodTable;
import uk.dangrew.nuts.graphics.meal.MealTableWithControls;
import uk.dangrew.nuts.graphics.table.ConceptTableWithControls;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;

/**
 * {@link FoodManagerPane} provides a method of manager the {@link Database} {@link uk.dangrew.nuts.food.Food}s
 * graphically.
 */
public class FoodManagerPane extends GridPane {
   
   static final double FOOD_ITEMS_HEIGHT_PROPORTION = 40.0;
   static final double MEALS_HEIGHT_PROPORTION = 30.0;
   static final double MEAL_VIEW_HEIGHT_PROPORTION = 30.0;
   
   private final ConceptTableWithControls< FoodItem > foodItemsTable;
   private final ConceptTableWithControls< Meal > mealsTable;
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
      
      add( foodItemsTable = new ConceptTableWithControls<>( "Foods", new GeneralFoodTable<>( database.foodItems() ) ), 0, 0 );
      add( mealsTable = new ConceptTableWithControls<>( "Meals", new GeneralFoodTable<>( database.meals() ) ), 0, 1 );
      add( mealView = new MealTableWithControls( "Selected Meal", database ), 0, 2 );
      
      mealsTable.table().getSelectionModel().selectedItemProperty().addListener( ( s, o, n ) -> {
         mealView.table().controller().showMeal( n.concept() );
      } );
   }//End Constructor
   
   ConceptTableWithControls< FoodItem > foodItemsTable(){
      return foodItemsTable;
   }//End Method
   
   ConceptTableWithControls< Meal > mealsTable(){
      return mealsTable;
   }//End Method
   
   MealTableWithControls mealView(){
      return mealView;
   }//End Method

}//End Class
