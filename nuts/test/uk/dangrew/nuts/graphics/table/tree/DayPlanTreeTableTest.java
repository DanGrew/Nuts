package uk.dangrew.nuts.graphics.table.tree;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import uk.dangrew.kode.javafx.platform.JavaFxThreading;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.selection.view.FoodSelectionWindow;
import uk.dangrew.nuts.graphics.table.TableConfiguration;
import uk.dangrew.nuts.graphics.table.configuration.TableColumnConfigurer;
import uk.dangrew.nuts.graphics.table.configuration.TableColumnWidths;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.store.Database;

public class DayPlanTreeTableTest {
   
   private FoodItem item1;
   private FoodItem item2;
   private FoodItem item3;
   private FoodItem item4;
   
   private Meal subMeal1;
   private Meal subMeal2;
   
   private DayPlan focus;

   private Database database;
   private DayPlanTreePane pane;
   
   @Spy private TableConfiguration configuration;
   @Spy private TableColumnWidths widths;
   private DayPlanTreeTable systemUnderTest; 
   
   @Before public void initialiseSystemUnderTest(){
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      database = new Database();
      database.stockLists().createConcept( "" );
      
      item1 = database.foodItems().createConcept( "Item1" );
      item1.nutrition().of( NutritionalUnit.Calories ).set( 101.0 );
      item1.nutrition().setMacroNutrients( 23, 4, 78 );
      item2 = database.foodItems().createConcept( "Item2" );
      item2.nutrition().of( NutritionalUnit.Calories ).set( 78.0 );
      item2.nutrition().setMacroNutrients( 0.1, 7.0, 5.6 );
      item3 = database.foodItems().createConcept( "Item3" );
      item3.nutrition().of( NutritionalUnit.Calories ).set( 140.0 );
      item3.nutrition().setMacroNutrients( 0, 15, 1 );
      item4 = database.foodItems().createConcept( "Item4" );
      item4.nutrition().of( NutritionalUnit.Calories ).set( 198.0 );
      item4.nutrition().setMacroNutrients( 4, 12, 3 );
      
      subMeal1 = database.meals().createConcept( "SubMeal1" );
      subMeal1.portions().addAll( 
               new FoodPortion( item1, 100 ),
               new FoodPortion( item2, 125 )
      );
      
      subMeal2 = database.meals().createConcept( "SubMeal2" );
      subMeal2.portions().addAll( 
               new FoodPortion( item2, 240 ),
               new FoodPortion( item3, 250 )
      );
      
      focus = new DayPlan( "Focus" );
      database.dayPlanController().add( new FoodPortion( subMeal1, 100 ), focus );
      database.dayPlanController().add( new FoodPortion( subMeal2, 100 ), focus );
      
      systemUnderTest = new DayPlanTreeTable( database, configuration, widths );
   }//End Method
   
   @Ignore
   @Test public void manual() throws InterruptedException {
      JavaFxThreading.runAndWait( () -> new FoodSelectionWindow( database ) );
      TestApplication.launch( () -> pane = new DayPlanTreePane( database ) );
      
      pane.controller().show( focus );
      
      Thread.sleep( 9999999 );
   }//End Method
   
   @Test public void shouldUseDatabaseStructures(){
      assertThat( systemUnderTest.settings(), is( database.settings() ) );
      assertThat( systemUnderTest.dayPlanController(), is( database.dayPlanController() ) );
   }//End Method
   
   @Test public void shouldSetFocus(){
      systemUnderTest.setFocus( focus );
      assertThat( systemUnderTest.getFocus(), is( focus ) );
      assertThat( systemUnderTest.getRoot().getValue().concept().food().get(), is( focus ) );
   }//End Method
   
   @Test public void shouldProvideColumns(){
      verify( configuration ).initialiseStringColumn( 
               Mockito.< TableColumnConfigurer< FoodPortion, String > >any(), 
               any(), 
               anyDouble(), 
               any(),
               eq( true )
      );
      verify( configuration ).initialisePortionColumn( 
               Mockito.< TableColumnConfigurer< FoodPortion, String > >any(), 
               any(), 
               anyDouble() 
      );
      verify( configuration ).configureVisibleNutrientUnitColumns(  
               any(),
               eq( widths ),
               any(), 
               any(), 
               anyBoolean(), 
               any() 
      );
   }//End Method
   
}//End Class
