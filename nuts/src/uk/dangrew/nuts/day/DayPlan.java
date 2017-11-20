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

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.template.Template;

/**
 * The {@link DayPlan} provides a specific {@link Template} for a day of the year.
 */
public class DayPlan extends Template {

   private final ObservableSet< FoodPortion > consumed; 
   private LocalDate date;
   
   public DayPlan( LocalDate date ) {
      this( date.toString() );
      setDate( date );
   }//End Constructor
   
   public DayPlan( String name ) {
      this( new FoodProperties( name ) );
   }//End Constructor
   
   public DayPlan( String id, String name ) {
      this( new FoodProperties( id, name ) );
   }//End Constructor
   
   DayPlan( FoodProperties properties ) {
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

}//End Class
