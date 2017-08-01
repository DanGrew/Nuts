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

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.kode.observable.PrivatelyModifiableObservableMapImpl;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodItemStore;

/**
 * {@link DayPlans} provides the {@link DayPlan} for each {@link LocalDate} in the {@link CalendarPeriod}.
 */
public class DayPlans {

   private final ObservableMap< LocalDate, DayPlan > dayPlans;
   private final PrivatelyModifiableObservableMapImpl< LocalDate, DayPlan > publicPlans;
   
   private FoodItemStore store;
   private CalendarPeriod period;
   
   /**
    * Constructs a new {@link DayPlans}.
    */
   public DayPlans() {
      this.dayPlans = FXCollections.observableHashMap();
      this.publicPlans = new PrivatelyModifiableObservableMapImpl<>( dayPlans );
   }//End Class
   
   /**
    * Associates with the given {@link CalendarPeriod} and {@link FoodItemStore}. Only allowed once.
    * @param period the {@link CalendarPeriod}.
    * @param store the {@link FoodItemStore}.
    */
   public void associate( CalendarPeriod period, FoodItemStore store ) {
      if ( this.period != null ) {
         throw new IllegalStateException( "Already Associated." );
      }
      
      this.period = period;
      this.store = store;
      
      this.period.days().forEach( this::dayAddedToPeriod );
      this.period.days().addListener( new FunctionListChangeListenerImpl<>( 
               this::dayAddedToPeriod, this::dayRemovedFromPeriod 
      ) );
      
      this.store.objectList().forEach( this::foodAddedToStore );
      this.store.objectList().addListener( new FunctionListChangeListenerImpl<>( 
               this::foodAddedToStore, this::foodRemovedFromStore 
      ) );
   }//End Class
   
   /**
    * Triggered when a day is added to the {@link CalendarPeriod}.
    * @param date the {@link LocalDate}.
    */
   private void dayAddedToPeriod( LocalDate date ) {
      if ( dayPlans.containsKey( date ) ) {
         return;
      }
      DayPlan plan = new DayPlan( date );
      store.objectList().forEach( plan::stockFor );
      
      dayPlans.put( plan.date(), plan );
   }//End Method
   
   /**
    * Triggered when a day is removed from the {@link CalendarPeriod}.
    * @param date the day added.
    */
   private void dayRemovedFromPeriod( LocalDate date ) {
      dayPlans.remove( date );
   }//End Method
   
   /**
    * Triggered when a {@link FoodItem} is added to the {@link FoodItemStore}.
    * @param item the {@link FoodItem} added.
    */
   private void foodAddedToStore( FoodItem item ) {
      dayPlans.values().forEach( p -> p.stock( item ) );
   }//End Method

   /**
    * Triggered when a {@link FoodItem} is removed from the {@link FoodItemStore}.
    * @param item the {@link FoodItem} removed.
    */
   private void foodRemovedFromStore( FoodItem item ) {
      dayPlans.values().forEach( p -> p.remove( item ) );
   }//End Method
   
   /**
    * Access to the {@link DayPlan} for the given {@link LocalDate} from the {@link CalendarPeriod}.
    * @param date the {@link LocalDate}.
    * @return the {@link DayPlan}.
    */
   public DayPlan dayPlanFor( LocalDate date ) {
      return dayPlans.get( date );
   }//End Method

   /**
    * Access to the {@link DayPlan}s.
    * @return the underlying structure, not modifiable.
    */
   public ObservableMap< LocalDate, DayPlan > dayPlans() {
      return dayPlans;
   }//End Method

}//End Class
