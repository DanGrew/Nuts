/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.table.food;

import java.util.function.Function;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import uk.dangrew.kode.javafx.table.EditCommitHandler;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.food.GoalAnalytics;

/**
 * {@link FoodTableConfiguration} provides configuration for the {@link FoodTable}.
 */
public class FoodTableConfiguration {
   
   /**
    * Method to initialise a {@link TableColumn} with the given properties/behaviour for a {@link String} value.
    * @param table the {@link TableView} to configure.
    * @param title the title of the {@link TableColumn}.
    * @param widthProportion the proportion of the width the {@link TableColumn} should be.
    * @param propertyRetriever the {@link Function} to retrieve the value from the {@link FoodProperties}.
    */
   public < FoodTypeT extends Food > void initialStringColumn( 
            TableView< FoodTableRow< FoodTypeT > > table,
            String title, 
            double widthProportion,
            Function< FoodProperties, ObjectProperty< String > > propertyRetriever 
   ){
      TableColumn< FoodTableRow< FoodTypeT >, String > column = new TableColumn<>( title );
      column.prefWidthProperty().bind( table.widthProperty().multiply( widthProportion ) );
      column.setCellValueFactory( object -> propertyRetriever.apply( object.getValue().food().properties() ) );
      
      column.setCellFactory(TextFieldTableCell.forTableColumn());
      column.setOnEditCommit( new EditCommitHandler<>( ( r, v ) -> 
               propertyRetriever.apply( r.food().properties() ).set( v )
      ) );
      table.getColumns().add( column );
   }//End Method
   
   /**
    * Method to initialise a {@link TableColumn} with the given properties/behaviour for a {@link ObjectProperty}.
    * @param table the {@link TableView} to configure.
    * @param title the title of the {@link TableColumn}.
    * @param widthProportion the proportion of the width the {@link TableColumn} should be.
    * @param propertyRetriever the {@link Function} to retrieve the value from the {@link FoodProperties}.
    * @param editable whether the column is editable.s
    */
   public < FoodTypeT extends Food > void initialNutrientColumn(
            TableView< FoodTableRow< FoodTypeT > > table,
            String title, 
            double widthProportion,
            Function< FoodProperties, ObjectProperty< Double > > propertyRetriever,
            boolean editable
   ){
      TableColumn< FoodTableRow< FoodTypeT >, String > column = new TableColumn<>( title );
      column.prefWidthProperty().bind( table.widthProperty().multiply( widthProportion ) );
      column.setCellValueFactory( object -> propertyRetriever.apply( object.getValue().food().properties() ).asString() );
      
      if ( editable ) {
         column.setCellFactory(TextFieldTableCell.forTableColumn());
         column.setOnEditCommit( new EditCommitHandler<>( ( r, v ) -> 
                  propertyRetriever.apply( r.food().properties() ).set( Double.valueOf( v ) )
         ) );
      }
      table.getColumns().add( column );
   }//End Method
   
   /**
    * Method to initialise a {@link TableColumn} with the given properties/behaviour for a ratio of a {@link NutrientValue}.
    * @param table the {@link TableView} to configure.
    * @param title the title of the {@link TableColumn}.
    * @param widthProportion the proportion of the width the {@link TableColumn} should be.
    * @param propertyRetriever the {@link Function} to retrieve the value from the {@link FoodAnalytics}.
    */
   public < FoodTypeT extends Food > void initialRatioColumn(
            TableView< FoodTableRow< FoodTypeT > > table,
            String title, 
            double widthProportion,
            Function< GoalAnalytics, ReadOnlyObjectProperty< Double > > propertyRetriever 
   ){
      TableColumn< FoodTableRow< FoodTypeT >, String > column = new TableColumn<>( title );
      column.prefWidthProperty().bind( table.widthProperty().multiply( widthProportion ) );
      column.setCellValueFactory( object -> propertyRetriever.apply( object.getValue().food().goalAnalytics() ).asString() );
      table.getColumns().add( column );
   }//End Method

}//End Class
