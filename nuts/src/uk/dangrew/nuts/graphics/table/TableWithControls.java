/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.table;

import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.system.Concept;

public class TableWithControls< RowTypeT, BackingConceptT extends Concept > extends TitledPane {

   static final String NO_CONTENT_INFORMATION = 
            "No content to display. Use the '+' and '-' to add and remove. Double click on the name to change it.";
   
   private final TableView< RowTypeT > table;
   private final ConceptControls controls;
   
   public TableWithControls( String title, TableView< RowTypeT > table, ConceptTableController< BackingConceptT > controller ) {
      this( new JavaFxStyle(), title, table, new ConceptControls( controller ) );
   }//End Constructor
   
   public TableWithControls( String title, TableView< RowTypeT > table, ConceptControls controls ) {
      this( new JavaFxStyle(), title, table, controls );
   }//End Constructor
   
   public TableWithControls( 
            JavaFxStyle styling, 
            String title, 
            TableView< RowTypeT > table,
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

   public TableView< RowTypeT > table() {
      return table;
   }//End Method

   public ConceptControls controls(){
      return controls;
   }//End Method
   
   BorderPane content(){
      return ( BorderPane ) getContent();
   }//End Method
   
}//End Class
