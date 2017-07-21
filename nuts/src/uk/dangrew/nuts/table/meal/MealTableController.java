/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.table.meal;

import java.util.Set;
import java.util.stream.Collectors;

import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.table.food.FoodControlsInterface;
import uk.dangrew.nuts.table.food.FoodTableRow;

/**
 * The {@link MealTableController} is responsible for controlling and updating the {@link MealTable}.
 */
public class MealTableController implements FoodControlsInterface< FoodPortion > {

   private final FunctionListChangeListenerImpl< FoodPortion > mealListener;
   
   private MealTable table;
   private Meal meal;
   
   /**
    * Constructs a new {@link MealTableController}.
    */
   MealTableController() {
      this.mealListener = new FunctionListChangeListenerImpl<>( 
               this::portionAddedToMeal, this::portionRemovedFromMeal 
      );
   }//End Constructor

   /**
    * Associates the controller with the given.
    * @param table the associated {@link MealTable}.
    */
   void associate( MealTable table ) {
      this.table = table;
   }//End Method
   
   /**
    * Method to show the given {@link Meal} in the {@link MealTable}.
    * @param meal the {@link Meal} to show.
    */
   public void showMeal( Meal meal ) {
      if ( this.meal != null ) {
         this.meal.portions().removeListener( mealListener );
         table.getRows().clear();
      }
      this.meal = meal;
      if ( this.meal != null ) {
         this.meal.portions().addListener( mealListener );
         this.meal.portions().forEach( this::portionAddedToMeal );
      }
   }//End Method
   
   /**
    * Getter for the {@link Meal} being shown.
    * @return the {@link Meal} being shown.
    */
   public Meal getShowingMeal(){
      return meal;
   }//End Method
   
   /**
    * Method to handle the adding of a {@link FoodPortion}.
    * @param portion the {@link FoodPortion} added.
    */
   private void portionAddedToMeal( FoodPortion portion ) {
      table.getItems().add( new FoodTableRow<>( portion ) );
   }//End Method

   /**
    * Method to handle the removal of a {@link FoodPortion}.
    * @param portion the {@link FoodPortion} removed.
    */
   private void portionRemovedFromMeal( FoodPortion portion ) {
      Set< FoodTableRow< FoodPortion > > toRemove = table.getRows()
               .stream()
               .filter( r -> r.food() == portion )
               .collect( Collectors.toSet() );
      table.getRows().removeAll( toRemove );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public FoodPortion createFood() {
      if ( meal != null ) {
         FoodPortion portion = new FoodPortion();
         meal.portions().add( portion );
         return portion;
      }
      
      return null;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void removeSelectedFood() {
      FoodTableRow< FoodPortion > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      if ( meal != null ) {
         meal.portions().remove( selection.food() );
      }
   }//End Method

}//End Class
