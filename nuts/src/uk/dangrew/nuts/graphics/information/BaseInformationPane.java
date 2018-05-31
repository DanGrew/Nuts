/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.information;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import uk.dangrew.kode.friendly.javafx.FriendlyDesktop;
import uk.dangrew.kode.javafx.style.JavaFxStyle;

public abstract class BaseInformationPane extends BorderPane {
   
   private final FriendlyDesktop desktop;
   private final VBox textWrapper;
   
   public BaseInformationPane() {
      this.desktop = new FriendlyDesktop();
      this.setPadding( new Insets( 10.0 ) );
      this.textWrapper = new VBox();
      
      GridPane wrapper = new GridPane();
      new JavaFxStyle().configureConstraintsForColumnPercentages( wrapper, 10, 80, 10 );
      wrapper.add( textWrapper, 1, 0 );
      
      this.setCenter( wrapper );
      this.populateText( textWrapper );
   }//End Constructor
   
   protected abstract void populateText( VBox textWrapper );
   
   protected FriendlyDesktop desktop(){
      return desktop;
   }//End Method
   
}//End Class
