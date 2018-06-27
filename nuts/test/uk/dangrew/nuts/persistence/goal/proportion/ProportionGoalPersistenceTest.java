package uk.dangrew.nuts.persistence.goal.proportion;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.json.JSONObject;
import org.junit.Test;

import uk.dangrew.jupa.file.protocol.WorkspaceJsonPersistingProtocol;
import uk.dangrew.nuts.goal.proportion.ProportionGoal;
import uk.dangrew.nuts.goal.proportion.ProportionGoalStore;
import uk.dangrew.nuts.goal.proportion.ProportionType;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.persistence.fooditems.DatabaseIo;
import uk.dangrew.nuts.store.Database;

public class ProportionGoalPersistenceTest {

   @Test public void shouldReadData() {
      Database database = new Database();
      new DatabaseIo( database )
         .withProportionGoals( new WorkspaceJsonPersistingProtocol( "goals.txt", getClass() ) )
         .read();

      assertGoal1IsParsed( database.proportionGoals().objectList().get( 0 ) );
      assertGoal2IsParsed( database.proportionGoals().objectList().get( 1 ) );
      assertEmptyGoal( database.proportionGoals().objectList().get( 2 ) );
   }//End Method
   
   @Test public void shouldWriteData(){
      ProportionGoalStore goals = new ProportionGoalStore();
      ProportionGoal proportionGoal = goals.createConcept( "this-is-the-id", "some-name" );
      
      proportionGoal.configuration().carbohydrateProportionType().set( ProportionType.Weight );
      proportionGoal.configuration().fatProportionType().set( ProportionType.PercentageOfCalories );
      proportionGoal.configuration().proteinProportionType().set( ProportionType.PercentageOfCalories );
      proportionGoal.configuration().fiberProportionType().set( ProportionType.Weight );
      proportionGoal.configuration().carbohydrateTargetValue().set( 20.0 );
      proportionGoal.configuration().fatTargetValue().set( 75.0 );
      proportionGoal.configuration().proteinTargetValue().set( 25.0 );
      proportionGoal.configuration().fiberTargetValue().set( 15.0 );
      proportionGoal.nutrition().of( NutritionalUnit.Calories ).set( 2531.4 );
      assertGoalsAreRealistic( proportionGoal );
      
      goals.createConcept( "Empty" );
      
      ProportionGoalPersistence persistence = new ProportionGoalPersistence( goals );
      JSONObject json = new JSONObject();
      persistence.structure().build( json );
      persistence.writeHandles().parse( json );
      
      System.out.println( json.toString() );
      
      goals = new ProportionGoalStore();
      persistence = new ProportionGoalPersistence( goals );
      
      persistence.readHandles().parse( json );
      assertThat( goals.objectList().size(), is( 2 ) );
      
      assertGoalsAreRealistic( goals.objectList().get( 0 ) );
      assertEmptyGoal( goals.objectList().get( 1 ) );
   }//End Method
   
   private void assertEmptyGoal( ProportionGoal empty ) {
      assertThat( empty.configuration().carbohydrateProportionType().get(), is( nullValue() ) );
      assertThat( empty.configuration().fatProportionType().get(), is( nullValue() ) );
      assertThat( empty.configuration().proteinProportionType().get(), is( nullValue() ) );
      assertThat( empty.configuration().fiberProportionType().get(), is( nullValue() ) );
      assertThat( empty.configuration().carbohydrateTargetValue().get(), is( 0.0 ) );
      assertThat( empty.configuration().fatTargetValue().get(), is( 0.0 ) );
      assertThat( empty.configuration().proteinTargetValue().get(), is( 0.0 ) );
      assertThat( empty.configuration().fiberTargetValue().get(), is( 0.0 ) );
   }//End Method
   
   private void assertGoal1IsParsed( ProportionGoal goal ) {
      assertThat( goal.properties().id(), is( "single-goal-no-id-provided-unique" ) );
      assertThat( goal.properties().nameProperty().get(), is( "some-name" ) );
      assertThat( goal.configuration().carbohydrateProportionType().get(), is( ProportionType.PercentageOfCalories ) );
      assertThat( goal.configuration().fatProportionType().get(), is( ProportionType.PercentageOfWeight ) );
      assertThat( goal.configuration().proteinProportionType().get(), is( ProportionType.Calorie ) );
      assertThat( goal.configuration().fiberProportionType().get(), is( nullValue() ) );
      assertThat( goal.configuration().carbohydrateTargetValue().get(), is( 65.0 ) );
      assertThat( goal.configuration().fatTargetValue().get(), is( 11.0 ) );
      assertThat( goal.configuration().proteinTargetValue().get(), is( 2343.0 ) );
      assertThat( goal.configuration().fiberTargetValue().get(), is( nullValue() ) );
      
      assertThat( goal.nutrition().of( NutritionalUnit.Calories ).get(), is( 231.4 ) );
   }//End Method
   
   private void assertGoal2IsParsed( ProportionGoal goal ) {
      assertThat( goal.properties().id(), is( "alwieufh0182347huflsd" ) );
      assertThat( goal.properties().nameProperty().get(), is( "Keto" ) );
      assertGoalsAreRealistic( goal );
   }//End Method
   
   private void assertGoalsAreRealistic( ProportionGoal goal ) {
      assertThat( goal.configuration().carbohydrateProportionType().get(), is( ProportionType.Weight ) );
      assertThat( goal.configuration().fiberProportionType().get(), is( ProportionType.Weight ) );
      assertThat( goal.configuration().fatProportionType().get(), is( ProportionType.PercentageOfCalories ) );
      assertThat( goal.configuration().proteinProportionType().get(), is( ProportionType.PercentageOfCalories ) );
      assertThat( goal.configuration().carbohydrateTargetValue().get(), is( 20.0 ) );
      assertThat( goal.configuration().fatTargetValue().get(), is( 75.0 ) );
      assertThat( goal.configuration().proteinTargetValue().get(), is( 25.0 ) );
      assertThat( goal.configuration().fiberTargetValue().get(), is( 15.0 ) );
      
      assertThat( goal.nutrition().of( NutritionalUnit.Calories ).get(), is( 2531.4 ) );
   }//End Method

}//End Class