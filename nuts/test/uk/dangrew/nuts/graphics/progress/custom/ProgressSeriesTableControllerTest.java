package uk.dangrew.nuts.graphics.progress.custom;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.javafx.table.base.ConceptTable;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.graph.custom.GraphSeriesVisibility;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.progress.custom.ProgressSeries;
import uk.dangrew.nuts.store.Database;

public class ProgressSeriesTableControllerTest {

   private Database database;
   private ConceptTable< ProgressSeries > table;
   
   @Mock private GraphSeriesVisibility seriesVisibility;
   private ProgressSeriesTableController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      database = new Database();
      database.progressSeries().createConcept( "Anything" );
      table = new TableComponents< ProgressSeries >()
               .withDatabase( database )
               .withColumns( new ProgressSeriesTableColumns( seriesVisibility ) )
               .withController( new ProgressSeriesTableController( database.progressSeries(), seriesVisibility ) )
               .buildTable();
      systemUnderTest = new ProgressSeriesTableController( database.progressSeries(), seriesVisibility );;
      systemUnderTest.associate( table );
   }//End Method

   @Test public void shouldHideSeriesBeforeRemoving() {
      table.getSelectionModel().select( 0 );
      ProgressSeries removed = database.progressSeries().objectList().get( 0 );
      systemUnderTest.removeSelectedConcept();
      verify( seriesVisibility ).hide( removed );
   }//End Method

}//End Class
