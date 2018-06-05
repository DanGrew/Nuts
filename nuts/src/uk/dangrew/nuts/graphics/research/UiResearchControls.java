package uk.dangrew.nuts.graphics.research;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.research.ResearchArticle;

public class UiResearchControls extends GridPane {

   public UiResearchControls( UiResearchController controller ) {
      JavaFxStyle styling = new JavaFxStyle();
      styling.configureConstraintsForEvenColumns( this, 1 );
      
      styling.configureConstraintsForEvenRows( 
               this, 
               Math.max( controller.articles().size(), 10 ) 
      );
      
      int row = 0;
      for ( ResearchArticle article : controller.articles() ) {
         Button button = new Button( article.properties().nameProperty().get() );
         button.setMaxWidth( Double.MAX_VALUE );
         button.setOnAction( e -> controller.show( article ) );
         add( button, 0, row++ );
      }
   }//End Constructor
}//End Class
