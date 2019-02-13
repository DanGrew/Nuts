package uk.dangrew.nuts.graphics.graph.custom;

import javafx.scene.layout.BorderPane;

public class GraphWithSettings extends BorderPane {

   private final Graph graph;
   private final GraphSettings settings;
   
   public GraphWithSettings( GraphBuilder graphBuilder ) {
      this.setCenter( graph = new Graph( graphBuilder ) );
      this.setRight( settings = new GraphSettings( graph.controller(), "Graph" ) );
   }//End Constructor
   
   public Graph graph() {
      return graph;
   }//End Method
   
}//End Class
