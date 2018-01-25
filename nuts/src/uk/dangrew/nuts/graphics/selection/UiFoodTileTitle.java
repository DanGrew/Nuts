package uk.dangrew.nuts.graphics.selection;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import uk.dangrew.kode.javafx.style.LabelBuilder;
import uk.dangrew.nuts.food.Food;

public class UiFoodTileTitle extends BorderPane {

   private final Food food;
   private final Label label;
   private final ChangeListener< String > nameListener;
   
   public UiFoodTileTitle( Food food ) {
      this.food = food;
      this.label = new LabelBuilder()
               .withText( food.properties().nameProperty().get() )
               .withFont( 16, true )
               .withWrappedText()
               .positioned( Pos.CENTER )
               .build();
      this.setCenter( label );
      
      this.nameListener = ( s, o, n ) -> label.setText( n );
      this.food.properties().nameProperty().addListener( nameListener );
   }//End Constructor

   public void detach() {
      food.properties().nameProperty().removeListener( nameListener );
   }//End Method

   Label label() {
      return label;
   }//End Method
   
}//End Class
