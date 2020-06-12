/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.progress.custom;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import uk.dangrew.kode.javafx.table.base.FriendlyTableView;

public class ProgressSeriesDataTable extends TableView< ProgressSeriesDataRow > implements FriendlyTableView< ProgressSeriesDataRow > {

   private final ProgressSeriesDataController controller;
   
   public ProgressSeriesDataTable() {
      this( new ProgressSeriesDataController() );
   }//End Constructor
   
   ProgressSeriesDataTable( ProgressSeriesDataController controller ) {
      this.controller = controller;
      this.controller.associate( this );
      this.setEditable( true );
      new ProgressSeriesDataTableColumns().populateColumns( this );
   }//End Constructor
   
   public ObservableList< ProgressSeriesDataRow > getRows(){
      return getItems();
   }//End Method
   
   public ProgressSeriesDataController controller(){
      return controller;
   }//End Method
}//End Class
