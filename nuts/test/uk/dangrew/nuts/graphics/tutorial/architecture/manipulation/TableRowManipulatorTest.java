package uk.dangrew.nuts.graphics.tutorial.architecture.manipulation;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableRow;
import javafx.scene.layout.BorderPane;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.tutorial.architecture.manipulation.TableRowManipulator;

public class TableRowManipulatorTest {

   private ObservableList< Node > children;
   @Mock private TableRow< Object > row;
   private TableRowManipulator< Object > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      children = FXCollections.observableArrayList();
      when( row.getChildrenUnmodifiable() ).thenReturn( children );
      
      systemUnderTest = new TableRowManipulator<>( row );
   }//End Method

   @Test public void shouldProvideNode() {
      assertThat( systemUnderTest.node(), is( row ) );
   }//End Method
   
   @Test public void shouldProvideCellFromChildren(){
      children.addAll( new BorderPane(), new BorderPane(), new BorderPane(), new BorderPane() );
      for( int i = 0; i < 4; i++ ) {
         assertThat( systemUnderTest.cell( i ), is( children.get( i ) ) );
      }
   }//End Method

}//End Class
