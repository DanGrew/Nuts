package uk.dangrew.nuts.graphics.tutorial.architecture;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class MouseLocationConverter {

   public boolean containedInScene( MouseEvent event, Node node ) {
      return node.localToScene( node.getBoundsInLocal() ).contains( event.getSceneX(), event.getSceneY() );
   }//End Method
   
}//End Class
