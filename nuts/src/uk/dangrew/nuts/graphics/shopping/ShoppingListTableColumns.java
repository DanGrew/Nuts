/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.shopping;

import javafx.scene.control.TableColumn;
import uk.dangrew.nuts.graphics.table.TableConfiguration;

/**
 * {@link ShoppingListTableColumns} provides the {@link javafx.scene.control.TableColumn} configuration 
 * for a {@link ShoppingListTable}.
 */
public class ShoppingListTableColumns {

   static final String COLUMN_TITLE_FOOD = "Food";
   static final String COLUMN_TITLE_TYPE = "Type";
   static final String COLUMN_TITLE_LOGGED_IN = "Logged In (g/ml)";
   static final String COLUMN_TITLE_SOLD_IN = "Sold In (g/ml)";
   static final String COLUMN_TITLE_REQUIRED = "Required (g/ml)";
   static final String COLUMN_TITLE_TO_BUY = "To Buy";
   
   private final TableConfiguration configuration;

   /**
    * Constructs a new {@link ShoppingListTableColumns}.
    */
   public ShoppingListTableColumns() {
      this.configuration = new TableConfiguration();
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   public void populateColumns( ShoppingListTable table ) {
      TableColumn< ShoppingListRow, String > column = new TableColumn<>( COLUMN_TITLE_FOOD );
      column.prefWidthProperty().bind( table.widthProperty().multiply( 0.4 ) );
      column.setCellValueFactory( object -> object.getValue().food().properties().nameProperty() );
      column.setEditable( false );
      table.getColumns().add( column );
      
      configuration.initialiseDoubleColumn( table, COLUMN_TITLE_LOGGED_IN, 0.15, r -> r.food().stockProperties().loggedWeight(), true );
      configuration.initialiseDoubleColumn( table, COLUMN_TITLE_SOLD_IN, 0.15, r -> r.food().stockProperties().soldInWeight(), true );

      configuration.initialiseDoubleColumn( table, COLUMN_TITLE_REQUIRED, 0.15, ShoppingListRow::requiredWeight, false );
      configuration.initialiseDoubleColumn( table, COLUMN_TITLE_TO_BUY, 0.15, ShoppingListRow::toBuy, false );
   }//End Method
   
}//End Class
