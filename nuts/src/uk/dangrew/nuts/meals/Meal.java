/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.meals;

import java.util.LinkedHashSet;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.nutrients.MacroNutrient;

/**
 * The {@link Meal} represents a collection of {@link FoodPortion}s and provides notifications
 * as they change.
 */
public class Meal {

   private final ChangeListener< Food > foodNotifier;
   private final ChangeListener< Double > valueNotifier;
   
   private final ObservableList< FoodPortion > portions;
   private final Set< MealChangeListener > listeners;
   
   /**
    * Constructs a new {@link Meal}.
    */
   public Meal() {
      this.portions = FXCollections.observableArrayList();
      this.listeners = new LinkedHashSet<>();
      this.foodNotifier = ( s, o, n ) -> notifyListeners();
      this.valueNotifier = ( s, o, n ) -> notifyListeners();
      
      this.portions.addListener( new FunctionListChangeListenerImpl<>( 
               this::portionAdded, this::portionRemoved 
      ) );
   }//End Constructor
   
   /**
    * Method to triggered when a {@link FoodPortion} is added.
    * @param portion the {@link FoodPortion} added.
    */
   private void portionAdded( FoodPortion portion ) {
      portion.food().addListener( foodNotifier );
      portion.portion().addListener( valueNotifier );
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         portion.nutritionFor( macro ).addListener( valueNotifier );
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
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         portion.nutritionFor( macro ).removeListener( valueNotifier );
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
    * Access to the {@link FoodPortion}s in the {@link Meal}.
    * @return the {@link FoodPortion}s.
    */
   public ObservableList< FoodPortion > portions() {
      return portions;
   }//End Method

   /**
    * Method to listen for {@link Meal} changes.
    * @param listener the {@link MealChangeListener} to register.
    */
   public void listen( MealChangeListener listener ) {
      this.listeners.add( listener );
   }//End Method

}//End Class
