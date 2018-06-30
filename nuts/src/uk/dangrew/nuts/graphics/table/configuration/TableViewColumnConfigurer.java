package uk.dangrew.nuts.graphics.table.configuration;

import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.function.Function;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import uk.dangrew.kode.javafx.spinner.StringExtractConverter;
import uk.dangrew.kode.javafx.table.TableViewEditCommitHandler;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;

public class TableViewColumnConfigurer< ConceptTypeT, DataTypeT > implements TableColumnConfigurer< ConceptTypeT, DataTypeT > {
   
   private TableView< ConceptTableRow< ConceptTypeT > > table;
   private TableColumn< ConceptTableRow< ConceptTypeT >, DataTypeT > column;
   
   public TableViewColumnConfigurer( TableView< ConceptTableRow< ConceptTypeT > > table ) {
      this( table, new TableColumn<>() );
   }//End Constructor
   
   TableViewColumnConfigurer( 
            TableView< ConceptTableRow< ConceptTypeT > > table,
            TableColumn< ConceptTableRow< ConceptTypeT >, DataTypeT > column
   ) {
      this.table = table;
      this.column = column;
      this.table.getColumns().add( column );
   }//End Constructor
   
   private < S, T > TableColumn< S, T > refine() {
      return ( TableColumn< S, T > ) column;
   }//End Method
   
   @Override public void setTitle( String title ) {
      column.setText( title );
   }//End Method

   @Override public void bindPrefWidth( double widthProportion ) {
      column.prefWidthProperty().bind( table.widthProperty().multiply( widthProportion ) );
   }//End Method
   
   @Override public void setCellValueFactory( Function< ConceptTypeT, ? extends ObservableValue< DataTypeT > > propertyRetriever ) {
      column.setCellValueFactory( object -> propertyRetriever.apply( object.getValue().concept() ) );
   }//End Method
   
   @Override public void setFixedCellValueFactory( Function< ConceptTypeT, DataTypeT > propertyRetriever ) {
      column.setCellValueFactory( object -> new SimpleObjectProperty<>( propertyRetriever.apply( object.getValue().concept() ) ) );
   }//End Method
   
   @Override public void setCellValueFactoryAsString( Function< ConceptTypeT, ? extends ReadOnlyObjectProperty< Double > > propertyRetriever ) {
      TableColumn< ConceptTableRow< ConceptTypeT >, String > refined = refine();
      refined.setCellValueFactory( object -> propertyRetriever.apply( object.getValue().concept() ).asString() );
   }//End Method
   
   @Override public void setTextFieldCellFactory(){
      TableColumn< ConceptTypeT, String > refined = refine();
      refined.setCellFactory(TextFieldTableCell.forTableColumn());
   }//End Method
   
   @Override public void setComboBoxCellFactory( StringExtractConverter< DataTypeT > converter, ObservableList< DataTypeT > choices ) {
      column.setCellFactory( ComboBoxTableCell.forTableColumn( converter, choices ) ); 
   }//End Method
   
   @Override public void setCheckBoxCellFactory() {
      TableColumn< ConceptTypeT, Boolean > refined = refine();
      refined.setCellFactory( CheckBoxTableCell.forTableColumn( refined ) );
   }//End Method
   
   @Override public void setOnEditCommit( BiConsumer< ConceptTypeT, DataTypeT > consumer ){
      column.setOnEditCommit( new TableViewEditCommitHandler<>( ( r, v ) -> consumer.accept( r.concept(), v ) ) );
   }//End Method
   
   @Override public void setEditable( boolean editable ) {
      column.setEditable( editable );
   }//End Method
   
   @Override public void setComparator( Comparator< DataTypeT > comparator ) {
      column.comparatorProperty().set( comparator );
   }//End Method

}//End Class
