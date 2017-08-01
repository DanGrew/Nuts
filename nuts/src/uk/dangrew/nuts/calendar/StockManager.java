/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.calendar;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import uk.dangrew.kode.observable.FunctionMapAnyKeyChangeListenerImpl;
import uk.dangrew.kode.observable.FunctionMapChangeListenerImpl;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodItemStore;
import uk.dangrew.nuts.meal.Meal;

/**
 * {@link StockManager} is responsible for monitoring the data in the system and responding to
 * appropriate changes by recalculating the stock for {@link FoodItem}s.
 */
public class StockManager {
   
   private final StockCalculator calculator;
   private final ChangeListener< Meal > planChangeListener;
   private final FunctionMapAnyKeyChangeListenerImpl< FoodItem, Double > stockChangeListener;
   
   private FoodCalendar calendar;
   private FoodItemStore foods;
   
   /**
    * Constructs a new {@link StockManager}.
    */
   public StockManager() {
      this( new StockCalculator() );
   }//End Constructor
   
   /**
    * Constructs a new {@link StockManager}.
    * @param calculator the {@link StockCalculator}.
    */
   StockManager( StockCalculator calculator ) {
      this.calculator = calculator;
      this.planChangeListener = ( s, o, n ) -> dayPlanMealChanged( o, n );
      this.stockChangeListener = new FunctionMapAnyKeyChangeListenerImpl<>( this::stockChanged );
   }//End Constructor

   /**
    * Associates with the given. Only called once.
    * @param calendar the {@link FoodCalendar}.
    * @param foods the {@link FoodItemStore}.
    */
   void associate( FoodCalendar calendar, FoodItemStore foods ) {
      if ( this.calendar != null || this.foods != null ) {
         throw new IllegalStateException( "Already associated." );
      }
      
      this.calendar = calendar;
      this.foods = foods;
      this.calculator.associate( calendar.dayPlans(), calendar.period() );
      
      recalculateFor( foods.objectList() );
      this.calendar.dayPlans().dayPlans().forEach( ( d, p ) -> listenToDayPlan( p ) );
      this.calendar.dayPlans().dayPlans().addListener( new FunctionMapChangeListenerImpl<>( 
               this.calendar.dayPlans().dayPlans(), 
               this::dayPlanAdded, this::dayPlanRemoved 
      ) );
   }//End Class
   
   /**
    * Recalculates all stock for all {@link FoodItem}s given.
    * @param foods the {@link FoodItem}s to calculate for.
    */
   private void recalculateFor( Collection< FoodItem > foods ){
      foods.forEach( calculator::recalculate );
   }//End Method
   
   /**
    * Monitors the given {@link DayPlan} with the appropriate listener.
    * @param plan the {@link DayPlan} to listen to.
    */
   private void listenToDayPlan( DayPlan plan ) {
      plan.plan().addListener( planChangeListener );
   }//End Method
   
   /**
    * Triggered when a {@link DayPlan} is added to the {@link FoodCalendar}.
    * @param date the {@link LocalDate} of the {@link DayPlan}.
    * @param plan the {@link DayPlan} added.
    */
   private void dayPlanAdded( LocalDate date, DayPlan plan ){
      listenToDayPlan( plan );
      recalculateFor( foods.objectList() );
   }//End Method
   
   /**
    * Triggered when a {@link DayPlan} is removed from the {@link FoodCalendar}.
    * @param date the {@link LocalDate} of the {@link DayPlan}.
    * @param plan the {@link DayPlan} removed.
    */
   private void dayPlanRemoved( LocalDate date, DayPlan plan ){
      plan.plan().removeListener( planChangeListener );
      recalculateFor( foods.objectList() );
   }//End Method
   
   /**
    * Triggered when a {@link Meal} changes in the {@link DayPlan}.
    * @param old the previous {@link Meal}.
    * @param updated the new {@link Meal}.
    */
   private void dayPlanMealChanged( Meal old, Meal updated ) {
      Set< FoodItem > foodsAffected = new LinkedHashSet<>();
      if ( old != null ) {
         old.stockUsage().stock().removeListener( stockChangeListener );
         foodsAffected.addAll( old.stockUsage().stock().keySet() );
      }
      if ( updated != null ) {
         updated.stockUsage().stock().addListener( stockChangeListener );
         foodsAffected.addAll( updated.stockUsage().stock().keySet() );
      }
      recalculateFor( foodsAffected );
   }//End Method

   /**
    * Triggered when the stock in a {@link Meal} changes for the given {@link FoodItem}.
    * @param item the {@link FoodItem} stock changed for.
    */
   private void stockChanged( FoodItem item ) {
      recalculateFor( Collections.singleton( item ) );
   }//End Method
}//End Class
