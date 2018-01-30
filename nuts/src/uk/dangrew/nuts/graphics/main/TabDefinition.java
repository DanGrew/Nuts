package uk.dangrew.nuts.graphics.main;

import javafx.scene.Node;

public class TabDefinition {

   private final String title;
   private final Node node;
   
   public TabDefinition( String title, Node node ) {
      this.title = title;
      this.node = node;
   }//End Constructor

   public Node node() {
      return node;
   }//End Method

   public String title() {
      return title;
   }//End Method

}//End Class
