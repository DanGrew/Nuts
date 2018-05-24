package uk.dangrew.nuts.graphics.tutorial.architecture;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TutorHighlight extends Rectangle {

   static final Color OPAQUE = Color.RED;
   static final Color TRANSPARENT = Color.TRANSPARENT;
   static final double STROKE_WIDTH = 4;
   
   public TutorHighlight() {
      this.setStrokeWidth( STROKE_WIDTH );
      this.setFill( TRANSPARENT );
      this.hide();
   }//End Constructor
   
   public void hide(){
      this.setStroke( TRANSPARENT );
   }//End Method
   
   public void focus( Node subject ) {
      Bounds bounds = subject.getLayoutBounds();
      Point2D coordinates = subject.localToScene( bounds.getMinX(), bounds.getMinY() );
      
      this.setX( coordinates.getX() );
      this.setY( coordinates.getY() );
      this.setWidth( bounds.getWidth() );
      this.setHeight( bounds.getHeight() );
      this.setStroke( OPAQUE );
   }//End Method
   
}//End Class
