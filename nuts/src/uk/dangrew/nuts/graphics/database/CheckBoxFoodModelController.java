package uk.dangrew.nuts.graphics.database;

import javafx.beans.value.ObservableValue;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.common.CheckBoxController;
import uk.dangrew.nuts.graphics.common.CheckBoxControllerImpl;
import uk.dangrew.nuts.graphics.selection.model.SimpleFoodModel;

public class CheckBoxFoodModelController extends CheckBoxControllerImpl< Food > implements CheckBoxController< Food >{

   private final SimpleFoodModel model;
   
   public CheckBoxFoodModelController( SimpleFoodModel model ) {
      this.model = model;
      this.model.concepts().addListener( new FunctionListChangeListenerImpl<>( 
                this::foodAdded, this::foodRemoved
      ) );
   }//End Constructor
   
   @Override protected boolean getModelValue( Food concept ) {
      return model.concepts().contains( concept );
   }//End Method
   
   @Override protected void apply( ObservableValue< ? extends Boolean > property, boolean o, boolean included ) {
      if ( included ) {
         model.add( conceptFor( property ) );
      } else {
         model.remove( conceptFor( property ) );
      }
   }//End Method
   
   private void foodAdded( Food food ) {
      propertyFor( food ).set( true ); 
   }//End Method
   
   private void foodRemoved( Food food ) {
      propertyFor( food ).set( false );
   }//End Method
   
}//End Class
