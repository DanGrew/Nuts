package uk.dangrew.nuts.graphics.label;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.label.Label;
import uk.dangrew.nuts.label.LabelStore;
import uk.dangrew.nuts.label.Labelables;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.system.Concept;

public class UiLabelConfigurationView extends GridPane {
   
   public UiLabelConfigurationView( LabelStore labels, Database database ) {
      UiLabelController controller = new UiLabelController( database );
      
      JavaFxStyle styling = new JavaFxStyle();
      styling.configureConstraintsForRowPercentages( this, 10, 90 );
      styling.configureConstraintsForColumnPercentages( this, 48, 4, 48 );
      
      ListView< Concept > selectedLabelConcepts = new ListView<>( controller.selectedLabelConcepts() );
      selectedLabelConcepts.getSelectionModel().setSelectionMode( SelectionMode.MULTIPLE );
      styling.setStringConverter( selectedLabelConcepts, c -> c.properties().nameProperty().get() );
      controller.monitorLabelSelection( selectedLabelConcepts.getSelectionModel().getSelectedItems() );
      add( selectedLabelConcepts, 0, 1 );
      
      ListView< Concept > selectedConceptTypes = new ListView<>( controller.selectedDatabaseConcepts() );
      selectedConceptTypes.getSelectionModel().setSelectionMode( SelectionMode.MULTIPLE );
      styling.setStringConverter( selectedConceptTypes, c -> c.properties().nameProperty().get() );
      controller.monitorDatabaseSelection( selectedConceptTypes.getSelectionModel().getSelectedItems() );
      add( selectedConceptTypes, 2, 1 );
      
      ComboBox< Label > labelSelection = new ComboBox<>( labels.objectList() );
      add( labelSelection, 0, 0 );
      labelSelection.getSelectionModel().selectedItemProperty().addListener( ( s, o, n ) -> controller.selectLabel( n ) );
      
      ComboBox< Labelables > conceptSelection = new ComboBox<>( FXCollections.observableArrayList( Labelables.values() ) );
      add( conceptSelection, 2, 0 );
      conceptSelection.getSelectionModel().selectedItemProperty().addListener( ( s, o, n ) -> controller.selectLabelable( n ) );
      
      add( new UiLabelControls( controller ), 1, 1 );
   }//End Constructor

}//End Class
