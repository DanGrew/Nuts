package uk.dangrew.nuts.graphics.table.configuration;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.function.Function;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;

public class TableViewColumnConfigurerTest {

   private ConceptTableRow< Object > firstRow;
   private TableView< ConceptTableRow< Object > > table;
   private TableColumn< ConceptTableRow< Object >, String > column;
   private TableViewColumnConfigurer< Object, String > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      table = new TableView<>();
      column = new TableColumn<>();
      systemUnderTest = new TableViewColumnConfigurer<>( table, column );
      
      table.getItems().add( firstRow = new ConceptTableRow<>( new Object() ) );
   }//End Method

   @Test public void shouldAddColumn() {
      assertThat( table.getColumns().get( 0 ), is( column ) );
   }//End Method
   
   @Test public void shouldSetTitle(){
      systemUnderTest.setTitle( "title" );
      assertThat( column.getText(), is( "title" ) );
   }//End Method
   
   @Test public void shouldBindPrefWidth(){
      systemUnderTest.bindPrefWidth( 0.5 );
      assertThat( column.getPrefWidth(), is( table.widthProperty().get() * 0.5 ) );
   }//End Method
   
   @Test public void shouldSetCellValueFactory(){
      Function< Object, ObservableValue< String > > retriever = o -> new SimpleObjectProperty<>( o.toString() );
      
      systemUnderTest.setCellValueFactory( retriever );
      ObservableValue< String > value = column.getCellValueFactory().call( new CellDataFeatures< ConceptTableRow<Object>, String >( table, column, firstRow ) );
      
      assertThat( value.getValue(), is( firstRow.concept().toString() ) );
   }//End Method
   
   @Test public void shouldSetFixedCellValueFactory(){
      Function< Object, String > retriever = Object::toString;
      
      systemUnderTest.setFixedCellValueFactory( retriever );
      ObservableValue< String > value = column.getCellValueFactory().call( new CellDataFeatures< ConceptTableRow<Object>, String >( table, column, firstRow ) );
      
      assertThat( value.getValue(), is( firstRow.concept().toString() ) );
   }//End Method
   
   @Test public void shouldCellValueFactoryAsString(){
      Function< Object, ObjectProperty< Double > > retriever = o -> new SimpleObjectProperty<>( 43.567 );
      
      systemUnderTest.setCellValueFactoryAsString( retriever );
      ObservableValue< String > value = column.getCellValueFactory().call( new CellDataFeatures< ConceptTableRow<Object>, String >( table, column, firstRow ) );
      
      assertThat( value.getValue(), is( "43.567" ) );
   }//End Method
   
   @Ignore
   @Test public void shouldCatchIncompatibleCellValueFactoryAsString(){
      fail();
   }//End Method
   
   @Ignore
   @Test public void shouldSetTextFieldCellFactory(){
      fail();
   }//End Method
   
   @Ignore
   @Test public void shouldCatchIncompatibleTextFieldCellFactory(){
      fail();
   }//End Method
   
   @Ignore
   @Test public void shouldSetComboBoxCellFactory(){
      fail();
   }//End Method
   
   @Ignore
   @Test public void shouldSetCheckBoxCellFactory(){
      fail();
   }//End Method
   
   @Ignore
   @Test public void shouldCatchIncompatibleCheckBoxCellFactory(){
      fail();
   }//End Method
   
   @Ignore
   @Test public void shouldSetEditable(){
      fail();
   }//End Method
   
   @Ignore
   @Test public void shouldSetComparator(){
      fail();
   }//End Method

}//End Class
