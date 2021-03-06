package uk.dangrew.nuts.persistence.goal;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.json.JSONObject;
import org.junit.Test;

import uk.dangrew.jupa.file.protocol.WorkspaceJsonPersistingProtocol;
import uk.dangrew.kode.TestCommon;
import uk.dangrew.nuts.goal.calorie.CalorieGoal;
import uk.dangrew.nuts.goal.calorie.CalorieGoalStore;
import uk.dangrew.nuts.goal.calorie.Gender;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.persistence.fooditems.DatabaseIo;
import uk.dangrew.nuts.persistence.goal.calorie.CalorieGoalPersistence;
import uk.dangrew.nuts.store.Database;

public class CalorieGoalPersistenceTest {

   @Test public void shouldReadData() {
      Database database = new Database();
      new DatabaseIo( database )
         .withCalorieGoals( new WorkspaceJsonPersistingProtocol( "goals.txt", getClass() ) )
         .read();

      CalorieGoalPersistence p = new CalorieGoalPersistence( database.calorieGoals() );
      JSONObject object = new JSONObject();
      p.structure().build( object );
      p.writeHandles().parse( object );
      System.out.println( object.toString() );
      
      assertGoal1IsParsed( database.calorieGoals().objectList().get( 0 ), true );
      assertThat( database.calorieGoals().objectList().get( 0 ).nutrition().of( NutritionalUnit.Fibre ).get(), is( 34.5 ) );
      assertGoal2IsParsed( database.calorieGoals().objectList().get( 1 ) );
      assertThat( database.calorieGoals().objectList().get( 1 ).nutrition().of( NutritionalUnit.Fibre ).get(), is( 0.0 ) );
      assertThat( database.calorieGoals().objectList().get( 1 ).nutrition().of( NutritionalUnit.Potassium ).get(), is( 101.5 ) );
   }//End Method
   
   @Test public void shouldReadDataOfIndividualMacros() {
      Database database = new Database();
      new DatabaseIo( database )
         .withCalorieGoals( new WorkspaceJsonPersistingProtocol( "goals-with-individual-macros.txt", getClass() ) )
         .read();

      assertGoal1IsParsed( database.calorieGoals().objectList().get( 0 ), true );
      assertGoal2IsParsed( database.calorieGoals().objectList().get( 1 ) );
   }//End Method
   
   @Test public void shouldReadFirstVersionData() {
      Database database = new Database();
      new DatabaseIo( database )
         .withCalorieGoals( new WorkspaceJsonPersistingProtocol( "goal-no-id-name.txt", getClass() ) )
         .read();

      assertGoal1IsParsed( database.calorieGoals().objectList().get( 0 ), false );
   }//End Method
   
   @Test public void shouldRetainNewNameOfOriginalGoal() {
      Database database = new Database();
      new DatabaseIo( database )
         .withCalorieGoals( new WorkspaceJsonPersistingProtocol( "goal-no-id-name.txt", getClass() ) )
         .withCalorieGoals( new WorkspaceJsonPersistingProtocol( "goals.txt", getClass() ) )
         .read();
      
      assertGoal1IsParsed( database.calorieGoals().objectList().get( 0 ), false );
      assertThat( database.calorieGoals().objectList().get( 0 ).properties().nameProperty().get(), is( "some-name" ) );
   }//End Method
   
   @Test public void shouldWriteData(){
      CalorieGoalStore goals = new CalorieGoalStore();
      CalorieGoal calorieGoal = goals.createConcept( "this-is-the-id", "some-name" );
      
      calorieGoal.gender().set( Gender.Male );
      calorieGoal.age().set( 28.0 );
      calorieGoal.weight().set( 197.0 );
      calorieGoal.height().set( 1.87 );
      calorieGoal.exerciseCalories().set( 500.0 );
      calorieGoal.calorieDeficit().set( 700.0 );
      calorieGoal.fatPerPound().set( 0.35 );
      
      calorieGoal.nutrition().of( NutritionalUnit.Fibre ).set( 56.7 );
      
      assertGoalsAreRealistic( calorieGoal );
      
      CalorieGoalPersistence persistence = new CalorieGoalPersistence( goals );
      JSONObject json = new JSONObject();
      persistence.structure().build( json );
      persistence.writeHandles().parse( json );
      
      System.out.println( json.toString() );
      
      goals = new CalorieGoalStore();
      persistence = new CalorieGoalPersistence( goals );
      
      persistence.readHandles().parse( json );
      assertThat( goals.objectList().size(), is( 1 ) );
      
      assertGoalsAreRealistic( goals.objectList().get( 0 ) );
   }//End Method
   
