package uk.dangrew.nuts.graphics.tutorial.database;

import java.util.List;
import java.util.function.BiFunction;

import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Pair;
import uk.dangrew.kode.javafx.hacks.JavaFxHacks;

public class TableManipulator< TableRowT, RowManipulatorT extends TableRowManipulator< TableRowT > > {
   
   protected final JavaFxHacks hacks;
   protected final TableView< TableRowT > table;
   private final BiFunction< TableRow< TableRowT >, Integer, RowManipulatorT > rowManipulatorSupplier; 
   
   public TableManipulator( 
            TableView< TableRowT > table,
            BiFunction< TableRow< TableRowT >, Integer, RowManipulatorT > rowManipulatorSupplier 
   ) {
      this( new JavaFxHacks(), table, rowManipulatorSupplier );
   }//End Constructor
   
   TableManipulator(
            JavaFxHacks hacks,
            TableView< TableRowT > table,
            BiFunction< TableRow< TableRowT >, Integer, RowManipulatorT > rowManipulatorSupplier 
   ) {
      this.hacks = hacks;
      this.table = table;
      this.rowManipulatorSupplier = rowManipulatorSupplier;
   }//End Constructor
   
   public RowManipulatorT row( int i ) {
      List< TableRow< TableRowT > > rows = hacks.lookupTableRows( table );
      if ( rows.isEmpty() || i >= rows.size() ) {
         return null;
      } else {
         return rowManipulatorOf( rows.get( i ), i );
      }
   }//End Method
   
   protected RowManipulatorT rowManipulatorOf( TableRow< TableRowT > row, int rowIndex ) {
      return rowManipulatorSupplier.apply( row, rowIndex );
   }//End Method
   
   protected RowManipulatorT rowManipulatorOf( Pair< TableRow< TableRowT >, Integer > pair ) {
      if ( pair == null ) {
         return null;
      }
      return rowManipulatorOf( pair.getKey(), pair.getValue() );
   }//End Method

   public void triggerCellEdit( int row, int column ) {
      table.edit( row, table.getColumns().get( column ) );
   }//End Method
   
   public void finishCellEdit() {
      table.edit( -1, null );
   }//End Method

}//End Class
