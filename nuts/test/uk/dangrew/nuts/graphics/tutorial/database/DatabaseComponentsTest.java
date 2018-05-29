package uk.dangrew.nuts.graphics.tutorial.database;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.scene.Node;
import javafx.scene.control.Button;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.model.BuilderVerifier;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.common.UiEnumTypeSelectionDialog;
import uk.dangrew.nuts.graphics.database.FoodTypes;
import uk.dangrew.nuts.graphics.database.UiDatabaseManagerPane;
import uk.dangrew.nuts.graphics.meal.MealTable;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.store.Database;

public class DatabaseComponentsTest {

   @Mock private ConceptTable< Food > mainTable;
   @Mock private MealTable mealTable;
   private DatabaseComponents systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new DatabaseComponents();
   }//End Method

   @Test public void shouldBuild() {
      new BuilderVerifier<>()
         .buildObject( systemUnderTest::withMainTable, systemUnderTest::mainTable, mainTable )
         .buildObject( systemUnderTest::withMainTableAddButton, systemUnderTest::mainTableAddButton, mock( Button.class ) )
         .buildObject( systemUnderTest::withMainTableFoodTypeDialog, systemUnderTest::mainTableFoodTypeDialog, mock( UiEnumTypeSelectionDialog.class ) )
         .buildObject( systemUnderTest::withMealTable, systemUnderTest::mealTable, mealTable )
         .buildObject( systemUnderTest::withMealTableAddButton, systemUnderTest::mealTableAddButton, mock( Button.class ) )
         .buildObject( systemUnderTest::withMealTableRemoveButton, systemUnderTest::mealTableRemoveButton, mock( Button.class ) )
         .buildObject( systemUnderTest::withMealTableCopyButton, systemUnderTest::mealTableCopyButton, mock( Button.class ) )
         .buildObject( systemUnderTest::withMealTableUpButton, systemUnderTest::mealTableUpButton, mock( Button.class ) )
         .buildObject( systemUnderTest::withMealTableDownButton, systemUnderTest::mealTableDownButton, mock( Button.class ) );
   }//End Method

   @Test public void shouldRegenerate(){
      Database database = systemUnderTest.database();
      DatabaseManipulator databaseManipulator = systemUnderTest.databaseManipulator();
      
      UiDatabaseManagerPane parent = systemUnderTest.parent();
      Node mainTable = systemUnderTest.mainTable();
      FoodTableManipulator< Food > mainTableManipulator = systemUnderTest.mainTableComponents();
      Node mainTableButton = systemUnderTest.mainTableAddButton();
      UiEnumTypeSelectionDialog< FoodTypes > dialog = systemUnderTest.mainTableFoodTypeDialog();
      
      Node mealTable = systemUnderTest.mealTable();
      FoodTableManipulator< FoodPortion > mealTableManipulator = systemUnderTest.mealTableComponents();
      Node mealTableAddButton = systemUnderTest.mealTableAddButton();
      Node mealTableRemoveButton = systemUnderTest.mealTableRemoveButton();
      Node mealTableCopyButton = systemUnderTest.mealTableCopyButton();
      Node mealTableUpButton = systemUnderTest.mealTableUpButton();
      Node mealTableDownButton = systemUnderTest.mealTableDownButton();
      
      systemUnderTest.generateComponents();
      
      assertThat( systemUnderTest.database(), is( not( database ) ) );
      assertThat( systemUnderTest.databaseManipulator(), is( not( databaseManipulator ) ) );
      
      assertThat( systemUnderTest.parent(), is( not( parent ) ) );
      assertThat( systemUnderTest.mainTable(), is( not( mainTable ) ) );
      assertThat( systemUnderTest.mainTableComponents(), is( not( mainTableManipulator ) ) );
      assertThat( systemUnderTest.mainTableAddButton(), is( not( mainTableButton ) ) );
      assertThat( systemUnderTest.mainTableFoodTypeDialog(), is( not( dialog ) ) );
      
      assertThat( systemUnderTest.mealTable(), is( not( mealTable ) ) );
      assertThat( systemUnderTest.mealTableAddButton(), is( not( mealTableAddButton ) ) );
      assertThat( systemUnderTest.mealTableComponents(), is( not( mealTableManipulator ) ) );
      assertThat( systemUnderTest.mealTableRemoveButton(), is( not( mealTableRemoveButton ) ) );
      assertThat( systemUnderTest.mealTableCopyButton(), is( not( mealTableCopyButton ) ) );
      assertThat( systemUnderTest.mealTableUpButton(), is( not( mealTableUpButton ) ) );
      assertThat( systemUnderTest.mealTableDownButton(), is( not( mealTableDownButton ) ) );
   }//End Method
   
}//End Class
