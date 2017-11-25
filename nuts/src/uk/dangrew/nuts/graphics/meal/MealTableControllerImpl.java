/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.meal;

import java.util.Set;
import java.util.stream.Collectors;

import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.FriendlyTableView;
import uk.dangrew.nuts.graphics.table.FoodTable;
import uk.dangrew.nuts.graphics.table.FoodTableRow;
import uk.dangrew.nuts.meal.Meal;

/**
 * The {@link MealTableController} is responsible for controlling and updating the {@link MealTable}.
 */
public class MealTableControllerImpl implements MealTableController {

   private final FunctionListChangeListenerImpl< FoodPortion > mealListener;
   
   private FriendlyTableView< FoodTableRow< FoodPortion > > table;
   private Meal meal;
   
   /**
    * Constructs a new {@link MealTableController}.
    */
   public MealTableControllerImpl() {
      this.mealListener = new FunctionListChangeListenerImpl<>( 
               this::portionAddedToMeal, this::portionRemovedFromMeal 
      );
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public void associate( FoodTable< FoodPortion > table ) {
      this.table = table;
   }//End Method
   
   /**
    * Method to show the given {@link Meal} in the {@link MealTable}.
    * @param meal the {@link Meal} to show.
    */
   @Override public void showMeal( Meal meal ) {
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
   @Override public Meal getShowingMeal(){
      return meal;
   }//End Method
   
   /**
    * Method to handle the adding of a {@link FoodPortion}.
    * @param portion the {@link FoodPortion} added.
    */
   private void portionAddedToMeal( FoodPortion portion ) {
      table.getRows().add( meal.portions().indexOf( portion ), new FoodTableRow<>( portion ) );
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
   
   @Override public void copySelectedFood() {
      FoodTableRow< FoodPortion > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      FoodPortion copy = selection.food().duplicate( "" );
      meal.portions().add( copy );
   }//End Method 

   @Override public void moveUp() {
      FoodTableRow< FoodPortion > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      int rowIndex = table.getRows().indexOf( selection );
      if ( rowIndex == 0 ) {
         return;
      }
      
      FoodPortion first = selection.food();
      FoodPortion second = table.getRows().get( rowIndex - 1 ).food();
      meal.swap( second, first );
      
      table.getSelectionModel().select( rowIndex - 1 );
   }//End Method
   
   @Override public void moveDown() {
      FoodTableRow< FoodPortion > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      int rowIndex = table.getRows().indexOf( selection );
      if ( rowIndex == table.getRows().size() - 1 ) {
         return;
      }
      
      FoodPortion first = selection.food();
      FoodPortion second = table.getRows().get( rowIndex + 1 ).food();
      meal.swap( first, second );
      
      table.getSelectionModel().select( rowIndex + 1 );
   }//End Method
   
}//End Class
