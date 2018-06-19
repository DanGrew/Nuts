package uk.dangrew.nuts.persistence.weighins;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.json.JSONObject;
import org.junit.Test;

import uk.dangrew.jupa.file.protocol.WorkspaceJsonPersistingProtocol;
import uk.dangrew.nuts.persistence.fooditems.DatabaseIo;
import uk.dangrew.nuts.progress.weight.WeightRecording;
import uk.dangrew.nuts.store.Database;

public class WeightRecordingPersistenceTest {

   @Test public void shouldReadData() {
      Database database = new Database();
      new DatabaseIo( database )
         .withWeightRecordings( new WorkspaceJsonPersistingProtocol( "weight-progress.txt", getClass() ) )
         .read();

      final WeightRecording first = database.weightProgress().records().get( 0 );
      assertThat( first.date(), is( LocalDate.of( 2017, 4, 24 ) ) );
      assertThat( first.morningWeighIn().weight().get(), is( 4.0 ) );
      assertThat( first.morningWeighIn().bodyFat().get(), is( 5.0 ) );
      assertThat( first.morningWeighIn().leanMass().get(), is( 6.0 ) );
      assertThat( first.eveningWeighIn().weight().get(), is( 2.0 ) );
      assertThat( first.eveningWeighIn().bodyFat().get(), is( 1.0 ) );
      assertThat( first.eveningWeighIn().leanMass().get(), is( 3.0 ) );
      
      final WeightRecording second = database.weightProgress().records().get( 1 );
      assertThat( second.date(), is( LocalDate.of( 2017, 4, 25 ) ) );
      assertThat( second.morningWeighIn().weight().get(), is( 10.0 ) );
      assertThat( second.morningWeighIn().bodyFat().get(), is( 11.0 ) );
      assertThat( second.morningWeighIn().leanMass().get(), is( 12.0 ) );
      assertThat( second.eveningWeighIn().weight().get(), is( 8.0 ) );
      assertThat( second.eveningWeighIn().bodyFat().get(), is( 7.0 ) );
      assertThat( second.eveningWeighIn().leanMass().get(), is( 9.0 ) );
      
      final WeightRecording third = database.weightProgress().records().get( 2 );
      assertThat( third.date(), is( LocalDate.of( 2017, 4, 26 ) ) );
      assertThat( third.morningWeighIn().weight().get(), is( nullValue() ) );
      assertThat( third.morningWeighIn().bodyFat().get(), is( nullValue() ) );
      assertThat( third.morningWeighIn().leanMass().get(), is( nullValue() ) );
      assertThat( third.eveningWeighIn().weight().get(), is( nullValue() ) );
      assertThat( third.eveningWeighIn().bodyFat().get(), is( nullValue() ) );
      assertThat( third.eveningWeighIn().leanMass().get(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldWriteData(){
      Database database = new Database();
      
      final WeightRecording existingWut = database.weightProgress().records().get( 25 );
      existingWut.morningWeighIn().weight().set( 56.0 );
      existingWut.morningWeighIn().bodyFat().set( 57.0 );
      existingWut.morningWeighIn().leanMass().set( 58.0 );
      existingWut.eveningWeighIn().weight().set( 59.0 );
      existingWut.eveningWeighIn().bodyFat().set( 60.0 );
      existingWut.eveningWeighIn().leanMass().set( 61.0 );
      
      WeightRecordingPersistence persistence = new WeightRecordingPersistence( database );
      JSONObject json = new JSONObject();
      persistence.structure().build( json );
      persistence.writeHandles().parse( json );
      
      database = new Database();
      persistence = new WeightRecordingPersistence( database );
      
      persistence.readHandles().parse( json );
      
      final WeightRecording wut = database.weightProgress().records().get( 25 );
      assertThat( wut.date(), is( existingWut.date() ) );
      assertThat( wut.morningWeighIn().weight().get(), is( existingWut.morningWeighIn().weight().get() ) );
      assertThat( wut.morningWeighIn().bodyFat().get(), is( existingWut.morningWeighIn().bodyFat().get() ) );
      assertThat( wut.morningWeighIn().leanMass().get(), is( existingWut.morningWeighIn().leanMass().get() ) );
      assertThat( wut.eveningWeighIn().weight().get(), is( existingWut.eveningWeighIn().weight().get() ) );
      assertThat( wut.eveningWeighIn().bodyFat().get(), is( existingWut.eveningWeighIn().bodyFat().get() ) );
      assertThat( wut.eveningWeighIn().leanMass().get(), is( existingWut.eveningWeighIn().leanMass().get() ) );
      
      final WeightRecording blank = database.weightProgress().records().get( 26 );
      assertThat( blank.morningWeighIn().weight().get(), is( nullValue() ) );
      assertThat( blank.morningWeighIn().bodyFat().get(), is( nullValue() ) );
      assertThat( blank.morningWeighIn().leanMass().get(), is( nullValue() ) );
      assertThat( blank.eveningWeighIn().weight().get(), is( nullValue() ) );
      assertThat( blank.eveningWeighIn().bodyFat().get(), is( nullValue() ) );
      assertThat( blank.eveningWeighIn().leanMass().get(), is( nullValue() ) );
   }//End Method

}//End Class