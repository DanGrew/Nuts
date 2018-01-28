package uk.dangrew.nuts.graphics.selection;

import org.controlsfx.control.textfield.TextFields;

import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.kode.javafx.style.LabelBuilder;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.food.FoodStringConverter;

public class UiFoodSelectionControls extends BorderPane {

   private final GridPane filterWrapper;
   private final ComboBox< Food > filterBox;
   private final ComboBox< FoodSelectionTypes > sortBox;
   private final CheckBox invertSorting;
   
   public UiFoodSelectionControls( ObservableList< Food > foods, UiFoodSelectionController controller ) {
      this.invertSorting = new CheckBox( "Reverse Sort" );
      this.invertSorting.selectedProperty().addListener( ( s, o, n ) -> controller.invertSort( n ) );
      
      this.filterWrapper = new GridPane();
      JavaFxStyle stying = new JavaFxStyle();
      stying.configureConstraintsForEvenRows( filterWrapper, 1 );
      stying.configureConstraintsForColumnPercentages( filterWrapper, 10, 40, 5, 10, 35 );
      this.filterWrapper.add( 
               new LabelBuilder()
                  .withText( "Filter:" )
                  .asBold()
                  .build(), 
      0, 0 );
      
      this.filterBox = new ComboBox<>( foods );
      FoodStringConverter converter = new FoodStringConverter( foods );
      TextFields.bindAutoCompletion( filterBox.getEditor(), SuggestionProvider.create( foods ), converter );
      this.filterBox.setConverter( converter );
      this.filterBox.setEditable( true );
      this.filterBox.getEditor().textProperty().addListener( ( s, o, n ) -> controller.filterOptions( n ) );
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
      
      this.setPadding( new Insets( 10 ) );
      this.setLeft( filterWrapper );
      this.setRight( invertSorting );
   }//End Constructor

   CheckBox sortingBox() {
      return invertSorting;
   }//End Method

   ComboBox< Food > filterTextField() {
      return filterBox;
   }//End Method

   ComboBox< FoodSelectionTypes > sortBox() {
      return sortBox;
   }//End Method
   
}//End Class