   /**
    * Method to assert the {@link Goal} values as defined in the file.
    * @param goal the {@link Goal} to test.
    * @param expectMatchingIdName whether the id and name are expected.
    */
   private void assertGoal1IsParsed( CalorieGoal calorieGoal, boolean expectMatchingIdName ) {
      if ( expectMatchingIdName ) {
         assertThat( calorieGoal.properties().id(), is( "single-goal-no-id-provided-unique" ) );
         assertThat( calorieGoal.properties().nameProperty().get(), is( "some-name" ) );
      }
      assertThat( calorieGoal.gender().get(), is( Gender.Male ) );
      assertThat( calorieGoal.fatPerPound().get(), is( closeTo( 1112, TestCommon.precision() ) ) );
      assertThat( calorieGoal.bmr().get(), is( closeTo( 1113, TestCommon.precision() ) ) );
      assertThat( calorieGoal.weight().get(), is( closeTo( 1114, TestCommon.precision() ) ) );
      assertThat( calorieGoal.nutrition().of( NutritionalUnit.Calories ).get(), is( closeTo( 1115, TestCommon.precision() ) ) );
      assertThat( calorieGoal.nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( closeTo( 1117, TestCommon.precision() ) ) );
      assertThat( calorieGoal.nutrition().of( NutritionalUnit.Fat ).get(), is( closeTo( 1118, TestCommon.precision() ) ) );
      assertThat( calorieGoal.nutrition().of( NutritionalUnit.Protein ).get(), is( closeTo( 1125, TestCommon.precision() ) ) );
      assertThat( calorieGoal.calorieDeficit().get(), is( closeTo( 1116, TestCommon.precision() ) ) );
      assertThat( calorieGoal.tee().get(), is( closeTo( 1119, TestCommon.precision() ) ) );
      assertThat( calorieGoal.proteinPerPound().get(), is( closeTo( 1120, TestCommon.precision() ) ) );
      assertThat( calorieGoal.pal().get(), is( closeTo( 1121, TestCommon.precision() ) ) );
      assertThat( calorieGoal.exerciseCalories().get(), is( closeTo( 1122, TestCommon.precision() ) ) );
      assertThat( calorieGoal.age().get(), is( closeTo( 1123, TestCommon.precision() ) ) );
      assertThat( calorieGoal.height().get(), is( closeTo( 1124, TestCommon.precision() ) ) );
   }//End Method
   
   /**
    * Method to assert the {@link Goal} values as defined in the file.
    * @param goal the {@link Goal} to test.
    */
   private void assertGoal2IsParsed( CalorieGoal calorieGoal ) {
      assertThat( calorieGoal.properties().id(), is( "alwieufh0182347huflsd" ) );
      assertThat( calorieGoal.properties().nameProperty().get(), is( "Low Cal" ) );
      assertThat( calorieGoal.gender().get(), is( Gender.Male ) );
      assertThat( calorieGoal.fatPerPound().get(), is( closeTo( 4445, TestCommon.precision() ) ) );
      assertThat( calorieGoal.bmr().get(), is( closeTo( 4446, TestCommon.precision() ) ) );
      assertThat( calorieGoal.weight().get(), is( closeTo( 4447, TestCommon.precision() ) ) );
      assertThat( calorieGoal.nutrition().of( NutritionalUnit.Calories ).get(), is( closeTo( 4448, TestCommon.precision() ) ) );
      assertThat( calorieGoal.nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( closeTo( 4450, TestCommon.precision() ) ) );
      assertThat( calorieGoal.nutrition().of( NutritionalUnit.Fat ).get(), is( closeTo( 4451, TestCommon.precision() ) ) );
      assertThat( calorieGoal.nutrition().of( NutritionalUnit.Protein ).get(), is( closeTo( 4457, TestCommon.precision() ) ) );
      assertThat( calorieGoal.calorieDeficit().get(), is( closeTo( 4449, TestCommon.precision() ) ) );
      assertThat( calorieGoal.tee().get(), is( closeTo( 4452, TestCommon.precision() ) ) );
      assertThat( calorieGoal.proteinPerPound().get(), is( closeTo( 4453, TestCommon.precision() ) ) );
      assertThat( calorieGoal.pal().get(), is( closeTo( 4454, TestCommon.precision() ) ) );
      assertThat( calorieGoal.exerciseCalories().get(), is( closeTo( 4455, TestCommon.precision() ) ) );
      assertThat( calorieGoal.age().get(), is( closeTo( 4456, TestCommon.precision() ) ) );
      assertThat( calorieGoal.height().get(), is( closeTo( 4457, TestCommon.precision() ) ) );
   }//End Method
   
   /**
    * Method to assert the {@link Goal} values as defined in this test.
    * @param goal the {@link Goal} to test.
    */
   private void assertGoalsAreRealistic( CalorieGoal calorieGoal ) {
      assertThat( calorieGoal.gender().get(), is( Gender.Male ) );
      assertThat( calorieGoal.fatPerPound().get(), is( closeTo( 0.35, TestCommon.precision() ) ) );
      assertThat( calorieGoal.bmr().get(), is( closeTo( 1985.0608352160002, TestCommon.precision() ) ) );
      assertThat( calorieGoal.weight().get(), is( closeTo( 197, TestCommon.precision() ) ) );
      assertThat( calorieGoal.nutrition().of( NutritionalUnit.Calories ).get(), is( closeTo( 2579.0851693024, TestCommon.precision() ) ) );
      assertThat( calorieGoal.nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( closeTo( 292.63379232560004, TestCommon.precision() ) ) );
      assertThat( calorieGoal.nutrition().of( NutritionalUnit.Fat ).get(), is( closeTo( 68.94999999999999, TestCommon.precision() ) ) );
      assertThat( calorieGoal.nutrition().of( NutritionalUnit.Protein ).get(), is( closeTo( 197, TestCommon.precision() ) ) );
      assertThat( calorieGoal.calorieDeficit().get(), is( closeTo( 700, TestCommon.precision() ) ) );
      assertThat( calorieGoal.tee().get(), is( closeTo( 2779.0851693024, TestCommon.precision() ) ) );
      assertThat( calorieGoal.proteinPerPound().get(), is( closeTo( 1, TestCommon.precision() ) ) );
      assertThat( calorieGoal.pal().get(), is( closeTo( 1.4, TestCommon.precision() ) ) );
      assertThat( calorieGoal.exerciseCalories().get(), is( closeTo( 500, TestCommon.precision() ) ) );
      assertThat( calorieGoal.age().get(), is( closeTo( 28, TestCommon.precision() ) ) );
      assertThat( calorieGoal.height().get(), is( closeTo( 1.87, TestCommon.precision() ) ) );
      
      assertThat( calorieGoal.nutrition().of( NutritionalUnit.Fibre ).get(), is( 56.7 ) );
   }//End Method

}//End Class