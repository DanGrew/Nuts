package uk.dangrew.nuts.persistence.goal;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.json.JSONObject;
import org.junit.Test;

import uk.dangrew.kode.TestCommon;
import uk.dangrew.nuts.goal.Gender;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.store.Database;

public class GoalPersistenceTest {

   @Test public void shouldReadData() {
      Database database = new Database();
      GoalPersistence persistence = new GoalPersistence( database );
      
      String value = TestCommon.readFileIntoString( getClass(), "goal.txt" );
      JSONObject json = new JSONObject( value );
      persistence.readHandles().parse( json );

      assertGoalsAreParsed( database.goal() );
   }//End Method
   
   @Test public void shouldWriteData(){
      Database database = new Database();
      
      database.goal().gender().set( Gender.Male );
      database.goal().age().set( 28.0 );
      database.goal().weight().set( 197.0 );
      database.goal().height().set( 1.87 );
      database.goal().exerciseCalories().set( 500.0 );
      database.goal().calorieDeficit().set( 700.0 );
      database.goal().fatPerPound().set( 0.35 );
      
      assertGoalsAreRealistic( database.goal() );
      
      GoalPersistence persistence = new GoalPersistence( database );
      JSONObject json = new JSONObject();
      persistence.structure().build( json );
      persistence.writeHandles().parse( json );
      
      System.out.println( json.toString() );
      
      database = new Database();
      persistence = new GoalPersistence( database );
      
      persistence.readHandles().parse( json );
      assertGoalsAreRealistic( database.goal() );
   }//End Method
   
   /**
    * Method to assert the {@link Goal} values as defined in the file.
    * @param goal the {@link Goal} to test.
    */
   private void assertGoalsAreParsed( Goal goal ) {
      assertThat( goal.gender().get(), is( Gender.Male ) );
      assertThat( goal.fatPerPound().get(), is( closeTo( 1112, TestCommon.precision() ) ) );
      assertThat( goal.bmr().get(), is( closeTo( 1113, TestCommon.precision() ) ) );
      assertThat( goal.weight().get(), is( closeTo( 1114, TestCommon.precision() ) ) );
      assertThat( goal.properties().calories().get(), is( closeTo( 1115, TestCommon.precision() ) ) );
      assertThat( goal.properties().carbohydrates().get(), is( closeTo( 1117, TestCommon.precision() ) ) );
      assertThat( goal.properties().fats().get(), is( closeTo( 1118, TestCommon.precision() ) ) );
      assertThat( goal.properties().protein().get(), is( closeTo( 1125, TestCommon.precision() ) ) );
      assertThat( goal.calorieDeficit().get(), is( closeTo( 1116, TestCommon.precision() ) ) );
      assertThat( goal.tee().get(), is( closeTo( 1119, TestCommon.precision() ) ) );
      assertThat( goal.proteinPerPound().get(), is( closeTo( 1120, TestCommon.precision() ) ) );
      assertThat( goal.pal().get(), is( closeTo( 1121, TestCommon.precision() ) ) );
      assertThat( goal.exerciseCalories().get(), is( closeTo( 1122, TestCommon.precision() ) ) );
      assertThat( goal.age().get(), is( closeTo( 1123, TestCommon.precision() ) ) );
      assertThat( goal.height().get(), is( closeTo( 1124, TestCommon.precision() ) ) );
   }//End Method
   
   /**
    * Method to assert the {@link Goal} values as defined in this test.
    * @param goal the {@link Goal} to test.
    */
   private void assertGoalsAreRealistic( Goal goal ) {
      assertThat( goal.gender().get(), is( Gender.Male ) );
      assertThat( goal.fatPerPound().get(), is( closeTo( 0.35, TestCommon.precision() ) ) );
      assertThat( goal.bmr().get(), is( closeTo( 1985.0608352160002, TestCommon.precision() ) ) );
      assertThat( goal.weight().get(), is( closeTo( 197, TestCommon.precision() ) ) );
      assertThat( goal.properties().calories().get(), is( closeTo( 2579.0851693024, TestCommon.precision() ) ) );
      assertThat( goal.properties().carbohydrates().get(), is( closeTo( 292.63379232560004, TestCommon.precision() ) ) );
      assertThat( goal.properties().fats().get(), is( closeTo( 68.94999999999999, TestCommon.precision() ) ) );
      assertThat( goal.properties().protein().get(), is( closeTo( 197, TestCommon.precision() ) ) );
      assertThat( goal.calorieDeficit().get(), is( closeTo( 700, TestCommon.precision() ) ) );
      assertThat( goal.tee().get(), is( closeTo( 2779.0851693024, TestCommon.precision() ) ) );
      assertThat( goal.proteinPerPound().get(), is( closeTo( 1, TestCommon.precision() ) ) );
      assertThat( goal.pal().get(), is( closeTo( 1.4, TestCommon.precision() ) ) );
      assertThat( goal.exerciseCalories().get(), is( closeTo( 500, TestCommon.precision() ) ) );
      assertThat( goal.age().get(), is( closeTo( 28, TestCommon.precision() ) ) );
      assertThat( goal.height().get(), is( closeTo( 1.87, TestCommon.precision() ) ) );
   }//End Method

}//End Class