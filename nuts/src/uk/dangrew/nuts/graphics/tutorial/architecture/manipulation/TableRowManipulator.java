package uk.dangrew.nuts.graphics.tutorial.architecture.manipulation;

import javafx.scene.Node;
import javafx.scene.control.TableRow;

public class TableRowManipulator< TableRowT > {
   
   private final TableRow< TableRowT > row;
   
   public TableRowManipulator( TableRow< TableRowT > row ) {
      this.row = row;
   }//End Method
   
   public TableRow< TableRowT > node() {
      return row;
   }//End Method
   
   public Node cell( int column ) {
      if ( column >= node().getChildrenUnmodifiable().size() ) {
         return null;
      }
      return node().getChildrenUnmodifiable().get( column );
   }//End Method
   
}//End Class
