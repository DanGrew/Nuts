package uk.dangrew.nuts.persistence.fooditems;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jupa.json.marshall.ModelMarshaller;
import uk.dangrew.jupa.mockito.NoResponseAnswer;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.persistence.resolution.TestResolutionStrategy;
import uk.dangrew.nuts.store.Database;

public class DatabaseIoTest {

   @Mock private Runnable postProcessor1;
   @Mock private Runnable postProcessor2;
   @Mock private Runnable postProcessor3;
   @Mock private ModelMarshaller marshaller1;
   @Mock private ModelMarshaller marshaller2;
   @Mock private ModelMarshaller marshaller3;
   private Database database;
   private DatabaseIo systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      database = new Database();
      systemUnderTest = new DatabaseIo( database );
   }//End Method

   @Test public void shouldWriteMarshallersInOrder() {
      assertThat( systemUnderTest.withMarshaller( marshaller1 ), is( systemUnderTest ) );
      assertThat( systemUnderTest.withMarshaller( marshaller2 ), is( systemUnderTest ) );
      assertThat( systemUnderTest.withMarshaller( marshaller3 ), is( systemUnderTest ) );
      systemUnderTest.write();
      
      InOrder order = inOrder( marshaller1, marshaller2, marshaller3 );
      order.verify( marshaller1 ).write();
      order.verify( marshaller2 ).write();
      order.verify( marshaller3 ).write();
   }//End Method
   
   @Test public void shouldReadMarshallersInOrder() {
      systemUnderTest.withMarshaller( marshaller1 );
      systemUnderTest.withMarshaller( marshaller2 );
      systemUnderTest.withMarshaller( marshaller3 );
      systemUnderTest.read();
      
      InOrder order = inOrder( marshaller1, marshaller2, marshaller3 );
      order.verify( marshaller1 ).read();
      order.verify( marshaller2 ).read();
      order.verify( marshaller3 ).read();
      assertThat( database.resolver().isFullyResolved(), is( true ) );
   }//End Method
   
   @Test public void shouldReadMarshallersInOrderWithPostProcessors() {
      assertThat( systemUnderTest.withMarshaller( marshaller1, postProcessor1 ), is( systemUnderTest ) );
      assertThat( systemUnderTest.withMarshaller( marshaller2, postProcessor2 ), is( systemUnderTest ) );
      assertThat( systemUnderTest.withMarshaller( marshaller3, postProcessor3 ), is( systemUnderTest ) );
      systemUnderTest.read();
      
      InOrder order = inOrder( 
               marshaller1, marshaller2, marshaller3,
               postProcessor1, postProcessor2, postProcessor3
      );
      order.verify( marshaller1 ).read();
      order.verify( postProcessor1 ).run();
      order.verify( marshaller2 ).read();
      order.verify( postProcessor2 ).run();
      order.verify( marshaller3 ).read();
      order.verify( postProcessor3 ).run();
      assertThat( database.resolver().isFullyResolved(), is( true ) );
   }//End Method
   
   @Test public void shouldResolveDatabase(){
      doAnswer( new NoResponseAnswer<>( () -> database.resolver().submitStrategy( new TestResolutionStrategy() ) ) )
         .when( marshaller1 ).read();
      
      systemUnderTest.withMarshaller( marshaller1 );
      systemUnderTest.read();
      assertThat( database.resolver().isFullyResolved(), is( true ) );
   }//End Method
   
   @Test public void shouldAccountForFileProtocol(){
      systemUnderTest
         .withMarshaller( marshaller1, FileIoProtocol.ReadOnly )
         .withMarshaller( marshaller2, FileIoProtocol.WriteOnly );
      
      systemUnderTest.read();
      verify( marshaller1 ).read();
      verify( marshaller2, never() ).read();
      
      systemUnderTest.write();
      verify( marshaller1, never() ).write();
      verify( marshaller2 ).write();
   }//End Method

}//End Class
