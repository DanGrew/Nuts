/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.meal;

import java.util.LinkedHashSet;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

/**
 * {@link MealRegistrations} provides a mechanism for notifying changes made to the {@link Meal}.
 */
public class MealRegistrations {
   
   private final ChangeListener< Food > foodNotifier;
   private final ChangeListener< Double > valueNotifier;
   
   private final Set< MealChangeListener > listeners;
   
   private Meal meal;
   
   /**
    * Constructs a new {@link MealRegistrations}.
    */
   public MealRegistrations() {
      this.listeners = new LinkedHashSet<>();
      this.foodNotifier = ( s, o, n ) -> notifyListeners();
      this.valueNotifier = ( s, o, n ) -> notifyListeners();
   }//End Constructor
   
   /**
    * Associate the registrations with the given {@link Meal}.
    * @param meal the {@link Meal} to associate with, only one allowed.
    */
   public void associate( Meal meal ) {
      if ( this.meal != null ){
         throw new IllegalStateException( "Already associated." );
      }
      this.meal = meal;
      
      meal.portions().addListener( new FunctionListChangeListenerImpl<>( 
               this::portionAdded, this::portionRemoved 
      ) );
   }//End Method
   
   /**
    * Method to triggered when a {@link FoodPortion} is added.
    * @param portion the {@link FoodPortion} added.
    */
   private void portionAdded( FoodPortion portion ) {
      portion.food().addListener( foodNotifier );
      portion.portion().addListener( valueNotifier );
      
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         unit.of( portion ).property().addListener( valueNotifier );
      }
      notifyListeners();
   }//End Method
   
   /**
    * Method to triggered when a {@link FoodPortion} is removed.
    * @param portion the {@link FoodPortion} removed.
    */
   private void portionRemoved( FoodPortion portion ) {
      portion.food().removeListener( foodNotifier );
      portion.portion().removeListener( valueNotifier );
      
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         unit.of( portion ).property().removeListener( valueNotifier );
      }
      notifyListeners();
   }//End Method
   
   /**
    * Method to notify all associated {@link MealChangeListener}s.
    */
   private void notifyListeners(){
      listeners.forEach( MealChangeListener::mealChanged );
   }//End Method
   

   /**
    * Method to listen for {@link Meal} changes.
    * @param listener the {@link MealChangeListener} to register.
    */
   public void listen( MealChangeListener listener ) {
      this.listeners.add( listener );
   }//End Method

   /**
    * Method to stop listening for {@link MealChangeListener} notifications.
    * @param updater the {@link MealChangeListener} to remove.
    */
   public void stopListening( MealChangeListener updater ) {
      this.listeners.remove( updater );
   }//End Method

}//End Class
