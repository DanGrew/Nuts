/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.day;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.LinkedHashSet;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import uk.dangrew.kode.concept.Properties;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.database.FoodTypes;
import uk.dangrew.nuts.meal.FoodHolder;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.meal.TargetedFoodHolder;
import uk.dangrew.nuts.template.Template;

/**
 * The {@link DayPlan} provides a specific {@link Template} for a day of the year.
 */
public class DayPlan extends Template implements TargetedFoodHolder {

   static final double DEFAULT_CONSUMED_CALORIES = 0.0;
   static final double DEFAULT_ALLOWED_CALORIES = 0.0;
   static final double DEFAULT_CALORIE_BALANCE = 0.0;
   static final boolean DEFAULT_BALANCE_IS_RESET = false;
   
   private final ObservableSet< FoodPortion > consumed; 
   private LocalDate date;
   
   public DayPlan( LocalDate date ) {
      this( date.toString() );
      setDate( date );
   }//End Constructor
   
   public DayPlan( String name ) {
      this( new Properties( name ) );
   }//End Constructor
   
   public DayPlan( String id, String name ) {
      this( new Properties( id, name ) );
   }//End Constructor
   
   private DayPlan( Properties properties ) {
      super( properties );
      this.consumed = FXCollections.observableSet( new LinkedHashSet<>() );
      portions().addListener( new FunctionListChangeListenerImpl<>( null, consumed::remove ) );
   }//End Constructor

   public void setDate( LocalDate date ) {
      if ( this.date != null ) {
         throw new IllegalStateException( "Date can only be set once." );
      }
      this.date = date;
   }//End Method
   
   public LocalDate date() {
      return date;
   }//End Method

   public ObservableSet< FoodPortion > consumed() {
      return consumed;
   }//End Method
   
   @Override public void swap( FoodPortion portion1, FoodPortion portion2 ) {
      boolean consumedFirst = consumed().contains( portion1 );
      boolean consumedSecond = consumed().contains( portion2 );
      super.swap( portion1, portion2 );
      if ( consumedFirst ) consumed().add( portion1 );
      if ( consumedSecond ) consumed().add( portion2 );
   }//End Method
   
   @Override public DayPlan duplicate() {
      return this;
   }//End Method
   
   void remove( FoodPortion toRemove ) {
      remove( toRemove, this );
   }//End Method
   
   void remove( FoodPortion toRemove, FoodHolder from ) {
      for ( Iterator< FoodPortion > iterator = from.portions().iterator(); iterator.hasNext(); ) {
         FoodPortion next = iterator.next();
         if ( next == toRemove ) {
            iterator.remove();
            return;
         }
         FoodTypes.ofType( next.food().get(), Meal.class ).ifPresent( meal -> remove( toRemove, meal ) );
      }
   }//End Method

}//End Class
