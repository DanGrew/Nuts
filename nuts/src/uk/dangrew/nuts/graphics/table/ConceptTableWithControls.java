/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.table;

import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.system.Concept;

/**
 * {@link ConceptTableWithControls} provides a {@link ConceptTable} with {@link ConceptControls}.
 */
public class ConceptTableWithControls< TypeT extends Concept > extends TitledPane {

   static final String NO_CONTENT_INFORMATION = 
            "No content to display. Use the '+' and '-' to add and remove. Double click on the name to change it.";
   
   private final ConceptTable< TypeT > table;
   private final ConceptControls controls;
   
   public ConceptTableWithControls( String title, ConceptTable< TypeT > table ) {
      this( new JavaFxStyle(), title, table, new ConceptControls( table.controller() ) );
   }//End Constructor
   
   public ConceptTableWithControls( String title, ConceptTable< TypeT > table, ConceptControls controls ) {
      this( new JavaFxStyle(), title, table, controls );
   }//End Constructor
   
   public ConceptTableWithControls( 
            JavaFxStyle styling, 
            String title, 
            ConceptTable< TypeT > table,
            ConceptControls controls
   ) {
      super( title, new BorderPane() );
      this.table = table;
      this.controls = controls;
      this.setCollapsible( false );
      this.setMaxHeight( Double.MAX_VALUE );
      this.setMaxWidth( Double.MAX_VALUE );
      
      BorderPane content = ( BorderPane ) getContent();
      content.setCenter( table );
      content.setRight( controls );
      
      table.setPlaceholder( styling.createWrappedTextLabel( NO_CONTENT_INFORMATION ) );
   }//End Constructor

   public ConceptTable< TypeT > table() {
      return table;
   }//End Method

   ConceptControls controls(){
      return controls;
   }//End Method
   
   BorderPane content(){
      return ( BorderPane ) getContent();
   }//End Method
   
}//End Class
