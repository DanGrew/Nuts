package uk.dangrew.nuts.graphics.database;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.selection.model.SimpleFoodModel;

public class CheckBoxFoodModelController {

   private final SimpleFoodModel model;
   private final Map< Food, BooleanProperty > foodProperties;
   private final Map< BooleanProperty, Food > propertyFoods;
   private final ChangeListener< Boolean > booleanBinding;
   
   public CheckBoxFoodModelController( SimpleFoodModel model ) {
      this.foodProperties = new HashMap<>();
      this.propertyFoods = new HashMap<>();
      this.model = model;
      this.booleanBinding = this::applyFood;
      
      this.model.concepts().addListener( new FunctionListChangeListenerImpl<>( 
                this::foodAdded, this::foodRemoved
      ) );
   }//End Constructor
   
   private void applyFood( ObservableValue< ? extends Boolean > property, boolean o, boolean included ) {
      if ( included ) {
         model.add( propertyFoods.get( property ) );
      } else {
         model.remove( propertyFoods.get( property ) );
      }
   }//End Method
   
   private void foodAdded( Food food ) {
      propertyFor( food ).set( true ); 
   }//End Method
   
   private void foodRemoved( Food food ) {
      propertyFor( food ).set( false );
   }//End Method
   
   public BooleanProperty propertyFor( Food food ) {
      if ( !foodProperties.containsKey( food ) ) {
         BooleanProperty property = new SimpleBooleanProperty(false );
         foodProperties.put( food, property );
         propertyFoods.put( property, food );
         property.addListener( booleanBinding );
      }
      return foodProperties.get( food );
   }//End Method
   
}//End Class
