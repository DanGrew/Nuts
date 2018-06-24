/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.table;

import java.util.function.BiConsumer;
import java.util.function.Function;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import uk.dangrew.kode.comparator.Comparators;
import uk.dangrew.kode.javafx.spinner.StringExtractConverter;
import uk.dangrew.kode.javafx.style.Conversions;
import uk.dangrew.kode.javafx.table.EditCommitHandler;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.common.CheckBoxController;
import uk.dangrew.nuts.system.Concept;
import uk.dangrew.nuts.system.Properties;

/**
 * {@link TableConfiguration} provides configuration for the {@link FoodTable}.
 */
public class TableConfiguration {
   
   private final static Conversions conversions = new Conversions();
   
   /**
    * Method to initialise a {@link TableColumn} with the given properties/behaviour for a {@link String} value.
    * @param table the {@link TableView} to configure.
    * @param title the title of the {@link TableColumn}.
    * @param widthProportion the proportion of the width the {@link TableColumn} should be.
    * @param propertyRetriever the {@link Function} to retrieve the value from the {@link FoodProperties}.
    * @param editable whether the column should be editable.
    */
   public < FoodTypeT extends Food > void initialiseFoodPropertyStringColumn( 
            TableView< ConceptTableRow< FoodTypeT > > table,
            String title, 
            double widthProportion,
            Function< Properties, ObjectProperty< String > > propertyRetriever, 
            boolean editable
   ){
      initialiseStringColumn( 
               table, title, widthProportion, 
               f -> propertyRetriever.apply( f.properties() ), 
               editable 
      );
   }//End Method
   
   /**
    * Method to initialise a {@link TableColumn} with the given properties/behaviour for a {@link String} value.
    * @param table the {@link TableView} to configure.
    * @param title the title of the {@link TableColumn}.
    * @param widthProportion the proportion of the width the {@link TableColumn} should be.
    * @param propertyRetriever the {@link Function} to retrieve the value from the {@link FoodProperties}.
    * @param editable whether the column should be editable.
    */
   public < TypeT extends Concept > void initialiseStringColumn( 
            TableView< ConceptTableRow< TypeT > > table,
            String title, 
            double widthProportion,
            Function< TypeT, ObjectProperty< String > > propertyRetriever, 
            boolean editable
   ){
      TableColumn< ConceptTableRow< TypeT >, String > column = new TableColumn<>( title );
      column.prefWidthProperty().bind( table.widthProperty().multiply( widthProportion ) );
      column.setCellValueFactory( object -> propertyRetriever.apply( object.getValue().concept() ) );
      
      column.setCellFactory(TextFieldTableCell.forTableColumn());
      column.setOnEditCommit( new EditCommitHandler<>( ( r, v ) -> 
               propertyRetriever.apply( r.concept() ).set( v )
      ) );
      column.setEditable( editable );
      table.getColumns().add( column );
   }//End Method
   
   /**
    * Method to initialise a {@link TableColumn} with the given properties/behaviour for a {@link String} value.
    * @param table the {@link TableView} to configure.
    * @param title the title of the {@link TableColumn}.
    * @param width the proportion of the width the {@link TableColumn} should be.
    * @param propertyRetriever the {@link Function} to retrieve the value from the {@link FoodProperties}.
    */
   public < RowTypeT > void initialiseStringColumn(
            TableView< RowTypeT > table,
            String title,
            double width,
            Function< RowTypeT, String > propertyetriever
   ){
      TableColumn< RowTypeT, String > date = new TableColumn<>( title );
      date.prefWidthProperty().bind( table.widthProperty().multiply( width ) );
      date.setCellValueFactory( object -> new SimpleObjectProperty<>( propertyetriever.apply( object.getValue() ) ) );
      date.setEditable( false );
      table.getColumns().add( date );
   }//End Method
   
   /**
    * Method to initialise a {@link TableColumn} with the given properties/behaviour for a {@link ObjectProperty}.
    * @param table the {@link TableView} to configure.
    * @param title the title of the {@link TableColumn}.
    * @param widthProportion the proportion of the width the {@link TableColumn} should be.
    * @param propertyRetriever the {@link Function} to retrieve the value from the {@link FoodProperties}.
    * @param editable whether the column is editable.
    */
   public < FoodTypeT extends Food > void initialiseNutrientColumn(
            TableView< ConceptTableRow< FoodTypeT > > table,
            String title, 
            double widthProportion,
            Function< FoodTypeT, ObjectProperty< Double > > propertyRetriever,
            boolean editable
   ){
      initialiseDoubleColumn( 
               table, 
               title, 
               widthProportion, 
               row -> propertyRetriever.apply( row.concept() ), 
               editable 
      );
   }//End Method
   
