package uk.dangrew.nuts.graphics.selection.view;

import java.util.function.Consumer;

import org.controlsfx.control.CheckComboBox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.kode.javafx.style.LabelBuilder;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.graphics.selection.model.FoodFilters;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionTypes;
import uk.dangrew.nuts.label.Label;

public class UiFoodSelectionControls extends BorderPane {

   private final GridPane filterWrapper;
   private final TextField filterBox;
   private final ComboBox< FoodSelectionTypes > sortBox;
   private final CheckComboBox< FoodFilters > filterOptionsBox;
   private final CheckComboBox< Label > labelsOptionsBox;
   private final CheckBox invertSorting;
   
   public UiFoodSelectionControls( ObservableList< Label > labels, UiFoodFilter controller ) {
      this( labels, controller, new FoodSelectionControlsConfiguration() );
   }//End Constructor
   
   public UiFoodSelectionControls( ObservableList< Label > labels, UiFoodFilter controller, FoodSelectionControlsConfiguration configuration ) {
      this.filterWrapper = new GridPane();
      JavaFxStyle stying = new JavaFxStyle();
      stying.configureConstraintsForEvenRows( filterWrapper, 1 );
      stying.configureConstraintsForColumnPercentages( filterWrapper, 5, 20, 5, 5, 10, 5, 5, 10, 5, 5, 10, 5, 10 );
      this.filterWrapper.add( 
               new LabelBuilder()
                  .withText( "Filter:" )
                  .asBold()
                  .build(), 
      0, 0 );
      
      this.filterBox = new TextField();
      this.filterBox.textProperty().addListener( ( s, o, n ) -> controller.filterOptions( n ) );
      this.filterBox.setFocusTraversable( false );
      this.filterWrapper.add( filterBox, 1, 0 );
      
      this.filterWrapper.add( 
               new LabelBuilder()
                  .withText( "Sort:" )
                  .asBold()
                  .build(), 
      3, 0 );
      
      this.sortBox = new ComboBox<>( FXCollections.observableArrayList( FoodSelectionTypes.values() ) );
      this.sortBox.getSelectionModel().selectedItemProperty().addListener( ( s, o, n ) -> controller.useSelectionType( n ) );
      this.sortBox.getSelectionModel().select( FoodSelectionTypes.All );
      this.filterWrapper.add( sortBox, 4, 0 );
      
      this.filterWrapper.add( 
               new LabelBuilder()
                  .withText( "Filters:" )
                  .asBold()
                  .build(), 
      6, 0 );
      this.filterOptionsBox = new CheckComboBox<>( FXCollections.observableArrayList( configuration.filtersAllowed() ) );
      Consumer< FoodFilters > filterApplier = f -> controller.applyFilters( filterOptionsBox.getCheckModel().getCheckedItems() );
      this.filterOptionsBox.getCheckModel().getCheckedItems().addListener( new FunctionListChangeListenerImpl<>( 
                filterApplier, filterApplier
      ) );
      this.filterWrapper.add( filterOptionsBox, 7, 0 );
      
      this.filterWrapper.add( 
               new LabelBuilder()
                  .withText( "Labels:" )
                  .asBold()
                  .build(), 
      9, 0 );
      this.labelsOptionsBox = new CheckComboBox<>( labels );
      Consumer< Label > labelApplier = f -> controller.applyLabels( labelsOptionsBox.getCheckModel().getCheckedItems() );
      this.labelsOptionsBox.getCheckModel().getCheckedItems().addListener( new FunctionListChangeListenerImpl<>( 
               labelApplier, labelApplier
      ) );
      this.filterWrapper.add( labelsOptionsBox, 10, 0 );

      if ( configuration.allowReverseSorting() ) {
         this.invertSorting = new CheckBox( "Reverse Sort" );
         this.invertSorting.selectedProperty().addListener( ( s, o, n ) -> controller.invertSort( n ) );
         this.filterWrapper.add( invertSorting, 12, 0 );
      } else {
         this.invertSorting = null;
      }
      
      this.setPadding( new Insets( 10 ) );
      this.setCenter( filterWrapper );
   }//End Constructor

   CheckBox sortingBox() {
      return invertSorting;
   }//End Method

   TextField filterTextField() {
      return filterBox;
   }//End Method

   ComboBox< FoodSelectionTypes > sortBox() {
      return sortBox;
   }//End Method
   
   CheckComboBox< FoodFilters > filterBox() {
      return filterOptionsBox;
   }//End Method
   
   CheckComboBox< Label > labelBox() {
      return labelsOptionsBox;
   }//End Method
   
}//End Class
