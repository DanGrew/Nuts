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

import uk.dangrew.kode.event.structure.Event;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.FriendlyTableView;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionEvent;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionRequest;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;
import uk.dangrew.nuts.graphics.table.ConceptTableRowImpl;
import uk.dangrew.nuts.graphics.table.ConceptTableViewController;
import uk.dangrew.nuts.meal.FoodHolder;

/**
 * The {@link MealTableController} is responsible for controlling and updating the {@link MealTable}.
 */
public class MealTableControllerImpl implements MealTableController, ConceptTableViewController< FoodPortion > {

   private final FunctionListChangeListenerImpl< FoodPortion > mealListener;
   private final FoodSelectionEvent mealSelectionEvents;
   
   private FriendlyTableView< ConceptTableRow< FoodPortion > > table;
   private FoodHolder meal;
   
   public MealTableControllerImpl() {
      this( new FoodSelectionEvent() );
   }//End Constructor
   
   MealTableControllerImpl( FoodSelectionEvent selectionEvents ) {
      this.mealSelectionEvents = selectionEvents;
      this.mealListener = new FunctionListChangeListenerImpl<>( 
               this::portionAddedToMeal, this::portionRemovedFromMeal, this::sort
      );
   }//End Constructor
   
   protected FriendlyTableView< ConceptTableRow< FoodPortion > > table(){
      return table;
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void associate( ConceptTable< FoodPortion > table ) {
      this.table = table;
   }//End Method
   
   /**
    * Method to show the given {@link Meal} in the {@link MealTable}.
    * @param meal the {@link Meal} to show.
    */
   @Override public void showMeal( FoodHolder meal ) {
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
   @Override public FoodHolder getShowingMeal(){
      return meal;
   }//End Method
   
   private void sort() {
      showMeal( meal );
   }//End Method
   
   /**
    * Method to handle the adding of a {@link FoodPortion}.
    * @param portion the {@link FoodPortion} added.
    */
   private void portionAddedToMeal( FoodPortion portion ) {
      table.getRows().add( meal.portions().indexOf( portion ), new ConceptTableRowImpl<>( portion ) );
   }//End Method

   /**
    * Method to handle the removal of a {@link FoodPortion}.
    * @param portion the {@link FoodPortion} removed.
    */
   private void portionRemovedFromMeal( FoodPortion portion ) {
      Set< ConceptTableRow< FoodPortion > > toRemove = table.getRows()
               .stream()
               .filter( r -> r.concept() == portion )
               .collect( Collectors.toSet() );
      table.getRows().removeAll( toRemove );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public FoodPortion createConcept() {
      if ( meal != null ) {
         mealSelectionEvents.fire( new Event<>( new FoodSelectionRequest( getShowingMeal() ) ) );
      }
      
      return null;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void removeSelectedConcept() {
      ConceptTableRow< FoodPortion > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      if ( meal != null ) {
         meal.portions().remove( selection.concept() );
      }
   }//End Method
   
   @Override public void copySelectedConcept() {
      ConceptTableRow< FoodPortion > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      FoodPortion copy = selection.concept().duplicate();
      meal.portions().add( copy );
   }//End Method 

   @Override public void moveUp() {
      ConceptTableRow< FoodPortion > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      int rowIndex = table.getRows().indexOf( selection );
      if ( rowIndex == 0 ) {
         return;
      }
      
      FoodPortion first = selection.concept();
      FoodPortion second = table.getRows().get( rowIndex - 1 ).concept();
      meal.swap( second, first );
      
      table.getSelectionModel().select( rowIndex - 1 );
   }//End Method
   
   @Override public void moveDown() {
      ConceptTableRow< FoodPortion > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      int rowIndex = table.getRows().indexOf( selection );
      if ( rowIndex == table.getRows().size() - 1 ) {
         return;
      }
      
      FoodPortion first = selection.concept();
      FoodPortion second = table.getRows().get( rowIndex + 1 ).concept();
      meal.swap( first, second );
      
      table.getSelectionModel().select( rowIndex + 1 );
   }//End Method
   
}//End Class
