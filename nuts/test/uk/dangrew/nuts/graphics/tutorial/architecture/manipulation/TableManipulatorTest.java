package uk.dangrew.nuts.graphics.tutorial.architecture.manipulation;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import uk.dangrew.kode.javafx.hacks.JavaFxHacks;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.tutorial.architecture.manipulation.TableManipulator;
import uk.dangrew.nuts.graphics.tutorial.architecture.manipulation.TableRowManipulator;

public class TableManipulatorTest {

   private List< TableRow< Object > > rows;
   private TableView< Object > table;
   @Mock private BiFunction< TableRow< Object >, Integer, TableRowManipulator< Object > > supplier;
   
   @Mock private JavaFxHacks hacks;
   private TableManipulator< Object, TableRowManipulator<  Object > > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      table = new TableView<>();
      table.setEditable(true);
      table.getColumns().addAll( new TableColumn<>(), new TableColumn<>() );
      
      rows = Arrays.asList( mock( TableRow.class ), mock( TableRow.class ) );
      
      when( hacks.lookupTableRows( table ) ).thenReturn( rows );
      
      systemUnderTest = new TableManipulator<>( hacks, table, supplier );
   }//End Method

   @Test public void shouldProvideRowManipulator() {
      systemUnderTest.row( 0 );
      verify( supplier ).apply( rows.get( 0 ), 0 );
      
      systemUnderTest.row( 1 );
      verify( supplier ).apply( rows.get( 1 ), 1 );
      
      assertThat( systemUnderTest.row( 2 ), is( nullValue() ) ) ;
      
      rows = new ArrayList<>();
      assertThat( systemUnderTest.row( 0 ), is( nullValue() ) ) ;
   }//End Method
   
   @Test public void shouldTriggerCellEdit() {
      systemUnderTest.triggerCellEdit( 0, 0 );
      assertThat(table.editingCellProperty().get().getColumn(), is( 0));
      assertThat(table.editingCellProperty().get().getRow(), is( 0));
      
      systemUnderTest.triggerCellEdit( 2, 1 );
      assertThat(table.editingCellProperty().get().getColumn(), is( 1));
      assertThat(table.editingCellProperty().get().getRow(), is( 2));
   }//End Method
   
   @Test public void shouldFinishCellEdit() {
      systemUnderTest.finishCellEdit();
      assertThat(table.editingCellProperty().get(), is( nullValue()));
   }//End Method

}//End Class
