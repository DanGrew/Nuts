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
import java.util.LinkedHashSet;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.system.Properties;
import uk.dangrew.nuts.template.Template;

/**
 * The {@link DayPlan} provides a specific {@link Template} for a day of the year.
 */
public class DayPlan extends Template {

   static final double DEFAULT_CONSUMED_CALORIES = 0.0;
   static final double DEFAULT_ALLOWED_CALORIES = 0.0;
   static final double DEFAULT_CALORIE_BALANCE = 0.0;
   static final boolean DEFAULT_BALANCE_IS_RESET = false;
   
   private final ObservableSet< FoodPortion > consumed; 
   private final ObjectProperty< Double > consumedCalories;
   private final ObjectProperty< Double > allowedCalories;
   private final ObjectProperty< Double > calorieBalance;
   private final ObjectProperty< Boolean > isBalanceReset;
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
   
   DayPlan( Properties properties ) {
      super( properties );
      this.consumedCalories = new SimpleObjectProperty<>( DEFAULT_CONSUMED_CALORIES );
      this.allowedCalories = new SimpleObjectProperty<>( DEFAULT_ALLOWED_CALORIES );
      this.calorieBalance = new SimpleObjectProperty<>( DEFAULT_CALORIE_BALANCE );
      this.isBalanceReset = new SimpleObjectProperty<>( DEFAULT_BALANCE_IS_RESET );
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
   
   public ObjectProperty< Double > consumedCalories() {
      return consumedCalories;
   }//End Method
   
   public ObjectProperty< Double > allowedCalories() {
      return allowedCalories;
   }//End Method
   
   public ObjectProperty< Double > calorieBalance() {
      return calorieBalance;
   }//End Method
   
   public ObjectProperty< Boolean > isBalanceReset() {
      return isBalanceReset;
   }//End Method
   
   @Override public void swap( FoodPortion portion1, FoodPortion portion2 ) {
      boolean consumedFirst = consumed().contains( portion1 );
      boolean consumedSecond = consumed().contains( portion2 );
      super.swap( portion1, portion2 );
      if ( consumedFirst ) consumed().add( portion1 );
      if ( consumedSecond ) consumed().add( portion2 );
   }//End Method
   
   @Override public Template duplicate( String referenceId ) {
      return this;
   }//End Method

}//End Class
