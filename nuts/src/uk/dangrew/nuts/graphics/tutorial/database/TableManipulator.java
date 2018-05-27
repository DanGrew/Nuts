package uk.dangrew.nuts.graphics.tutorial.database;

import java.util.List;
import java.util.function.Function;

import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import uk.dangrew.kode.javafx.hacks.JavaFxHacks;

public class TableManipulator< TableRowT, RowManipulatorT extends TableRowManipulator< TableRowT > > {
   
   private final JavaFxHacks hacks;
   private final TableView< TableRowT > table;
   private final Function< TableRow< TableRowT >, RowManipulatorT > rowManipulatorSupplier; 
   
   public TableManipulator( 
            TableView< TableRowT > table,
            Function< TableRow< TableRowT >, RowManipulatorT > rowManipulatorSupplier 
   ) {
      this( new JavaFxHacks(), table, rowManipulatorSupplier );
   }//End Constructor
   
   TableManipulator(
            JavaFxHacks hacks,
            TableView< TableRowT > table,
            Function< TableRow< TableRowT >, RowManipulatorT > rowManipulatorSupplier 
   ) {
      this.hacks = hacks;
      this.table = table;
      this.rowManipulatorSupplier = rowManipulatorSupplier;
   }//End Constructor
   
   public RowManipulatorT row( int i ) {
      List< TableRow< TableRowT > > rows = hacks.lookupTableRows( table );
      if ( rows.isEmpty() || i >= rows.size() ) {
         return rowManipulatorSupplier.apply( null );
      } else {
         return rowManipulatorSupplier.apply( rows.get( i ) );
      }
   }//End Method

   public void triggerCellEdit( int row, int column ) {
      table.edit( row, table.getColumns().get( column ) );
   }//End Method
   
   public void finishCellEdit() {
      table.edit( -1, null );
   }//End Method

}//End Class
