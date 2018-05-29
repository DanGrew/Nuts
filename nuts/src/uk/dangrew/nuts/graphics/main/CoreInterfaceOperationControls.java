package uk.dangrew.nuts.graphics.main;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;

public class CoreInterfaceOperationControls extends GridPane {

   private final Button saveButton;
   private final Button tutorialsButton;
   
   public CoreInterfaceOperationControls( CoreInterfaceOperations operations ) {
      JavaFxStyle styling = new JavaFxStyle();
      styling.configureConstraintsForEvenRows( this, 1 );
      styling.configureConstraintsForColumnPercentages( this, 60, 20, 20 );
      
      this.saveButton = new Button( "Save" );
      this.saveButton.setMaxWidth( Double.MAX_VALUE );
      this.saveButton.setOnAction( e -> operations.save() );
      
      this.tutorialsButton = new Button( "Tutorials" );
      this.tutorialsButton.setMaxWidth( Double.MAX_VALUE );
      this.tutorialsButton.setOnAction( e -> operations.showTutorial() );
      
      this.add( saveButton, 1, 0 );
      this.add( tutorialsButton, 2, 0 );
   }//End Constructor
   
}//End Class
