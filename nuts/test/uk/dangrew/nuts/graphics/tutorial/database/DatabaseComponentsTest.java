package uk.dangrew.nuts.graphics.tutorial.database;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import javafx.scene.control.Button;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.model.BuilderVerifier;
import uk.dangrew.nuts.graphics.common.UiEnumTypeSelectionDialog;
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
         .build( systemUnderTest::withParent, systemUnderTest::parent, mock( UiDatabaseManagerPane.class ) )
         .build( systemUnderTest::withMainTable, systemUnderTest::mainTable, mock( ConceptTable.class ) )
         .build( systemUnderTest::withMainTableAddButton, systemUnderTest::mainTableAddButton, mock( Button.class ) )
         .build( systemUnderTest::withMainTableFoodTypeDialog, systemUnderTest::mainTableFoodTypeDialog, mock( UiEnumTypeSelectionDialog.class ) );
   }//End Method

}//End Class
