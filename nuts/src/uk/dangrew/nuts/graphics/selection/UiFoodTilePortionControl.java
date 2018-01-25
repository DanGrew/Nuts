package uk.dangrew.nuts.graphics.selection;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.Conversions;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.food.FoodPortion;

public class UiFoodTilePortionControl extends GridPane {

   private final FoodPortion portion;
   private final TextField portionField;
   private final ChangeListener< Double > portionUpdater; 
   
   public UiFoodTilePortionControl( FoodPortion portion ) {
      
      Conversions conversion = new Conversions();
      JavaFxStyle stying = new JavaFxStyle();
      
      stying.configureConstraintsForEvenRows( this, 1 );
      stying.configureConstraintsForColumnPercentages( this, 20, 60, 20 );
      
      Button decrease = new Button( "<" );
      decrease.setOnAction( e -> portion.setPortion( portion.portion().get() - 25 ) );
      decrease.setMaxSize( Double.MAX_VALUE, Double.MAX_VALUE );
      add( decrease, 0, 0 );
      
      this.portionField = new TextField( conversion.doubleToStringFunction().apply( portion.portion().get() ) );
      this.portionUpdater = ( s, o, n ) -> 
            portionField.setText( conversion.doubleToStringFunction().apply( n ) );
            
      this.portion = portion;
      this.portion.portion().addListener( portionUpdater );
      
      this.portionField.textProperty().addListener( ( s, o, n ) -> 
            portion.setPortion( conversion.stringToDoubleFunction().apply( n ) ) 
      );
      this.portionField.setMaxSize( Double.MAX_VALUE, Double.MAX_VALUE );
      this.portionField.setAlignment( Pos.CENTER );
      add( portionField, 1, 0 );
      
      Button increase = new Button( ">" );
      increase.setOnAction( e -> portion.setPortion( portion.portion().get() + 25 ) );
      increase.setMaxSize( Double.MAX_VALUE, Double.MAX_VALUE );
      add( increase, 2, 0 );
   }//End Constructor

   public void detach() {
      portion.portion().removeListener( portionUpdater );
   }//End Method

   TextField portionField() {
      return portionField;
   }//End Method
   
}//End Class
