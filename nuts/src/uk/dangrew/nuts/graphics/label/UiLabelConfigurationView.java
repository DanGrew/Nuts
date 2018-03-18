package uk.dangrew.nuts.graphics.label;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.kode.javafx.style.LabelBuilder;
import uk.dangrew.nuts.label.Label;
import uk.dangrew.nuts.label.Labelables;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.system.Concept;

public class UiLabelConfigurationView extends GridPane {
   
   public UiLabelConfigurationView( Database database ) {
      UiLabelController controller = new UiLabelController( database );
      
      JavaFxStyle styling = new JavaFxStyle();
      styling.configureConstraintsForRowPercentages( this, 10, 90, 5 );
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
      
      ComboBox< Label > labelSelection = new ComboBox<>( database.labels().objectList() );
      labelSelection.getSelectionModel().selectedItemProperty().addListener( ( s, o, n ) -> controller.selectLabel( n ) );
      HBox labelSelectionContainer = new HBox( 
               10,
               new LabelBuilder().asBold().withText( "Label" ).build(), 
               labelSelection 
      );
      labelSelectionContainer.setPadding( new Insets( 10 ) );
      labelSelectionContainer.setAlignment( Pos.CENTER_LEFT );
      add( labelSelectionContainer, 0, 0 );
      
      ComboBox< Labelables > conceptSelection = new ComboBox<>( FXCollections.observableArrayList( Labelables.values() ) );
      conceptSelection.getSelectionModel().selectedItemProperty().addListener( ( s, o, n ) -> controller.selectLabelable( n ) );
      HBox conceptSelectionContainer = new HBox( 
               10,  
               new LabelBuilder().asBold().withText( "Concepts" ).build(), 
               conceptSelection 
      );
      conceptSelectionContainer.setPadding( new Insets( 10 ) );
      conceptSelectionContainer.setAlignment( Pos.CENTER_LEFT );
      add( conceptSelectionContainer, 2, 0 );
      
      add( new UiLabelControls( controller ), 1, 1 );
      add( new UiLabelCreationControls( controller ), 0, 2 );
   }//End Constructor

}//End Class
