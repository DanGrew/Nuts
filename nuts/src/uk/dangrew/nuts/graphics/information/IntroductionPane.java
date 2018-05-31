package uk.dangrew.nuts.graphics.information;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class IntroductionPane extends BorderPane {

   public IntroductionPane() {
      VBox content = new VBox();
      
      content.getChildren().add( new UnderstandingBodyAndHealthIntroductionPane() );
      content.getChildren().add( new StandardDietIntroductionPane() );
      
      ScrollPane scroller = new ScrollPane( content );
      scroller.setFitToWidth( true );
      scroller.setBackground( new Background( new BackgroundFill( Color.TRANSPARENT, null, null ) ) );
      this.setCenter( scroller );
   }//End Constructor
   
}//End Class
