package uk.dangrew.nuts.graphics.day;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import uk.dangrew.kode.observable.FunctionSetChangeListenerImpl;
import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.food.FoodPortion;

public class ConsumptionProperties {

   private final Map< FoodPortion, BooleanProperty > consumption;
   private final Map< BooleanProperty, FoodPortion > properties;
   private final ChangeListener< Boolean > propertyListener;
   private final FunctionSetChangeListenerImpl< FoodPortion > synchronizer;
   
   private DayPlan dayPlan;
   
   public ConsumptionProperties() {
      this.consumption = new HashMap<>();
      this.properties = new HashMap<>();
      this.propertyListener = ( s, o, n ) -> propertyChangedFor( s );
      this.synchronizer = new FunctionSetChangeListenerImpl<>( 
               this::updateConsumption, this::updateConsumption 
      );
   }//End Constructor
   
   private void updateConsumption( FoodPortion portion ) {
      if ( !consumption.containsKey( portion ) ) {
         BooleanProperty property = new SimpleBooleanProperty( false );
         consumption.put( portion, property );
         properties.put( property, portion );
         property.addListener( propertyListener );
      }
      consumption.get( portion ).set( dayPlan.consumed().contains( portion ) );
   }//End Method
   
   public void setDayPlan( DayPlan dayPlan ) {
      if ( this.dayPlan != null ) {
         this.dayPlan.consumed().removeListener( synchronizer );
      }
      this.dayPlan = dayPlan;
      this.dayPlan.consumed().addListener( synchronizer );
      this.consumption.clear();
      this.dayPlan.portions().forEach( this::updateConsumption );
   }//End Method
   
   public BooleanProperty propertyFor( FoodPortion portion ) {
      return consumption.get( portion );
   }//End Method
   
   public void propertyChangedFor( ObservableValue< ? extends Boolean > property ) {
      FoodPortion portion = properties.get( property );
      if ( property.getValue() ) {
         dayPlan.consumed().add( portion );
      } else {
         dayPlan.consumed().remove( portion );
      }
   }//End Method
   
}//End Class
