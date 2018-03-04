package uk.dangrew.nuts.apis.tesco.graphics.selection;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.kode.javafx.style.LabelBuilder;

public class UiTescoFoodSelectionControls extends BorderPane {
   
   private final GridPane filterWrapper;
   private final TextField searchTextField;
   private final Button searchButton;

   public UiTescoFoodSelectionControls( UiTescoPortionOptionsController controller ) {
      this.filterWrapper = new GridPane();
      JavaFxStyle stying = new JavaFxStyle();
      stying.configureConstraintsForEvenRows( filterWrapper, 1 );
      stying.configureConstraintsForColumnPercentages( filterWrapper, 10, 20, 5, 65 );
      this.filterWrapper.add( 
               new LabelBuilder()
                  .withText( "Search Tesco:" )
                  .asBold()
                  .build(), 
      0, 0 );
      
      this.searchTextField = new TextField();
      this.filterWrapper.add( searchTextField, 1, 0 );
      
      this.searchButton = new Button( "Search" );
      this.searchButton.setOnAction( e -> controller.search( searchTextField.getText() ) );
      this.filterWrapper.add( searchButton, 2, 0 );

      this.setPadding( new Insets( 10 ) );
      this.setCenter( filterWrapper );
   }//End Constructor

   TextField searchTextField() {
      return searchTextField;
   }//End Method

   Button searchButton() {
      return searchButton;
   }//End Method

}//End Class
