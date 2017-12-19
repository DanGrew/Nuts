package uk.dangrew.nuts.graphics.cycle;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import uk.dangrew.nuts.cycle.Cycle;

public class UiCycleConfigurationPane extends BorderPane {

   public void setCycle( Cycle cycle ) {
      Node configurationPane = cycle.type().constructConfigurationPane( cycle );
      setCenter( configurationPane );
   }//End Method
}//End Class
