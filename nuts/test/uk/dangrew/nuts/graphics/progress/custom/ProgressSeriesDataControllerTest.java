package uk.dangrew.nuts.graphics.progress.custom;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.common.UiDateTimeInputDialog;
import uk.dangrew.nuts.graphics.graph.custom.GraphSeriesVisibility;
import uk.dangrew.nuts.progress.custom.ProgressSeries;
import uk.dangrew.nuts.store.Database;

public class ProgressSeriesDataControllerTest {

   private ProgressSeries first;
   private ProgressSeries second;
   
   private Database database;
   private ProgressSeriesTable seriesTable;
   private ProgressSeriesDataTable dataTable;
   
   @Mock private GraphSeriesVisibility graphController;
   @Mock private UiDateTimeInputDialog timestampInput;
   private ProgressSeriesDataController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ProgressSeriesDataController( timestampInput );
      
      dataTable = new ProgressSeriesDataTable( systemUnderTest );
      seriesTable = new ProgressSeriesTable( database = new Database(), graphController );
      systemUnderTest.associate( seriesTable );
      
      first = database.progressSeries().createConcept( "First" );
      second = database.progressSeries().createConcept( "Second" );
      for ( int i = 0; i < 10; i++ ) {
         first.record( LocalDateTime.now().plusDays( i ), i* 10.0 );
         second.record( LocalDateTime.now().plusDays( i ), i* 7.0 );
      }
   }//End Method

   @Test public void shouldRespondToSeriesSelectionAndPopulateRows() {
      seriesTable.getSelectionModel().select( 0 );
      assertThat( dataTable.getRows(), hasSize( 10 ) );
      
      assertDataTableContainsEntriesFor( first );
      
      seriesTable.getSelectionModel().select( 1 );
      assertThat( dataTable.getRows(), hasSize( 10 ) );
      
      assertDataTableContainsEntriesFor( second );
   }//End Method
   
   @Test public void shouldRespondToSeriesChangesAndPopulateRows() {
      seriesTable.getSelectionModel().select( 0 );
      assertThat( dataTable.getRows(), hasSize( 10 ) );
      
      LocalDateTime timestamp = LocalDateTime.now().plusMonths( 12 );
      first.record( timestamp, 45.0 );
      assertDataTableContainsEntriesFor( first );
      
      first.record( timestamp, null );
      assertDataTableContainsEntriesFor( first );
      
      first.record( timestamp, 46.0 );
      assertDataTableContainsEntriesFor( first );
   }//End Method
   
   @Test public void shouldCreateNewEntryWithDateTime() {
      LocalDateTime timestamp = LocalDateTime.now().plusMinutes( 1 );
      when( timestampInput.friendly_showAndWait() ).thenReturn( Optional.of( timestamp ) );
      seriesTable.getSelectionModel().select( 0 );
      
      assertThat( systemUnderTest.createConcept(), is( first ) );
      assertThat( dataTable.getRows(), hasSize( 11 ) );
      assertThat( dataTable.getRows().get( 1 ).timestamp(), is( timestamp ) );
      assertThat( dataTable.getRows().get( 1 ).valueProperty().get(), is( 0.0 ) );
      
      InOrder order = inOrder( timestampInput );
      order.verify( timestampInput ).resetInputToNow();
      order.verify( timestampInput ).friendly_showAndWait();
   }//End Method
   
   @Test public void shouldIgnoreCreateWhenCancelled() {
      when( timestampInput.friendly_showAndWait() ).thenReturn( Optional.empty() );
      seriesTable.getSelectionModel().select( 0 );
      
      assertThat( systemUnderTest.createConcept(), is( nullValue() ) );
      assertThat( dataTable.getRows(), hasSize( 10 ) );
   }//End Method
   
   @Test public void shouldNotCreateEntryWhenNoSelection() {
      LocalDateTime timestamp = LocalDateTime.now().plusMinutes( 1 );
      when( timestampInput.friendly_showAndWait() ).thenReturn( Optional.of( timestamp ) );
      
      assertThat( systemUnderTest.createConcept(), is( nullValue() ) );
      assertThat( dataTable.getRows(), hasSize( 0 ) );
   }//End Method
   
   @Test public void shouldRemoveEntryWithDateTime() {
      seriesTable.getSelectionModel().select( 0 );
      dataTable.getSelectionModel().select( 4 );
      
      systemUnderTest.removeSelectedConcept();
      assertThat( dataTable.getRows(), hasSize( 9 ) );
      assertDataTableContainsEntriesFor( first );
   }//End Method
   
   @Test public void shouldIgnoreRemoveEntryWhenNoSelection() {
      seriesTable.getSelectionModel().select( 0 );
      
      systemUnderTest.removeSelectedConcept();
      assertThat( dataTable.getRows(), hasSize( 10 ) );
      assertDataTableContainsEntriesFor( first );
   }//End Method
   
   @Test public void shouldCopyEntry() {
      LocalDateTime timestamp = new ArrayList<>( first.entries() ).get( 4 );
      seriesTable.getSelectionModel().select( 0 );
      dataTable.getSelectionModel().select( 4 );
      
      systemUnderTest.copySelectedConcept();
      assertThat( dataTable.getRows(), hasSize( 11 ) );
      assertThat( dataTable.getRows().get( 5 ).timestamp(), is( timestamp.plusSeconds( 1 ) ) );
      assertThat( dataTable.getRows().get( 5 ).valueProperty().get(), is( first.entryFor( timestamp ) ) );
   }//End Method
   
   @Test public void shouldCopyEntryAndAvoidClashingTimestamp() {
      LocalDateTime timestamp = new ArrayList<>( first.entries() ).get( 4 );
      seriesTable.getSelectionModel().select( 0 );
      dataTable.getSelectionModel().select( 4 );
      
      systemUnderTest.copySelectedConcept();
      systemUnderTest.copySelectedConcept();
      systemUnderTest.copySelectedConcept();
      assertThat( dataTable.getRows(), hasSize( 13 ) );
      assertThat( dataTable.getRows().get( 7 ).timestamp(), is( timestamp.plusSeconds( 3 ) ) );
      assertThat( dataTable.getRows().get( 7 ).valueProperty().get(), is( first.entryFor( timestamp ) ) );
   }//End Method
   
   @Test public void shouldIgnoreCopyEntryWhenNoSelection() {
      seriesTable.getSelectionModel().select( 0 );
      
      systemUnderTest.copySelectedConcept();
      assertDataTableContainsEntriesFor( first );
   }//End Method
   
   @Test public void shouldRemoveListenersForPreviousSelection(){
      seriesTable.getSelectionModel().select( 0 );
      assertThat( dataTable.getRows(), hasSize( 10 ) );
      seriesTable.getSelectionModel().select( 1 );
      assertThat( dataTable.getRows(), hasSize( 10 ) );
      
      LocalDateTime timestamp = LocalDateTime.now().plusMonths( 12 );
      first.record( timestamp, 45.0 );
      assertDataTableContainsEntriesFor( second );
      
      first.record( timestamp, null );
      assertDataTableContainsEntriesFor( second );
      
      first.record( timestamp, 46.0 );
      assertDataTableContainsEntriesFor( second );
   }//End Method
   
   private void assertDataTableContainsEntriesFor( ProgressSeries series ) {
      List< LocalDateTime > timestamps = new ArrayList<>( series.entries() );
      for ( int i = 0; i < series.entries().size(); i++ ) {
         assertThat( dataTable.getRows().get( i ).timestamp(), is( timestamps.get( i ) ) );
         assertThat( dataTable.getRows().get( i ).valueProperty().get(), is( series.entryFor( timestamps.get( i ) ) ) );
      }
   }//End Method
   
}//End Class
