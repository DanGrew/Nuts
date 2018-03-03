package uk.dangrew.nuts.graphics.selection;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import uk.dangrew.kode.javafx.style.LabelBuilder;
import uk.dangrew.nuts.system.Concept;

public class UiFoodTileConceptTitle extends BorderPane {

   private final Concept concept;
   private final Label label;
   private final ChangeListener< String > nameListener;
   
   public UiFoodTileConceptTitle( Concept concept ) {
      this.concept = concept;
      this.label = new LabelBuilder()
               .withText( concept.properties().nameProperty().get() )
               .withFont( 16, true )
               .withWrappedText()
               .positioned( Pos.CENTER )
               .build();
      this.setCenter( label );
      
      this.nameListener = ( s, o, n ) -> label.setText( n );
      this.concept.properties().nameProperty().addListener( nameListener );
   }//End Constructor

   public void detach() {
      concept.properties().nameProperty().removeListener( nameListener );
   }//End Method

   Label label() {
      return label;
   }//End Method
   
}//End Class
