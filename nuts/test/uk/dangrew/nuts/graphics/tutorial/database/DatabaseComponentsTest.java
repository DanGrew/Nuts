package uk.dangrew.nuts.graphics.tutorial.database;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import javafx.scene.Node;
import javafx.scene.control.Button;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.model.BuilderVerifier;
import uk.dangrew.nuts.graphics.common.UiEnumTypeSelectionDialog;
import uk.dangrew.nuts.graphics.database.FoodTypes;
import uk.dangrew.nuts.graphics.database.UiDatabaseManagerPane;
import uk.dangrew.nuts.graphics.table.ConceptTable;

public class DatabaseComponentsTest {

   private DatabaseComponents systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new DatabaseComponents();
   }//End Method

   @Test public void shouldBuild() {
      new BuilderVerifier<>()
         .buildObject( systemUnderTest::withMainTable, systemUnderTest::mainTable, mock( ConceptTable.class ) )
         .buildObject( systemUnderTest::withMainTableAddButton, systemUnderTest::mainTableAddButton, mock( Button.class ) )
         .buildObject( systemUnderTest::withMainTableFoodTypeDialog, systemUnderTest::mainTableFoodTypeDialog, mock( UiEnumTypeSelectionDialog.class ) );
   }//End Method

   @Test public void shouldRegenerate(){
      UiDatabaseManagerPane parent = systemUnderTest.parent();
      Node mainTable = systemUnderTest.mainTable();
      Node mainTableButton = systemUnderTest.mainTableAddButton();
      UiEnumTypeSelectionDialog< FoodTypes > dialog = systemUnderTest.mainTableFoodTypeDialog();
      
      systemUnderTest.generateComponents();
      assertThat( systemUnderTest.parent(), is( not( parent ) ) );
      assertThat( systemUnderTest.mainTable(), is( not( mainTable ) ) );
      assertThat( systemUnderTest.mainTableAddButton(), is( not( mainTableButton ) ) );
      assertThat( systemUnderTest.mainTableFoodTypeDialog(), is( not( dialog ) ) );
   }//End Method
   
}//End Class
