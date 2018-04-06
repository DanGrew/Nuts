package uk.dangrew.nuts.graphics.selection.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;

public class UiFoodSelectionWindowControls extends GridPane {

   private final Button apply;
   private final Button cancel;
   
   public UiFoodSelectionWindowControls( UiFoodSelectionController selectionController, FoodSelectionWindowStageControls stageControls ) {
      JavaFxStyle styling = new JavaFxStyle();
      styling.configureConstraintsForEvenRows( this, 1 );
      styling.configureConstraintsForEvenColumns( this, 5 );
      
      this.add( apply = new Button( "Apply" ), 3, 0 );
      this.add( cancel = new Button( "Cancel" ), 4, 0 );
      
      this.apply.setMaxSize( Double.MAX_VALUE, Double.MAX_VALUE );
      this.cancel.setMaxSize( Double.MAX_VALUE, Double.MAX_VALUE );
      
      this.apply.setOnAction( e -> stageControls.apply( selectionController.getAndClearSelection() ) );
      this.cancel.setOnAction( e -> stageControls.cancel() );
      
      this.setPadding( new Insets( 5 ) );
   }//End Constructor
   
   Button cancelButton(){
      return cancel;
   }//End Method
   
   Button applyButton(){
      return apply;
   }//End Method
   
}//End Class