   /**
    * Method to initialise a {@link TableColumn} with the given properties/behaviour for a {@link ObjectProperty}.
    * @param table the {@link TableView} to configure.
    * @param title the title of the {@link TableColumn}.
    * @param widthProportion the proportion of the width the {@link TableColumn} should be.
    * @param propertyRetriever the {@link Function} to retrieve the value from the row.
    * @param editable whether the column is editable.
    */
   public < RowTypeT > void initialiseDoubleColumn(
            TableView< RowTypeT > table,
            String title, 
            double widthProportion,
            Function< RowTypeT, ObjectProperty< Double > > propertyRetriever,
            boolean editable
   ){
      TableColumn< RowTypeT, String > column = new TableColumn<>( title );
      column.prefWidthProperty().bind( table.widthProperty().multiply( widthProportion ) );
      column.setCellValueFactory( object -> propertyRetriever.apply( object.getValue() ).asString() );
      column.comparatorProperty().set( Comparators.STRING_AS_NUMBER_ASCENDING );
      
      if ( editable ) {
         column.setCellFactory(TextFieldTableCell.forTableColumn());
         column.setOnEditCommit( new EditCommitHandler<>( ( r, v ) -> 
                  propertyRetriever.apply( r ).set( conversions.nullableStringToDoubleFunction().apply( v ) )
         ) );
      }
      table.getColumns().add( column );
   }//End Method
   
   /**
    * Method to initialise a {@link TableColumn} with the given properties/behaviour for a ratio of a {@link NutrientValue}.
    * @param table the {@link TableView} to configure.
    * @param title the title of the {@link TableColumn}.
    * @param widthProportion the proportion of the width the {@link TableColumn} should be.
    * @param propertyRetriever the {@link Function} to retrieve the value from the {@link Food}.
    */
   public < FoodTypeT extends Food > void initialiseRatioColumn(
            TableView< ConceptTableRow< FoodTypeT > > table,
            String title, 
            double widthProportion,
            Function< FoodTypeT, ReadOnlyObjectProperty< Double > > propertyRetriever 
   ){
      TableColumn< ConceptTableRow< FoodTypeT >, String > column = new TableColumn<>( title );
      column.prefWidthProperty().bind( table.widthProperty().multiply( widthProportion ) );
      column.setCellValueFactory( object -> propertyRetriever.apply( object.getValue().concept() ).asString() );
      column.comparatorProperty().set( Comparators.STRING_AS_NUMBER_ASCENDING );
      table.getColumns().add( column );
   }//End Method
   
   /**
    * Method to initialise a {@link TableColumn} with a drop down {@link javafx.scene.control.ComboBox} for the {@link Food}.
    * @param table the {@link TableView} to configure.
    * @param title the title of the {@link TableColumn}.
    * @param widthProportion the proportion of the width the {@link TableColumn} should be.
    * @param propertyRetriever the {@link Function} to retrieve the food from the row.
    * @param propertySetter the {@link BiConsumer} to set the {@link Food} when selected.
    * @param foodOptions the {@link FoodOptions} for the {@link javafx.scene.control.ComboBox}.
    */
   public < RowTypeT, FoodTypeT extends Food > void initialiseFoodDropDownColumn(
            TableView< RowTypeT > table,
            String title, 
            double widthProportion,
            Function< RowTypeT, ObservableValue< FoodTypeT > > propertyRetriever,
            BiConsumer< RowTypeT, FoodTypeT > propertySetter,
            ConceptOptions< FoodTypeT > foodOptions
   ){
      TableColumn< RowTypeT, FoodTypeT > nameColumn = new TableColumn<>( title );
      nameColumn.setCellFactory( ComboBoxTableCell.forTableColumn( new StringExtractConverter<>( 
               object -> object.properties().nameProperty().get(),
               foodOptions::find,
               "None"
      ), foodOptions.options() ) );
      nameColumn.setCellValueFactory( object -> propertyRetriever.apply( object.getValue() ) );
      nameColumn.prefWidthProperty().bind( table.widthProperty().multiply( widthProportion ) );
      nameColumn.setOnEditCommit( new EditCommitHandler<>( propertySetter::accept ) );
      table.getColumns().add( nameColumn );
   }//End Method
   
   /**
    * Method to initialise a {@link TableColumn} with a {@link FoodPortion#portion()} configuration.
    * @param table the {@link TableView} to configure.
    * @param title the title of the {@link TableColumn}.
    * @param widthProportion the proportion of the width the {@link TableColumn} should be.
    */
   public void initialisePortionColumn(
            ConceptTable< FoodPortion > table,
            String title, 
            double widthProportion
   ){
      TableColumn< ConceptTableRow< FoodPortion >, String > portionColumn = new TableColumn<>( title );
      portionColumn.prefWidthProperty().bind( table.widthProperty().multiply( widthProportion ) );
      portionColumn.setCellValueFactory( object -> object.getValue().concept().portion().asString() );
      table.getColumns().add( portionColumn );
      
      portionColumn.setEditable( true );
      portionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
      portionColumn.setOnEditCommit( new EditCommitHandler<>( ( r, v ) -> r.concept().setPortion( Double.valueOf( v ) ) ) );
   }//End Method
   
   public < TypeT extends Concept > void configureCheckBoxController(
            ConceptTable< TypeT > table,
            CheckBoxController< TypeT > controller,
            double widthProportion
   ) {
      TableColumn< ConceptTableRow< TypeT >, Boolean > column = new TableColumn<>();
      column.prefWidthProperty().bind( table.widthProperty().multiply( widthProportion ) );
      column.setCellValueFactory( param -> controller.propertyFor( param.getValue().concept() ) );
      column.setCellFactory( CheckBoxTableCell.forTableColumn( column ) );
      column.setEditable( true );
      table.getColumns().add( column );
   }//End Method

}//End Class
