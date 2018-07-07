package uk.dangrew.nuts.graphics.table.configuration;

import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.function.Function;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.control.cell.ComboBoxTreeTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import uk.dangrew.kode.javafx.spinner.StringExtractConverter;
import uk.dangrew.kode.javafx.table.TreeTableEditCommitHandler;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;

public class TreeTableColumnConfigurer< ConceptTypeT, DataTypeT, RowTypeT extends ConceptTableRow< ConceptTypeT > > implements TableColumnConfigurer< ConceptTypeT, DataTypeT >{
   
   private TreeTableView< RowTypeT > table;
   private TreeTableColumn< RowTypeT, DataTypeT > column;
   
   public TreeTableColumnConfigurer( TreeTableView< RowTypeT > table ) {
      this.table = table;
      this.column = new TreeTableColumn<>();
      this.table.getColumns().add( column );
   }//End Constructor
   
   private < S, T > TreeTableColumn< S, T > refine() {
      return ( TreeTableColumn< S, T > ) column;
   }//End Method
   
   @Override public void setTitle( String title ) {
      column.setText( title );
   }//End Method

   @Override public void bindPrefWidth( double widthProportion ) {
      column.prefWidthProperty().bind( table.widthProperty().multiply( widthProportion ) );
   }//End Method
   
   @Override public void setCellValueFactory( Function< ConceptTypeT, ? extends ObservableValue< DataTypeT > > propertyRetriever ) {
      column.setCellValueFactory( object -> propertyRetriever.apply( object.getValue().getValue().concept() ) );
   }//End Method
   
   @Override public void setFixedCellValueFactory( Function< ConceptTypeT, DataTypeT > propertyRetriever ) {
      column.setCellValueFactory( object -> new SimpleObjectProperty<>( propertyRetriever.apply( object.getValue().getValue().concept() ) ) );
   }//End Method
   
   @Override public void setCellValueFactoryAsString( Function< ConceptTypeT, ? extends ReadOnlyObjectProperty< Double > > propertyRetriever ) {
      TreeTableColumn< ConceptTableRow< ConceptTypeT >, String > refined = refine();
      refined.setCellValueFactory( object -> propertyRetriever.apply( object.getValue().getValue().concept() ).asString() );
   }//End Method
   
   @Override public void setTextFieldCellFactory(){
      TreeTableColumn< ConceptTypeT, String > refined = refine();
      refined.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
   }//End Method
   
   @Override public void setComboBoxCellFactory( StringExtractConverter< DataTypeT > converter, ObservableList< DataTypeT > choices ) {
      column.setCellFactory( ComboBoxTreeTableCell.forTreeTableColumn( converter, choices ) ); 
   }//End Method
   
   @Override public void setCheckBoxCellFactory() {
      TreeTableColumn< ConceptTypeT, Boolean > refined = refine();
      refined.setCellFactory( CheckBoxTreeTableCell.forTreeTableColumn( refined ) );
   }//End Method
   
   @Override public void setOnEditCommit( BiConsumer< ConceptTypeT, DataTypeT > consumer ){
      column.setOnEditCommit( new TreeTableEditCommitHandler<>( ( r, v ) -> consumer.accept( r.concept(), v ) ) );
   }//End Method
   
   @Override public void setEditable( boolean editable ) {
      column.setEditable( editable );
   }//End Method

   @Override public void setComparator( Comparator< DataTypeT > comparator ) {
      column.comparatorProperty().set( comparator );
   }//End Method

}//End Class
