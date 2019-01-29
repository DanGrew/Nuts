package uk.dangrew.nuts.graphics.table.configuration;

import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.function.Function;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import uk.dangrew.kode.javafx.spinner.StringExtractConverter;

public interface TableColumnConfigurer< ConceptTypeT, DataTypeT > {

   public void setTitle( String title );

   public void bindPrefWidth( double widthProportion );

   public void setCellValueFactory( 
            Function< ConceptTypeT, ? extends ObservableValue< DataTypeT > > propertyRetriever 
   );
   
   public void setFixedCellValueFactory( 
            Function< ConceptTypeT, DataTypeT > propertyRetriever 
   );
   
   public void setCellValueFactoryAsString( 
            Function< ConceptTypeT, ? extends ReadOnlyObjectProperty< Double > > propertyRetriever 
   );
   
   public void setTextFieldCellFactory();
   
   public void setComboBoxCellFactory( 
            StringExtractConverter< DataTypeT > converter, ObservableList< DataTypeT > choices 
   );
   
   public void setCheckBoxCellFactory();
   
//   public void setButtonFactory( String buttonText, Consumer< ConceptTypeT > handler );

   public void setEditable( boolean editable );
   
   public void setOnEditCommit( BiConsumer< ConceptTypeT, DataTypeT > consumer );

   public void setComparator( Comparator< DataTypeT > comparator );


}//End Interface
