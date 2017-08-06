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
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import uk.dangrew.kode.comparator.Comparators;
import uk.dangrew.kode.javafx.spinner.StringExtractConverter;
import uk.dangrew.kode.javafx.table.EditCommitHandler;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.food.FoodProperties;

/**
 * {@link TableConfiguration} provides configuration for the {@link FoodTable}.
 */
public class TableConfiguration {
   
   /**
    * Method to initialise a {@link TableColumn} with the given properties/behaviour for a {@link String} value.
    * @param table the {@link TableView} to configure.
    * @param title the title of the {@link TableColumn}.
    * @param widthProportion the proportion of the width the {@link TableColumn} should be.
    * @param propertyRetriever the {@link Function} to retrieve the value from the {@link FoodProperties}.
    * @param editable whether the column should be editable.
    */
   public < FoodTypeT extends Food > void initialiseStringColumn( 
            TableView< FoodTableRow< FoodTypeT > > table,
            String title, 
            double widthProportion,
            Function< FoodProperties, ObjectProperty< String > > propertyRetriever, 
            boolean editable
   ){
      TableColumn< FoodTableRow< FoodTypeT >, String > column = new TableColumn<>( title );
      column.prefWidthProperty().bind( table.widthProperty().multiply( widthProportion ) );
      column.setCellValueFactory( object -> propertyRetriever.apply( object.getValue().food().properties() ) );
      
      column.setCellFactory(TextFieldTableCell.forTableColumn());
      column.setOnEditCommit( new EditCommitHandler<>( ( r, v ) -> 
               propertyRetriever.apply( r.food().properties() ).set( v )
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
            TableView< FoodTableRow< FoodTypeT > > table,
            String title, 
            double widthProportion,
            Function< FoodTypeT, ObjectProperty< Double > > propertyRetriever,
            boolean editable
   ){
      initialiseDoubleColumn( 
               table, 
               title, 
               widthProportion, 
               row -> propertyRetriever.apply( row.food() ), 
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
                  propertyRetriever.apply( r ).set( Double.valueOf( v ) )
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
            TableView< FoodTableRow< FoodTypeT > > table,
            String title, 
            double widthProportion,
            Function< FoodTypeT, ReadOnlyObjectProperty< Double > > propertyRetriever 
   ){
      TableColumn< FoodTableRow< FoodTypeT >, String > column = new TableColumn<>( title );
      column.prefWidthProperty().bind( table.widthProperty().multiply( widthProportion ) );
      column.setCellValueFactory( object -> propertyRetriever.apply( object.getValue().food() ).asString() );
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
   public < RowTypeT > void initialiseFoodDropDownColumn(
            TableView< RowTypeT > table,
            String title, 
            double widthProportion,
            Function< RowTypeT, ObservableValue< Food > > propertyRetriever,
            BiConsumer< RowTypeT, Food > propertySetter,
            FoodOptions foodOptions
   ){
      TableColumn< RowTypeT, Food > nameColumn = new TableColumn<>( title );
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
            FoodTable< FoodPortion > table,
            String title, 
            double widthProportion
   ){
      TableColumn< FoodTableRow< FoodPortion >, String > portionColumn = new TableColumn<>( title );
      portionColumn.prefWidthProperty().bind( table.widthProperty().multiply( widthProportion ) );
      portionColumn.setCellValueFactory( object -> object.getValue().food().portion().asString() );
      table.getColumns().add( portionColumn );
      
      portionColumn.setEditable( true );
      portionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
      portionColumn.setOnEditCommit( new EditCommitHandler<>( ( r, v ) -> r.food().setPortion( Double.valueOf( v ) ) ) );
   }//End Method

}//End Class
